package com.food.demo.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.demo.dto.StandCreateDTO;
import com.food.demo.dto.StandDTO;
import com.food.demo.dto.StandUpdateDTO;
import com.food.demo.service.StandService;

@RestController
@RequestMapping("/api/v2/stands")
public class StandController {

    private final StandService service;
    

    public StandController(StandService service) {
        this.service = service;
    }

    @GetMapping
    public List<StandDTO> listarTodos() {
        return service.listarStands();
    }

    @GetMapping("/evento/{idEvento}")
    public List<StandDTO> listarPorEvento(@PathVariable Long idEvento) {
        return service.listarStandsPorEvento(idEvento);
    }

    @PostMapping
    public ResponseEntity<StandDTO> crear(@Valid @RequestBody StandCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearStand(dto));
    }

    @GetMapping("/{id}")
    public StandDTO obtenerPorId(@PathVariable Long id) {
        return service.obtenerStandPorId(id);
    }

    @PutMapping("/{id}")
    public StandDTO actualizar(@PathVariable Long id, @RequestBody StandUpdateDTO dto) {
        return service.actualizarStand(id, dto);
    }

    @PutMapping("/{id}/evento")
    public StandDTO asignarEvento(@PathVariable Long id, @RequestParam Long idEvento) {
        return service.asignarStandAEvento(id, idEvento);
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<StandDTO> activar(@PathVariable Long id) {
        return ResponseEntity.ok(service.activarStand(id));
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<StandDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(service.desactivarStand(id));
    }
}