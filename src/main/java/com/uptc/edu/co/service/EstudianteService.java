package com.uptc.edu.co.service;

import com.uptc.edu.co.dto.EstudianteResponseDTO;
import com.uptc.edu.co.model.Estudiante;

import java.time.LocalDate;
import java.util.List;

public interface EstudianteService {

    EstudianteResponseDTO buscarEstudiantes(
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortDirection,
            Long id,
            List<String> nombres,
            List<String> apellidos,
            String correo,
            List<String> programas,
            LocalDate fechaNacimientoDesde,
            LocalDate fechaNacimientoHasta,
            String search,
            Boolean and
    );

    Estudiante obtenerPorId(Long id);

    Estudiante crear(Estudiante estudiante);

    Estudiante reemplazar(Long id, Estudiante estudiante);

    Estudiante actualizarParcial(Long id, Estudiante estudiante);

    void eliminar(Long id);
}