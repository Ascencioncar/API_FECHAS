package com.apifecha.apifecha.domain.repository;

import com.apifecha.apifecha.domain.model.Festivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivoRepository extends JpaRepository<Festivo, Integer> {
}
