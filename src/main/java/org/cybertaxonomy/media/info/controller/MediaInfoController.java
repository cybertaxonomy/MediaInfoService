/**
* Copyright (C) 2021 EDIT
* European Distributed Institute of Taxonomy
* http://www.e-taxonomy.eu
*
* The contents of this file are subject to the Mozilla Public License Version 1.1
* See LICENSE.TXT at the top of this package for the full license terms.
*/
package org.cybertaxonomy.media.info.controller;

import java.io.IOException;

import org.cybertaxonomy.media.info.model.MediaInfo;
import org.cybertaxonomy.media.info.service.IMediaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IMediaInfoService mediaInfoService;

    @GetMapping("/info")
    public MediaInfo doInfo(
            @RequestParam(value = "file", required = true) String relativePath,
            @RequestParam(value = "refresh", required = false) Boolean refreshCache
            ) throws IOException {

        MediaInfo mediaInfo = mediaInfoService.readImageInfo(relativePath, refreshCache);
        return mediaInfo;
    }


    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementFoundException(IOException exception) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(exception.getMessage());
    }

}
