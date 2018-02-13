package oscar.model;

public class PersonHistory {
	
	private String role;
	private String filmTitle;
	private String year;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFilmTitle() {
		return filmTitle;
	}
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public PersonHistory() {
		// TODO Auto-generated constructor stub
	}
	public PersonHistory(String role, String filmTitle, String year) {
		super();
		this.role = role;
		this.filmTitle = filmTitle;
		this.year = year;
	}
	
	

}
