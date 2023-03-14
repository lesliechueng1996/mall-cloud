package org.leslie.auth.pojo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.leslie.auth.enums.AdminStatus;

import java.util.Date;

/**
 * @author zhang
 * date created in 2023/2/26 00:13
 */
@Entity
@Table(name = "t_admin", schema = "usr")
@Getter
@Setter
public class Admin {
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

    public boolean isEnabled() {
        return this.status == AdminStatus.ENABLED.ordinal();
    }
}
