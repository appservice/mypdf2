package eu.luckyapp.parsers;

import eu.luckyapp.mypdf.model.Order;



public interface OrderParser 
{ 
	/**
	 * 
	 * @param textToParse as String
	 * @return List of parsed table of String
	 * @throws ParserException 
	 */
	/*public List<String[]> parse(String textToParse) throws ParserException ;*/
	
	/**
	 * 
	 * @param textToParse as String
	 * @return TableView  {@link eu.luckyapp.mypdf.model.TableView}
	 * @throws ParserException
	 */
/*	public List<TableView> parseToTableView(String textToParse) throws ParserException;*/
	
	
	public Order parseToOrder(String textToParse) throws ParserException;
}