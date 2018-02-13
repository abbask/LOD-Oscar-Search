package oscar.model;

public class Movie {
	
	private String movieName;
	private String roleName;
	private String personName;
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public Movie() {
		// TODO Auto-generated constructor stub
	}
	public Movie(String movieName, String roleName, String personName) {
		super();
		this.movieName = movieName;
		this.roleName = roleName;
		this.personName = personName;
	}
	

}
