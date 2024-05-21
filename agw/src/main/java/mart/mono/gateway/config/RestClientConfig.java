package mart.mono.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ConfigDataLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(@Value("${spring.config.import}") String importLocation) {
        ConfigDataLocation configDataLocation = ConfigDataLocation.of(importLocation);
        return RestClient.builder()
            .baseUrl(configDataLocation.getNonPrefixedValue("configserver:"))
            .build();
    }
}
