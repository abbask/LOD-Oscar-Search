package oscar;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("/person")
public class PersonResource {
	
	@GET
	@Path ("/{id}")
	@Produces (javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public HashMap<String, Object> restorePersonInformation(@PathParam("id") String id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
    		MyDBPediaConnection c = new MyDBPediaConnection();  
    		result.put( "data" , c.restorePersonAllInformation(id));
    				
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    	}
		
		return result;
	}
	
	@GET
	@Path ("/{id}/movies")
	@Produces (javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public HashMap<String, Object> restorePersonMovies(@PathParam("id") String id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
    		MyDBPediaConnection c = new MyDBPediaConnection();  
    		result.put( "data" , c.restorePersonAllMovies(id));
    				
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    	}
		
		return result;
	}
}
