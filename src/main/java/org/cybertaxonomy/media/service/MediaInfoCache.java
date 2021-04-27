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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.spi.loaderwriter.CacheLoadingException;
import org.ehcache.spi.loaderwriter.CacheWritingException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author a.kohlbecker
 * @since Apr 27, 2021
 */
@Component
public class MediaInfoCache implements IMediaInfoCache, DisposableBean {

    private static String CACHE_NAME = "default";

    private Path cacheFile;

    CacheManager cacheManager;

    private void initCache() throws IOException {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(new File(getStoragePath(), "myData")))
                .withCache(
                        CACHE_NAME,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, MediaInfo.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(100, EntryUnit.ENTRIES)
                                .offheap(1, MemoryUnit.MB)
                                .disk(20, MemoryUnit.MB, true)
                            )
                            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(1)))
                        )
                .build(true);
    }

    /**
     * @return
     * @throws IOException
     */
    @Override
    public String getStoragePath() throws IOException {
        if(cacheFile == null) {
            cacheFile = Files.createTempDirectory("MediaInfoCache-");
        }
        return cacheFile.getFileName().toString();
    }

    @Override
    public Cache<String, MediaInfo> getCache() throws IOException {
        if(cacheManager == null) {
            initCache();
        }
        return cacheManager.getCache(CACHE_NAME, String.class, MediaInfo.class);
    }

    @Override
    public MediaInfo lookup(String relativeFilePath) throws CacheLoadingException, IOException {
       return getCache().get(relativeFilePath);
    }

    @Override
    public void put(String relativeFilePath, MediaInfo mediaInfo) throws CacheWritingException, IOException {
        getCache().put(relativeFilePath, mediaInfo);
    }

    @Override
    public void destroy() throws Exception {
        cacheManager.close();
    }

}
