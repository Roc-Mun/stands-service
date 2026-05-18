package com.food.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandUpdateDTO {
    private String nombre;
    private String tipoComida;
    private String descripcion;
}
