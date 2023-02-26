package org.leslie.auth.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.leslie.auth.config.MallAuthConfig;
import org.leslie.auth.entity.Admin;
import org.leslie.auth.entity.Oauth2RegisteredClient;
import org.leslie.auth.enums.AdminStatus;
import org.leslie.auth.repository.AdminRepository;
import org.leslie.auth.repository.Oauth2RegisteredClientRepository;
import org.leslie.auth.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zhang
 * date created in 2023/2/25 23:01
 */
@Component
@Slf4j
public class ProgramStartTask implements CommandLineRunner {

    private final Oauth2RegisteredClientRepository oauth2RegisteredClientRepository;
    private final AdminRepository adminRepository;
    private final MallAuthConfig mallAuthConfig;
    private final ObjectMapper objectMapper;
    private final PasswordUtil passwordUtil;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    private static final String NONE = "none";

    public ProgramStartTask(Oauth2RegisteredClientRepository oauth2RegisteredClientRepository, AdminRepository adminRepository,
                            MallAuthConfig mallAuthConfig, ObjectMapper objectMapper, PasswordUtil passwordUtil) {
        this.oauth2RegisteredClientRepository = oauth2RegisteredClientRepository;
        this.adminRepository = adminRepository;
        this.mallAuthConfig = mallAuthConfig;
        this.objectMapper = objectMapper;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Program Start Task begin");
        this.insertDefaultOauth2Client();
        this.insertDefaultAdmin();
    }

    private void insertDefaultOauth2Client() throws JsonProcessingException {
        log.info("ddlAuto: {}", ddlAuto);
        if (NONE.equals(ddlAuto)) {
            log.info("Don't insert default oauth2 client into db");
            return;
        }

        String webClientId = mallAuthConfig.getWebClientId();
        if (!oauth2RegisteredClientRepository.existsByClientId(webClientId)) {
            Oauth2RegisteredClient webClient = new Oauth2RegisteredClient();
            webClient.setId(UUID.randomUUID().toString());
            webClient.setClientId(webClientId);
            webClient.setClientSecret(this.passwordUtil.encryptPwd(mallAuthConfig.getWebClientSecret()));
            webClient.setClientAuthenticationMethods(ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue());
            webClient.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(
                    List.of(AuthorizationGrantType.AUTHORIZATION_CODE.getValue(), AuthorizationGrantType.REFRESH_TOKEN.getValue())));
            webClient.setRedirectUris(mallAuthConfig.getWebRedirectUrl());
            webClient.setScopes("user");
            webClient.setClientSettings(objectMapper.writeValueAsString(ClientSettings.builder().requireAuthorizationConsent(true).build().getSettings()));
            webClient.setTokenSettings(objectMapper.writeValueAsString(
                    TokenSettings.builder()
                            .accessTokenTimeToLive(Duration.ofSeconds(mallAuthConfig.getAccessTokenLiveSeconds()))
                            .refreshTokenTimeToLive(Duration.ofSeconds(mallAuthConfig.getRefreshTokenLiveSeconds()))
                            .build().getSettings()));
            oauth2RegisteredClientRepository.save(webClient);
            log.info("Insert default oauth2 client into db success");
        }
    }

    private void insertDefaultAdmin() {
        log.info("ddlAuto: {}", ddlAuto);
        if (NONE.equals(ddlAuto)) {
            log.info("Don't insert default admin into db");
            return;
        }

        String rootUsername = mallAuthConfig.getWebRootUsername();
        if (!adminRepository.existsByUsername(rootUsername)) {
            Admin admin = new Admin();
            admin.setCreateTime(new Date());
            admin.setUsername(rootUsername);
            admin.setPassword(this.passwordUtil.encryptPwd(mallAuthConfig.getWebRootPassword()));
            admin.setNickName("Leslie");
            admin.setStatus(AdminStatus.ENABLED.ordinal());
            adminRepository.save(admin);
            log.info("Insert default admin into db success");
        }
    }
}
