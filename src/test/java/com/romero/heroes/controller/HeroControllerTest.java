package com.romero.heroes.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerTest {

    private static String HEROES_PATH = "/heroes";
    @Value("${spring.security.user.name}")
    private String USER;

    @Value("${spring.security.user.password}")
    private String PASSWORD;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenRequestToGetAllHeroes_ShouldReturnAllHeroes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(HEROES_PATH)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenRequestToCreateHeroShouldReturnHero() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(HEROES_PATH)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
