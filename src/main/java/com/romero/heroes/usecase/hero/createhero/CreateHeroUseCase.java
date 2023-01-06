package com.romero.heroes.usecase.hero.createhero;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.entity.HeroEntity;
import com.romero.heroes.repository.HeroRepository;
import com.romero.heroes.usecase.UseCase;
import com.romero.heroes.usecase.hero.common.HeroUtils;
import com.romero.heroes.usecase.hero.exception.HeroCreationErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CreateHeroUseCase implements UseCase<HeroDTO, String> {

    @Autowired
    private HeroRepository repository;

    @Override
    public HeroDTO execute(String param) {
        return Stream.of(repository.save(
                HeroEntity.builder().name(param).build()
        )).map(HeroUtils::heroEntityToDTO).findFirst().orElseThrow(HeroCreationErrorException::new);
    }
}
