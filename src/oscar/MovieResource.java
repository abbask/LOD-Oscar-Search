package oscar;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/movie")
public class MovieResource {
	
	@GET
	@Path ("/{id}/year/{yearId}")
	@Produces (javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public HashMap<String, Object> restoreMovieInformation(@PathParam("id") String id, @PathParam("yearId") String yearId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
    		MyDBPediaConnection c = new MyDBPediaConnection();  
    		result.put( "data" , c.restoreMovieInformation(id, yearId));
    				
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    	}
		
		return result;
		
	}

	@GET
	@Path ("/{id}/year/{yearId}/person")
	@Produces (javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public HashMap<String, Object> restoreMovieCasts(@PathParam("id") String id,@PathParam("yearId") String yearId) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		try {
    		MyDBPediaConnection c = new MyDBPediaConnection();  
    		result.put( "data" , c.restoreMovieAllCasts(id, yearId));
    				
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    	}
		
		return result;
		
	}
}
