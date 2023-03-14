package org.leslie.auth.utils;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author zhang
 * date created in 2023/2/26 22:20
 */
@Component
@AllArgsConstructor
public class PasswordUtil {


    private final PasswordEncoder passwordEncoder;

    public String encryptPwd(String password) {
        return this.passwordEncoder.encode(password);
    }

}