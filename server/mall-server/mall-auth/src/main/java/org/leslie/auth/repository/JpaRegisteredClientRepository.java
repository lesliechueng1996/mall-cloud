package org.leslie.auth.repository;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.leslie.auth.entity.Oauth2RegisteredClient;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author zhang
 * date created in 2023/2/25 02:48
 */
@Component
@AllArgsConstructor
public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    private final Oauth2RegisteredClientRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(RegisteredClient registeredClient) {
        Optional<Oauth2RegisteredClient> optioanl = repository.findById(registeredClient.getId());
        Oauth2RegisteredClient data;

        data = optioanl.orElseGet(Oauth2RegisteredClient::new);

        data.setClientId(registeredClient.getClientId());
        data.setClientIdIssuedAt(registeredClient.getClientIdIssuedAt() == null ? Instant.now() : registeredClient.getClientIdIssuedAt());
        data.setClientSecret(registeredClient.getClientSecret());
        data.setClientSecretExpiresAt(registeredClient.getClientSecretExpiresAt() == null ? null : registeredClient.getClientSecretExpiresAt());
        data.setClientName(registeredClient.getClientName());
        data.setClientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(
                registeredClient.getClientAuthenticationMethods()
                        .stream()
                        .map(ClientAuthenticationMethod::getValue)
                        .collect(Collectors.toList())));
        data.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(
                registeredClient.getAuthorizationGrantTypes()
                        .stream()
                        .map(AuthorizationGrantType::getValue)
                        .collect(Collectors.toList())));
        data.setRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));
        data.setScopes(StringUtils.collectionToCommaDelimitedString(registeredClient.getScopes()));

        try {
            data.setClientSettings(objectMapper.writeValueAsString(registeredClient.getClientSettings().getSettings()));
            data.setTokenSettings(objectMapper.writeValueAsString(registeredClient.getTokenSettings().getSettings()));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        repository.save(data);
    }

    @Override
    public RegisteredClient findById(String id) {
        Optional<Oauth2RegisteredClient> optional  = repository.findById(id);
        return formatRegisteredClient(optional);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<Oauth2RegisteredClient> optional  = repository.findByClientId(clientId);
        return formatRegisteredClient(optional);
    }

    private RegisteredClient formatRegisteredClient(Optional<Oauth2RegisteredClient> optional) {
        if (optional.isEmpty()) {
            return null;
        }

        Oauth2RegisteredClient data = optional.get();
        ClientSettings clientSettings;
        TokenSettings tokenSettings;
        try {
            clientSettings = data.getClientSettings() == null ? null : ClientSettings.withSettings(objectMapper.readValue(data.getClientSettings(), new TypeReference<>() {
            })).build();
//            tokenSettings = data.getTokenSettings() == null ? null : TokenSettings.withSettings(objectMapper.readValue(data.getTokenSettings(), new TypeReference<>() {
//            })).build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return RegisteredClient
                .withId(data.getId())
                .clientId(data.getClientId())
                .clientIdIssuedAt(Optional.ofNullable(data.getClientIdIssuedAt()).orElse(new Date().toInstant()))
                .clientSecret(data.getClientSecret())
                .clientSecretExpiresAt(data.getClientSecretExpiresAt())
                .clientName(data.getClientName())
                .clientAuthenticationMethods(clientAuthenticationMethods -> clientAuthenticationMethods.addAll(
                        Arrays.stream(data.getClientAuthenticationMethods().split(","))
                                .map(ClientAuthenticationMethod::new)
                                .collect(Collectors.toSet())
                ))
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(
                        Arrays.stream(data.getAuthorizationGrantTypes().split(","))
                                .map(AuthorizationGrantType::new)
                                .collect(Collectors.toSet())
                ))
                .redirectUris(strings -> strings.addAll(
                        Arrays.stream(data.getRedirectUris().split(",")).toList()
                ))
                .scopes(strings -> strings.addAll(
                        Arrays.stream(data.getScopes().split(",")).toList()
                ))
                .clientSettings(clientSettings)
//                .tokenSettings(tokenSettings)
                .build();
    }
}
