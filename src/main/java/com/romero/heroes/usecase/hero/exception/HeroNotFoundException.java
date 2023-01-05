package com.romero.heroes.usecase.hero.exception;

import com.romero.heroes.usecase.hero.HeroException;
import org.springframework.http.HttpStatus;

public class HeroNotFoundException extends HeroException {
    public HeroNotFoundException() {
        super("Hero not found.", HttpStatus.NOT_FOUND);
    }
}
