package org.leslie.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.leslie.auth.enums.AdminStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Date;

/**
 * @author zhang
 * date created in 2023/2/26 00:13
 */
@Entity
@Table(name = "t_admin", schema = "usr")
@Getter
@Setter
public class Admin implements UserDetails {

    @Serial
    @Transient
    private static final long serialVersionUID = 1485451138155122351L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_admin_id_seq")
    @SequenceGenerator(schema = "usr", name = "t_admin_id_seq", sequenceName = "t_admin_id_seq", allocationSize = 1)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createTime;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String icon;
    private String email;
    private String nickName;
    private String note;
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;
    @Column(nullable = false)
    private Integer status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == AdminStatus.ENABLED.ordinal();
    }
}
