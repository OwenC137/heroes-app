package com.romero.heroes.controller;

import com.romero.heroes.controller.dto.CreateHeroRequestDTO;
import com.romero.heroes.controller.dto.UpdateHeroRequestDTO;
import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.usecase.hero.createhero.CreateHeroUseCase;
import com.romero.heroes.usecase.hero.deletehero.DeleteHeroUseCase;
import com.romero.heroes.usecase.hero.getheroes.GetHeroesUseCase;
import com.romero.heroes.usecase.hero.getheroes.SearchParameters;
import com.romero.heroes.usecase.hero.updatehero.UpdateHeroUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController()
@RequestMapping("heroes")
public class HeroController {

    @Autowired
    private GetHeroesUseCase getHeroesUseCase;

    @Autowired
    private CreateHeroUseCase createHeroUseCase;

    @Autowired
    private UpdateHeroUseCase updateHeroUseCase;

    @Autowired
    private DeleteHeroUseCase deleteHeroUseCase;

    @GetMapping()
    @Cacheable("users")
    public List<HeroDTO> getHeroes(@RequestParam(value = "name", required = false) String name){
        return getHeroesUseCase.execute(
                SearchParameters.builder().name(name).build()
        );
    }

    @GetMapping("/{id}")
    @Cacheable(value = "user", key = "#result.id", condition = "#result != null")
    public HeroDTO getHeroById(@PathVariable("id") Integer id) {
        return getHeroesUseCase.execute(
                SearchParameters.builder().id(id).build()
        ).stream().findFirst().get();
    }

    @PostMapping(consumes = {"application/json"})
    @CacheEvict(value = "users", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    public HeroDTO createHero(@RequestBody CreateHeroRequestDTO request){
        return createHeroUseCase.execute(request.getName());
    }

    @PatchMapping(value = "/{id}",  consumes = {"application/json"})
    @CachePut(value = "user", key = "#result.id")
    @CacheEvict(value = "users", allEntries = true)
    @ResponseStatus(HttpStatus.OK)
    public HeroDTO updateHero(@PathVariable Integer id,@RequestBody UpdateHeroRequestDTO request){
        return updateHeroUseCase.execute(HeroDTO.builder().id(id).name(request.getName()).build());
    }

    @DeleteMapping(value = "/{id}",consumes = {"application/json"})
    @CacheEvict({"users", "user"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteHero(@PathVariable Integer id){
        deleteHeroUseCase.execute(id);
    }
}
