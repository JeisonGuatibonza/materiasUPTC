package com.uptc.edu.co.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaResponseDTO {

    private List<MateriaDTO> data;
    private PaginationDTO pagination;
}