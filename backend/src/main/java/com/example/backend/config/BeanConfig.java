package com.example.backend.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {

    @Bean/*нихуя себе однако, шифруется пароль перед сохранением*/
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
