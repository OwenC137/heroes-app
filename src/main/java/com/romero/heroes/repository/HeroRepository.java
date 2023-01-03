package com.romero.heroes.repository;

import com.romero.heroes.entity.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeroRepository extends JpaRepository<HeroEntity, Integer> {

    List<HeroEntity> findByNameIsContaining(String name);
}
