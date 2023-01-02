package com.romero.heroes.usecase.hero.getheroes;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.entity.HeroEntity;
import com.romero.heroes.repository.HeroRepository;
import com.romero.heroes.usecase.UseCase;
import com.romero.heroes.usecase.hero.common.HeroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GetHeroesUseCase implements UseCase<List<HeroDTO>, SearchParameters> {

    @Autowired
    private HeroRepository repository;

    @Override
    public List<HeroDTO> execute(SearchParameters param) {
        if (param.getId() != null){
            return Stream.of(repository.findById(param.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(HeroUtils::heroEntityToDTO)
                    .collect(Collectors.toList());
        }

        return null;
    }

}
