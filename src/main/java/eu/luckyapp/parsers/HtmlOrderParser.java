package eu.luckyapp.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import eu.luckyapp.mypdf.model.Item;
import eu.luckyapp.mypdf.model.Order;

public class HtmlOrderParser implements OrderParser {
	private static final Logger LOG = Logger.getLogger(HtmlOrderParser.class.getName());
	private static final String START_ORDER_TAG = "Raport ZZAM5 Can-Pack S.A.";
	private static final String[] FIRST_TABLE_TAG = { "C.Zysku", "Rezerwacja /poz.", "Data dost." };
	private static final String END_TABLE_TAG = "Netto";
	private String[] orderHeaderDatas;
	private String[] orderItemDatas;
	private List<String[]>itemsDataList=new ArrayList<>();


	public void parse(String html) throws ParserException {
		try {
			// ----------remove &nbsp; and double spaces
			String clearHtml = html.replace("&nbsp;", " ").replace("  ", " ");

			Document doc = Jsoup.parse(clearHtml);

			Elements elements = doc.getElementsByTag("nobr");

			LOG.info(elements.toString());

			int htmlTagNumber = 0;
			int itemTagNumber = 0;

			String[] tableHeader = new String[3];
			boolean isTable = false;
			orderHeaderDatas = null;
			orderItemDatas = null;
		

			for (Element e : elements) {

				if (e.ownText().startsWith(START_ORDER_TAG)) {
					htmlTagNumber = 0; // set 0 if next order is beginning
					orderHeaderDatas = new String[5];
				}

				// LOG.info(htmlTagNumber + " :" + itemTagNumber + " " +
				// e.ownText());

				switch (htmlTagNumber) {
				case 5:
					orderHeaderDatas[0] = e.ownText().replace("Zamówienie : ", "");// number
																					// of
																					// order
					break;
				case 13:
					orderHeaderDatas[1] = e.ownText().replace("Data : ", "");// date
																				// of
																				// order
					break;
				case 19:
					orderHeaderDatas[2] = e.ownText();// supplier name
					break;
				case 21:
					orderHeaderDatas[3] = e.ownText().replace("Dział zaopatrzenia : ", ""); // dział
																							// zaopatrzenia
					break;
				case 35:
					orderHeaderDatas[4] = e.ownText();// ordering person
					break;
				}
				// LOG.info(orderHeaderDatas[0]);
				/*
				 * for(String test:orderHeaderDatas){ System.out.println(test);
				 * }
				 */ // --------table to check the first number of table
					// order--------//
				if (htmlTagNumber < 3) {
					tableHeader[htmlTagNumber] = e.ownText();
				} else {
					tableHeader[0] = tableHeader[1];
					tableHeader[1] = tableHeader[2];
					tableHeader[2] = e.ownText();

				}

				if (Arrays.equals(tableHeader, (FIRST_TABLE_TAG))) {
					isTable = true;

				}

				htmlTagNumber++;

				// htmlTagNumber++;
				if (isTable) {

					if (itemTagNumber > 15) {
						itemTagNumber = 1;
					}

					System.out.println(itemTagNumber + " " + e.ownText());
					switch (itemTagNumber) {
					case 1:
						orderItemDatas = new String[10];
				

						if (!e.ownText().contains("0")) {
							isTable = false;
							itemTagNumber = 0;
						}

						orderItemDatas[0] = e.ownText();// pos on order
						break;
					case 2:
						// System.out.println(e.ownText());
						orderItemDatas[1] = e.ownText();// index
						break;
					case 3:
						orderItemDatas[2] = e.ownText(); // item name
						break;
					case 4:
						String[] innerDataAmount = e.ownText().split(" ");
						System.out.println("case 5 " + e.ownText());
						orderItemDatas[3] = innerDataAmount[0];// amount
						orderItemDatas[4] = innerDataAmount[1]; // unit
						break;
					case 5:
						String[] innerDataValue = e.ownText().replace(".", "").split(" ");
						orderItemDatas[5] = innerDataValue[0];// value
						orderItemDatas[6] = innerDataValue[1]; // currency
						// System.out.println(orderItemDatas[11]);
						break;
					case 11:
						orderItemDatas[7] = e.ownText();// MPK
						break;
					case 12:
						orderItemDatas[8] = e.ownText(); // Budget

						break;
					case 15:
						System.out.println(e.ownText());
						orderItemDatas[9] = e.ownText();

						itemsDataList.add(orderItemDatas);		// list

						break;
					}
					if (e.ownText().contains(END_TABLE_TAG)) {
						isTable = false;
						itemTagNumber = 0;
					}

					itemTagNumber++;

				}
				// return parsedDataList;
			}
		} catch (Exception e) {
			LOG.log(Level.WARNING, "Date Parse error");
			throw new ParserException("ParserException ", e);

		}

		//LOG.log(Level.WARNING, itemsDataList.toString());
	//	return parsedDataList;

	}

	
	

	private Date parseOrderDate(String dateToParse) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

		return format.parse(dateToParse);
	}

	@Override
	public Order parseToOrder(String html) throws ParserException {
		
		parse(html);
		
	    Order order=new Order();
		
	    order.setNumber(this.orderHeaderDatas[0]);
	    try {
			order.setDate(parseOrderDate(this.orderHeaderDatas[1]));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ParserException("Date of order parse exception!",e);
		}
		
	    order.setSupplier(this.orderHeaderDatas[2]);
		order.setSuppliesGroup(this.orderHeaderDatas[3]);
		order.setPurchaser(this.orderHeaderDatas[4]);
		
		
		List<Item> items=new ArrayList<>();
		for(String[] itemData:itemsDataList){
			
			Item item=new Item();
			item.setPositionInOrder(Integer.parseInt(itemData[0]));
			item.setIndex(itemData[1]);
			item.setName(itemData[2]);
			item.setAmount(Double.valueOf(itemData[3].replace(",", ".")));
			item.setUnit(itemData[4]);
			item.setPrice(Double.valueOf(itemData[5].replace(",", ".")));
			item.setCurrency(itemData[6]);
			item.setMpk(itemData[7]);
			item.setBudget(itemData[8]);
			
			try {
				item.setExpectedDeliveryDate(parseOrderDate(itemData[9]));
			} catch (ParseException e) {
				
				e.printStackTrace();
				throw new ParserException("Expected delivery date parse on item parse exception!",e);
			}
			
			
			
			items.add(item);
		}
		LOG.warning(items.toString());
	     order.setItems(items);
	     LOG.warning(order.toString());

		return order;
	}
}