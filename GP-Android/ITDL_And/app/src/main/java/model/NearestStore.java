package model;

import java.util.Vector;

public class NearestStore {


    private String storeAddress;

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    private String storeEmail;

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    private String storeName;
    private String userProductToBuy;
    Vector<String> storeCategories = new Vector<String>();
    Vector<String> listOfUserShoppingsNote = new Vector<String>();
    private String category;

    public String getCategory() {
        return category;
    }

    public int getUserShoppingNotesSize() {
        return listOfUserShoppingsNote.size();
    }

    public void setStoreCategories(Vector<String> categories) {
        storeCategories = new Vector<String>();
        for (int i = 0; i < categories.size(); i++) {
            storeCategories.add(categories.get(i));
        }
    }

    public Vector<String> getStoreCategories() {
        return storeCategories;
    }

    public void setListOfUserShoppingNotes(Vector<String> shoppingNotes) {
        listOfUserShoppingsNote = new Vector<String>();
        for (int i = 0; i < shoppingNotes.size(); i++) {
            listOfUserShoppingsNote.add(shoppingNotes.get(i));
        }
    }

    public Vector<String> getListOfUserShoppingsNote() {
        return listOfUserShoppingsNote;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private double lat;
    private double longt;


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

    public String toString() {
        String result = "Store Name    :" + storeName + "\n";
        result += "Store Address :" + storeAddress + "\n";
        result += "Store Email   :" + storeEmail + "\n";
        result += "Some shopping notes you can made\n";
        String allNotes = "";
        for (int i = 0; i < listOfUserShoppingsNote.size(); i++) {

            allNotes += (i + 1) + "- " + listOfUserShoppingsNote.get(i) + "\n";
        }
        result += allNotes;

        String storeCategoroiess = "";
        result += "Store products categories:\n";
        for (int i = 0; i < storeCategories.size(); i++) {

            storeCategoroiess += (i + 1) + "- " + storeCategories.get(i) + "\n";
        }
        result += storeCategoroiess;

//		System.out.println("Store name: "+storeName);
//		System.out.println("Strore address: "+storeAddress);
//		System.out.println("Store email : "+storeEmail);
//		System.out.println("langt : "+longt);
//		System.out.println("lat: "+lat);
//		System.out.println("User Shopping notes : "+listOfUserShoppingsNote.toString());
//		System.out.println("Notes size: "+listOfUserShoppingsNote.size());
//		System.out.println("Store list of caategories: "+storeCategories.toString());
//		System.out.println("store Cat Size: "+storeCategories.size());
//		System.out.println("____________________________________________________________________________________");
        return result;
    }


}