/**
* Copyright (C) 2021 EDIT
* European Distributed Institute of Taxonomy
* http://www.e-taxonomy.eu
*
* The contents of this file are subject to the Mozilla Public License Version 1.1
* See LICENSE.TXT at the top of this package for the full license terms.
*/
package org.cybertaxonomy.media.info.service;

import java.io.IOException;

import org.cybertaxonomy.media.info.model.MediaInfo;

/**
 * @author a.kohlbecker
 * @since May 17, 2021
 */
public interface IMediaInfoService {

    MediaInfo readImageInfo(String relativePath, Boolean refreshCache) throws IOException;

}