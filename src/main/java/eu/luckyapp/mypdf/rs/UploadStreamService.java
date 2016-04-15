package eu.luckyapp.mypdf.rs;

import eu.luckyapp.mypdf.dao.OrderDAO;
import eu.luckyapp.mypdf.dao.OrderRepository;
import eu.luckyapp.mypdf.exceptions.OrderExistException;
import eu.luckyapp.mypdf.model.Order;
import eu.luckyapp.parsers.OrderParser;
import eu.luckyapp.parsers.ParserException;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.io.UnsupportedEncodingException;

@Path("/file")
@Stateless
public class UploadStreamService {

	//public static final Logger LOG = Logger.getLogger(UploadStreamService.class.getName());

	// @Inject
	// EntityManager em;
	@Inject
	Logger Log;

	@Inject
	OrderRepository orderRepository;
	//OrderDAO orderDAO;

	@Inject
	OrderParser op;

	@Context
	UriInfo uriInfo;

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @RolesAllowed(value="ADMIN")
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(@MultipartForm UploadedFileMultiForm form) {

		
		
		try {
		
		
			String orderReference =new String(form.getOrderReference(),"UTF-8")/*.replaceAll("(\\r\\n|\\n)", "<br>")*/;
			Log.info(orderReference);
	

			String output = new String(form.getFileData(), "UTF-8");

		
			Order savedOrder = addOrderToDb(parseOrder(output, orderReference));
			
		//	Log.info(savedOrder.toString());
			return Response.accepted(savedOrder).build();

		} catch ( OrderExistException| ParserException | UnsupportedEncodingException e) {
		
			return Response.serverError().entity("{\"error\":\""+e.getMessage()+"\"}").header("error", e.getMessage())

					.build();
		}
		catch ( Exception e) {
			
			return Response.serverError().entity("{\"error\":\""+e.getMessage()+"\"}").header("error", e.getMessage())
					.build();
		}

	}

	// ---------------------------------------------------------------------------------------------

	private Order parseOrder(String textToParse, String orderReference) throws ParserException {
		Order o = op.parseToOrder(textToParse);
		o.setOrderReference(orderReference);
		return o;
	}

	private Order addOrderToDb(Order order) throws OrderExistException {

		if (orderRepository.findOrderByNumber(order.getNumber()) != null) {
			Log.info("order exist exception!");
			throw new OrderExistException(order.getNumber());

		} else {

			Order o=orderRepository.save(order);//.create(order);
		//	Log.info(order.toString());
			//Log.info(order.getItems().toString());
			return o;
		}

	}
}
