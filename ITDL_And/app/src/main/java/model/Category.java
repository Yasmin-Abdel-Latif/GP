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
    double categoryPercentage;

    public Category(String categoryName) {
        this.categoryName = categoryName;
        categoryPercentage = 0;
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

    public double getCategoryPercentage() {
        return categoryPercentage;
    }

    public void setCategoryPercentage(double categoryPercentage) {
        this.categoryPercentage = categoryPercentage;
    }
}
