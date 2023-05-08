package com.example.backend.exeptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ErrorAuth extends ResponseStatusException {
    public ErrorAuth() {
        super(HttpStatus.UNAUTHORIZED, "unauthorized");
    }
}