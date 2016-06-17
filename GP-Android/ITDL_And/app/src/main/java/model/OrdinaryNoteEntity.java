package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Created by samah on 22/03/2016.
 */
public class OrdinaryNoteEntity extends NoteEntity {
    private String noteContent;

    public OrdinaryNoteEntity(String noteType, String notePriority, String noteContent, int id) {
        super(noteType, notePriority, id);
        this.noteContent = noteContent;

    }

    public OrdinaryNoteEntity(long noteID, long userID, Timestamp creationDate, boolean isDone,
                              boolean isTextCategorized, String noteType, String noteContent) {
        super(noteID, userID, creationDate, isDone, isTextCategorized, noteType);
        this.noteContent = noteContent;
    }

    public OrdinaryNoteEntity(String noteType, String notePriority, Timestamp noteDateCreation,
                              boolean isDone, boolean isActive, boolean isTextCategorized, String noteContent, boolean isSync) {
        super(noteType, notePriority, noteDateCreation, isDone, isActive, isTextCategorized, isSync);
        this.noteContent = noteContent;

    }


    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("noteContent", noteContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String h = obj.toString();
        h = h.substring(0, h.length() - 1);
        return h + "," + super.toString();
    }
}
