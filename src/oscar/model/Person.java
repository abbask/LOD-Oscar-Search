package oscar.model;

public class Person {
	
	private String personId;
	private String personFullName;
	
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getPersonFullName() {
		return personFullName;
	}
	public void setPersonFullName(String personFullName) {
		this.personFullName = personFullName;
	}
	
	public Person() {
		// TODO Auto-generated constructor stub
	}
	public Person(String personId, String personFullName) {
		super();
		this.personId = personId;
		this.personFullName = personFullName;
	}
	
	
	
}
