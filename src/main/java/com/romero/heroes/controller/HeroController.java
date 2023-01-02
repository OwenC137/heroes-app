package com.romero.heroes.controller;

import com.romero.heroes.dto.HeroDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("heroes")
public class HeroController {

    @GetMapping()
    public List<HeroDTO> getHeroes(@RequestParam Map<String, String> parameters) {
        return null;
    }
}
