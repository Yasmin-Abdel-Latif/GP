package com.fci.itdl.model;

import java.sql.Timestamp;

import org.json.simple.JSONObject;
import com.google.appengine.api.datastore.Entity;
import com.fci.itdl.model.DeadlineNoteEntity;
import com.fci.itdl.model.MeetingNoteEntity;
import com.fci.itdl.model.OrdinaryNoteEntity;
import com.fci.itdl.model.ShoppingNoteEntity;

public class NoteParser {

	

	public DeadlineNoteEntity convertJsonObjToDeadLineNoteObj(JSONObject object1) {

		DeadlineNoteEntity deadLineNoteObj = new DeadlineNoteEntity(
				Integer.parseInt(object1.get("progressPercentage").toString()), object1.get("deadLineTitle").toString(),
				java.sql.Timestamp.valueOf(object1.get("deadLineDate").toString()), object1.get("noteID").toString(),
				object1.get("userID").toString(), java.sql.Timestamp.valueOf(object1.get("creationDate").toString()),
				Boolean.valueOf(object1.get("isDone").toString()),
				Boolean.valueOf(object1.get("isTextCategorized").toString()), object1.get("noteType").toString());

		return deadLineNoteObj;
	}

	public OrdinaryNoteEntity convertJsonObjToOrdinaryNoteObj(JSONObject jsonObj) {

		return new OrdinaryNoteEntity(jsonObj.get("noteID").toString(), jsonObj.get("userID").toString(),
				Timestamp.valueOf(jsonObj.get("creationDate").toString()),
				Boolean.valueOf(jsonObj.get("isDone").toString()),
				Boolean.valueOf(jsonObj.get("isTextCategorized").toString()), jsonObj.get("noteType").toString(),
				jsonObj.get("noteContent").toString());

	}

	public ShoppingNoteEntity convertJsonObjToShoppingNoteObj(JSONObject jsonObj) {

		return new ShoppingNoteEntity(String.valueOf(jsonObj.get("noteID")), jsonObj.get("userID").toString(),
				java.sql.Timestamp.valueOf(jsonObj.get("creationDate").toString()),
				Boolean.valueOf(jsonObj.get("isDone").toString()),
				Boolean.valueOf(jsonObj.get("isTextCategorized").toString()), jsonObj.get("noteType").toString(),
				jsonObj.get("productToBuy").toString(), jsonObj.get("productCategory").toString());

	}

	public MeetingNoteEntity convertJsonObjToMeetingNoteObj(JSONObject jsonObj) {

		return new MeetingNoteEntity(java.sql.Timestamp.valueOf(jsonObj.get("meetingNoteDate").toString()),
				java.sql.Time.valueOf(jsonObj.get("estimatedTransportTime").toString()),
				jsonObj.get("meetingTitle").toString(), jsonObj.get("meetingPlace").toString(),
				jsonObj.get("meetingAgenda").toString(), jsonObj.get("noteID").toString(),
				jsonObj.get("userID").toString(), java.sql.Timestamp.valueOf(jsonObj.get("creationDate").toString()),
				Boolean.valueOf(jsonObj.get("isDone").toString()),
				Boolean.valueOf(jsonObj.get("isTextCategorized").toString()), jsonObj.get("noteType").toString());

	}

	
	@SuppressWarnings("unchecked")
	public JSONObject handleMeetingNoteJSONObject(MeetingNoteEntity note) {

		JSONObject object = new JSONObject();
		object.put("creationDate", String.valueOf(note.getCreationDate()));
		object.put("estimatedTransportTime", String.valueOf(note.getEstimatedTransportTime()));
		object.put("meetingAgenda", note.getMeetingAgenda());
		object.put("meetingNoteDate", String.valueOf(note.getmeetingNoteDate()));
		object.put("meetingPlace", note.getMeetingPlace());
		object.put("meetingTitle", note.getMeetingTitle());
		object.put("noteID", note.getNoteID());
		object.put("noteType", note.getNoteType());
		object.put("userID", note.getUserID());
		object.put("isDone", note.isDone());
		object.put("isTextCategorized", note.isTextCategorized());

		return object;
	}

	@SuppressWarnings("unchecked")
	public JSONObject handleOrdinaryNoteJSONObject(OrdinaryNoteEntity note) {
		JSONObject object = new JSONObject();
		object.put("noteContent", note.getNoteContent());
		object.put("creationDate", String.valueOf(note.getCreationDate()));
		object.put("noteID", note.getNoteID());
		object.put("noteType", note.getNoteType());
		object.put("userID", note.getUserID());
		object.put("isDone", note.isDone());
		object.put("isTextCategorized", note.isTextCategorized());

		return object;
	}

	@SuppressWarnings("unchecked")
	public JSONObject handleDeadLineNoteJSONObject(DeadlineNoteEntity note) {
		JSONObject object = new JSONObject();
		object.put("deadLineDate", String.valueOf(note.getDeadLineDate()));
		object.put("deadLineTitle", note.getDeadLineTitle());
		object.put("progressPercentage", String.valueOf(note.getProgressPercentage()));

		object.put("creationDate", String.valueOf(note.getCreationDate()));
		object.put("noteID", note.getNoteID());
		object.put("noteType", note.getNoteType());
		object.put("userID", note.getUserID());
		object.put("isDone", String.valueOf(note.isDone()));
		object.put("isTextCategorized", String.valueOf(note.isTextCategorized()));

		return object;
	}

	@SuppressWarnings("unchecked")
	public JSONObject handleShoppingNoteJSONObject(ShoppingNoteEntity note) {
		JSONObject object = new JSONObject();
		object.put("productToBuy", note.getProductToBuy());
		object.put("productCategory", note.getProductCategory());
		object.put("creationDate", String.valueOf(note.getCreationDate()));
		object.put("noteID", note.getNoteID());
		object.put("noteType", note.getNoteType());
		object.put("userID", note.getUserID());
		object.put("isDone", note.isDone());
		object.put("isTextCategorized", note.isTextCategorized());

		return object;
	}

	public ShoppingNoteEntity shoppingNoteEntityHandler(Entity entity) {
		ShoppingNoteEntity shoppingNoteObj = new ShoppingNoteEntity(String.valueOf(entity.getKey().getId()),
				entity.getProperty("userID").toString(),
				java.sql.Timestamp.valueOf(entity.getProperty("creationDate").toString()),
				Boolean.valueOf(entity.getProperty("isDone").toString()),
				Boolean.valueOf(entity.getProperty("isTextCategorized").toString()),
				entity.getProperty("noteType").toString(), entity.getProperty("productToBuy").toString(),
				entity.getProperty("productCategory").toString());
		return shoppingNoteObj;
	}

	public OrdinaryNoteEntity ordinaryNoteEntityHandler(Entity entity) {
		OrdinaryNoteEntity ordniaryNoteObj = new OrdinaryNoteEntity(String.valueOf(entity.getKey().getId()),
				entity.getProperty("userID").toString(),
				java.sql.Timestamp.valueOf(entity.getProperty("creationDate").toString()),
				Boolean.valueOf(entity.getProperty("isDone").toString()),
				Boolean.valueOf(entity.getProperty("isTextCategorized").toString()),
				entity.getProperty("noteType").toString(), entity.getProperty("noteContent").toString());
		return ordniaryNoteObj;
	}

	public MeetingNoteEntity meetingNoteEntityHandeler(Entity entity) {
		MeetingNoteEntity meetingNoteObj = new MeetingNoteEntity(
				java.sql.Timestamp.valueOf(entity.getProperty("meetingNoteDate").toString()),
				java.sql.Time.valueOf(entity.getProperty("estimatedTransportTime").toString()),
				entity.getProperty("meetingTitle").toString(), entity.getProperty("meetingPlace").toString(),
				entity.getProperty("meetingAgenda").toString(), String.valueOf(entity.getKey().getId()),
				entity.getProperty("userID").toString(),
				java.sql.Timestamp.valueOf(entity.getProperty("creationDate").toString()),
				Boolean.valueOf(entity.getProperty("isDone").toString()),
				Boolean.valueOf(entity.getProperty("isTextCategorized").toString()),
				entity.getProperty("noteType").toString());
		return meetingNoteObj;
	}

	public DeadlineNoteEntity deadlineNoteEntityHandler(Entity entity) {
		DeadlineNoteEntity deadLineNoteObj = new DeadlineNoteEntity(
				Integer.parseInt(String.valueOf(entity.getProperty("progressPercentage"))),
				String.valueOf(entity.getProperty("deadLineTitle")),
				java.sql.Timestamp.valueOf(String.valueOf(entity.getProperty("deadLineDate"))),
				String.valueOf(entity.getKey().getId()), String.valueOf(entity.getProperty("userID")),
				java.sql.Timestamp.valueOf(String.valueOf(entity.getProperty("creationDate"))),
				Boolean.valueOf(String.valueOf(entity.getProperty("isDone"))),
				Boolean.valueOf(String.valueOf(entity.getProperty("isTextCategorized"))),
				String.valueOf(entity.getProperty("noteType")));
		return deadLineNoteObj;
	}
}
