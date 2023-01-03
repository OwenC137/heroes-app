package com.romero.heroes.usecase.hero.getheroes;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.entity.HeroEntity;
import com.romero.heroes.repository.HeroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class GetHeroesUseCaseTest {
    @Autowired
    private GetHeroesUseCase getHeroesUseCase;

    @Autowired
    private HeroRepository repository;

    private static final String SUPERMAN = "SUPERMAN";
    private static final String BATMAN = "BATMAN";
    private static final String ANT_MAN = "ANT_MAN";
    private static final List<String> HEROES = Arrays.asList(SUPERMAN, BATMAN, ANT_MAN);

    @BeforeEach
    public void clearDB(){
        repository.deleteAll();
    }

    @Test
    public void whenExecuteUseCaseToRetrieveHeroById_ShouldReturnOneHero(){

        repository.save(
                HeroEntity.builder()
                        .id(1)
                        .name(HEROES.get(0))
                        .build()
        );

        List<HeroDTO> heroes = getHeroesUseCase.execute(
                SearchParameters.builder().id(1).build()
        );

        Assertions.assertEquals(1, heroes.size());
        Assertions.assertEquals(HEROES.get(0), heroes.get(0).getName());
    }

    @Test
    public void whenTryRetrieveHeroesLikeName_ShouldRetrieveHeroesWithMatchedPartialOrFullName(){
        repository.saveAll(
                HEROES.stream().map( heroName -> HeroEntity.builder().name(heroName).build())
                        .collect(Collectors.toList())
        );

        List<HeroDTO> heroes = getHeroesUseCase.execute(SearchParameters.builder().name("ANT").build());
        Assertions.assertEquals(1, heroes.size());
        Assertions.assertEquals(ANT_MAN, heroes.get(0).getName());
    }
}
