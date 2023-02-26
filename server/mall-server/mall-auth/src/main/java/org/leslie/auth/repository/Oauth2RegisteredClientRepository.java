package org.leslie.auth.repository;

import org.leslie.auth.entity.Oauth2RegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author zhang
 * date created in 2023/2/25 02:40
 */
public interface Oauth2RegisteredClientRepository extends JpaRepository<Oauth2RegisteredClient, String> {

    Optional<Oauth2RegisteredClient> findByClientId(String clientId);

    boolean existsByClientId(String clientId);
}
