package com.uptc.edu.co.service;

import com.uptc.edu.co.dto.MateriaResponseDTO;
import com.uptc.edu.co.model.Materia;

public interface MateriaService {

    MateriaResponseDTO listar(int pageNumber, int pageSize);

    Materia obtenerPorId(Long id);

    Materia crear(Materia materia);

    Materia reemplazar(Long id, Materia materia);

    Materia actualizarParcial(Long id, Materia materia);

    void eliminar(Long id);
}