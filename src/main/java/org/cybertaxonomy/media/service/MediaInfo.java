/**
* Copyright (C) 2021 EDIT
* European Distributed Institute of Taxonomy
* http://www.e-taxonomy.eu
*
* The contents of this file are subject to the Mozilla Public License Version 1.1
* See LICENSE.TXT at the top of this package for the full license terms.
*/
package org.cybertaxonomy.media.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author a.kohlbecker
 * @since Mar 11, 2021
 */
public class MediaInfo {

    private int width;
    private int height;
    private int bitPerPixel;
    private String colorType;
    private String formatName;
    private String extension;
    private String mimeType;
    private Map<String, List<String>> metaData = new HashMap<>();

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

    public int getBitPerPixel() {
        return bitPerPixel;
    }

    public void setBitPerPixel(int bitPerPixel) {
        this.bitPerPixel = bitPerPixel;
    }

    public Map<String, List<String>> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, List<String>> metaData) {
        this.metaData = metaData;
    }


    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public String getExtenstion() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }



}
