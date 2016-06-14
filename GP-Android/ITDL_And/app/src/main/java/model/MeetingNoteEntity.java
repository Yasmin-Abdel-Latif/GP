package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by samah on 25/03/2016.
 */
public class MeetingNoteEntity extends NoteEntity {
    protected Timestamp meetingNoteDate;
    protected Time estimatedTransportTime;
    protected String meetingTitle;
    protected String meetingPlace;
    protected String meetingAgenda;

    public MeetingNoteEntity(String noteType,String Priority, Timestamp noteDateCreation,
                             boolean isDone, boolean isActive, boolean isTextCategorized, boolean isSynchronized,
                        String meetingtitle ,String meetingplace,String agenda, Timestamp meetingNoteDate,Time estimatedTransportTime) {
        super(noteType,Priority , noteDateCreation, isDone, isActive, isTextCategorized, isSynchronized);

        this.estimatedTransportTime=estimatedTransportTime;
        this.meetingPlace=meetingplace;
        this.meetingAgenda=agenda;
        this.meetingTitle=meetingtitle;
        this.meetingNoteDate=meetingNoteDate;
    }
    public MeetingNoteEntity(String noteType,String Priority,
                             String meetingtitle ,String meetingplace,String agenda, Timestamp meetingNoteDate,Time estimatedTransportTime, int id) {
        super(noteType,Priority , id);

        this.estimatedTransportTime=estimatedTransportTime;
        this.meetingPlace=meetingplace;
        this.meetingAgenda=agenda;
        this.meetingTitle=meetingtitle;
        this.meetingNoteDate=meetingNoteDate;
    }
    public MeetingNoteEntity(String noteType,String Priority, String meetingtitle ,int id){
        super(noteType,Priority,id);
        meetingTitle=meetingtitle;
    }
    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public String getMeetingAgenda() {
        return meetingAgenda;
    }

    public void setMeetingAgenda(String meetingAgenda) {
        this.meetingAgenda = meetingAgenda;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public Time getEstimatedTransportTime() {
        return estimatedTransportTime;
    }

    public void setEstimatedTransportTime(Time estimatedTransportTime) {
        this.estimatedTransportTime = estimatedTransportTime;
    }

    public Timestamp getMeetingNoteDate() {
        return meetingNoteDate;
    }

    public void setMeetingNoteDate(Timestamp meetingNoteDate) {
        this.meetingNoteDate = meetingNoteDate;
    }

    @Override
    public String toString() {
        JSONObject obj= new JSONObject();
        try {
            obj.put("meetingNoteDate",meetingNoteDate);
            obj.put("estimatedTransportTime",estimatedTransportTime);
            obj.put("meetingTitle",meetingTitle);
            obj.put("meetingAgenda",meetingAgenda);
            obj.put("meetingPlace",meetingPlace);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String h =obj.toString();
       h= h.substring(0,h.length()-1);
        return h+","+super.toString() ;
    }
}
