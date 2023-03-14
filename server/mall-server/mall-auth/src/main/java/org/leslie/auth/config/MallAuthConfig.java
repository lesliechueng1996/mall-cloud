package org.leslie.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author zhang
 * date created in 2023/2/25 23:13
 */
@ConfigurationProperties(prefix = "mall-auth")
@RefreshScope
@Component
@Data
public class MallAuthConfig {

    private String webClientId;
    private String webClientSecret;
    private String webRedirectUrl;
    private String webRootUsername;
    private String webRootPassword;
    private Integer accessTokenLiveSeconds;
    private Integer refreshTokenLiveSeconds;
}
