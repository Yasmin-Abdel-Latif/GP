package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Created by samah on 23/03/2016.
 */
public class ShoppingNoteEntity extends NoteEntity {

    private String productToBuy;
    private String productCategory;

    public ShoppingNoteEntity(String noteType, String notePriority, Timestamp noteDateCreation, boolean isDone, boolean isActive,
                              boolean isTextCategorized, boolean isSynchronized, String productToBuy, String productCategory ) {
        super(noteType, notePriority, noteDateCreation, isDone, isActive, isTextCategorized, isSynchronized);
        this.productToBuy = productToBuy;
        this.productCategory = productCategory;
    }
    public ShoppingNoteEntity(String noteType, String notePriority,String productToBuy,int noteid){
        super(noteType,notePriority,noteid);
this .productToBuy=productToBuy;
    }


    public String getProductToBuy() {
        return productToBuy;
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

    @Override
    public String toString() {
        JSONObject obj= new JSONObject();
        JSONObject object = new JSONObject();

        try {
            obj.put("productToBuy",productToBuy);
            obj.put("productCategory",productCategory);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String h =obj.toString();
        h=h.substring(0, h.length() - 1);
        return h+","+super.toString() ;
    }
}
