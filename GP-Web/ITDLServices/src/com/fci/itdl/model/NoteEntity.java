package com.fci.itdl.model;

import java.sql.Timestamp;

public abstract class NoteEntity {
	protected String noteID;
	protected String userID;
	protected Timestamp creationDate;
	protected boolean isDone;
	protected boolean isActive;
	protected boolean isTextCategorized;
	protected String noteType;
	
	public NoteEntity() {
		super();
	}
	
	public NoteEntity(String noteID, String userID, Timestamp creationDate, boolean isDone, boolean isTextCategorized,
			String noteType ) {
		super();
		
		this.noteID = noteID;
		this.userID = userID;
		this.creationDate = creationDate;
		this.isDone = isDone;
		this.isTextCategorized = isTextCategorized;
		this.noteType = noteType;
	}
	
	public String getNoteID() {
		return noteID;
	}
	public void setNoteID(String noteID) {
		this.noteID = noteID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public boolean isDone() {
		return isDone;
	}
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	public boolean isTextCategorized() {
		return isTextCategorized;
	}
	public void setTextCategorized(boolean isTextCategorized) {
		this.isTextCategorized = isTextCategorized;
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	

}
