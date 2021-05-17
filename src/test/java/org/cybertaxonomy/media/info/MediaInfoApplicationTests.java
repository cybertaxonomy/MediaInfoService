package org.cybertaxonomy.media.info;

import org.cybertaxonomy.media.info.service.IMediaInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MediaInfoApplicationTests {

    @Autowired
    private IMediaInfoService service;

	@Test
	void contextLoads() {
	}

}
