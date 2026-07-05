package com.food.demo.repository;

import com.food.demo.model.Stand;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false"
})
class StandRepositoryTest {

    @Autowired
    private StandRepository repository;

    @Test
    @DisplayName("Debe guardar stand correctamente")
    void debeGuardarStandCorrectamente() {

        // Given
        Stand stand = Stand.builder()
                .nombre("Stand Sushi")
                .tipoComida("Japonesa")
                .descripcion("Sushi premium")
                .estado("activo")
                .idEvento(2L)
                .build();

        // When
        Stand guardado = repository.save(stand);

        // Then
        assertNotNull(guardado.getIdStand());
        assertEquals("Stand Sushi", guardado.getNombre());
        assertEquals("activo", guardado.getEstado());
    }

    @Test
    @DisplayName("Debe buscar stand por ID")
    void debeBuscarStandPorId() {

        // Given
        Stand stand = Stand.builder()
                .nombre("Stand Tacos")
                .tipoComida("Mexicana")
                .descripcion("Tacos artesanales")
                .estado("activo")
                .idEvento(3L)
                .build();

        Stand guardado = repository.save(stand);

        // When
        Optional<Stand> encontrado = repository.findById(guardado.getIdStand());

        // Then
        assertTrue(encontrado.isPresent());
        assertEquals("Stand Tacos", encontrado.get().getNombre());
    }

    @Test
    @DisplayName("Debe listar todos los stands")
    void debeListarTodosLosStands() {

        // Given
        Stand stand1 = Stand.builder()
                .nombre("Stand Sushi")
                .tipoComida("Japonesa")
                .descripcion("Sushi premium")
                .estado("activo")
                .idEvento(2L)
                .build();

        Stand stand2 = Stand.builder()
                .nombre("Stand Pizza")
                .tipoComida("Italiana")
                .descripcion("Pizza artesanal")
                .estado("inactivo")
                .idEvento(2L)
                .build();

        repository.save(stand1);
        repository.save(stand2);

        // When
        List<Stand> stands = repository.findAll();

        // Then
        assertEquals(2, stands.size());
    }

    @Test
    @DisplayName("Debe buscar stands activos por evento")
    void debeBuscarStandsActivosPorEvento() {

        // Given
        Stand activo = Stand.builder()
                .nombre("Stand Activo")
                .tipoComida("Chilena")
                .descripcion("Comida chilena")
                .estado("activo")
                .idEvento(10L)
                .build();

        Stand inactivo = Stand.builder()
                .nombre("Stand Inactivo")
                .tipoComida("Peruana")
                .descripcion("Comida peruana")
                .estado("inactivo")
                .idEvento(10L)
                .build();

        repository.save(activo);
        repository.save(inactivo);

        // When
        List<Stand> resultado = repository.findByIdEventoAndEstado(10L, "activo");

        // Then
        assertEquals(1, resultado.size());
        assertEquals("Stand Activo", resultado.get(0).getNombre());
        assertEquals("activo", resultado.get(0).getEstado());
    }
}