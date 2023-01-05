package com.romero.heroes.usecase.hero.deletehero;

import com.romero.heroes.repository.HeroRepository;
import com.romero.heroes.usecase.UseCase;
import com.romero.heroes.usecase.hero.HeroOperationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteHeroUseCase implements UseCase<HeroOperationResult, Integer> {

    @Autowired
    private HeroRepository repository;

    @Override
    public HeroOperationResult execute(Integer param) {
        return repository.findById(param)
                .map(hero -> tryDeleteHero(hero.getId()))
                .orElse(HeroOperationResult.NOT_FOUND);
    }

    private HeroOperationResult tryDeleteHero(Integer id){
        try {
            repository.deleteById(id);
            return HeroOperationResult.OK;
        }catch (Exception e){
            return HeroOperationResult.UNKNOWN;
        }
    }
}
