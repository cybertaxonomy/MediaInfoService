# Media Information Service

A simple and lightweight service to make media information and metadata accessible as REST service.

It can for example be used to complement image servers like [digilib](https://robcast.github.io/digilib/).   

Includes a 3 tier cache for snappy responsiveness.

### Web service

There is one REST service end point by now:

`/info?file=${filePath}`

Optional query parameters: 

* `refresh=true`: update the cache with the media metadata for the file specified.


Example response:

~~~json
{
    "bitPerPixel": 24,
    "colorType": "YCbCr",
    "formatName": "JPEG (Joint Photographic Experts Group) Format",
    "height": 1024,
    "metaData": {
        "Copyright Notice": "A. Charalambous",
        "DateTime": "'2020:03:07 10:59:29'",
        "ExifOffset": "2242",
        "Keywords": null,
        "Orientation": "1",
        "Padding": null,
        "ProcessingSoftware": "'Windows Photo Editor 10.0.10011.16384'",
        "Software": "'Windows Photo Editor 10.0.10011.16384'"
    },
    "mimeType": "image/jpeg",
    "width": 576
}
~~~

### Limitations

Currently only image files are supported.

### Setup

The service expects one mandatory configuration parameter

* `mediaHome`: The root folder where the media files are found.

Optional configuration parameter

* `cacheExpirationMinutes` or `cacheExpirationHours`: These are exclusive options, `cacheExpirationHours` is set to **24 hours** per default.

For more details on the principle options of how to set the configuration property see [https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config)
  
#### Tomcat

in 

<Context>
  ...
  <Parameter name="companyName" value="My Company, Incorporated" override="false"/>
  ...
</Context>

### Build Tools Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.3/maven-plugin/reference/html/#build-image)

