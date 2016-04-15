package eu.luckyapp.mypdf.exceptions;

public class OrderExistException extends Exception {

	
	private static final long serialVersionUID = -5044374777840839442L;
private String orderNumber;

	@Override
	public String getMessage() {
		return String.format("Zamówienie o numerze %s istnieje już w bazie danych!", orderNumber); //super.getMessage();
	}

	
	public OrderExistException(String orderNumber){
		this.orderNumber=orderNumber;
	}
}
