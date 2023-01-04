package com.romero.heroes.usecase.hero.createhero;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.repository.HeroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class CreateHeroUseCaseTest{

    @Autowired
    private CreateHeroUseCase createHeroUseCase;

    @Autowired
    private HeroRepository repository;

    private static final String SUPERMAN = "SUPERMAN";


    @BeforeEach
    public void clearDB(){
        repository.deleteAll();
    }

    @Test
    public void whenTryToCreateHero_ShouldRetrieveOptionalHeroDTO(){
        Optional<HeroDTO> hero = createHeroUseCase.execute(SUPERMAN);
        Assertions.assertTrue(hero.isPresent());
        Assertions.assertEquals(SUPERMAN, hero.get().getName());
    }

}
