package com.food.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de un stand")
public class StandDTO {

    @Schema(description = "ID del stand", example = "1")
    private Long idStand;

    @Schema(description = "Nombre del stand", example = "Stand Sushi")
    private String nombre;

    @Schema(description = "Tipo de comida", example = "Japonesa")
    private String tipoComida;

    @Schema(description = "Descripción del stand", example = "Sushi premium y comida japonesa")
    private String descripcion;

    @Schema(description = "Estado del stand", example = "activo")
    private String estado;

    @Schema(description = "ID del evento asociado", example = "1")
    private Long idEvento;
}