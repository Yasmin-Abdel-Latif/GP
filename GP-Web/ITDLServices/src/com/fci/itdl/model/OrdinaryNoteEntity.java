package com.fci.itdl.model;

import java.sql.Timestamp;

public class OrdinaryNoteEntity extends NoteEntity {

	private String noteContent;

	public OrdinaryNoteEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrdinaryNoteEntity(String noteID, String userID, Timestamp creationDate, boolean isDone,
			boolean isTextCategorized, String noteType, String noteContent) {
		super(noteID, userID, creationDate, isDone, isTextCategorized, noteType);
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
		return "OrdinaryNoteEntity [noteContent=" + noteContent + ", noteID=" + noteID + ", userID=" + userID
				+ ", creationDate=" + creationDate + ", isDone=" + isDone + ", isTextCategorized=" + isTextCategorized
				+ ", noteType=" + noteType + "]";
	}

}
