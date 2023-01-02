package com.romero.heroes.usecase.hero.getheroes;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.entity.HeroEntity;
import com.romero.heroes.repository.HeroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GetHeroesUseCaseTest {
    @Autowired
    private GetHeroesUseCase getHeroesUseCase;

    @Autowired
    private HeroRepository repository;

    private static String HERO_NAME = "SUPERMAN";

    @BeforeEach
    public void clearDB(){
        repository.deleteAll();
    }

    @Test
    public void whenExecuteUseCaseToRetrieveHeroById_ShouldReturnOneHero(){

        repository.save(
                HeroEntity.builder()
                        .id(1)
                        .name(HERO_NAME)
                        .build()
        );

        List<HeroDTO> heroes = getHeroesUseCase.execute(
                SearchParameters.builder().id(1).build()
        );

        Assertions.assertEquals(1, heroes.size());
        Assertions.assertEquals(HERO_NAME, heroes.get(0).getName());
    }
}
