package oscar;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;

@Path("/remote")
public class RemoteResource {
	
	@GET
	@Path ("/")
	@Produces (javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public HashMap<String, Object> restoreRemote() {
		HashMap<String, Object> result = new HashMap<String, Object>();
    	try {
    		MyLocalConnection c = new MyLocalConnection();  
    		result.put( "tests" , c.restoreRemote());
    				
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		
    	}
    	return result;
	}

}
