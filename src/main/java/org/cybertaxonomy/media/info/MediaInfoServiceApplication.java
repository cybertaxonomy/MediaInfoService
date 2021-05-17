package org.cybertaxonomy.media.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication(scanBasePackages = {
        "org.cybertaxonomy.media.info.repository",
        "org.cybertaxonomy.media.info.service",
        "org.cybertaxonomy.media.info.controller"
        })
public class MediaInfoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaInfoServiceApplication.class, args);
	}

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
