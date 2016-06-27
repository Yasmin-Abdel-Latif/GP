package model;

import java.util.Vector;

public class Store {

    private String name;
    private String email;
    private String password;
    private String address;
    private double lat;
    private double lon;


    private Vector<String> storeCategory;

    public Store() {
        storeCategory = new Vector<String>();

    }

    public Vector<String> getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(Vector<String> allstoreCategory) {
        storeCategory = new Vector<String>();

        for (int i = 0; i < allstoreCategory.size(); i++) {
            System.out.println(allstoreCategory.get(i).trim().toLowerCase());
            if (allstoreCategory.get(i).trim().toLowerCase().equals("")
                    || allstoreCategory.get(i).trim().toLowerCase().equals("nocategory")) {
            } else
                storeCategory.add(allstoreCategory.get(i).trim().toLowerCase());
        }
        System.out.println("LLLL  =  " + storeCategory.size());

    }

    public void addcategoryToStore(String c) {
        storeCategory.add(c);
    }

    public boolean isCategoryFound(String c) {
        // System.out.println("HHHHHH "+storeCategory.toString()+" c = "+c);
        for (int i = 0; i < storeCategory.size(); i++) {
            if (storeCategory.get(i).trim().toLowerCase().equals(c.toLowerCase().trim())) {
                return true;
            }
        }
        return false;
    }

    public int getStoreVecSize() {
        return storeCategory.size();
    }

    public Vector<String> getStoreAllCategories() {
        return storeCategory;
    }

    public void printStoreAllCategories() {
        for (int i = 0; i < storeCategory.size(); i++) {
            System.out.println(storeCategory.get(i));
        }
    }

    public Store(String name, String email, String password, String address, double lat, double lon) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        storeCategory = new Vector<String>();
    }

    public Store(String email, String password) {
        this.email = email;
        this.password = password;
        storeCategory = new Vector<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        String str = "StoreEmail = " + email + "\nStoreCategory = " + storeCategory.toString() + "\nstoreSize = "
                + getStoreVecSize();

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return str;
        // return "Store [name=" + name + ", email=" + email + ", password=" +
        // password + ", address=" + address + ", lat="
        // + lat + ", lon=" + lon + ", storeCategory=" + storeCategory + "]";
    }
}