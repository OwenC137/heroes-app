package com.romero.heroes.usecase.hero.common;

import com.romero.heroes.dto.HeroDTO;
import com.romero.heroes.entity.HeroEntity;

public class HeroUtils {
    public static HeroDTO heroEntityToDTO(HeroEntity hero){
        return HeroDTO.builder().name(hero.getName()).id(hero.getId()).build();
    }
}
