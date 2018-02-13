package oscar;

import java.util.HashMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/award")
public class AwardResource {
	
	@GET
	@Path ("/")
	@Produces (javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public HashMap<String, Object> restoreAllCategories() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
    		MyLocalConnection c = new MyLocalConnection();  
    		result.put( "data" , c.restoreAllCategory());
    				
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    	}
		
		return result;
		
	}

	@GET
	@Path ("{id}/nominees")
	@Produces (javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public HashMap<String, Object> restoreCategoriesNominees(@PathParam("id") String id) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
    		MyLocalConnection c = new MyLocalConnection();  
    		result.put( "data" , c.restoreCategoryNominees(id));
    				
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    	}
		
		return result;
		
	}
}
