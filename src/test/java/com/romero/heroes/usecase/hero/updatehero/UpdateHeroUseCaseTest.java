package com.romero.heroes.usecase.hero.updatehero;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.entity.HeroEntity;
import com.romero.heroes.repository.HeroRepository;
import com.romero.heroes.usecase.hero.exception.HeroNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        final HeroDTO hero = updateHeroUseCase.execute(
                HeroDTO.builder().id(currentHero.getId()).name(NEW_NAME).build()
        );
        Assertions.assertEquals(currentHero.getId(), hero.getId());
        Assertions.assertEquals(NEW_NAME, hero.getName());
    }

    @Test
    public void whenTryToUpdateHeroAndItDoesntExist_ShouldRetrieveOperationCodeNotFound(){
        final String NEW_NAME = "BATMAN";
        Assertions.assertThrowsExactly(HeroNotFoundException.class, () -> updateHeroUseCase.execute(
                HeroDTO.builder().id(Integer.MAX_VALUE).name(NEW_NAME).build()
        ));
    }
}
