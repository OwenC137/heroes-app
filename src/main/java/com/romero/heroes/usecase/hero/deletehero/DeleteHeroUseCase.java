package com.romero.heroes.usecase.hero.deletehero;

import com.romero.heroes.repository.HeroRepository;
import com.romero.heroes.usecase.UseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteHeroUseCase implements UseCase<Void, Integer> {

    @Autowired
    private HeroRepository repository;

    @Override
    public Void execute(Integer param) {
        repository.deleteById(param);
        return null;
    }
}
