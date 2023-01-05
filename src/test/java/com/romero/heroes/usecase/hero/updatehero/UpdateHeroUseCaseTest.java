package com.romero.heroes.usecase.hero.updatehero;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.entity.HeroEntity;
import com.romero.heroes.repository.HeroRepository;
import com.romero.heroes.usecase.hero.HeroOperationResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class UpdateHeroUseCaseTest {

    @Autowired
    private HeroRepository repository;

    @Autowired
    private UpdateHeroUseCase updateHeroUseCase;

    @BeforeEach
    public void clearDB(){
        repository.deleteAll();
    }

    @Test
    public void whenTryToUpdateHero_ShouldRetrieveUpdatedHero(){
        final HeroEntity currentHero = repository.save(
                HeroEntity.builder().name("SUPERMAN").build()
        );
        final String NEW_NAME = "BATMAN";
        final Pair<HeroOperationResult, HeroDTO> hero = updateHeroUseCase.execute(
                HeroDTO.builder().id(currentHero.getId()).name(NEW_NAME).build()
        );
        Assertions.assertEquals(HttpStatus.OK, hero.getFirst().getHttpStatus());
        Assertions.assertEquals(currentHero.getId(), hero.getSecond().getId());
        Assertions.assertEquals(NEW_NAME, hero.getSecond().getName());
    }

    @Test
    public void whenTryToUpdateHeroAndItDoesntExist_ShouldRetrieveOperationCodeNotFound(){
        final String NEW_NAME = "BATMAN";
        final Pair<HeroOperationResult, HeroDTO> hero = updateHeroUseCase.execute(
                HeroDTO.builder().id(Integer.MAX_VALUE).name(NEW_NAME).build()
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, hero.getFirst().getHttpStatus());
    }
}
