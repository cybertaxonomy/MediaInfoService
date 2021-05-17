/**
* Copyright (C) 2021 EDIT
* European Distributed Institute of Taxonomy
* http://www.e-taxonomy.eu
*
* The contents of this file are subject to the Mozilla Public License Version 1.1
* See LICENSE.TXT at the top of this package for the full license terms.
*/
package org.cybertaxonomy.media.info.repository;

import java.io.IOException;

import org.cybertaxonomy.media.info.model.MediaInfo;
import org.ehcache.Cache;
import org.ehcache.spi.loaderwriter.CacheLoadingException;
import org.ehcache.spi.loaderwriter.CacheWritingException;

/**
 * @author a.kohlbecker
 * @since Apr 27, 2021
 */
public interface IMediaInfoCache {

    Cache<String, MediaInfo> getCache() throws IOException;

    /**
     * @return
     * @throws IOException
     */
    String getStoragePath() throws IOException;

    void put(String relativeFilePath, MediaInfo mediaInfo) throws CacheWritingException, IOException;

    MediaInfo lookup(String relativeFilePath) throws CacheLoadingException, IOException;

}