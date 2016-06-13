package com.fci.itdl.model;

import java.util.ArrayList;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;

public class Store {
	
	private String name;
	private String email;
	private String password;
	private String address;
	private double lat;
	private double lon;
	
	public Store(String name, String email, String password, String address, double lat, double lon)
	{
		this.email = email;
		this.name = name;
		this.password = password;
		this.address = address;
		this.lat = lat;
		this.lon = lon;
	}
	
	public Store(String email, String password)
	{
		this.email = email;
		this.password = password;
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
	
	public boolean updateStore(String name, String email, String password)
	{
		ArrayList<Offer> activeOffers = getActiveStoreOffers(this.getEmail());
		for(int i = 0 ; i < activeOffers.size() ; i++)
		{
			Offer offer = activeOffers.get(i);
			Offer.updateOffer(offer, email);
		}

		ArrayList<Offer> inActiveOffers = getInActiveStoreOffers(this.getEmail());
		for(int i = 0 ; i < inActiveOffers.size() ; i++)
		{
			Offer offer = inActiveOffers.get(i);
			Offer.updateInActiveOffer(offer, email);
		}
		deleteStore(this.getEmail());
		
		Store temp = new Store(name, email, password, this.address, this.lat, this.lon);
		temp.saveStore();
		
		this.email = email;
		this.name = name;
		this.password = password;
		return true;
	}
	
	public static boolean deleteStore(String storeEmail)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Store");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("email").toString().equals(storeEmail)) 
			{
				Key keyEntity = entity.getKey();
				datastore.delete(keyEntity);
				return true;
			}
		}
		return false;
	}
	
	public static Store getStore(String storeEmail)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("Store");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("email").toString().equals(storeEmail)) 
			{
				Store returnedStore = new Store(entity.getProperty("name").toString(), entity.getProperty("email").toString(), entity.getProperty("password").toString(), entity.getProperty("address").toString(), Double.parseDouble(entity.getProperty("latitude").toString()), Double.parseDouble(entity.getProperty("longitude").toString()));
				return returnedStore;
			}
		}
		return null;
	}
	
	public static Store getStore(String storeEmail, String storePass)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("Store");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("email").toString().equals(storeEmail) && entity.getProperty("password").toString().equals(storePass)) 
			{
				Store returnedUser = new Store(entity.getProperty("name").toString(), entity.getProperty("email").toString(), entity.getProperty("password").toString(), entity.getProperty("address").toString(), Double.parseDouble(entity.getProperty("latitude").toString()), Double.parseDouble(entity.getProperty("longitude").toString()));
				return returnedUser;
			}
		}
		return null;
	}
	
	public Boolean saveStore()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Entity store = new Entity("Store", email);
	    store.setProperty("name", name);
		store.setProperty("email", email);
		store.setProperty("password", password);
		store.setProperty("address", address);
		store.setProperty("latitude", lat);
		store.setProperty("longitude", lon);
        
		datastore.put(store);
		return true;
	}
	
	public static ArrayList<Offer> getActiveStoreOffers(String storeEmail)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter filterStoreID = new FilterPredicate("StoreID",
                FilterOperator.EQUAL,
                storeEmail);
		Filter filterOfferStatus = new FilterPredicate("Status",
                FilterOperator.EQUAL,
                "ON");
		Filter activeStoreOffersFilter =
				  CompositeFilterOperator.and(filterStoreID, filterOfferStatus);
		Query gaeQuery = new Query("Offer").setFilter(activeStoreOffersFilter);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<Offer> offers = new ArrayList<Offer>();
		for (Entity entity : pq.asIterable()) 
		{
			Filter filterCategoryID = new FilterPredicate("CategoryID",
	                FilterOperator.EQUAL,
	                Integer.parseInt(entity.getProperty("CategoryID").toString()));
			Query gaeQuery1 = new Query("Category").setFilter(filterCategoryID);
			PreparedQuery pq1 = datastore.prepare(gaeQuery1);
			Entity entity1 = pq1.asSingleEntity();
			String category = entity1.getProperty("CategoryName").toString();
			Offer storeOffer = new Offer(entity.getProperty("OfferID").toString(), category, entity.getProperty("Content").toString(), entity.getProperty("StartDate").toString(), entity.getProperty("EndDate").toString());
			offers.add(storeOffer);
		}
		return offers;
	}
	
	public static ArrayList<Offer> getInActiveStoreOffers(String storeEmail)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter filterStoreID = new FilterPredicate("StoreID",
                FilterOperator.EQUAL,
                storeEmail);
		Filter filterOfferStatus = new FilterPredicate("Status",
                FilterOperator.EQUAL,
                "OFF");
		Filter activeStoreOffersFilter =
				  CompositeFilterOperator.and(filterStoreID, filterOfferStatus);
		Query gaeQuery = new Query("Offer").setFilter(activeStoreOffersFilter);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<Offer> offers = new ArrayList<Offer>();
		for (Entity entity : pq.asIterable()) 
		{
			Filter filterCategoryID = new FilterPredicate("CategoryID",
	                FilterOperator.EQUAL,
	                Integer.parseInt(entity.getProperty("CategoryID").toString()));
			Query gaeQuery1 = new Query("Category").setFilter(filterCategoryID);
			PreparedQuery pq1 = datastore.prepare(gaeQuery1);
			Entity entity1 = pq1.asSingleEntity();
			String category = entity1.getProperty("CategoryName").toString();
			Offer storeOffer = new Offer(entity.getProperty("OfferID").toString(), category, entity.getProperty("Content").toString(), entity.getProperty("StartDate").toString(), entity.getProperty("EndDate").toString());
			offers.add(storeOffer);
		}
		return offers;
	}
	
	public static ArrayList<Offer> getAllStoreOffers(String storeEmail)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter filterStoreID = new FilterPredicate("StoreID",
                FilterOperator.EQUAL,
                storeEmail);
		Query gaeQuery = new Query("Offer").setFilter(filterStoreID);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<Offer> offers = new ArrayList<Offer>();
		for (Entity entity : pq.asIterable()) 
		{
			Filter filterCategoryID = new FilterPredicate("CategoryID",
	                FilterOperator.EQUAL,
	                Integer.parseInt(entity.getProperty("CategoryID").toString()));
			Query gaeQuery1 = new Query("Category").setFilter(filterCategoryID);
			PreparedQuery pq1 = datastore.prepare(gaeQuery1);
			Entity entity1 = pq1.asSingleEntity();
			String category = entity1.getProperty("CategoryName").toString();
			Offer storeOffer = new Offer(entity.getProperty("OfferID").toString(), category, entity.getProperty("Content").toString(), entity.getProperty("StartDate").toString(), entity.getProperty("EndDate").toString());
			offers.add(storeOffer);
		}
		return offers;
	}
}