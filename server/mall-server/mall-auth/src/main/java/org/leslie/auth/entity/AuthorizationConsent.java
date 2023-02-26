package org.leslie.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author zhang
 * date created in 2023/2/27 01:10
 */
@Entity
@Table(name = "t_authorization_consent", schema = "usr")
@IdClass(AuthorizationConsent.AuthorizationConsentId.class)
@Getter
@Setter
public class AuthorizationConsent {
    @Id
    private String registeredClientId;
    @Id
    private String principalName;
    @Column(length = 1000)
    private String authorities;

    public static class AuthorizationConsentId implements Serializable {
        @Serial
        private static final long serialVersionUID = 2739788406240136033L;
        private String registeredClientId;
        private String principalName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AuthorizationConsentId that = (AuthorizationConsentId) o;
            return registeredClientId.equals(that.registeredClientId) && principalName.equals(that.principalName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(registeredClientId, principalName);
        }
    }
}