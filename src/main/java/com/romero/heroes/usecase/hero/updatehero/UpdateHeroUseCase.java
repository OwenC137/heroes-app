package com.romero.heroes.usecase.hero.updatehero;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.repository.HeroRepository;
import com.romero.heroes.usecase.UseCase;
import com.romero.heroes.usecase.hero.common.HeroUtils;
import com.romero.heroes.usecase.hero.exception.HeroNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateHeroUseCase implements UseCase<HeroDTO, HeroDTO> {

    @Autowired
    private HeroRepository heroRepository;

    @Override
    public HeroDTO execute(HeroDTO param) {
        return HeroUtils.heroEntityToDTO(
                heroRepository.findById(param.getId())
                        .map(hero -> {
                            hero.setName(param.getName());
                            return heroRepository.save(hero);
                        })
                        .orElseThrow(HeroNotFoundException::new)
        );
    }
}
