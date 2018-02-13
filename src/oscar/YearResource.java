package oscar;

import java.util.HashMap;
import javax.ws.rs.*;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

@Path("/year")
public class YearResource {
	
	@GET
	@Path ("/")
	@Produces (javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public HashMap<String, Object> restoreAllYear() {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
    		MyLocalConnection c = new MyLocalConnection();  
    		result.put( "data" , c.restoreAllYear());
    				
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
	public HashMap<String, Object> restoreYearNominees(@PathParam("id") String id, @FormParam("Nominees") int newRate) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
    		MyLocalConnection c = new MyLocalConnection();  
    		result.put( "data" , c.restoreYearallNominees(id));
    				
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    	}
		
		return result;
		
	}
	
	@GET
	@Path ("{id}/award/{awardId}/nominees")
	@Produces (javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public HashMap<String, Object> restoreYearAwardNominees(@PathParam("id") String id,@PathParam("awardId") String awardId ) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
    		MyLocalConnection c = new MyLocalConnection();  
    		result.put( "data" , c.restoreYearCategoryNominees(id, awardId));
    				
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    	}
		
		return result;
		
	}
}
