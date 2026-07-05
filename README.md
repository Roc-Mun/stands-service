# Stand-Service

## Descripción

Microservicio encargado de administrar stands gastronómicos dentro de ReadyStand, incluyendo creación, actualización, activación, desactivación y consulta de stands asociados a eventos.

## Funcionalidades

* Crear stands
* Listar stands
* Buscar stand por ID
* Actualizar stand
* Asignar stand a evento
* Activar stand
* Desactivar stand
* Consultar stands activos por evento
* Comunicación con microservicio de eventos

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Validation
* Spring Cloud OpenFeign
* Springdoc OpenAPI (Swagger)
* MySQL
* H2 Database (Testing)
* JUnit 5
* Mockito
* Maven
* Docker
* Docker Compose

## Ejecución del proyecto

```bash
docker compose up -d
```

## Ejecución de pruebas

```bash
mvn test
```

## Swagger

Disponible en:

```text
http://localhost:8083/doc/swagger-ui.html
```

## Endpoints principales

### Obtener stands

GET /api/v3/stands

### Obtener stand por ID

GET /api/v3/stands/{id}

### Crear stand

POST /api/v3/stands

### Actualizar stand

PUT /api/v3/stands/{id}

### Asignar stand a evento

PUT /api/v3/stands/{id}/evento?idEvento={idEvento}

### Activar stand

PUT /api/v3/stands/{id}/activar

### Desactivar stand

PUT /api/v3/stands/{id}/desactivar

### Stands activos por evento

GET /api/v3/stands/evento/{idEvento}

## Testing

El proyecto incluye pruebas unitarias para las capas:

* Modelo
* Servicio
* Repositorio
* Controlador

Todas las pruebas deben finalizar con BUILD SUCCESS.

## Validaciones

* Validación de campos obligatorios del stand
* Validación de ID de evento
* Validación de evento existente
* Validación de estado del evento asociado
* Validación de stands inexistentes
* Manejo global de errores con Bean Validation