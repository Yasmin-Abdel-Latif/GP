package com.fci.itdl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fci.itdl.model.*;
//import com.fci.itdl.services.*;

@Path("/")
@Produces("text/html")
public class UserController {

	// http://localhost:8888/rest/
	// http://itdloffers.appspot.com/rest/
	// http://8-dot-itdloffers.appspot.com/rest/
	public String webServiceLink = "http://8-dot-itdloffers.appspot.com/rest/";
	public static Store storeData = null;
	public static ArrayList<Offer> offers;
	public static String echo;
	public static Offer offerChosen = null;
	public static ArrayList<Offer> myOffers;

	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signupview")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/signup")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/loginview")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}

	@GET
	@Path("/addofferview")
	public Response addOfferView() {
		return Response.ok(new Viewable("/jsp/addOffer")).build();
	}

	@GET
	@Path("/updateAccountView")
	public Response updateAccountView(@Context HttpServletResponse response, @Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String storeEmail = (String) session.getAttribute("storeEmail");
		storeData = Store.getStore(storeEmail);
		return Response.ok().entity(new Viewable("/jsp/updateAccount")).build();
	}

	@POST
	@Path("/updateOfferView")
	public Response updateOfferView(@Context HttpServletResponse response, @Context HttpServletRequest request,
			@FormParam("OfferID") String offerID) {
		HttpSession session = request.getSession(false);
		String storeEmail = (String) session.getAttribute("storeEmail");
		storeData = Store.getStore(storeEmail);
		offerChosen = Offer.getOffer(offerID);
		return Response.ok().entity(new Viewable("/jsp/updateOffer")).build();
	}

	/**
	 * Action function to render Sign Out page, this function will be executed
	 * using url like this /rest/signout
	 * 
	 * @return signOut page
	 */

	@GET
	@Path("/signoutview")
	public Response signOut() {
		return Response.ok(new Viewable("/jsp/signOut")).build();
	}

	/**
	 * Action function to redirect user to startup page after signing out.
	 * 
	 * @return entryPoint page
	 */
	@GET
	@Path("/redirectStartUpView")
	public Response redirectStartUp() {
		storeData.setEmail("");
		storeData.setPassword("");
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param storeName
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @param lat
	 *            latitude of store location
	 * @param lon
	 *            longitude of store location
	 * @param address
	 *            store address in case not in current location
	 * @return Status string
	 * @throws ServletException
	 */
	@POST
	@Path("/signup")
	@Produces("text/html")
	public Response signup(@Context HttpServletResponse response, @Context HttpServletRequest request,
			@FormParam("name") String storeName, @FormParam("email") String email, @FormParam("password") String pass,
			@FormParam("lat") String lat, @FormParam("lon") String lon, @FormParam("address") String address)
			throws ServletException {
		String serviceUrl = webServiceLink + "RegistrationService";
		String urlParameters = "name=" + storeName + "&email=" + email + "&password=" + pass + "&lat=" + lat + "&lon="
				+ lon + "&address=" + address;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK")) {
				HttpSession session = request.getSession();
				session.setAttribute("storeEmail", email);
				storeData = Store.getStore(email);
				return Response.ok(new Viewable("/jsp/goHome")).build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return Response.ok(new Viewable("/jsp/signup")).build();
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param storeEmail
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@Context HttpServletResponse response, @Context HttpServletRequest request,
			@FormParam("email") String storeEmail, @FormParam("password") String pass) {
		String serviceUrl = webServiceLink + "LoginService";
		String urlParameters = "email=" + storeEmail + "&password=" + pass;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK")) {
				HttpSession session = request.getSession(true);
				session.setAttribute("storeEmail", storeEmail);
				storeData = Store.getStore(storeEmail);
				offers = new ArrayList<Offer>();
				offers.addAll(Store.getActiveStoreOffers(storeEmail));
				return Response.ok().entity(new Viewable("/jsp/homepage")).build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Path("/addOffer")
	@Produces("text/html")
	public Response addOffer(@FormParam("StoreID") String storeEmail, @FormParam("datepickerStart") String offerStart,
			@FormParam("datepickerEnd") String offerEnd, @FormParam("offerContent") String offerContent,
			@FormParam("category") String offerCategory) {
		String serviceUrl = webServiceLink + "AddOfferService";
		String urlParameters = "StoreID=" + storeEmail + "&datepickerStart=" + offerStart + "&datepickerEnd=" + offerEnd
				+ "&offerContent=" + offerContent + "&category=" + offerCategory;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK")) {
				storeData = Store.getStore(storeEmail);
				return Response.ok().entity(new Viewable("/jsp/goHome")).build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Path("/updateAccount")
	@Produces("text/html")
	public Response updateAccount(@FormParam("StoreID") String storeEmail, @FormParam("name") String newName,
			@FormParam("email") String newEmail, @FormParam("password") String newPassword) {
		String serviceUrl = webServiceLink + "UpdateAccountService";
		String urlParameters = "StoreID=" + storeEmail + "&name=" + newName + "&email=" + newEmail + "&password="
				+ newPassword;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK")) {
				return Response.ok().entity(new Viewable("/jsp/goHome")).build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Path("/updateOffer")
	@Produces("text/html")
	public Response updateOffer(@FormParam("OfferID") String offerID, @FormParam("datepickerStart") String offerStart,
			@FormParam("datepickerEnd") String offerEnd, @FormParam("offerContent") String offerContent,
			@FormParam("category") String offerCategory) {
		String serviceUrl = webServiceLink + "UpdateOfferService";
		String urlParameters = "OfferID=" + offerID + "&datepickerStart=" + offerStart + "&datepickerEnd=" + offerEnd
				+ "&offerContent=" + offerContent + "&category=" + offerCategory;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK")) {
				return Response.ok().entity(new Viewable("/jsp/goHome")).build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Path("/deleteOffer")
	@Produces("text/html")
	public Response deleteOffer(@FormParam("OfferID") String offerID) {
		String serviceUrl = webServiceLink + "DeleteOfferService";
		String urlParameters = "OfferID=" + offerID;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK")) {
				return Response.ok().entity(new Viewable("/jsp/goHome")).build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Path("/getOffers")
	@Produces("text/html")
	public String getOffers() {
		String serviceUrl = webServiceLink + "GetOffersService";
		String urlParameters = "";
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			String output = "";
			if (object.get("Status").equals("OK")) {
				JSONArray jstores = (JSONArray) parser.parse(object.get("AllStores").toString());
				for (int i = 0; i < jstores.size(); i++) {
					JSONObject jstore;
					jstore = (JSONObject) jstores.get(i);
					ArrayList<Offer> storeOffers = new ArrayList<Offer>();
					String storeLat = jstore.get("latitude").toString();
					String storeLong = jstore.get("longitude").toString();
					String jsonStoreEmail = jstore.get("storeEmail").toString();
					String storeAddress = jstore.get("storeAddress").toString();
					String storeName = jstore.get("storeName").toString();
					JSONArray joffers = (JSONArray) parser.parse(jstore.get("offers").toString());
					for (int j = 0; j < joffers.size(); j++) {
						JSONObject joffer;
						joffer = (JSONObject) joffers.get(j);
						storeOffers.add(convertJsonObjToOfferObj(joffer));
					}
					output += storeName + " " + storeAddress + " " + storeLat + " " + storeLong + " " + jsonStoreEmail + " " + storeOffers.toString() + "\n";
				}
			}
			return output;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "failed";
	}

	public Offer convertJsonObjToOfferObj(JSONObject jsonObj) {
		return new Offer(jsonObj.get("StoreID").toString(), jsonObj.get("OfferID").toString(),
				jsonObj.get("CategoryName").toString(), jsonObj.get("Content").toString(),
				jsonObj.get("StartDate").toString(), jsonObj.get("EndDate").toString());
	}

	@POST
	@Path("/getAllStores")
	@Produces("text/html")
	public String getAllStores() {
		String serviceUrl = webServiceLink + "GetAllStores";
		String urlParameters = "";
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK")) {
				ArrayList<Store> stores = new ArrayList<Store>();
				JSONArray jstores = (JSONArray) parser.parse(object.get("AllStores").toString());
				for (int i = 0; i < jstores.size(); i++) {
					JSONObject jstore;
					jstore = (JSONObject) jstores.get(i);
					stores.add(convertJsonObjToStoreObj(jstore));
				}
				return stores.toString();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "failed";
	}

	public Store convertJsonObjToStoreObj(JSONObject jsonObj) {
		return new Store(jsonObj.get("name").toString(), jsonObj.get("email").toString(),
				jsonObj.get("password").toString(), jsonObj.get("address").toString(),
				Double.parseDouble(jsonObj.get("latitude").toString()),
				Double.parseDouble(jsonObj.get("longitude").toString()));
	}

	@POST
	@Path("/getPosts")
	@Produces("text/html")
	public String getPosts(@FormParam("UserID") String userID) {
		String serviceUrl = webServiceLink + "GetPostsService";
		String urlParameters = "userID=" + userID;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			ArrayList<FacebookPost> posts = new ArrayList<FacebookPost>();
			if (object.get("Status").equals("OK")) {
				JSONArray jposts = (JSONArray) parser.parse(object.get("AllUserPosts").toString());
				for (int i = 0; i < jposts.size(); i++) {
					JSONObject jpost;
					jpost = (JSONObject) jposts.get(i);
					posts.add(convertJsonObjToPostObj(jpost));
				}
				Map<String, ArrayList<FacebookPost>> allPosts = new HashMap<String, ArrayList<FacebookPost>>();
				allPosts.put("allposts", posts);
				return posts.toString();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "failed";
	}

	public FacebookPost convertJsonObjToPostObj(JSONObject jsonObj) {
		return new FacebookPost(jsonObj.get("userID").toString(), jsonObj.get("postID").toString(),
				jsonObj.get("postContent").toString(), jsonObj.get("creationDate").toString(), 1);
	}

	public String getAllNotes(String userID) throws ParseException {
		String serviceUrl = "http://4-dot-secondhelloworld-1221.appspot.com/restNotes/getAllNotesService";
		String urlParameters = "userID=" + userID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		ArrayList<NoteEntity> notes = new ArrayList<NoteEntity>();
		NoteParser noteParser = new NoteParser();

		JSONParser parser = new JSONParser();

		JSONArray array = (JSONArray) parser.parse(retJson.toString());

		if (array.size() == 0) {
			return "emptyNotes";
		}
		for (int i = 0; i < array.size(); i++) {
			JSONObject object;
			object = (JSONObject) array.get(i);
			System.out.println(object.toJSONString());
			if (object.containsKey("meeting")) {
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get("meeting").toString());
				notes.add(noteParser.convertJsonObjToMeetingNoteObj(object1));

			} else if (object.containsKey("ordinary")) {
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get("ordinary").toString());
				notes.add(noteParser.convertJsonObjToOrdinaryNoteObj(object1));

			} else if (object.containsKey("shopping")) {
				System.out.println("shopping");
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get("shopping").toString());
				notes.add(noteParser.convertJsonObjToShoppingNoteObj(object1));

			} else if (object.containsKey("deadline")) {
				System.out.println("deadline");
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get("deadline").toString());
				notes.add(noteParser.convertJsonObjToDeadLineNoteObj(object1));
			}
		}

		return notes.toString();
	}
}
