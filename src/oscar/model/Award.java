package oscar.model;

public class Award {
	
	private String awardId;
	private String awardLabel;
	public String getAwardId() {
		return awardId;
	}
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	public String getAwardLabel() {
		return awardLabel;
	}
	public void setAwardLabel(String awardLabel) {
		this.awardLabel = awardLabel;
	}
	
	public Award() {
		// TODO Auto-generated constructor stub
	}
	public Award(String awardId, String awardLabel) {
		super();
		this.awardId = awardId;
		this.awardLabel = awardLabel;
	}
	
	

}
