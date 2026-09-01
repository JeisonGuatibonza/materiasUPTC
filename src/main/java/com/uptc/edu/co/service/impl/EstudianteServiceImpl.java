package com.uptc.edu.co.service.impl;

import com.uptc.edu.co.dto.EstudianteDTO;
import com.uptc.edu.co.dto.EstudianteResponseDTO;
import com.uptc.edu.co.dto.PaginationDTO;
import com.uptc.edu.co.exception.ResourceNotFoundException;
import com.uptc.edu.co.model.Estudiante;
import com.uptc.edu.co.repository.EstudianteRepository;
import com.uptc.edu.co.service.EstudianteService;
import com.uptc.edu.co.specification.EstudianteSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private static final int MAX_PAGE_SIZE = 100;
    private static final Set<String> CAMPOS_ORDENABLES = Set.of(
            "nombre", "apellido", "correo", "programa", "fechaNacimiento"
    );

    private final EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public EstudianteResponseDTO buscarEstudiantes(
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
    ) {
        int paginaValidada = Math.max(pageNumber, 1);
        int tamanoValidado = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : Math.max(pageSize, 1);

        String campoOrden = CAMPOS_ORDENABLES.contains(sortBy) ? sortBy : "id";
        Sort.Direction direccion = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(paginaValidada - 1, tamanoValidado, Sort.by(direccion, campoOrden));

        Specification<Estudiante> specification = EstudianteSpecification.buildSpecification(
                id, nombres, apellidos, correo, programas,
                fechaNacimientoDesde, fechaNacimientoHasta, search, and
        );

        Page<Estudiante> pagina = estudianteRepository.findAll(specification, pageable);

        List<EstudianteDTO> data = pagina.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        PaginationDTO paginationDTO = new PaginationDTO(
                paginaValidada,
                tamanoValidado,
                pagina.getTotalElements(),
                pagina.getTotalPages()
        );

        return new EstudianteResponseDTO(data, paginationDTO);
    }

    @Override
    public Estudiante obtenerPorId(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));
    }

    @Override
    public Estudiante crear(Estudiante estudiante) {
        estudiante.setId(null);
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Estudiante reemplazar(Long id, Estudiante estudiante) {
        Estudiante existente = obtenerPorId(id);

        existente.setNombre(estudiante.getNombre());
        existente.setApellido(estudiante.getApellido());
        existente.setCorreo(estudiante.getCorreo());
        existente.setFechaNacimiento(estudiante.getFechaNacimiento());
        existente.setPrograma(estudiante.getPrograma());

        return estudianteRepository.save(existente);
    }

    @Override
    public Estudiante actualizarParcial(Long id, Estudiante estudiante) {
        Estudiante existente = obtenerPorId(id);

        if (estudiante.getNombre() != null) {
            existente.setNombre(estudiante.getNombre());
        }
        if (estudiante.getApellido() != null) {
            existente.setApellido(estudiante.getApellido());
        }
        if (estudiante.getCorreo() != null) {
            existente.setCorreo(estudiante.getCorreo());
        }
        if (estudiante.getFechaNacimiento() != null) {
            existente.setFechaNacimiento(estudiante.getFechaNacimiento());
        }
        if (estudiante.getPrograma() != null) {
            existente.setPrograma(estudiante.getPrograma());
        }

        return estudianteRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Estudiante existente = obtenerPorId(id);
        estudianteRepository.delete(existente);
    }

    private EstudianteDTO mapToDTO(Estudiante estudiante) {
        return new EstudianteDTO(
                estudiante.getId(),
                estudiante.getNombre(),
                estudiante.getApellido(),
                estudiante.getCorreo(),
                estudiante.getFechaNacimiento(),
                estudiante.getPrograma()
        );
    }
}