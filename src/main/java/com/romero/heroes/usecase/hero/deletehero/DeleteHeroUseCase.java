package com.romero.heroes.usecase.hero.deletehero;

import com.romero.heroes.entity.HeroEntity;
import com.romero.heroes.repository.HeroRepository;
import com.romero.heroes.usecase.UseCase;
import com.romero.heroes.usecase.hero.exception.HeroNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteHeroUseCase implements UseCase<Void, Integer> {

    @Autowired
    private HeroRepository repository;

    @Override
    public Void execute(Integer param) {
        final HeroEntity hero = repository.findById(param)
                .orElseThrow(HeroNotFoundException::new);
        repository.deleteById(hero.getId());
        return null;
    }
}
