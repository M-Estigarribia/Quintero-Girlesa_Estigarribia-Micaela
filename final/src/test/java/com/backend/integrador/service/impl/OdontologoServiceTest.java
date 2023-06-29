package com.backend.integrador.service.impl;


import com.backend.integrador.dto.OdontologoDto;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.exceptions.ConflictException;
import com.backend.integrador.exceptions.ResourceNotFoundException;
import com.backend.integrador.service.IOdontologoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoServiceTest {
    @Autowired
    IOdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarUnOdontologo() throws ConflictException {
        Odontologo odontologo = new Odontologo("Nombre", "Prueba", "ODO-1112223334");
        OdontologoDto odontologoResult = odontologoService.guardarOdontologo(odontologo);

        assertEquals("ODO-1112223334", odontologoResult.getMatricula());
    }

    @Test
    @Order(2)
    void deberiaListarSolamenteUnOdontologo() {
        List<OdontologoDto> odontologoDtoList = odontologoService.listarOdontologos();

        assertEquals(1, odontologoDtoList.size());
    }

    @Test
    @Order(3)
    void deberiaEliminarElOdontologoConId1() throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(1L);

        assertThrows(ResourceNotFoundException.class, () -> odontologoService.eliminarOdontologo(1L));
    }
}
