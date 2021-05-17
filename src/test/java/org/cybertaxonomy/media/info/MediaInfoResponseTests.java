/**
* Copyright (C) 2021 EDIT
* European Distributed Institute of Taxonomy
* http://www.e-taxonomy.eu
*
* The contents of this file are subject to the Mozilla Public License Version 1.1
* See LICENSE.TXT at the top of this package for the full license terms.
*/
package org.cybertaxonomy.media.info;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.cybertaxonomy.media.info.model.MediaInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author a.kohlbecker
 * @since May 17, 2021
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MediaInfoResponseTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void readImageInfoNoCache() {
        ResponseEntity<MediaInfo> response = restTemplate.getForEntity("/info?file={filePath}", MediaInfo.class,
                "DR-03939__10.jpg");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().hasFieldOrPropertyWithValue("bitPerPixel", 24)
                .hasFieldOrPropertyWithValue("colorType", "YCbCr").hasFieldOrPropertyWithValue("extension", "JPEG")
                .hasFieldOrPropertyWithValue("formatName", "JPEG").hasFieldOrPropertyWithValue("height", 2304)
                .hasFieldOrPropertyWithValue("mimeType", "image/jpeg").hasFieldOrPropertyWithValue("width", 3456);
        assertThat(response.getBody().getMetaData()).isNotNull().isNotEmpty().containsKey("Copyright Notice")
                .extractingByKeys(new String[]{"Copyright Notice"}).contains(Arrays.asList("CC-BY-SA"));
    }

}
