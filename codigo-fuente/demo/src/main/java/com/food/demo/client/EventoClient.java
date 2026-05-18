package com.food.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.food.demo.dto.EventoDTO;

@FeignClient(
    name = "evento-client",
    url = "${evento.service.url}"
)
public interface EventoClient {

    @GetMapping("/api/v2/eventos/{id}")
    EventoDTO obtenerEventoPorId(@PathVariable("id") Long id);

}