package com.uptc.edu.co.controller;

import com.uptc.edu.co.dto.MateriaResponseDTO;
import com.uptc.edu.co.model.Materia;
import com.uptc.edu.co.service.MateriaService;
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
@RequestMapping("/api/materias")
@Tag(name = "Materias", description = "Operaciones CRUD sobre Materias")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping
    @Operation(summary = "Lista materias con paginación simple")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    public ResponseEntity<MateriaResponseDTO> listar(
            @Parameter(description = "Número de página (inicia en 1)") @RequestParam(defaultValue = "1") int pageNumber,
            @Parameter(description = "Tamaño de página (máximo 100)") @RequestParam(defaultValue = "20") int pageSize
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(materiaService.listar(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una materia por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materia encontrada"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    public ResponseEntity<Materia> obtenerPorId(
            @Parameter(description = "Id de la materia") @PathVariable Long id
    ) {
        return ResponseEntity.ok(materiaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crea una nueva materia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Materia creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Materia> crear(
            @Parameter(description = "Datos de la materia a crear") @Valid @RequestBody Materia materia
    ) {
        Materia creada = materiaService.crear(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Reemplaza completamente una materia existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materia reemplazada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    public ResponseEntity<Materia> reemplazar(
            @Parameter(description = "Id de la materia") @PathVariable Long id,
            @Parameter(description = "Datos completos de la materia") @Valid @RequestBody Materia materia
    ) {
        return ResponseEntity.ok(materiaService.reemplazar(id, materia));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualiza parcialmente uno o varios campos de una materia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materia actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    public ResponseEntity<Materia> actualizarParcial(
            @Parameter(description = "Id de la materia") @PathVariable Long id,
            @Parameter(description = "Campos a actualizar") @RequestBody Materia materia
    ) {
        return ResponseEntity.ok(materiaService.actualizarParcial(id, materia));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una materia existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Materia eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Id de la materia") @PathVariable Long id
    ) {
        materiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}