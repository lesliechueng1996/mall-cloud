package org.leslie.auth.repository;

import org.leslie.auth.pojo.entity.AuthorizationConsent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author zhang
 * date created in 2023/2/27 01:12
 */
public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {

    Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}