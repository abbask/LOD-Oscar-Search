package oscar;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/test")
public class TestResource {
	
	 @GET
		@Path ("/")
		@Produces (javax.ws.rs.core.MediaType.APPLICATION_JSON)
		public HashMap<String, Object> restoreTestsAll() {
	    	HashMap<String, Object> result = new HashMap<String, Object>();
	    	try {
	    		MyLocalConnection c = new MyLocalConnection();  
	    		result.put( "tests" , c.restoreTest());
	    				
	    	}
	    	catch(Exception e) {
	    		System.out.println(e.getMessage());
	    		e.printStackTrace();
	    		
	    	}
	    	return result; 
	    	
		}
	

}
