package com.romero.heroes.repository;

import com.romero.heroes.entity.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<HeroEntity, Integer> {
}
