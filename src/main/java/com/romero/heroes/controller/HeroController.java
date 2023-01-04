package com.romero.heroes.controller;

import com.romero.heroes.controller.dto.CreateHeroRequestDTO;
import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.usecase.hero.createhero.CreateHeroUseCase;
import com.romero.heroes.usecase.hero.getheroes.GetHeroesUseCase;
import com.romero.heroes.usecase.hero.getheroes.SearchParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("heroes")
public class HeroController {

    @Autowired
    private GetHeroesUseCase getHeroesUseCase;

    @Autowired
    private CreateHeroUseCase createHeroUseCase;

    @GetMapping()
    public ResponseEntity<List<HeroDTO>> getHeroes(@RequestParam(value = "name", required = false) String name) {
        return new ResponseEntity<>(getHeroesUseCase.execute(
                SearchParameters.builder().name(name).build()
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroDTO> getHeroById(@PathVariable("id") Integer id) {

        final Optional<HeroDTO> result = getHeroesUseCase.execute(
                SearchParameters.builder().id(id).build()
        ).stream().findFirst();

        return result.map(heroDTO -> new ResponseEntity<>(heroDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<HeroDTO> createHero(@RequestBody CreateHeroRequestDTO request){
        return createHeroUseCase.execute(request.getName())
                .map(heroDTO -> new ResponseEntity<>(heroDTO, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }
}
