package com.food.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos para actualizar un stand")
public class StandUpdateDTO {

    @Schema(description = "Nuevo nombre del stand", example = "Stand Sushi Premium")
    private String nombre;

    @Schema(description = "Nuevo tipo de comida", example = "Japonesa")
    private String tipoComida;

    @Schema(description = "Nueva descripción del stand", example = "Sushi, ramen y comida japonesa")
    private String descripcion;
}