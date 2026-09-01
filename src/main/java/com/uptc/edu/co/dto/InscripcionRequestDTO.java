package com.uptc.edu.co.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionRequestDTO {

    @NotNull(message = "El id del estudiante es obligatorio")
    private Long estudianteId;

    @NotNull(message = "El id de la materia es obligatorio")
    private Long materiaId;

    @NotBlank(message = "El periodo es obligatorio")
    private String periodo;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}