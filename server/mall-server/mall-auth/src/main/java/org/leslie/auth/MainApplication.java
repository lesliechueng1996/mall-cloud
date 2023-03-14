package org.leslie.auth;

import org.leslie.auth.config.MallAuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhang
 * date created in 2023/2/21 22:54
 */
@SpringBootApplication
@RestController
public class MainApplication {

    @Autowired
    private MallAuthConfig authConfig;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @GetMapping("/hahaha")
    public String test() {
        return authConfig.getWebRootPassword();
    }
}
