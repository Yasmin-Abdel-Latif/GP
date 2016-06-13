package com.fci.itdl.model;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class Offer {

	private String offerID;
	private String category;
	private String content;
	private String startDate;
	private String endDate;
	private String storeID;
	
	public Offer(String offerID, String categoryID, String content, String startDate, String endDate)
	{
		this.offerID = offerID;
		this.category = categoryID;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Offer(String storeID, String offerID, String categoryID, String content, String startDate, String endDate)
	{
		this.storeID = storeID;
		this.offerID = offerID;
		this.category = categoryID;
		this.content = content;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Offer(String categoryID, String content, String startDate, String endDate)
	{
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
	
	public static Offer getOffer(String offerID)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Offer");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("OfferID").toString().equals(offerID)) 
			{
				String offerCategory = entity.getProperty("CategoryID").toString();
				Query gaeQuery1 = new Query("Category");
				PreparedQuery pq1 = datastore.prepare(gaeQuery1);
				for (Entity entity1 : pq1.asIterable()) 
				{
					if (entity1.getProperty("CategoryID").toString().equals(offerCategory)) 
					{
						String category = entity1.getProperty("CategoryName").toString();
						Offer returnedOffer = new Offer(entity.getProperty("OfferID").toString(), category, entity.getProperty("Content").toString(), entity.getProperty("StartDate").toString(), entity.getProperty("EndDate").toString());
						return returnedOffer;
					}
				}
			}
		}
		return null;
		
	}
	
	public static boolean deleteOffer(String offerID, String storeID)
	{
		Offer returnedOffer = getOffer(offerID);
		int categoryID = 0;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Category");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("CategoryName").toString().equals(returnedOffer.getCategoryID())) 
			{
				categoryID = Integer.parseInt(entity.getProperty("CategoryID").toString());
				break;
			}
		}
		Entity offer = new Entity("Offer", Integer.parseInt(offerID));
		offer.setProperty("StoreID", storeID);
		offer.setProperty("OfferID", Integer.parseInt(offerID));
	    offer.setProperty("StartDate", returnedOffer.getStartDate());
		offer.setProperty("EndDate", returnedOffer.getEndDate());
		offer.setProperty("Content", returnedOffer.getContent());
		offer.setProperty("Status", "OFF");
		offer.setProperty("CategoryID", categoryID);
		
		datastore.put(offer);
		return true;
	}
	
	public static boolean updateOffer(Offer updatedOffer, String storeID)
	{
		int categoryID = 0;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Category");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("CategoryName").toString().equals(updatedOffer.getCategoryID())) 
			{
				categoryID = Integer.parseInt(entity.getProperty("CategoryID").toString());
				break;
			}
		}
		Entity offer = new Entity("Offer", Integer.parseInt(updatedOffer.getOfferID()));
		offer.setProperty("StoreID", storeID);
		offer.setProperty("OfferID", Integer.parseInt(updatedOffer.getOfferID()));
	    offer.setProperty("StartDate", updatedOffer.getStartDate());
		offer.setProperty("EndDate", updatedOffer.getEndDate());
		offer.setProperty("Content", updatedOffer.getContent());
		offer.setProperty("Status", "ON");
		offer.setProperty("CategoryID", categoryID);
		
		datastore.put(offer);
		return true;
	}
	
	public static boolean updateInActiveOffer(Offer updatedOffer, String storeID)
	{
		int categoryID = 0;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Category");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("CategoryName").toString().equals(updatedOffer.getCategoryID())) 
			{
				categoryID = Integer.parseInt(entity.getProperty("CategoryID").toString());
				break;
			}
		}
		Entity offer = new Entity("Offer", Integer.parseInt(updatedOffer.getOfferID()));
		offer.setProperty("StoreID", storeID);
		offer.setProperty("OfferID", Integer.parseInt(updatedOffer.getOfferID()));
	    offer.setProperty("StartDate", updatedOffer.getStartDate());
		offer.setProperty("EndDate", updatedOffer.getEndDate());
		offer.setProperty("Content", updatedOffer.getContent());
		offer.setProperty("Status", "OFF");
		offer.setProperty("CategoryID", categoryID);
		
		datastore.put(offer);
		return true;
	}
	
	public Boolean saveOffer(String storeEmail)
	{
		saveTableCategory();
		int categoryID = findCategoryID(category);
		
		DatastoreService datastore3 = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery1 = new Query("Offer");
		PreparedQuery pq1 = datastore3.prepare(gaeQuery1);
		int size = pq1.countEntities(FetchOptions.Builder.withDefaults());
		
		DatastoreService datastore4 = DatastoreServiceFactory.getDatastoreService();
		Entity offer = new Entity("Offer", (size+1));
		offer.setProperty("StoreID", storeEmail);
		offer.setProperty("OfferID", (size+1));
	    offer.setProperty("StartDate", startDate);
		offer.setProperty("EndDate", endDate);
		offer.setProperty("Content", content);
		offer.setProperty("Status", "ON");
		offer.setProperty("CategoryID", categoryID);
		
		datastore4.put(offer);
		return true;
	}
	
	public Boolean saveTableCategory()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

    	int categoryID = 1;
    	Entity category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 1);
	    category.setProperty("CategoryName", "Arts and Entertainments");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 2;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 2);
	    category.setProperty("CategoryName", "Movies");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 3;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 3);
	    category.setProperty("CategoryName", "Music");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 4;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 4);
	    category.setProperty("CategoryName", "Food and drinks");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 5;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 5);
	    category.setProperty("CategoryName", "Technology");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 6;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 6);
	    category.setProperty("CategoryName", "Sports");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 7;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 7);
	    category.setProperty("CategoryName", "Health");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 8;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 8);
	    category.setProperty("CategoryName", "Religion");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 9;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 9);
	    category.setProperty("CategoryName", "Education");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 10;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 10);
	    category.setProperty("CategoryName", "Pets and animals");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 11;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 11);
	    category.setProperty("CategoryName", "Fashion");
	    datastore.put(category);
	    txn.commit();
	    
	    txn = datastore.beginTransaction();
	    categoryID = 12;
    	category = new Entity("Category", categoryID);
		category.setProperty("CategoryID", 12);
	    category.setProperty("CategoryName", "Reading");
	    datastore.put(category);
	    txn.commit();
	    
		return true;
	}
	
	public int findCategoryID(String categoryName)
	{
		int categoryID = 0;
		DatastoreService datastore2 = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Category");
		PreparedQuery pq = datastore2.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("CategoryName").toString().equals(categoryName)) 
			{
				categoryID = Integer.parseInt(entity.getProperty("CategoryID").toString());
				break;
			}
		}
		return categoryID;
	}
	
	public String toString()
	{
		String offerToString = "[STORE_EMAIL:" + this.storeID + ", " + "OFFER_ID:" + this.offerID + ", " + "OFFER_CATEGORY:" + this.category + ", " + "OFFER_CONTENT:" + this.content + ", " + "OFFER_START_DATE:" + this.startDate + ", " + "OFFER_END_DATE:" + this.endDate + "]<br>";
		return offerToString;
	}
	
	public static ArrayList<Offer> getActiveOffers()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter filterOfferStatus = new FilterPredicate("Status",
                FilterOperator.EQUAL,
                "ON");
		Query gaeQuery = new Query("Offer").setFilter(filterOfferStatus);
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
			
			Offer storeOffer = new Offer(entity.getProperty("StoreID").toString(), entity.getProperty("OfferID").toString(), category, entity.getProperty("Content").toString(), entity.getProperty("StartDate").toString(), entity.getProperty("EndDate").toString());
			offers.add(storeOffer);
		}
		return offers;
	}
}
