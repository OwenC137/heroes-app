package com.romero.heroes.usecase.hero;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum HeroOperationResult {
    NOT_FOUND(HttpStatus.NOT_FOUND),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR),
    OK(HttpStatus.OK);

    private final HttpStatus httpStatus;
    HeroOperationResult(HttpStatus status) {
        httpStatus = status;
    }

}
