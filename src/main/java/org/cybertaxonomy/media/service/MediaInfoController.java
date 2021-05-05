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

import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.GenericImageMetadata.GenericImageMetadataItem;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.ehcache.spi.loaderwriter.CacheLoadingException;
import org.ehcache.spi.loaderwriter.CacheWritingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author a.kohlbecker
 * @since Mar 11, 2021
 */

@RestController
public class MediaInfoController {

    private static Logger logger = LoggerFactory.getLogger(MediaInfoController.class);

    @Value("${mediaHome}")
    private String mediaHome;

    @Autowired
    private IMediaInfoCache cache;

    @GetMapping("/info")
    public MediaInfo doInfo(
            @RequestParam(value = "file", required = true) String relativePath,
            @RequestParam(value = "refresh", required = false) Boolean refreshCache
            ) throws IOException {

        MediaInfo mediaInfo = null;

        if(refreshCache == null || !refreshCache) {
            try {
                mediaInfo = cache.lookup(relativePath);
                if(mediaInfo != null) {
                    logger.debug("MediaInfo for " + relativePath + " read from cache");
                }
            } catch (CacheLoadingException | IOException e1) {
                logger.warn("Cannot use cache for lookup", e1);
            }
        }

        if(mediaInfo == null) {
            try {
                mediaInfo = readImageInfo(relativePath);
            } catch (IOException e) {
                logger.error("Error reading image info", e);
                throw e;
            }
            try {
                if(mediaInfo != null) {
                    cache.put(relativePath, mediaInfo);
                    logger.debug("MediaInfo for " + relativePath + " put in to cache");
                }
            } catch (CacheWritingException | IOException e) {
                logger.warn("Cannot write to cache ", e);
            }
        }
        return mediaInfo;
    }

    private MediaInfo readImageInfo(String relativePath) throws IOException {

        InputStream inputStream;
        MediaInfo metadata = new MediaInfo();
        try {
            File mediaFile = new File(mediaHome + File.separator + relativePath);
            logger.debug("processing request for: " + mediaFile.getAbsolutePath());
            inputStream = new FileInputStream(mediaFile);
            ImageInfo imageInfo = Imaging.getImageInfo(inputStream, null);
            ImageFormat imageFormat = imageInfo.getFormat();
            metadata.setSize(mediaFile.length());
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
                    if(text != null && !text.isEmpty()) {
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
            logger.error("Could not read: " + relativePath + ". " + e.getMessage());
            throw new IOException(e);
        }
        return metadata;
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementFoundException(IOException exception) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(exception.getMessage());
    }

}
