package oscar.model;


public class Year {
	
	private String id;
	private String number;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYear() {
		return number;
	}
	public void setYear(String year) {
		this.number = year;
	}
	
	public Year() {
		// TODO Auto-generated constructor stub
	}
	public Year(String id, String number) {
		
		this.id = id;
		this.number = number;
	}

}
