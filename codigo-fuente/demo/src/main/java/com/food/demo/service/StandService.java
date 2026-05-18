package com.food.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import feign.FeignException;

import com.food.demo.client.EventoClient;
import com.food.demo.dto.EventoDTO;
import com.food.demo.dto.StandCreateDTO;
import com.food.demo.dto.StandDTO;
import com.food.demo.dto.StandUpdateDTO;
import com.food.demo.exception.EstadoInvalidoException;
import com.food.demo.exception.RecursoNoEncontradoException;
import com.food.demo.exception.ServicioNoDisponibleException;
import com.food.demo.model.Stand;
import com.food.demo.repository.StandRepository;

@Service
public class StandService {

    private static final Logger log =
            LoggerFactory.getLogger(StandService.class);

    private final StandRepository repository;
    private final EventoClient eventoClient;

    public StandService(
            StandRepository repository,
            EventoClient eventoClient) {

        this.repository = repository;
        this.eventoClient = eventoClient;
    }

    public List<StandDTO> listarStands() {

        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public StandDTO crearStand(StandCreateDTO dto) {

        EventoDTO evento;

        try {

            log.info("Consultando evento id={}", dto.getIdEvento());

            evento = eventoClient.obtenerEventoPorId(dto.getIdEvento());

            log.info("Evento encontrado: {}", evento.getNombre());

        } catch (FeignException.NotFound e) {

            log.warn("Evento id={} no existe", dto.getIdEvento());

            throw new RecursoNoEncontradoException(
                    "Evento no encontrado");

        } catch (FeignException e) {

            log.error("Error al consultar servicio Eventos: {}",
                    e.getMessage());

            throw new ServicioNoDisponibleException(
                    "Servicio de eventos no disponible");
        }

        if (!"publicado".equalsIgnoreCase(evento.getEstado())
                && !"iniciado".equalsIgnoreCase(evento.getEstado())) {

            throw new EstadoInvalidoException(
                    "El evento no permite registrar stands");
        }

        Stand stand = new Stand();

        stand.setNombre(dto.getNombre());
        stand.setTipoComida(dto.getTipoComida());
        stand.setDescripcion(dto.getDescripcion());
        stand.setIdEvento(dto.getIdEvento());
        stand.setEstado("activo");

        Stand guardado = repository.save(stand);

        log.info("Stand creado exitosamente id={}",
                guardado.getIdStand());

        return toDto(guardado);
    }

    public StandDTO obtenerStandPorId(Long id) {
        return toDto(obtenerEntidadPorId(id));
    }

    public List<StandDTO> listarStandsPorEvento(Long idEvento) {

        EventoDTO evento;

        try {

            log.info("Consultando evento id={}", idEvento);

            evento = eventoClient.obtenerEventoPorId(idEvento);

            log.info("Evento encontrado: {}", evento.getNombre());

        } catch (FeignException.NotFound e) {

            log.warn("Evento id={} no existe", idEvento);

            throw new RecursoNoEncontradoException(
                    "Evento no encontrado");

        } catch (FeignException e) {

            log.error("Error al consultar servicio Eventos: {}",
                    e.getMessage());

            throw new ServicioNoDisponibleException(
                    "Servicio de eventos no disponible");
        }

        if (!"publicado".equalsIgnoreCase(evento.getEstado())
                && !"iniciado".equalsIgnoreCase(evento.getEstado())) {

            throw new EstadoInvalidoException(
                    "El evento no está disponible");
        }

        return repository.findByIdEventoAndEstado(
                        idEvento,
                        "activo")
                .stream()
                .map(this::toDto)
                .toList();
    }

    public StandDTO actualizarStand(Long id, StandUpdateDTO dto) {

        if (dto == null) {
            dto = new StandUpdateDTO();
        }

        Stand datos = new Stand();

        datos.setNombre(dto.getNombre());
        datos.setTipoComida(dto.getTipoComida());
        datos.setDescripcion(dto.getDescripcion());

        return toDto(actualizarEntidad(id, datos));
    }

    public StandDTO asignarStandAEvento(
            Long idStand,
            Long idEvento) {

        eventoClient.obtenerEventoPorId(idEvento);

        Stand stand = obtenerEntidadPorId(idStand);

        stand.setIdEvento(idEvento);

        return toDto(repository.save(stand));
    }

    public StandDTO desactivarStand(Long id) {

        Stand stand = obtenerEntidadPorId(id);

        stand.setEstado("inactivo");

        return toDto(repository.save(stand));
    }

    public StandDTO activarStand(Long id) {

        Stand stand = obtenerEntidadPorId(id);

        stand.setEstado("activo");

        return toDto(repository.save(stand));
    }

    private Stand obtenerEntidadPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Stand no encontrado"));
    }

    private Stand actualizarEntidad(Long id, Stand datos) {

        Stand existente = obtenerEntidadPorId(id);

        if (datos.getNombre() != null &&
                !datos.getNombre().isBlank()) {

            existente.setNombre(datos.getNombre());
        }

        if (datos.getTipoComida() != null &&
                !datos.getTipoComida().isBlank()) {

            existente.setTipoComida(datos.getTipoComida());
        }

        if (datos.getDescripcion() != null) {
            existente.setDescripcion(datos.getDescripcion());
        }

        return repository.save(existente);
    }

    private StandDTO toDto(Stand s) {

        return new StandDTO(
                s.getIdStand(),
                s.getNombre(),
                s.getTipoComida(),
                s.getDescripcion(),
                s.getEstado(),
                s.getIdEvento()
        );
    }
}