package com.romero.heroes.usecase.hero.deletehero;

import com.romero.heroes.entity.HeroEntity;
import com.romero.heroes.repository.HeroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class DeleteHeroUseCaseTest {

    @Autowired
    private DeleteHeroUseCase deleteHeroUseCase;

    @Autowired
    private HeroRepository repository;

    @Test
    public void whenTryToDeleteHero_ShouldNotReturnAnything(){
        repository.save(
                HeroEntity.builder().id(1).name("SUPERMAN").build()
        );
        deleteHeroUseCase.execute(1);
        Optional<HeroEntity> entity = repository.findById(1);
        Assertions.assertTrue(entity.isEmpty());
    }

}
