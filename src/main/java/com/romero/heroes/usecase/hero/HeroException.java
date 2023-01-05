package com.romero.heroes.usecase.hero;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HeroException extends RuntimeException{
    private final HttpStatus statusCode;
    private final String errorMessage;
    public HeroException(String message, HttpStatus statusCode) {
        super(message);
        this.errorMessage = message;
        this.statusCode = statusCode;
    }
}
