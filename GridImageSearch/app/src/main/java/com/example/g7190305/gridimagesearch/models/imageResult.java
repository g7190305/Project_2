package com.example.g7190305.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by g7190305 on 2015/8/1.
 */
public class imageResult implements Serializable {
    private final static long serialVersionUID = 0L;
    public String fullUrl;
    public String thumbUrl;
    public String title;
    public int width;
    public int height;

    public imageResult(JSONObject jsonObject) {
        try {
            this.fullUrl = jsonObject.getString("url");
            this.thumbUrl = jsonObject.getString("tbUrl");
            this.title = jsonObject.getString("title");
            this.width = jsonObject.getInt("width");
            this.height = jsonObject.getInt("height");
        } catch (JSONException e) {
        e.printStackTrace();
        }
    }

    public static ArrayList<imageResult> fromJSONArray(JSONArray array) {
        ArrayList<imageResult> results = new ArrayList<imageResult>();
        for(int i=0; i<array.length(); i++) {
            try {
                results.add(new imageResult(array.getJSONObject(i)) );
            } catch( JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thjumbUrl) {
        this.thumbUrl = thjumbUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
