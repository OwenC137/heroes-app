package com.romero.heroes.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HeroDTO implements Serializable {
    private String name;
    private Integer id;
}
