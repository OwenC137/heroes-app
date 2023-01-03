package com.romero.heroes.usecase.hero.getheroes;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.entity.HeroEntity;
import com.romero.heroes.repository.HeroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class GetHeroesUseCaseTest{
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
    public void whenTryRetrieveHeroById_ShouldReturnOneHero(){

        final HeroEntity savedHero = repository.save(
                HeroEntity.builder()
                        .name(HEROES.get(0))
                        .build()
        );

        List<HeroDTO> heroes = getHeroesUseCase.execute(
                SearchParameters.builder().id(savedHero.getId()).build()
        );

        Assertions.assertEquals(1, heroes.size());
        Assertions.assertEquals(HEROES.get(0), heroes.get(0).getName());
    }

    @Test
    public void whenTryToGetHeroesWithNonExistentID(){
        List<HeroDTO> heroes = getHeroesUseCase.execute(
                SearchParameters.builder()
                        .id(Integer.MAX_VALUE)
                        .build()
        );

        Assertions.assertEquals(0, heroes.size());
    }

    @Test
    public void whenTryToGetHeroesLikeName_ShouldRetrieveHeroesWithMatchedPartialOrFullName(){
        repository.saveAll(
                HEROES.stream().map( heroName -> HeroEntity.builder().name(heroName).build())
                        .collect(Collectors.toList())
        );

        List<HeroDTO> heroes = getHeroesUseCase.execute(SearchParameters.builder().name("ANT").build());
        Assertions.assertEquals(1, heroes.size());
        Assertions.assertEquals(ANT_MAN, heroes.get(0).getName());
    }

    @Test
    public void whenTryToGetHeroWithHeroNameOutOfMatch_ShouldRetrieveEmptyListOfHeroes(){
        repository.saveAll(
                HEROES.stream().map( heroName -> HeroEntity.builder().name(heroName).build())
                        .collect(Collectors.toList())
        );
        List<HeroDTO> heroes = getHeroesUseCase.execute(SearchParameters.builder().name("Rick").build());
        Assertions.assertEquals(0, heroes.size());
    }
}
