package com.uptc.edu.co.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaDTO {

    private Long id;
    private String nombre;
    private String codigo;
    private Integer creditos;
    private Integer semestre;
}