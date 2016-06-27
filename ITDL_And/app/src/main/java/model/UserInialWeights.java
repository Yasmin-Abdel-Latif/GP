package model;

public class UserInialWeights {

	
	private double inialWeight;
	private String categoryID;
	private String categoryName;
	private String userID;
	private String categoryRecordID;
	public void incrementTest()
	{
		inialWeight+=0;
	}
	public String getCategoryRecordID() {
		return categoryRecordID;
	}
	public void setCategoryRecordID(String categoryRecordID) {
		this.categoryRecordID = categoryRecordID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public UserInialWeights() {
		super();
	}
	public UserInialWeights( String categoryID, double inialWeight) {
		super();
		this.inialWeight = inialWeight;
		this.categoryID = categoryID;
		this.categoryName = "";
	}
	public double getInialWeight() {
		String num1Str = String.format("%.5g%n", inialWeight);

		double num1 = Double.parseDouble(num1Str);

		return num1;
	}
	public void setInialWeight(double inialWeight) {
		this.inialWeight = inialWeight;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "UserInialWeights [inialWeight=" + inialWeight + ", categoryID=" + categoryID + ", categoryName="
				+ categoryName + ", userID=" + userID + ", categoryRecordID=" + categoryRecordID + "]";
	}
	
	
	 
}
