package com.food.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandTest {

    @Test
    @DisplayName("Debe crear stand con constructor vacío y setters")
    void debeCrearStandConConstructorVacioYSetters() {

        // Given
        Stand stand = new Stand();

        // When
        stand.setIdStand(1L);
        stand.setNombre("Stand Sushi");
        stand.setTipoComida("Japonesa");
        stand.setDescripcion("Sushi premium");
        stand.setEstado("activo");
        stand.setIdEvento(2L);

        // Then
        assertEquals(1L, stand.getIdStand());
        assertEquals("Stand Sushi", stand.getNombre());
        assertEquals("Japonesa", stand.getTipoComida());
        assertEquals("Sushi premium", stand.getDescripcion());
        assertEquals("activo", stand.getEstado());
        assertEquals(2L, stand.getIdEvento());
    }

    @Test
    @DisplayName("Debe crear stand con constructor completo")
    void debeCrearStandConConstructorCompleto() {

        // Given / When
        Stand stand = new Stand(
                1L,
                "Stand Tacos",
                "Mexicana",
                "Tacos artesanales",
                "activo",
                3L
        );

        // Then
        assertEquals(1L, stand.getIdStand());
        assertEquals("Stand Tacos", stand.getNombre());
        assertEquals("Mexicana", stand.getTipoComida());
        assertEquals("Tacos artesanales", stand.getDescripcion());
        assertEquals("activo", stand.getEstado());
        assertEquals(3L, stand.getIdEvento());
    }

    @Test
    @DisplayName("Debe validar equals y hashCode")
    void debeValidarEqualsYHashCode() {

        // Given
        Stand stand1 = new Stand(
                1L,
                "Stand Pizza",
                "Italiana",
                "Pizza artesanal",
                "activo",
                4L
        );

        Stand stand2 = new Stand(
                1L,
                "Stand Pizza",
                "Italiana",
                "Pizza artesanal",
                "activo",
                4L
        );

        // Then
        assertEquals(stand1, stand2);
        assertEquals(stand1.hashCode(), stand2.hashCode());
    }
}