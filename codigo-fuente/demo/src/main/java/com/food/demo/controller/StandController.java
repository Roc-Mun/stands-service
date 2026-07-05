package com.food.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.demo.dto.StandCreateDTO;
import com.food.demo.dto.StandDTO;
import com.food.demo.dto.StandUpdateDTO;
import com.food.demo.service.StandService;

@Tag(name = "Stands", description = "Operaciones relacionadas con stands gastronómicos")
@RestController
@RequestMapping("/api/v3/stands")
public class StandController {

    private final StandService service;

    public StandController(StandService service) {
        this.service = service;
    }

    @Operation(summary = "Listar stands")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stands listados correctamente")
    })
    @GetMapping
    public List<StandDTO> listarTodos() {
        return service.listarStands();
    }

    @Operation(summary = "Listar stands activos por evento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stands listados correctamente"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            @ApiResponse(responseCode = "409", description = "El evento no está disponible"),
            @ApiResponse(responseCode = "503", description = "Servicio de eventos no disponible")
    })
    @GetMapping("/evento/{idEvento}")
    public List<StandDTO> listarPorEvento(
            @Parameter(description = "ID del evento", example = "1")
            @PathVariable Long idEvento) {

        return service.listarStandsPorEvento(idEvento);
    }

    @Operation(summary = "Crear stand")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Stand creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado"),
            @ApiResponse(responseCode = "409", description = "El evento no permite registrar stands"),
            @ApiResponse(responseCode = "503", description = "Servicio de eventos no disponible")
    })
    @PostMapping
    public ResponseEntity<StandDTO> crear(@Valid @RequestBody StandCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearStand(dto));
    }

    @Operation(summary = "Obtener stand por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stand encontrado"),
            @ApiResponse(responseCode = "404", description = "Stand no encontrado")
    })
    @GetMapping("/{id}")
    public StandDTO obtenerPorId(
            @Parameter(description = "ID del stand", example = "1")
            @PathVariable Long id) {

        return service.obtenerStandPorId(id);
    }

    @Operation(summary = "Actualizar stand")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stand actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Stand no encontrado")
    })
    @PutMapping("/{id}")
    public StandDTO actualizar(
            @Parameter(description = "ID del stand", example = "1")
            @PathVariable Long id,
            @RequestBody StandUpdateDTO dto) {

        return service.actualizarStand(id, dto);
    }

    @Operation(summary = "Asignar stand a evento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stand asignado correctamente"),
            @ApiResponse(responseCode = "404", description = "Stand o evento no encontrado"),
            @ApiResponse(responseCode = "409", description = "El evento no permite registrar stands"),
            @ApiResponse(responseCode = "503", description = "Servicio de eventos no disponible")
    })
    @PutMapping("/{id}/evento")
    public StandDTO asignarEvento(
            @Parameter(description = "ID del stand", example = "1")
            @PathVariable Long id,
            @Parameter(description = "ID del evento", example = "1")
            @RequestParam Long idEvento) {

        return service.asignarStandAEvento(id, idEvento);
    }

    @Operation(summary = "Activar stand")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stand activado correctamente"),
            @ApiResponse(responseCode = "404", description = "Stand no encontrado")
    })
    @PutMapping("/{id}/activar")
    public ResponseEntity<StandDTO> activar(
            @Parameter(description = "ID del stand", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(service.activarStand(id));
    }

    @Operation(summary = "Desactivar stand")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stand desactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Stand no encontrado")
    })
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<StandDTO> desactivar(
            @Parameter(description = "ID del stand", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(service.desactivarStand(id));
    }
}