/**
* Copyright (C) 2022 EDIT
* European Distributed Institute of Taxonomy
* http://www.e-taxonomy.eu
*
* The contents of this file are subject to the Mozilla Public License Version 1.1
* See LICENSE.TXT at the top of this package for the full license terms.
*/
package org.cybertaxonomy.media.info.exceptions;

import org.apache.commons.imaging.ImageReadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "ImageReadException")
public class ImageFileReadException extends RuntimeException {

    public ImageFileReadException(ImageReadException e) {
        super(e);
    }

    private static final long serialVersionUID = -1L;
}

