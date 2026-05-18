package com.food.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandCreateDTO {

    @NotBlank(message = "El nombre del stand es obligatorio")
    private String nombre;

    @NotBlank(message = "El tipo de comida es obligatorio")
    private String tipoComida;

    private String descripcion;

    @NotNull(message = "El id del evento es obligatorio")
    @Positive(message = "El id del evento debe ser mayor a cero")
    private Long idEvento;
}
