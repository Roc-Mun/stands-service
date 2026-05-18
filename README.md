# Stand-Service

## Descripción

Microservicio encargado de gestionar los stands participantes de cada evento gastronómico dentro de ReadyStand.

## Funcionalidades

- Crear stands

- Listar stands

- Buscar stand por ID

- Actualizar stand

- Activar o desactivar stand

- Asociar stand a evento

- Listar stands por evento

- Comunicación con microservicio de eventos

## Tecnologías utilizadas

- Java 21

- Spring Boot

- Spring Data JPA

- Spring Validation

- MySQL

- Maven

- Docker

- Docker Compose

## Ejecución del proyecto

```bash
docker compose up -d
```

## Endpoints principales

-Obtener stands

GET /api/v2/stands

-Obtener stand por ID

GET /api/v2/stands/{id}

-Crear stand

POST /api/v2/stands

-Actualizar stand

PUT /api/v2/stands/{id}

-Activar o desactivar stand

PATCH /api/v2/stands/{id}/estado

-Obtener stands por evento

GET /api/v2/stands/evento/{idEvento}

## Validaciones

-Validación de evento existente

-Validación de campos obligatorios

-Validación de estados

-Manejo global de errores con Bean Validation
