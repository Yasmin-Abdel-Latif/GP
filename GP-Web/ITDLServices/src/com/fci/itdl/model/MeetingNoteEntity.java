package com.fci.itdl.model;

import java.sql.Time;
import java.sql.Timestamp;

public class MeetingNoteEntity extends NoteEntity {

	

	private Timestamp meetingNoteDate; 
	private Time estimatedTransportTime;	
	private String meetingTitle;
	private String meetingPlace;
	private String meetingAgenda;
	
	
	
	@Override
	public String toString() {
		return "MeetingNoteEntity [meetingNoteDate=" + meetingNoteDate + ", estimatedTransportTime="
				+ estimatedTransportTime + ", meetingTitle=" + meetingTitle + ", meetingPlace=" + meetingPlace
				+ ", meetingAgenda=" + meetingAgenda + ", noteID=" + noteID + ", userID=" + userID + ", creationDate="
				+ creationDate + ", isDone=" + isDone + ", isActive=" + isActive + ", isTextCategorized="
				+ isTextCategorized + ", noteType=" + noteType + "]";
	}

	public MeetingNoteEntity() {
		super();
	}
	
	public MeetingNoteEntity(
			Timestamp meetingNoteDate, 
			Time estimatedTransportTime, 
			String meetingTitle,
			String meetingPlace, 
			String meetingAgenda, 
			String noteID,
			String userID, 
			Timestamp creationDate,
			boolean isDone, 
			boolean isTextCategorized,
			String noteType) {
		super(noteID,userID,creationDate,isDone,isTextCategorized,noteType);
		this.meetingNoteDate = meetingNoteDate;
		this.estimatedTransportTime = estimatedTransportTime;
		this.meetingTitle = meetingTitle;
		this.meetingPlace = meetingPlace;
		this.meetingAgenda = meetingAgenda;
		
	}

	public Timestamp getmeetingNoteDate() {
		return meetingNoteDate;
	}
	public void setmeetingNoteDate(Timestamp meetingNoteDate) {
		this.meetingNoteDate = meetingNoteDate;
	}
	public Time getEstimatedTransportTime() {
		return estimatedTransportTime;
	}
	public void setEstimatedTransportTime(Time estimatedTransportTime) {
		this.estimatedTransportTime = estimatedTransportTime;
	}
	public String getMeetingTitle() {
		return meetingTitle;
	}
	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
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
	
	
	
	

}
