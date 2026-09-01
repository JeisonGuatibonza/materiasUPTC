package com.uptc.edu.co.controller;

import com.uptc.edu.co.dto.EstudianteResponseDTO;
import com.uptc.edu.co.model.Estudiante;
import com.uptc.edu.co.service.EstudianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@Tag(name = "Estudiantes", description = "Operaciones CRUD y consulta avanzada sobre Estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    @Operation(summary = "Lista estudiantes con paginación, ordenamiento y filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<EstudianteResponseDTO> listarEstudiantes(
            @Parameter(description = "Número de página (inicia en 1)") @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(description = "Tamaño de página (máximo 100)") @RequestParam(defaultValue = "20") int pageSize,
            @Parameter(description = "Campo de ordenamiento: nombre, apellido, correo, programa o fechaNacimiento")
            @RequestParam(defaultValue = "apellido") String sortBy,
            @Parameter(description = "Dirección de ordenamiento: asc o desc") @RequestParam(defaultValue = "asc") String sortDirection,
            @Parameter(description = "Búsqueda exacta por id") @RequestParam(required = false) Long id,
            @Parameter(description = "Uno o varios nombres (coincidencia parcial)") @RequestParam(required = false) List<String> nombres,
            @Parameter(description = "Uno o varios apellidos (coincidencia parcial)") @RequestParam(required = false) List<String> apellidos,
            @Parameter(description = "Correo (coincidencia parcial)") @RequestParam(required = false) String correo,
            @Parameter(description = "Uno o varios programas (coincidencia exacta)") @RequestParam(required = false) List<String> programas,
            @Parameter(description = "Fecha de nacimiento desde (yyyy-MM-dd)")
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate fechaNacimientoDesde,
            @Parameter(description = "Fecha de nacimiento hasta (yyyy-MM-dd)")
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate fechaNacimientoHasta,
            @Parameter(description = "Búsqueda libre en nombre, apellido y correo") @RequestParam(required = false) String search,
            @Parameter(description = "true combina filtros con AND, false con OR") @RequestParam(defaultValue = "true") Boolean and
    ) {
        EstudianteResponseDTO respuesta = estudianteService.buscarEstudiantes(
                pageNumber, pageSize, sortBy, sortDirection,
                id, nombres, apellidos, correo, programas,
                fechaNacimientoDesde, fechaNacimientoHasta, search, and
        );

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un estudiante por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<Estudiante> obtenerPorId(
            @Parameter(description = "Id del estudiante") @PathVariable Long id
    ) {
        return ResponseEntity.ok(estudianteService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Estudiante> crear(
            @Parameter(description = "Datos del estudiante a crear") @Valid @RequestBody Estudiante estudiante
    ) {
        Estudiante creado = estudianteService.crear(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Reemplaza completamente un estudiante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante reemplazado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<Estudiante> reemplazar(
            @Parameter(description = "Id del estudiante") @PathVariable Long id,
            @Parameter(description = "Datos completos del estudiante") @Valid @RequestBody Estudiante estudiante
    ) {
        return ResponseEntity.ok(estudianteService.reemplazar(id, estudiante));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza parcialmente uno o varios campos de un estudiante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<Estudiante> actualizarParcial(
            @Parameter(description = "Id del estudiante") @PathVariable Long id,
            @Parameter(description = "Campos a actualizar") @RequestBody Estudiante estudiante
    ) {
        return ResponseEntity.ok(estudianteService.actualizarParcial(id, estudiante));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un estudiante existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Id del estudiante") @PathVariable Long id
    ) {
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}