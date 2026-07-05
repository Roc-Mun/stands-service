package com.food.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.demo.model.Stand;

public interface StandRepository extends JpaRepository<Stand, Long> {

    List<Stand> findByIdEvento(Long idEvento);

    List<Stand> findByIdEventoAndEstado(Long idEvento, String estado);
}