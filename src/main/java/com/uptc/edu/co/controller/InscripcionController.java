package com.uptc.edu.co.controller;

import com.uptc.edu.co.dto.InscripcionRequestDTO;
import com.uptc.edu.co.dto.InscripcionResponseDTO;
import com.uptc.edu.co.model.Inscripcion;
import com.uptc.edu.co.service.InscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscripciones")
@Tag(name = "Inscripciones", description = "Operaciones CRUD sobre Inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping
    @Operation(summary = "Lista inscripciones con paginación simple")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<InscripcionResponseDTO> listar(
            @Parameter(description = "Número de página (inicia en 1)") @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(description = "Tamaño de página (máximo 100)") @RequestParam(defaultValue = "20") int pageSize
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(inscripcionService.listar(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una inscripción por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripción encontrada"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    public ResponseEntity<Inscripcion> obtenerPorId(
            @Parameter(description = "Id de la inscripción") @PathVariable Long id
    ) {
        return ResponseEntity.ok(inscripcionService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crea una nueva inscripción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscripción creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Estudiante o materia no encontrados")
    })
    public ResponseEntity<Inscripcion> crear(
            @Parameter(description = "Datos de la inscripción a crear") @Valid @RequestBody InscripcionRequestDTO inscripcionRequestDTO
    ) {
        Inscripcion creada = inscripcionService.crear(inscripcionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Reemplaza completamente una inscripción existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripción reemplazada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Inscripción, estudiante o materia no encontrados")
    })
    public ResponseEntity<Inscripcion> reemplazar(
            @Parameter(description = "Id de la inscripción") @PathVariable Long id,
            @Parameter(description = "Datos completos de la inscripción") @Valid @RequestBody InscripcionRequestDTO inscripcionRequestDTO
    ) {
        return ResponseEntity.ok(inscripcionService.reemplazar(id, inscripcionRequestDTO));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza parcialmente uno o varios campos de una inscripción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripción actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Inscripción, estudiante o materia no encontrados")
    })
    public ResponseEntity<Inscripcion> actualizarParcial(
            @Parameter(description = "Id de la inscripción") @PathVariable Long id,
            @Parameter(description = "Campos a actualizar") @RequestBody InscripcionRequestDTO inscripcionRequestDTO
    ) {
        return ResponseEntity.ok(inscripcionService.actualizarParcial(id, inscripcionRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una inscripción existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inscripción eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Id de la inscripción") @PathVariable Long id
    ) {
        inscripcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}