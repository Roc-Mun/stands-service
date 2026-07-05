package com.food.demo.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de evento recibida desde el microservicio Evento")
public class EventoDTO {

    @Schema(description = "ID del evento", example = "1")
    private Long idEvento;

    @Schema(description = "Nombre del evento", example = "Festival Gastronómico 2026")
    private String nombre;

    @Schema(description = "Organización responsable", example = "Food Fest Chile")
    private String organizacionResponsable;

    @Schema(description = "Fecha de inicio", example = "2026-08-15T10:00:00")
    private LocalDateTime fechaInicio;

    @Schema(description = "Fecha de finalización", example = "2026-08-15T20:00:00")
    private LocalDateTime fechaFin;

    @Schema(description = "Ubicación", example = "Parque O'Higgins")
    private String ubicacion;

    @Schema(description = "Estado del evento", example = "publicado")
    private String estado;

    @Schema(description = "ID del organizador", example = "1")
    private Long idOrganizador;
}