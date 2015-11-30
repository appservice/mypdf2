package eu.luckyapp.mypdf.rs;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import eu.luckyapp.mypdf.dao.OrderDAO;
import eu.luckyapp.mypdf.exceptions.OrderExistException;
import eu.luckyapp.mypdf.model.Order;
import eu.luckyapp.mypdf.model.TableView;
import eu.luckyapp.parsers.OrderParser;
import eu.luckyapp.parsers.ParserException;

@Path("/file")
@Stateless
public class UploadStreamService {

	public static final Logger LOG = Logger.getLogger(UploadStreamService.class.getName());

	// @Inject
	// EntityManager em;
	@Inject
	Logger Log;

	@Inject
	OrderDAO orderDAO;

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
		
		
			String orderReference =new String(form.getOrderReference(),"UTF-8");
			LOG.info(orderReference);
	

			String output = new String(form.getFileData(), "UTF-8");

		
			Order savedOrder = addOrderToDb(parseOrder(output, orderReference));
			return Response.accepted(savedOrder).build();

		} catch ( OrderExistException| ParserException | UnsupportedEncodingException e) {
		
			return Response.serverError().header("error", e.getMessage())
					.build();
		}
		catch ( Exception e) {
			
			return Response.serverError().header("error", e.getMessage())
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

		if (orderDAO.findByNumberStrict(order.getNumber()) != null) {
			Log.info("order exist!");
			throw new OrderExistException(order.getNumber());

		} else {

			orderDAO.create(order);
			return order;
		}

	}
}
