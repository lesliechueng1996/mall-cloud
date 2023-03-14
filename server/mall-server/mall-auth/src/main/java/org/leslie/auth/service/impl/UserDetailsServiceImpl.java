package org.leslie.auth.service.impl;

import lombok.AllArgsConstructor;
import org.leslie.auth.pojo.entity.Admin;
import org.leslie.auth.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang
 * date created in 2023/2/26 00:39
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(admin.getUsername(), admin.getPassword(), admin.isEnabled(), true, true, true, List.of());
    }
}
