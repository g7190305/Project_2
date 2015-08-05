package com.example.g7190305.gridimagesearch.models;

import java.io.Serializable;

/**
 * Created by g7190305 on 2015/8/3.
 */
public class SetupInfo implements Serializable{
    private String imageSize;
    private String colorFilter;
    private String imageType;
    private String siteFilter;

    public SetupInfo(String imageSize, String colorFilter, String imageType, String siteFilter) {
        this.imageSize = imageSize;
        this.colorFilter = colorFilter;
        this.imageType = imageType;
        this.siteFilter = siteFilter;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }
}
