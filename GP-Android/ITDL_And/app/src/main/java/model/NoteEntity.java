package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by samah on 21/03/2016.
 */
public abstract class NoteEntity implements Serializable {

    protected int localNoteId;
    protected long userId;
    protected long servernoteId;
    protected Timestamp noteDateCreation;
    protected String notePriority;
    protected String noteType;
    protected boolean isDone;
    protected boolean isDeleted;
    protected boolean isTextCategorized;
    boolean ischecked;

    public NoteEntity(long noteID, long userID, Timestamp creationDate, boolean isDone, boolean isTextCategorized,
                      String noteType) {
        super();

        this.servernoteId = noteID;
        this.userId = userID;
        this.noteDateCreation = creationDate;
        this.isDone = isDone;
        this.isTextCategorized = isTextCategorized;
        this.noteType = noteType;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setIsAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    protected boolean isAdded;
    protected boolean isUpdated;

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("localNoteId", localNoteId);
            obj.put("noteDateCreation", noteDateCreation);
            obj.put("notePriority", notePriority);
            obj.put("noteType", noteType);
            obj.put("isDone", isDone);
            obj.put("isDeleted", isDeleted);
            obj.put("isAdded", isAdded);
            obj.put("isUpdated", isUpdated);
            obj.put("isTextCategorized", isTextCategorized);
            obj.put("userId", userId);
            obj.put("serverNoteId", servernoteId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString().substring(1);
    }

    public long getServernoteId() {
        return servernoteId;
    }

    public void setServernoteId(long servernoteId) {
        this.servernoteId = servernoteId;
    }

    public NoteEntity(String noteType, String notePriority, int id) {
        this.noteType = noteType;
        this.notePriority = notePriority;
        this.localNoteId = id;
    }

    public NoteEntity(String noteType, long userId, Timestamp noteDateCreation,
                      boolean isDone, boolean isDeleted, boolean isTextCategorized, boolean isAdded) {
        this.noteDateCreation = noteDateCreation;
        this.noteType = noteType;
        this.isDeleted = isDeleted;
        this.isDone = isDone;
        this.isTextCategorized = isTextCategorized;
        this.userId = userId;
        this.isAdded = isAdded;
    }

    public NoteEntity(String noteType, String notePriority, Timestamp noteDateCreation,
                      boolean isDone, boolean isDeleted, boolean isTextCategorized, boolean isAdded) {
        this.noteDateCreation = noteDateCreation;
        this.noteType = noteType;
        this.isDeleted = isDeleted;
        this.isDone = isDone;
        this.isTextCategorized = isTextCategorized;
        this.notePriority = notePriority;
        this.isAdded = isAdded;
    }

    public int getNoteId() {
        return localNoteId;
    }

    public void setNoteId(int noteId) {
        this.localNoteId = noteId;
    }

    public Timestamp getNoteDateCreation() {
        return noteDateCreation;
    }

    public void setNoteDateCreation(Timestamp noteDateCreation) {
        this.noteDateCreation = noteDateCreation;
    }

    public String getNotePriority() {
        return notePriority;
    }

    public void setNotePriority(String notePriority) {
        this.notePriority = notePriority;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isTextCategorized() {
        return isTextCategorized;
    }

    public void setIsTextCategorized(boolean isTextCategorized) {
        this.isTextCategorized = isTextCategorized;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean ischecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }
}
