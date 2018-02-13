package oscar;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import oscar.TestResource;

public class OscarApplication extends Application {
	 private Set<Object> singletons = new HashSet<Object>();
	    private Set<Class<?>> empty = new HashSet<Class<?>>();

	    public OscarApplication() {
	        singletons.add(new TestResource());
	        singletons.add(new RemoteResource());
	        singletons.add(new AwardResource());
	        singletons.add(new YearResource());
	        singletons.add(new PersonResource());
	        singletons.add(new MovieResource());
	            }

	    @Override
		public Set<Class<?>> getClasses() {
	        return empty;
	    }

	    @Override
		public Set<Object> getSingletons() {
	        return singletons;
	    }

}
