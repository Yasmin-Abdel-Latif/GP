package model;

public class Offer  {

	private String offerID;
	private String category;
	private String content;
	private String startDate;
	private String endDate;
	private String storeID;
	private double storeLat;
	private double storeLong;
	private String jsonStoreEmail;
	private String storeName;
	private String storeAddress;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}


	
	public boolean isStoreEmailMatched(String givenStoreEmail)
	{
		return jsonStoreEmail.equals(givenStoreEmail);
	}
//
//	private String categoryName;
//
//	public String getCategoryName() {
//		return categoryName;
//	}
//
//	public void setCategoryName(String categoryName) {
//		this.categoryName = categoryName;
//	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Offer() {
		super();
	}

	public double getStoreLat() {
		return storeLat;
	}

	public void setStoreLat(double storeLat) {
		this.storeLat = storeLat;
	}

	public double getStoreLong() {
		return storeLong;
	}

	public void setStoreLong(double storeLong) {
		this.storeLong = storeLong;
	}

	public String getJsonStoreEmail() {
		return jsonStoreEmail;
	}

	public void setJsonStoreEmail(String jsonStoreEmail) {
		this.jsonStoreEmail = jsonStoreEmail;
	}

	public Offer(String offerID, String categoryID, String content, String startDate, String endDate) {
		this.offerID = offerID;
		this.category = categoryID;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Offer(String storeID, String offerID, String categoryID, String content, String startDate, String endDate) {
		this.storeID = storeID;
		this.offerID = offerID;
		this.category = categoryID;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Offer(String categoryID, String content, String startDate, String endDate) {
		this.category = categoryID;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getStoreID() {
		return storeID;
	}

	public void setStoreID(String storeID) {
		this.storeID = storeID;
	}

	public String getOfferID() {
		return offerID;
	}

	public void setOfferID(String offerID) {
		this.offerID = offerID;
	}

	public String getCategoryID() {
		return category;
	}

	public void setCategoryID(String categoryID) {
		this.category = categoryID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Offer [offerID=" + offerID + ", category=" + category + ", content=" + content + ", startDate="
				+ startDate + ", endDate=" + endDate + ", storeID=" + storeID + ", storeLat=" + storeLat
				+ ", storeLong=" + storeLong + ", jsonStoreEmail=" + jsonStoreEmail + "]";
	}

	
}
