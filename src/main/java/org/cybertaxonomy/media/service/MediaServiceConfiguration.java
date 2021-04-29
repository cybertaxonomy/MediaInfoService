/**
* Copyright (C) 2021 EDIT
* European Distributed Institute of Taxonomy
* http://www.e-taxonomy.eu
*
* The contents of this file are subject to the Mozilla Public License Version 1.1
* See LICENSE.TXT at the top of this package for the full license terms.
*/
package org.cybertaxonomy.media.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Configuration to let Spring load PropertySources.
 *
 * This is for example being used to inject the value
 * of <code>MedataInfoController.mediaHome</code>
 *
 * @author a.kohlbecker
 * @since Mar 11, 2021
 */
@Configuration
public class MediaServiceConfiguration {

    /**
     * Defines <code>@null</code> as string for configuring
     * null values in for example application.properties.
     */
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourceConfigurer() {
        PropertySourcesPlaceholderConfigurer propertySourceConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourceConfigurer.setNullValue("@null");
        return propertySourceConfigurer;

    }

}
