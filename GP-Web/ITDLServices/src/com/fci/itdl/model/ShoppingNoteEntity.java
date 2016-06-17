package com.fci.itdl.model;

import java.sql.Timestamp;

public class ShoppingNoteEntity extends NoteEntity {

	private String productToBuy;
	private String productCategory;

	public String getProductToBuy() {
		return productToBuy;
	}

	public ShoppingNoteEntity(String noteID, String userID, Timestamp creationDate, boolean isDone,
			boolean isTextCategorized, String noteType, String productToBuy, String productCategory) {
		super(noteID, userID, creationDate, isDone, isTextCategorized, noteType);
		this.productToBuy = productToBuy;
		this.productCategory = productCategory;
		// TODO Auto-generated constructor stub
	}

	public void setProductToBuy(String productToBuy) {
		this.productToBuy = productToBuy;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public ShoppingNoteEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ShoppingNoteEntity [productToBuy=" + productToBuy + ", productCategory=" + productCategory + ", noteID="
				+ noteID + ", userID=" + userID + ", creationDate=" + creationDate + ", isDone=" + isDone
				+ ", isTextCategorized=" + isTextCategorized + ", noteType=" + noteType + "]";
	}

}
