package model;

public class NearestStore {

	
	private String storeName;
	private String userProductToBuy;
	private double lat;
	private double longt;
	private String category;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public NearestStore() {
		super();
	}
	public NearestStore(String storeName, String userProductToBuy, double lat, double longt) {
		super();
		this.storeName = storeName;
		this.userProductToBuy = userProductToBuy;
		this.lat = lat;
		this.longt = longt;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getUserProductToBuy() {
		return userProductToBuy;
	}
	public void setUserProductToBuy(String userProductToBuy) {
		this.userProductToBuy = userProductToBuy;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLongt() {
		return longt;
	}
	public void setLongt(double longt) {
		this.longt = longt;
	}
	@Override
	public String toString() {
		return "NearestStore [storeName=" + storeName + ", userProductToBuy=" + userProductToBuy + ", lat=" + lat
				+ ", longt=" + longt + "]";
	}
	
	
}
