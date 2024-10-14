package io.devsense.basic_rest_service.config;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("storage")
@Component
public class StorageProperties {

    private String location ="/Users/manas/works/spring-guides/basic-rest-service/src/main/resources/upload-dir";

}
