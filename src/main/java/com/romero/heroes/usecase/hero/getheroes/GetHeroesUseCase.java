package com.romero.heroes.usecase.hero.getheroes;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.repository.HeroRepository;
import com.romero.heroes.usecase.UseCase;
import com.romero.heroes.usecase.hero.common.HeroUtils;
import com.romero.heroes.usecase.hero.exception.HeroNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GetHeroesUseCase implements UseCase<List<HeroDTO>, SearchParameters> {

    @Autowired
    private HeroRepository repository;

    @Override
    public List<HeroDTO> execute(SearchParameters param){
        if (param.getId() != null){
            return findById(param.getId());
        }

        if (param.getName() != null && !param.getName().isEmpty()){
            return findByNameLike(param.getName());
        }

        return getAllHeroes();
    }

    private List<HeroDTO> findById(Integer id){
        return Stream.of(repository.findById(id).orElseThrow(HeroNotFoundException::new))
                .map(HeroUtils::heroEntityToDTO)
                .collect(Collectors.toList());
    }

    private List<HeroDTO> findByNameLike(String name){
        return repository.findByNameIsContaining(name).stream()
                .map(HeroUtils::heroEntityToDTO).collect(Collectors.toList());
    }

    private List<HeroDTO> getAllHeroes(){
        return repository.findAll().stream().map(HeroUtils::heroEntityToDTO)
                .collect(Collectors.toList());
    }
}
