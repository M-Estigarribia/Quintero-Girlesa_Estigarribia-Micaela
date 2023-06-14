package com.backend.service;

import com.backend.dao.impl.OdontologoDaoH2;
import com.backend.entity.Odontologo;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {
    private static Connection connection = null;
    private OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2(connection));

    @Test
    public void deberiaGuardarUnOdontologo() {
        Odontologo odontologoPrueba = new Odontologo(555555, "Pepito", "Perez");

        Odontologo odontologoResult = odontologoService.guardarOdontologo(odontologoPrueba);

        assertEquals(555555, odontologoResult.getMatricula());
    }

    @Test
    void listarOdontologos() {
        List<Odontologo> odontologoListPrueba = odontologoService.listarOdontologos();

        assertFalse(odontologoListPrueba.isEmpty());
    }

    // Implementa los tests para los otros métodos
}