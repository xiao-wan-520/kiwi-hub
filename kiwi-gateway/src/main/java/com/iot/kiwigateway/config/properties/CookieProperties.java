package com.iot.kiwigateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Cookie属性
 * @author wan
 */
@Data
@Component
@ConfigurationProperties(prefix = "server.servlet.session.cookie")
public class CookieProperties {
    private String path;
    private String name;
}
