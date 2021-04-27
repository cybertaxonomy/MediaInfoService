/**
* Copyright (C) 2021 EDIT
* European Distributed Institute of Taxonomy
* http://www.e-taxonomy.eu
*
* The contents of this file are subject to the Mozilla Public License Version 1.1
* See LICENSE.TXT at the top of this package for the full license terms.
*/
package org.cybertaxonomy.media.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.GenericImageMetadata.GenericImageMetadataItem;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author a.kohlbecker
 * @since Mar 11, 2021
 */

@RestController
public class MediaInfoController {

    protected static final Logger LOG = Logger.getLogger(MediaInfoController.class.getName());

    @Value("${mediaHome}")
    private String mediaHome;

    @GetMapping("/info")
    public MediaInfo doInfo(@RequestParam(value = "file", required = true) String relativePath) {
        try {
            return readImageInfo(relativePath);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error reading image info", e);
            return null;
        }
    }

    private MediaInfo readImageInfo(String relativePath) throws IOException {

        InputStream inputStream;
        MediaInfo metadata = new MediaInfo();
        try {
            File mediaFile = new File(mediaHome + File.separator + relativePath);
            LOG.log(Level.INFO, "processing request for: " + mediaFile.getAbsolutePath());
            inputStream = new FileInputStream(mediaFile);
            ImageInfo imageInfo = Imaging.getImageInfo(inputStream, null);
            ImageFormat imageFormat = imageInfo.getFormat();
            metadata.setFormatName(imageFormat.getName());
            metadata.setExtension(imageFormat.getExtension());
            metadata.setMimeType(imageInfo.getMimeType());
            metadata.setWidth(imageInfo.getWidth());
            metadata.setHeight(imageInfo.getHeight());
            metadata.setBitPerPixel(imageInfo.getBitsPerPixel());
            metadata.setColorType(imageInfo.getColorType().name());

            inputStream.close();
            inputStream = new FileInputStream(mediaFile);


            ImageMetadata imageMetaData = Imaging.getMetadata(inputStream, null);
            Map<String, List<String>> mdMap = metadata.getMetaData();
            for (ImageMetadataItem item : imageMetaData.getItems()) {
                if (GenericImageMetadataItem.class.isAssignableFrom(item.getClass())) {
                    // no interest in GIFImageMetadata since this only includes
                    // positioning information etc
                    GenericImageMetadataItem gim = (GenericImageMetadataItem) item;
                    String key = gim.getKeyword();
                    String text = gim.getText();
                    if(text != null && !text.isBlank()) {
                        if (!mdMap.containsKey(key))  {
                            mdMap.put(key, new ArrayList<>());
                        }
                        if(!mdMap.get(gim.getKeyword()).stream().anyMatch(t -> t.equals(text))) {
                            // add if no duplicate
                            mdMap.get(gim.getKeyword()).add(text);
                        }
                    }
                }
            }
            inputStream.close();
        } catch (ImageReadException e) {
            Logger.getLogger(this.getClass().toString()).log(Level.SEVERE,
                    "Could not read: " + relativePath + ". " + e.getMessage());
            throw new IOException(e);
        }
        return metadata;
    }

}
