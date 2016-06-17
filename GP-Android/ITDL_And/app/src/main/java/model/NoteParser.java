package model;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import model.DeadlineNoteEntity;
import model.MeetingNoteEntity;
import model.OrdinaryNoteEntity;
import model.ShoppingNoteEntity;

public class NoteParser {


    public DeadlineNoteEntity convertJsonObjToDeadLineNoteObj(JSONObject object1) throws JSONException {

        DeadlineNoteEntity deadLineNoteObj = new DeadlineNoteEntity(
                Integer.parseInt(object1.getString("progressPercentage")), object1.getString("deadLineTitle"),
                Timestamp.valueOf(object1.getString("deadLineDate")), Long.parseLong(object1.getString("noteID")),
                Long.parseLong(object1.getString("userID")), Timestamp.valueOf(object1.getString("creationDate")),
                Boolean.valueOf(object1.getString("isDone")),
                Boolean.valueOf(object1.getString("isTextCategorized")), object1.getString("noteType"));

        return deadLineNoteObj;
    }

    public OrdinaryNoteEntity convertJsonObjToOrdinaryNoteObj(JSONObject jsonObj) throws JSONException {

        return new OrdinaryNoteEntity(Long.parseLong(jsonObj.getString("noteID")), Long.parseLong(jsonObj.getString("userID")),
                Timestamp.valueOf(jsonObj.getString("creationDate")),
                Boolean.valueOf(jsonObj.getString("isDone")),
                Boolean.valueOf(jsonObj.getString("isTextCategorized")), jsonObj.getString("noteType"),
                jsonObj.getString("noteContent"));

    }

    public ShoppingNoteEntity convertJsonObjToShoppingNoteObj(JSONObject jsonObj) throws JSONException {

        return new ShoppingNoteEntity(Long.parseLong(jsonObj.getString("noteID")), Long.parseLong(jsonObj.getString("userID")),
                Timestamp.valueOf(jsonObj.getString("creationDate")),
                Boolean.valueOf(jsonObj.getString("isDone")),
                Boolean.valueOf(jsonObj.getString("isTextCategorized")), jsonObj.getString("noteType"),
                jsonObj.getString("productToBuy"), jsonObj.getString("productCategory"));

    }

    public MeetingNoteEntity convertJsonObjToMeetingNoteObj(JSONObject jsonObj) throws JSONException {

        return new MeetingNoteEntity(Timestamp.valueOf(jsonObj.getString("meetingNoteDate")),
                java.sql.Time.valueOf(jsonObj.getString("estimatedTransportTime")),
                jsonObj.getString("meetingTitle"), jsonObj.getString("meetingPlace"),
                jsonObj.getString("meetingAgenda"), Long.parseLong(jsonObj.getString("noteID")),
                Long.parseLong(jsonObj.getString("userID")), Timestamp.valueOf(jsonObj.getString("creationDate")),
                Boolean.valueOf(jsonObj.getString("isDone")),
                Boolean.valueOf(jsonObj.getString("isTextCategorized")), jsonObj.getString("noteType"));

    }


    @SuppressWarnings("unchecked")
    public JSONObject handleMeetingNoteJSONObject(MeetingNoteEntity note) throws JSONException {

        JSONObject object = new JSONObject();
        object.put("creationDate", String.valueOf(note.getNoteDateCreation()));
        object.put("estimatedTransportTime", String.valueOf(note.getEstimatedTransportTime()));
        object.put("meetingAgenda", note.getMeetingAgenda());
        object.put("meetingNoteDate", String.valueOf(note.getMeetingNoteDate()));
        object.put("meetingPlace", note.getMeetingPlace());
        object.put("meetingTitle", note.getMeetingTitle());
        object.put("noteID", note.getNoteId());
        object.put("noteType", note.getNoteType());
        object.put("userID", note.getUserId());
        object.put("isDone", note.isDone());
        object.put("isTextCategorized", note.isTextCategorized());

        return object;
    }

    @SuppressWarnings("unchecked")
    public JSONObject handleOrdinaryNoteJSONObject(OrdinaryNoteEntity note) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("noteContent", note.getNoteContent());
        object.put("creationDate", String.valueOf(note.getNoteDateCreation()));
        object.put("noteID", note.getNoteId());
        object.put("noteType", note.getNoteType());
        object.put("userID", note.getUserId());
        object.put("isDone", note.isDone());
        object.put("isTextCategorized", note.isTextCategorized());

        return object;
    }

    @SuppressWarnings("unchecked")
    public JSONObject handleDeadLineNoteJSONObject(DeadlineNoteEntity note) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("deadLineDate", String.valueOf(note.getDeadLineDate()));
        object.put("deadLineTitle", note.getDeadLineTitle());
        object.put("progressPercentage", String.valueOf(note.getProgressPercentage()));

        object.put("creationDate", String.valueOf(note.getNoteDateCreation()));
        object.put("noteID", note.getNoteId());
        object.put("noteType", note.getNoteType());
        object.put("userID", note.getUserId());
        object.put("isDone", String.valueOf(note.isDone()));
        object.put("isTextCategorized", String.valueOf(note.isTextCategorized()));

        return object;
    }

    @SuppressWarnings("unchecked")
    public JSONObject handleShoppingNoteJSONObject(ShoppingNoteEntity note) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("productToBuy", note.getProductToBuy());
        object.put("productCategory", note.getProductCategory());
        object.put("creationDate", String.valueOf(note.getNoteDateCreation()));
        object.put("noteID", note.getNoteId());
        object.put("noteType", note.getNoteType());
        object.put("userID", note.getUserId());
        object.put("isDone", note.isDone());
        object.put("isTextCategorized", note.isTextCategorized());

        return object;
    }
}
