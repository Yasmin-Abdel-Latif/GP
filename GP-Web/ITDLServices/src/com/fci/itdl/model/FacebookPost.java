package com.fci.itdl.model;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class FacebookPost {

	private String userID;
	private String postID;
	private String postContent;
	private String creationDate;
	private int read;

	public FacebookPost() {
		super();
	}

	public FacebookPost(String userID, String postID, String postContent, String creationDate, int read) {
		super();
		this.userID = userID;
		this.postID = postID;
		this.postContent = postContent;
		this.creationDate = creationDate;
		this.read = read;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPostID() {
		return postID;
	}

	public void setPostID(String postID) {
		this.postID = postID;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	
	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	@Override
	public String toString() {
		return "FacebookPost [userID=" + userID + ", postID=" + postID + ", postContent=" + postContent + ", creationDate=" + creationDate + ", read="
				+ read + "]";
	}

	public static FacebookPost getfbPost(String postID) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("FacebookPosts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("PostID").toString().equals(postID)) {
				FacebookPost returnedPost = new FacebookPost(entity.getProperty("UserID").toString(),
						entity.getProperty("PostID").toString(), entity.getProperty("PostContent").toString(),
						entity.getProperty("CreationDate").toString(), Integer.parseInt(entity.getProperty("Read").toString()));
				return returnedPost;
			}
		}
		return null;
	}

	public Boolean savePost() {
		DatastoreService datastore4 = DatastoreServiceFactory.getDatastoreService();
		Entity post = new Entity("FacebookPosts");
		post.setProperty("UserID", userID);
		post.setProperty("PostID", postID);
		post.setProperty("PostContent", postContent);
		post.setProperty("CreationDate", creationDate);
		post.setProperty("Read", 0);

		datastore4.put(post);
		return true;
	}

	public static ArrayList<FacebookPost> getUserPosts(String userID) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter filterOfferStatus = new FilterPredicate("userID", FilterOperator.EQUAL, userID);
		Query gaeQuery = new Query("FacebookPosts").setFilter(filterOfferStatus);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<FacebookPost> posts = new ArrayList<FacebookPost>();
		for (Entity entity : pq.asIterable()) {
			if (Integer.parseInt(entity.getProperty("Read").toString()) == 0) {
				FacebookPost userPost = new FacebookPost(entity.getProperty("UserID").toString(),
						entity.getProperty("PostID").toString(), entity.getProperty("PostContent").toString(),
						entity.getProperty("CreationDate").toString(), 0);
				posts.add(userPost);
			}

		}
		return posts;
	}
	
	public static void setPostsRead(String userID) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter filterOfferStatus = new FilterPredicate("userID", FilterOperator.EQUAL, userID);
		Query gaeQuery = new Query("FacebookPosts").setFilter(filterOfferStatus);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			setPostRead(entity);
		}
	}
	
	public static void setPostRead(Entity entity){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

		long entityID = entity.getKey().getId();
    	Entity post = new Entity("FacebookPosts", entityID);
		post.setProperty("UserID", entity.getProperty("UserID").toString());
		post.setProperty("PostID", entity.getProperty("PostID").toString());
		post.setProperty("PostContent", entity.getProperty("PostContent").toString());
		post.setProperty("CreationDate", entity.getProperty("CreationDate").toString());
		post.setProperty("Read", 1);
	    datastore.put(post);
	    txn.commit();
	}

}
