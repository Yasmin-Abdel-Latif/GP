package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by samah on 18/05/2016.
 */
public class Category implements Serializable {
    String categoryName;
    long userId;
    int categoryPercentage;

    public Category(String categoryName) {
        this.categoryName = categoryName;
        categoryPercentage=0;
    }

    public boolean ischecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    boolean ischecked;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userid) {
        this.userId = userid;
    }

    public int getCategoryPercentage() {
        return categoryPercentage;
    }

    public void setCategoryPercentage(int categoryPercentage) {
        this.categoryPercentage = categoryPercentage;
    }

    @Override
    public String toString() {
        JSONObject obj= new JSONObject();
        try {
            obj.put("categoryName",categoryName);
            obj.put("userId",userId);
            obj.put("categoryPercentage",categoryPercentage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();


    }
}
