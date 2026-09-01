package com.uptc.edu.co.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDTO {

    private Long id;
    private Long estudianteId;
    private Long materiaId;
    private String periodo;
    private String estado;
}