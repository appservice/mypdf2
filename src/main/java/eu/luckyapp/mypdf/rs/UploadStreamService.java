package eu.luckyapp.mypdf.rs;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
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
import eu.luckyapp.mypdf.model.Order;
import eu.luckyapp.mypdf.model.TableView;
import eu.luckyapp.parsers.OrderParser;
import eu.luckyapp.parsers.ParserException;

@Path("/file")
@Stateless
public class UploadStreamService {

	public static final Logger LOG = Logger.getLogger(UploadStreamService.class
			.getName());

	//@Inject
	//EntityManager em;
	
	@Inject
	OrderDAO orderDAO;

	@Inject
	OrderParser op;
	
	@Context
	UriInfo uriInfo;

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@RolesAllowed(value="ADMIN")
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(@MultipartForm UploadedFileMultiForm form)  {


		try {
			LOG.info(form.getOrderReference());
			String orderReference=form.getOrderReference();
						
			
			String output=new String(form.getFileData(),"UTF-8");

	
			//LOG.info(output);
         Order order=addOrderToDb(output,orderReference);
         return Response.accepted(order).build();
			//List<TableView> addedItems=addToDB(output,orderReference);
		//	if(addedItems.size()>0){
			
		//		GenericEntity<List<TableView>> urisWrapper=new GenericEntity<List<TableView>>(addedItems){};
		//		return Response.status(201).entity(urisWrapper).build();
		//	}
		//	return Response.serverError().entity("Incorrect type of file uploaded!").build();

		} catch (ParserException | UnsupportedEncodingException e) { 
			e.printStackTrace();
			return Response.serverError().entity(e.toString()).build();
		}

	}

	// ---------------------------------------------------------------------------------------------
	private List<TableView> addToDB(String text,String orderReference) throws ParserException {
	/*	List<TableView> tableViewList = op.parseToTableView(text);
		
	//	List<URI> uris = new ArrayList<>();

		for (TableView entity : tableViewList) {
			//LOG.log(Level.WARNING,orderReference);
			entity.setOrderReference(orderReference);
			em.persist(entity);


		}*/
		return null;
	}
	
	private Order addOrderToDb(String textToParse,String orderReference) throws ParserException{
		Order order= op.parseToOrder(textToParse);
		order.setOrderReference(orderReference);
		LOG.info(order.toString());
		// em.persist(order);
		orderDAO.create(order);
		 return order;
		
	}
}
