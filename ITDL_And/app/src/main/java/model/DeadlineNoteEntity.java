package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Created by samah on 04/04/2016.
 */
public class DeadlineNoteEntity extends NoteEntity {

    private int progressPercentage;
    private String deadLineTitle;
    private Timestamp deadLineDate;

    public DeadlineNoteEntity(String noteType, String priority, String deadLineTitle, int id) {
        super(noteType, priority, id);
        this.deadLineTitle = deadLineTitle;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getDeadLineTitle() {
        return deadLineTitle;
    }

    public void setDeadLineTitle(String deadLineTitle) {
        this.deadLineTitle = deadLineTitle;
    }

    public Timestamp getDeadLineDate() {
        return deadLineDate;
    }

    public void setDeadLineDate(Timestamp deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    public DeadlineNoteEntity(

            int progressPercentage,
            String deadLineTitle,
            Timestamp deadLineDate,
            long noteID,
            long userID,
            Timestamp creationDate,
            boolean isDone,
            boolean isTextCategorized,
            String noteType) {
        super(noteID, userID, creationDate, isDone, isTextCategorized, noteType);

        this.progressPercentage = progressPercentage;
        this.deadLineTitle = deadLineTitle;
        this.deadLineDate = deadLineDate;
    }

    public DeadlineNoteEntity(String noteType, Timestamp noteDateCreation, boolean isDone,
                              boolean isActive, boolean isTextCategorized, boolean isSynchronized,
                              int progressPercentage, String deadLineTitle, Timestamp deadLineDate, String priority) {
        super(noteType, priority, noteDateCreation, isDone, isActive, isTextCategorized, isSynchronized);

        this.progressPercentage = progressPercentage;
        this.deadLineTitle = deadLineTitle;
        this.deadLineDate = deadLineDate;
    }


    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("progressPercentage", progressPercentage);
            obj.put("deadLineTitle", deadLineTitle);
            obj.put("deadLineDate", deadLineDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String h = obj.toString();
        h = h.substring(0, h.length() - 1);
        return h + "," + super.toString();

    }
}
