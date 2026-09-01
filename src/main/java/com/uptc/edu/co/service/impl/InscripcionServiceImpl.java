package com.uptc.edu.co.service.impl;

import com.uptc.edu.co.dto.InscripcionDTO;
import com.uptc.edu.co.dto.InscripcionRequestDTO;
import com.uptc.edu.co.dto.InscripcionResponseDTO;
import com.uptc.edu.co.dto.PaginationDTO;
import com.uptc.edu.co.exception.ResourceNotFoundException;
import com.uptc.edu.co.model.Estudiante;
import com.uptc.edu.co.model.Inscripcion;
import com.uptc.edu.co.model.Materia;
import com.uptc.edu.co.repository.EstudianteRepository;
import com.uptc.edu.co.repository.InscripcionRepository;
import com.uptc.edu.co.repository.MateriaRepository;
import com.uptc.edu.co.service.InscripcionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    private static final int MAX_PAGE_SIZE = 100;

    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    public InscripcionServiceImpl(
            InscripcionRepository inscripcionRepository,
            EstudianteRepository estudianteRepository,
            MateriaRepository materiaRepository
    ) {
        this.inscripcionRepository = inscripcionRepository;
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
    }

    @Override
    public InscripcionResponseDTO listar(int pageNumber, int pageSize) {
        int paginaValidada = Math.max(pageNumber, 1);
        int tamanoValidado = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : Math.max(pageSize, 1);

        Pageable pageable = PageRequest.of(paginaValidada - 1, tamanoValidado);
        Page<Inscripcion> pagina = inscripcionRepository.findAll(pageable);

        List<InscripcionDTO> data = pagina.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        PaginationDTO paginationDTO = new PaginationDTO(
                paginaValidada,
                tamanoValidado,
                pagina.getTotalElements(),
                pagina.getTotalPages()
        );

        return new InscripcionResponseDTO(data, paginationDTO);
    }

    @Override
    public Inscripcion obtenerPorId(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con id: " + id));
    }

    @Override
    public Inscripcion crear(InscripcionRequestDTO dto) {
        Inscripcion inscripcion = new Inscripcion();
        aplicarDatos(inscripcion, dto);
        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public Inscripcion reemplazar(Long id, InscripcionRequestDTO dto) {
        Inscripcion existente = obtenerPorId(id);
        aplicarDatos(existente, dto);
        return inscripcionRepository.save(existente);
    }

    @Override
    public Inscripcion actualizarParcial(Long id, InscripcionRequestDTO dto) {
        Inscripcion existente = obtenerPorId(id);

        if (dto.getEstudianteId() != null) {
            existente.setEstudiante(obtenerEstudiante(dto.getEstudianteId()));
        }
        if (dto.getMateriaId() != null) {
            existente.setMateria(obtenerMateria(dto.getMateriaId()));
        }
        if (dto.getPeriodo() != null) {
            existente.setPeriodo(dto.getPeriodo());
        }
        if (dto.getEstado() != null) {
            existente.setEstado(dto.getEstado());
        }

        return inscripcionRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Inscripcion existente = obtenerPorId(id);
        inscripcionRepository.delete(existente);
    }

    private void aplicarDatos(Inscripcion inscripcion, InscripcionRequestDTO dto) {
        inscripcion.setEstudiante(obtenerEstudiante(dto.getEstudianteId()));
        inscripcion.setMateria(obtenerMateria(dto.getMateriaId()));
        inscripcion.setPeriodo(dto.getPeriodo());
        inscripcion.setEstado(dto.getEstado());
    }

    private Estudiante obtenerEstudiante(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));
    }

    private Materia obtenerMateria(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Materia no encontrada con id: " + id));
    }

    private InscripcionDTO mapToDTO(Inscripcion inscripcion) {
        return new InscripcionDTO(
                inscripcion.getId(),
                inscripcion.getEstudiante() != null ? inscripcion.getEstudiante().getId() : null,
                inscripcion.getMateria() != null ? inscripcion.getMateria().getId() : null,
                inscripcion.getPeriodo(),
                inscripcion.getEstado()
        );
    }
}