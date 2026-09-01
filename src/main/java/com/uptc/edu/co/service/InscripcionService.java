package com.uptc.edu.co.service;

import com.uptc.edu.co.dto.InscripcionRequestDTO;
import com.uptc.edu.co.dto.InscripcionResponseDTO;
import com.uptc.edu.co.model.Inscripcion;

public interface InscripcionService {

    InscripcionResponseDTO listar(int pageNumber, int pageSize);

    Inscripcion obtenerPorId(Long id);

    Inscripcion crear(InscripcionRequestDTO inscripcionRequestDTO);

    Inscripcion reemplazar(Long id, InscripcionRequestDTO inscripcionRequestDTO);

    Inscripcion actualizarParcial(Long id, InscripcionRequestDTO inscripcionRequestDTO);

    void eliminar(Long id);
}