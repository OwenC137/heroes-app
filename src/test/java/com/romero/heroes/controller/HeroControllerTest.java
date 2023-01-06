package com.romero.heroes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romero.heroes.controller.dto.CreateHeroRequestDTO;
import com.romero.heroes.controller.dto.UpdateHeroRequestDTO;
import com.romero.heroes.entity.HeroEntity;
import com.romero.heroes.repository.HeroRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
public class HeroControllerTest {

    private static final String HEROES_PATH = "/heroes";
    private static final String HEROES_PATH_BY_ID = HEROES_PATH+"/{id}";

    @Autowired
    private HeroRepository repository;
    @Value("${spring.security.user.name}")
    private String USER;

    @Value("${spring.security.user.password}")
    private String PASSWORD;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    final List<HeroEntity> HEROES = Stream.of(
            "SUPERMAN",
            "BATMAN",
            "ANTMAN"
    ).map(name -> HeroEntity.builder().name(name).build()).collect(Collectors.toList());

    @BeforeEach
    public void preLoad(){
        repository.saveAll(HEROES);
    }

    @AfterEach
    public void clear(){
        repository.deleteAll();
    }

    @Test
    public void whenRequestToGetAllHeroes_ShouldReturnAllHeroes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(HEROES_PATH)
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(HEROES.size()));

    }

    @Test
    public void whenRequestToGetHeroesByNameLike_ShouldReturnMatchedHeroes() throws Exception {
        final String PATH = HEROES_PATH+"?name=BAT";
        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("BATMAN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));

    }

    @Test
    public void whenRequestToGetHeroesByNameLikeAndNotMatch_ShouldReturnEmptyList() throws Exception {
        final String PATH = HEROES_PATH+"?name=MORTY";
        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

    }

    @Test
    public void whenRequestToGetAHeroById_ShouldReturnHero() throws Exception {

        final HeroEntity savedHero = repository.findAll().get(0);
        final String PATH = HEROES_PATH_BY_ID.replace("{id}", savedHero.getId().toString());
        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(savedHero.getName()));

    }

    @Test
    public void whenRequestToGetAHeroByIdAndNotExist_ShouldReturnStatusNotFound() throws Exception {

        final String PATH = HEROES_PATH_BY_ID.replace("{id}", String.valueOf(Integer.MAX_VALUE));
        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void whenRequestToCreateHeroShouldReturnHero() throws Exception {
        final String HERO_NAME = "SUPERHERO";
        final CreateHeroRequestDTO requestDTO = CreateHeroRequestDTO.builder()
                .name(HERO_NAME)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post(HEROES_PATH)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(HERO_NAME));
    }

    @Test
    public void whenRequestToUpdateHeroShouldReturnUpdatedHero() throws Exception {

        final HeroEntity HERO = repository.findAll().get(0);
        final String HERO_UPDATED_NAME = "MAD MAX";
        final UpdateHeroRequestDTO requestDTO = UpdateHeroRequestDTO.builder()
                .name(HERO_UPDATED_NAME)
                .build();
        final String PATH = HEROES_PATH_BY_ID.replace("{id}", HERO.getId().toString());
        mockMvc.perform(MockMvcRequestBuilders.patch(PATH)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(HERO_UPDATED_NAME));
        //reset on db
        repository.save(HERO);
    }

    @Test
    public void whenRequestToDeleteHeroShouldReturn_200_OK() throws Exception {

        final HeroEntity HERO = repository.save(HeroEntity.builder().name("DELETE HERO").build());
        final String PATH = HEROES_PATH_BY_ID.replace("{id}", HERO.getId().toString());
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenRequestToDeleteHeroButHeroDoesntExistShouldReturn_404_NOT_FOUND() throws Exception {

        final String PATH = HEROES_PATH_BY_ID.replace("{id}", String.valueOf(Integer.MAX_VALUE));
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
