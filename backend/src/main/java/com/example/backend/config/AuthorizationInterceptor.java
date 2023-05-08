package com.example.backend.config;

import com.example.backend.exeptions.NoBearerTokenError;
import com.example.backend.exeptions.UnauthenticatedError;
import com.example.backend.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final AuthService service;


    public AuthorizationInterceptor(AuthService authService) {
        this.service = authService;

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (authorizationHeader == null) {
            throw new UnauthenticatedError();
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new NoBearerTokenError();
        }


        //написать функцию обработки времени

        request.setAttribute("user", service.getUserFromToken(authorizationHeader.substring(7)));

        return true;
    }
}