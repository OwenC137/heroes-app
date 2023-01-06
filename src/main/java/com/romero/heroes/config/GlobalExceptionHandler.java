package com.romero.heroes.config;

import com.romero.heroes.usecase.hero.HeroException;
import com.romero.heroes.usecase.hero.exception.HeroCreationErrorException;
import com.romero.heroes.usecase.hero.exception.HeroNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ HeroException.class, HeroNotFoundException.class, HeroCreationErrorException.class})
    public ResponseEntity<Object> heroNotFound(HeroNotFoundException ex) {
        return new ResponseEntity<>(ex.getStatusCode());
    }
}
