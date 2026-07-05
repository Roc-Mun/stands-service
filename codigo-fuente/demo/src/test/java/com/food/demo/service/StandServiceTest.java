package com.food.demo.service;

import com.food.demo.client.EventoClient;
import com.food.demo.dto.EventoDTO;
import com.food.demo.dto.StandCreateDTO;
import com.food.demo.dto.StandDTO;
import com.food.demo.dto.StandUpdateDTO;
import com.food.demo.exception.EstadoInvalidoException;
import com.food.demo.exception.RecursoNoEncontradoException;
import com.food.demo.model.Stand;
import com.food.demo.repository.StandRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StandServiceTest {

    @Mock
    private StandRepository repository;

    @Mock
    private EventoClient eventoClient;

    @InjectMocks
    private StandService service;

    @Test
    @DisplayName("Debe listar stands correctamente")
    void debeListarStandsCorrectamente() {

        // Given
        Stand stand = new Stand(
                1L,
                "Stand Sushi",
                "Japonesa",
                "Sushi premium",
                "activo",
                2L
        );

        when(repository.findAll()).thenReturn(List.of(stand));

        // When
        List<StandDTO> resultado = service.listarStands();

        // Then
        assertEquals(1, resultado.size());
        assertEquals("Stand Sushi", resultado.get(0).getNombre());
        assertEquals("activo", resultado.get(0).getEstado());

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Debe crear stand cuando el evento está publicado")
    void debeCrearStandCuandoEventoEstaPublicado() {

        // Given
        StandCreateDTO dto = new StandCreateDTO(
                "Stand Sushi",
                "Japonesa",
                "Sushi premium",
                2L
        );

        EventoDTO evento = new EventoDTO(
                2L,
                "Festival Gastronómico",
                "ReadyStand",
                LocalDateTime.of(2026, 9, 10, 10, 0),
                LocalDateTime.of(2026, 9, 10, 18, 0),
                "Santiago",
                "publicado",
                1L
        );

        Stand guardado = new Stand(
                1L,
                "Stand Sushi",
                "Japonesa",
                "Sushi premium",
                "activo",
                2L
        );

        when(eventoClient.obtenerEventoPorId(2L)).thenReturn(evento);
        when(repository.save(any(Stand.class))).thenReturn(guardado);

        // When
        StandDTO resultado = service.crearStand(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdStand());
        assertEquals("Stand Sushi", resultado.getNombre());
        assertEquals("activo", resultado.getEstado());
        assertEquals(2L, resultado.getIdEvento());

        verify(eventoClient).obtenerEventoPorId(2L);
        verify(repository).save(any(Stand.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el evento está en borrador")
    void debeLanzarExcepcionSiEventoEstaEnBorrador() {

        // Given
        StandCreateDTO dto = new StandCreateDTO(
                "Stand Pizza",
                "Italiana",
                "Pizza artesanal",
                3L
        );

        EventoDTO evento = new EventoDTO(
                3L,
                "Evento Borrador",
                "ReadyStand",
                LocalDateTime.of(2026, 9, 10, 10, 0),
                LocalDateTime.of(2026, 9, 10, 18, 0),
                "Santiago",
                "borrador",
                1L
        );

        when(eventoClient.obtenerEventoPorId(3L)).thenReturn(evento);

        // When / Then
        assertThrows(
                EstadoInvalidoException.class,
                () -> service.crearStand(dto)
        );

        verify(repository, never()).save(any(Stand.class));
    }

    @Test
    @DisplayName("Debe obtener stand por ID")
    void debeObtenerStandPorId() {

        // Given
        Stand stand = new Stand(
                5L,
                "Stand Tacos",
                "Mexicana",
                "Tacos artesanales",
                "activo",
                2L
        );

        when(repository.findById(5L)).thenReturn(Optional.of(stand));

        // When
        StandDTO resultado = service.obtenerStandPorId(5L);

        // Then
        assertEquals(5L, resultado.getIdStand());
        assertEquals("Stand Tacos", resultado.getNombre());
        assertEquals("activo", resultado.getEstado());

        verify(repository).findById(5L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el stand no existe")
    void debeLanzarExcepcionCuandoStandNoExiste() {

        // Given
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(
                RecursoNoEncontradoException.class,
                () -> service.obtenerStandPorId(99L)
        );

        verify(repository).findById(99L);
    }

    @Test
    @DisplayName("Debe actualizar stand manteniendo campos vacíos sin cambios")
    void debeActualizarStandManteniendoCamposVaciosSinCambios() {

        // Given
        Stand existente = new Stand(
                1L,
                "Stand Original",
                "Chilena",
                "Comida chilena",
                "activo",
                2L
        );

        StandUpdateDTO dto = new StandUpdateDTO(
                "Stand Actualizado",
                "",
                "Nueva descripción"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Stand.class))).thenReturn(existente);

        // When
        StandDTO resultado = service.actualizarStand(1L, dto);

        // Then
        assertEquals("Stand Actualizado", resultado.getNombre());
        assertEquals("Chilena", resultado.getTipoComida());
        assertEquals("Nueva descripción", resultado.getDescripcion());

        verify(repository).save(existente);
    }

    @Test
    @DisplayName("Debe desactivar stand correctamente")
    void debeDesactivarStandCorrectamente() {

        // Given
        Stand stand = new Stand(
                1L,
                "Stand Sushi",
                "Japonesa",
                "Sushi premium",
                "activo",
                2L
        );

        when(repository.findById(1L)).thenReturn(Optional.of(stand));
        when(repository.save(any(Stand.class))).thenReturn(stand);

        // When
        StandDTO resultado = service.desactivarStand(1L);

        // Then
        assertEquals("inactivo", resultado.getEstado());

        verify(repository).save(stand);
    }
}