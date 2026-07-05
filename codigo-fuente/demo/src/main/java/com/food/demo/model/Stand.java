package com.food.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stands")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stand")
    private Long idStand;

    private String nombre;

    @Column(name = "tipo_comida")
    private String tipoComida;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String estado;

    @Column(name = "id_evento")
    private Long idEvento;
}