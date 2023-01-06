package com.romero.heroes.usecase.hero.exception;

import com.romero.heroes.usecase.hero.HeroException;
import org.springframework.http.HttpStatus;

public class HeroCreationErrorException extends HeroException {
    public HeroCreationErrorException() {
        super("An error was found while creating Hero.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
