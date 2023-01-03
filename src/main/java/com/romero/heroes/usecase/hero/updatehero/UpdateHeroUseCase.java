package com.romero.heroes.usecase.hero.updatehero;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.repository.HeroRepository;
import com.romero.heroes.usecase.UseCase;
import com.romero.heroes.usecase.hero.common.HeroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class UpdateHeroUseCase implements UseCase<Optional<HeroDTO>, HeroDTO> {

    @Autowired
    private HeroRepository heroRepository;

    @Override
    public Optional<HeroDTO> execute(HeroDTO param) {
        return heroRepository.findById(param.getId())
                .map(hero -> {
                    hero.setName(param.getName());
                    return heroRepository.save(hero);
                }).map(HeroUtils::heroEntityToDTO);
    }
}
