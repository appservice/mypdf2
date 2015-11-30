package eu.luckyapp.mypdf.rs;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.adobe.xmp.impl.Base64;

import eu.luckyapp.mypdf.dao.OrderDAO;
import eu.luckyapp.mypdf.model.Order;
import eu.luckyapp.mypdf.model.TableView;
import eu.luckyapp.mypdf.model.TableViewToOrderParser;

@Path("/copy")
@Stateless
public class CopyOldDataEndpoint {

	@Inject
	private Logger Log;
	
	@Inject OrderDAO orderDAO;

	List<TableView> tableList;

	private static final String BASEURL = "http://10.11.1.2:8080/mypdf/resources/tableviews";

	@GET
	//@Produces(MediaType.APPLICATION_JSON)
	public Response copyData() {

		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target(BASEURL);
		String authorizationCredentials = Base64.encode("user1:start123");

		Response r = target.request().accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic " + authorizationCredentials).get();
		Log.info(r.getStringHeaders().toString());
		Log.info(String.valueOf(r.getStatus()));

		try {
			Log.info(r.getStatusInfo().toString());
			if (r.getStatus() == 200) {
				tableList = r.readEntity(new GenericType<List<TableView>>() {
				});


			}

		} finally {
			r.close();
			Log.info("response closed");
		}
		
		
		Log.info("readed table list");
		Log.info("table list size " + tableList.size());

		TableViewToOrderParser parser=new TableViewToOrderParser();
		Set<Order>ordersList=parser.parseOrdersList(tableList);
		for(Order order:ordersList){
			orderDAO.create(order);
		}
	
		return Response.accepted("Dodano do bazy "+ordersList.size()+ " pozycji\nBaza zawiera obecnie "+orderDAO.count()+" zamowien!").build();
	}
	
	
	
}
