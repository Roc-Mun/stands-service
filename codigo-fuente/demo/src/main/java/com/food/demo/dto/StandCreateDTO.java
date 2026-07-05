package com.food.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos necesarios para crear un stand")
public class StandCreateDTO {

    @Schema(description = "Nombre del stand", example = "Stand Sushi")
    @NotBlank(message = "El nombre del stand es obligatorio")
    private String nombre;

    @Schema(description = "Tipo de comida ofrecida", example = "Japonesa")
    @NotBlank(message = "El tipo de comida es obligatorio")
    private String tipoComida;

    @Schema(description = "Descripción del stand", example = "Sushi premium y comida japonesa")
    private String descripcion;

    @Schema(description = "ID del evento asociado", example = "1")
    @NotNull(message = "El id del evento es obligatorio")
    @Positive(message = "El id del evento debe ser mayor a cero")
    private Long idEvento;
}