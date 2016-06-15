package com.fci.itdl.services;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fci.itdl.controller.UserController;
import com.fci.itdl.model.FacebookPost;
import com.fci.itdl.model.Offer;
import com.fci.itdl.model.Store;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class Service {
	
	public static String emailLoggedin;
	
	/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param storeName
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json in string fromat
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("name") String storeName, @FormParam("email") String email,
			@FormParam("password") String pass, @FormParam("lat") String lat, @FormParam("lon") String lon,
			@FormParam("address") String address) 
	{
		JSONObject object = new JSONObject();
		Store store = new Store(storeName, email, pass, address, Double.parseDouble(lat), Double.parseDouble(lon));
		store.saveStore();
		object.put("Status", "OK");
		
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("email") String sEmail, @FormParam("password") String pass) 
	{
		JSONObject object = new JSONObject();
		Store store = Store.getStore(sEmail, pass);
		if (store == null) 
		{
			object.put("Status", "Failed");
		} 
		else 
		{
			object.put("Status", "OK");
			object.put("name", store.getName());
			object.put("email", store.getEmail());
			object.put("password", store.getPassword());
		}
		return object.toString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/AddOfferService")
	public String addOfferService(@FormParam("StoreID") String storeEmail, @FormParam("datepickerStart") String offerStart,
			@FormParam("datepickerEnd") String offerEnd, @FormParam("offerContent") String offerContent, @FormParam("category") String offerCategory) 
	{
		JSONObject object = new JSONObject();
		Offer offer = new Offer(offerCategory, offerContent, offerStart, offerEnd);
		offer.saveOffer(storeEmail);
		object.put("Status", "OK");
		
		return object.toString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/UpdateAccountService")
	public String updateAccountService(@FormParam("StoreID") String storeEmail, @FormParam("name") String newName,
			@FormParam("email") String newEmail, @FormParam("password") String newPassword) 
	{
		JSONObject object = new JSONObject();
		UserController.storeData = Store.getStore(storeEmail);
		UserController.storeData.updateStore(newName, newEmail, newPassword);
		object.put("Status", "OK");
		return object.toString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/DeleteOfferService")
	public String deleteOfferService(@FormParam("OfferID") String offerID) 
	{
		JSONObject object = new JSONObject();
        String storeEmail = UserController.storeData.getEmail();
        Offer.deleteOffer(offerID, storeEmail);
		object.put("Status", "OK");
		return object.toString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/UpdateOfferService")
	public String updateOfferService(@FormParam("OfferID") String offerID, @FormParam("datepickerStart") String offerStart,
			@FormParam("datepickerEnd") String offerEnd, @FormParam("offerContent") String offerContent, @FormParam("category") String offerCategory) 
	{
		JSONObject object = new JSONObject();
        String storeEmail = UserController.storeData.getEmail();
        Offer offer = new Offer(offerID, offerCategory, offerContent, offerStart, offerEnd);
		Offer.updateOffer(offer, storeEmail);
		object.put("Status", "OK");
		return object.toString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/GetOffersService")
	public String getOffersService() 
	{
		JSONObject jresult = new JSONObject();
		JSONArray jarray = new JSONArray();
		ArrayList<Store> stores = new ArrayList<Store>();
		stores.addAll(Store.getAllStores());
		for(int i = 0 ; i < stores.size() ; i++)
		{
			JSONObject jobject = new JSONObject();
			JSONArray joffers = new JSONArray();
			
			ArrayList<Offer> offers = new ArrayList<Offer>();
			offers.addAll(Store.getActiveStoreOffers(stores.get(i).getEmail()));
			for(int j = 0 ; j < offers.size() ; j++)
			{
				JSONObject joffer = new JSONObject();
				joffer.put("OfferID", offers.get(j).getOfferID());
				joffer.put("StoreID", offers.get(j).getStoreID());
			    joffer.put("StartDate", offers.get(j).getStartDate());
				joffer.put("EndDate", offers.get(j).getEndDate());
				joffer.put("Content", offers.get(j).getContent());
				joffer.put("Status", "ON");
				joffer.put("CategoryName", offers.get(j).getCategoryID());
				
				joffers.add(joffer);
			}
			jobject.put("offers", joffers);
			jobject.put("storeEmail", stores.get(i).getEmail());
			jobject.put("latitude", stores.get(i).getLat());
			jobject.put("longitude", stores.get(i).getLon());
			
			jarray.add(jobject);
		}
		jresult.put("AllStores", jarray);
		jresult.put("Status", "OK");
		
		return jresult.toString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/GetAllStores")
	public String getAllStores() 
	{
		JSONObject jresult = new JSONObject();
		JSONArray jarray = new JSONArray();
		ArrayList<Store> stores = new ArrayList<Store>();
		stores.addAll(Store.getAllStores());
		for(int i = 0 ; i < stores.size() ; i++)
		{
			JSONObject jobject = new JSONObject();
			jobject.put("email", stores.get(i).getEmail());
			jobject.put("latitude", stores.get(i).getLat());
			jobject.put("longitude", stores.get(i).getLon());
			jobject.put("name", stores.get(i).getName());
			jobject.put("password", stores.get(i).getPassword());
			jobject.put("address", stores.get(i).getAddress());
			
			jarray.add(jobject);
		}
		jresult.put("AllStores", jarray);
		jresult.put("Status", "OK");
		
		return jresult.toString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/AddUserPostService")
	public String addUserPostService(@FormParam("userID") String userID, @FormParam("postID") String postID,
			@FormParam("postContent") String postContent, @FormParam("creationDate") String creationDate) 
	{
		JSONObject object = new JSONObject();
		if(FacebookPost.getfbPost(postID) == null)
		{
			FacebookPost fbPost = new FacebookPost(userID, postID, postContent, creationDate, 0);
			fbPost.savePost();
		}
		object.put("Status", "OK");
		
		return object.toString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/GetPostsService")
	public String getPostsService(@FormParam("userID") String userID) 
	{
		JSONObject jobject = new JSONObject();
		JSONArray jposts = new JSONArray();
		
		ArrayList<FacebookPost> posts = new ArrayList<FacebookPost>();
		posts.addAll(FacebookPost.getUserPosts(userID));
		for(int i = 0 ; i < posts.size() ; i++)
		{
			JSONObject jpost = new JSONObject();
			jpost.put("userID", posts.get(i).getUserID());
			jpost.put("postID", posts.get(i).getPostID());
			jpost.put("postContent", posts.get(i).getPostContent());
			jpost.put("creationDate", posts.get(i).getCreationDate());
			jpost.put("read", 0);
			
			jposts.add(jpost);
		}
		jobject.put("AllUserPosts", jposts);
		jobject.put("Status", "OK");

		//FacebookPost.setPostsRead(userID);
		
		return jobject.toString();
	}
}
