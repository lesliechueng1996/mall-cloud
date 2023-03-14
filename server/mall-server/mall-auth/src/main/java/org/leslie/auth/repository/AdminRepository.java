package org.leslie.auth.repository;

import org.leslie.auth.pojo.entity.Admin;
import org.springframework.data.repository.Repository;

/**
 * @author zhang
 * date created in 2023/2/26 00:39
 */
public interface AdminRepository extends Repository<Admin, Integer> {

    Admin findByUsername(String username);

    boolean existsByUsername(String username);

    Admin save(Admin admin);
}
