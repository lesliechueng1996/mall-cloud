package org.leslie.auth.pojo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * @author zhang
 * date created in 2023/2/25 01:36
 */
@Entity
@Table(name = "t_oauth2_registered_client", schema = "usr", indexes = {
        @Index(name = "t_oauth2_registered_client_client_id_uk", columnList = "clientId", unique = true)
})
@Getter
@Setter
public class Oauth2RegisteredClient {

    @Id
    private String id;
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    @Column(length = 1000)
    private String clientAuthenticationMethods;
    @Column(length = 1000)
    private String authorizationGrantTypes;
    @Column(length = 1000)
    private String redirectUris;
    @Column(length = 1000)
    private String scopes;
    @Column(length = 2000)
    private String clientSettings;
    @Column(length = 2000)
    private String tokenSettings;
}
