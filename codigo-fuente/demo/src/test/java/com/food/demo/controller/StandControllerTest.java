package com.food.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.demo.dto.StandCreateDTO;
import com.food.demo.dto.StandDTO;
import com.food.demo.exception.GlobalExceptionHandler;
import com.food.demo.exception.RecursoNoEncontradoException;
import com.food.demo.service.StandService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StandControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private LocalValidatorFactoryBean validator;

    @Mock
    private StandService standService;

    @InjectMocks
    private StandController standController;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders
                .standaloneSetup(standController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    @DisplayName("Debe listar stands con status 200")
    void debeListarStandsConStatus200() throws Exception {

        // Given
        StandDTO stand = new StandDTO(
                1L,
                "Stand Sushi",
                "Japonesa",
                "Sushi premium",
                "activo",
                2L
        );

        when(standService.listarStands()).thenReturn(List.of(stand));

        // When / Then
        mockMvc.perform(get("/api/v3/stands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idStand").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Stand Sushi"))
                .andExpect(jsonPath("$[0].estado").value("activo"));
    }

    @Test
    @DisplayName("Debe crear stand con status 201")
    void debeCrearStandConStatus201() throws Exception {

        // Given
        StandCreateDTO request = new StandCreateDTO(
                "Stand Sushi",
                "Japonesa",
                "Sushi premium",
                2L
        );

        StandDTO response = new StandDTO(
                1L,
                "Stand Sushi",
                "Japonesa",
                "Sushi premium",
                "activo",
                2L
        );

        when(standService.crearStand(any(StandCreateDTO.class)))
                .thenReturn(response);

        // When / Then
        mockMvc.perform(post("/api/v3/stands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idStand").value(1))
                .andExpect(jsonPath("$.nombre").value("Stand Sushi"))
                .andExpect(jsonPath("$.estado").value("activo"));
    }

    @Test
    @DisplayName("Debe retornar 404 cuando el stand no existe")
    void debeRetornar404CuandoStandNoExiste() throws Exception {

        // Given
        when(standService.obtenerStandPorId(99L))
                .thenThrow(new RecursoNoEncontradoException("Stand no encontrado"));

        // When / Then
        mockMvc.perform(get("/api/v3/stands/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Stand no encontrado"));
    }

    @Test
    @DisplayName("Debe retornar 400 cuando los datos de creación son inválidos")
    void debeRetornar400CuandoDatosSonInvalidos() throws Exception {

        // Given
        StandCreateDTO request = new StandCreateDTO(
                "",
                "",
                "Descripción incompleta",
                null
        );

        // When / Then
        mockMvc.perform(post("/api/v3/stands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("Debe listar stands por evento con status 200")
    void debeListarStandsPorEventoConStatus200() throws Exception {

        // Given
        StandDTO stand = new StandDTO(
                2L,
                "Stand Tacos",
                "Mexicana",
                "Tacos artesanales",
                "activo",
                5L
        );

        when(standService.listarStandsPorEvento(5L))
                .thenReturn(List.of(stand));

        // When / Then
        mockMvc.perform(get("/api/v3/stands/evento/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idStand").value(2))
                .andExpect(jsonPath("$[0].idEvento").value(5))
                .andExpect(jsonPath("$[0].estado").value("activo"));
    }
}