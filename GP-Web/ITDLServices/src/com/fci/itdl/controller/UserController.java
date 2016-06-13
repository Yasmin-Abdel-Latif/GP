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
		HttpSession session=request.getSession(false);
		String storeEmail=(String)session.getAttribute("storeEmail");
		storeData = Store.getStore(storeEmail);
		return Response.ok().entity(new Viewable("/jsp/updateAccount")).build();
	}
	
	@POST
	@Path("/updateOfferView")
	public Response updateOfferView(@Context HttpServletResponse response, @Context HttpServletRequest request, @FormParam("OfferID") String offerID) {
		HttpSession session=request.getSession(false);
		String storeEmail=(String)session.getAttribute("storeEmail");
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
			if (object.get("Status").equals("OK"))
			{
				HttpSession session=request.getSession();
				session.setAttribute("storeEmail",email);
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
			if (object.get("Status").equals("OK"))
			{
				HttpSession session=request.getSession(true);
				session.setAttribute("storeEmail",storeEmail);
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
			@FormParam("datepickerEnd") String offerEnd, @FormParam("offerContent") String offerContent, @FormParam("category") String offerCategory) {
		String serviceUrl = webServiceLink + "AddOfferService";
		String urlParameters = "StoreID=" + storeEmail + "&datepickerStart=" + offerStart + "&datepickerEnd=" + offerEnd + "&offerContent=" + offerContent + "&category=" + offerCategory;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
			{
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
		String urlParameters = "StoreID=" + storeEmail + "&name=" + newName + "&email=" + newEmail + "&password=" + newPassword;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
			{
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
			@FormParam("datepickerEnd") String offerEnd, @FormParam("offerContent") String offerContent, @FormParam("category") String offerCategory) {
		String serviceUrl = webServiceLink + "UpdateOfferService";
		String urlParameters = "OfferID=" + offerID + "&datepickerStart=" + offerStart + "&datepickerEnd=" + offerEnd + "&offerContent=" + offerContent + "&category=" + offerCategory;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
			{
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
			if (object.get("Status").equals("OK"))
			{
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
	public String getOffers(@FormParam("email") String storeEmail) {
		String serviceUrl = webServiceLink + "GetOffersService";
		String urlParameters = "email=" + storeEmail;
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			myOffers = new ArrayList<Offer>();
			if (object.get("Status").equals("OK"))
			{
				JSONArray joffers = (JSONArray) parser.parse(object.get("AllOffers").toString());
				for (int i = 0; i < joffers.size(); i++) {
					JSONObject joffer;
					joffer = (JSONObject) joffers.get(i);
					myOffers.add(convertJsonObjToOfferObj(joffer));
				}
				Map<String, ArrayList<Offer>> allOffers = new HashMap<String, ArrayList<Offer>>();
				allOffers.put("alloffers", myOffers);
				return myOffers.toString();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "failed";
	}
	
	public Offer convertJsonObjToOfferObj(JSONObject jsonObj){
		return new Offer(jsonObj.get("StoreID").toString(), jsonObj.get("OfferID").toString(), jsonObj.get("CategoryName").toString(), jsonObj.get("Content").toString(), jsonObj.get("StartDate").toString(), jsonObj.get("EndDate").toString());
	}
}
