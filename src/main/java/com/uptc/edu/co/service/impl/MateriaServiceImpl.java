package com.uptc.edu.co.service.impl;

import com.uptc.edu.co.dto.MateriaDTO;
import com.uptc.edu.co.dto.MateriaResponseDTO;
import com.uptc.edu.co.dto.PaginationDTO;
import com.uptc.edu.co.exception.ResourceNotFoundException;
import com.uptc.edu.co.model.Materia;
import com.uptc.edu.co.repository.MateriaRepository;
import com.uptc.edu.co.service.MateriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriaServiceImpl implements MateriaService {

    private static final int MAX_PAGE_SIZE = 100;

    private final MateriaRepository materiaRepository;

    public MateriaServiceImpl(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @Override
    public MateriaResponseDTO listar(int pageNumber, int pageSize) {
        int paginaValidada = Math.max(pageNumber, 1);
        int tamanoValidado = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : Math.max(pageSize, 1);

        Pageable pageable = PageRequest.of(paginaValidada - 1, tamanoValidado);
        Page<Materia> pagina = materiaRepository.findAll(pageable);

        List<MateriaDTO> data = pagina.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        PaginationDTO paginationDTO = new PaginationDTO(
                paginaValidada,
                tamanoValidado,
                pagina.getTotalElements(),
                pagina.getTotalPages()
        );

        return new MateriaResponseDTO(data, paginationDTO);
    }

    @Override
    public Materia obtenerPorId(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id: " + id));
    }

    @Override
    public Materia crear(Materia materia) {
        materia.setId(null);
        return materiaRepository.save(materia);
    }

    @Override
    public Materia reemplazar(Long id, Materia materia) {
        Materia existente = obtenerPorId(id);

        existente.setNombre(materia.getNombre());
        existente.setCodigo(materia.getCodigo());
        existente.setCreditos(materia.getCreditos());
        existente.setSemestre(materia.getSemestre());

        return materiaRepository.save(existente);
    }

    @Override
    public Materia actualizarParcial(Long id, Materia materia) {
        Materia existente = obtenerPorId(id);

        if (materia.getNombre() != null) {
            existente.setNombre(materia.getNombre());
        }
        if (materia.getCodigo() != null) {
            existente.setCodigo(materia.getCodigo());
        }
        if (materia.getCreditos() != null) {
            existente.setCreditos(materia.getCreditos());
        }
        if (materia.getSemestre() != null) {
            existente.setSemestre(materia.getSemestre());
        }

        return materiaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Materia existente = obtenerPorId(id);
        materiaRepository.delete(existente);
    }

    private MateriaDTO mapToDTO(Materia materia) {
        return new MateriaDTO(
                materia.getId(),
                materia.getNombre(),
                materia.getCodigo(),
                materia.getCreditos(),
                materia.getSemestre()
        );
    }
}