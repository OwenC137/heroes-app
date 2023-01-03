package com.romero.heroes.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HeroDTO {
    private String name;
    private Integer id;
}
