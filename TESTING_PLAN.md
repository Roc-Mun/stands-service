# TESTING_PLAN.md — Microservicio Stands

## Pruebas Unitarias y Cobertura de Reglas de Negocio

Este documento resume las reglas de negocio críticas del microservicio de Stands y el estado actual de cobertura mediante pruebas unitarias.

El microservicio fue probado en cuatro capas principales: modelo, servicio, repositorio y controlador.

---

## Reglas de Negocio Críticas

1. Un stand solo puede crearse si el evento asociado existe.
2. Un stand solo puede crearse si el evento está en estado `publicado` o `iniciado`.
3. Al crear un stand, este debe quedar inicialmente en estado `activo`.
4. No se debe permitir operar sobre stands inexistentes.
5. La consulta de stands por evento debe retornar solo stands activos.
6. Un stand puede cambiar de estado entre `activo` e `inactivo`.
7. La comunicación con el microservicio de Eventos debe manejar errores cuando el evento no existe o el servicio no está disponible.

---

## Cobertura Actual

| Regla / Capa | Estado | Casos Cubiertos |
|---|---|---|
| Modelo Stand | ✅ Cubierta | Constructor vacío, constructor completo, getters/setters, equals y hashCode |
| Crear stand correctamente | ✅ Cubierta | Creación exitosa cuando el evento está `publicado` |
| Estado inicial del stand | ✅ Cubierta | Stand creado con estado `activo` |
| Evento en estado inválido | ✅ Cubierta | Lanza excepción si el evento está en estado `borrador` |
| Stand inexistente | ✅ Cubierta | Lanza excepción al buscar stand no existente |
| Actualizar stand | ✅ Cubierta | Actualización parcial manteniendo campos vacíos sin cambios |
| Desactivar stand | ✅ Cubierta | Cambio de estado a `inactivo` |
| Repositorio Stand | ✅ Cubierta | `save`, `findById`, `findAll`, `findByIdEventoAndEstado` |
| Controlador Stand | ✅ Cubierta | Respuestas HTTP 200, 201, 400 y 404 mediante MockMvc |

---

## Clases de Test Implementadas

| Capa | Clase de Test | Herramientas |
|---|---|---|
| Modelo | `StandTest` | JUnit 5 |
| Servicio | `StandServiceTest` | JUnit 5, Mockito, `@Mock`, `@InjectMocks` |
| Repositorio | `StandRepositoryTest` | `@DataJpaTest`, H2 en memoria |
| Controlador | `StandControllerTest` | MockMvc |

---

## Reflexión y Deuda Técnica

Como mejora futura, se podrían agregar más pruebas para validar errores remotos específicos del microservicio de Eventos, por ejemplo cuando Feign retorna errores distintos a 404 o cuando el servicio externo no responde.

También se podrían ampliar los casos de prueba relacionados con la reasignación de stands a otros eventos y con transiciones inválidas de estado.

---

## Ejecución de Pruebas

Comando utilizado:

```bash
mvn test
```

Resultado esperado:

```text
Tests run: 19, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```