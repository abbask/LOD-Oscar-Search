package oscar.model;

import java.util.List;

public class Nominee {
	
	private String awardId;
	private String award;
	private String movieId;
	private String movie;
	private List<Person> persons;
	private Year year; 
	private Boolean won;
	
	public Boolean getWon() {
		return won;
	}
	public void setWon(Boolean won) {
		this.won = won;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public String getAwardId() {
		return awardId;
	}
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getMovie() {
		return movie;
	}
	public void setMovie(String movie) {
		this.movie = movie;
	}
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	public Nominee() {
		// TODO Auto-generated constructor stub
	}
	public Nominee(String awardId, String award, String movieId, String movie,
			List<Person> persons, Year year, Boolean won) {
		super();
		this.awardId = awardId;
		this.award = award;
		this.movieId = movieId;
		this.movie = movie;
		this.persons = persons;
		this.year = year;
		this.won = won;
	}
	
	
	
	
	
 
	

}
