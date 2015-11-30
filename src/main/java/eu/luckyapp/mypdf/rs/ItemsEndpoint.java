package eu.luckyapp.mypdf.rs;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eu.luckyapp.mypdf.dao.ItemDAO;
import eu.luckyapp.mypdf.model.Item;

@Path("/items")
@Produces(value = { MediaType.APPLICATION_JSON })
@Consumes(MediaType.APPLICATION_JSON)
public class ItemsEndpoint {
	
	@Inject
	Logger Log;

	@Inject
	ItemDAO itemDAO;

	@GET
	public List<Item> getAll(@PathParam("orderId")Long orderId){
		
		
		return itemDAO.findAllByOrder(orderId);
		//return itemDAO.findAll();

	}
	
	@GET
	@Path("/{itemId}")
	public Item getItem(@PathParam("itemId")Long itemId){
		return itemDAO.find(itemId);
	}
	
	
	@PUT
	@Path("/{itemId}")
	public Item setItem(@PathParam("orderId")Long orderId,@PathParam("itemId")Long itemId,Item item){
		item.setId(itemId);
		//item.setOrder(myOrder);
		Log.info(item.toString());
		return itemDAO.edit(item);
	}
	
	

}
