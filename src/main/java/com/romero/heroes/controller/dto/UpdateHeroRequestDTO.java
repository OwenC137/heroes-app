package com.romero.heroes.controller.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UpdateHeroRequestDTO {
    private String name;
}
