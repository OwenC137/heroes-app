package com.romero.heroes.usecase.hero.getheroes;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchParameters {
    private Integer id;
    private String name;
}
