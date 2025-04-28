package com.apifecha.apifecha.domain.repository;

import com.apifecha.apifecha.domain.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoRepository extends JpaRepository<Tipo, Integer> {
}