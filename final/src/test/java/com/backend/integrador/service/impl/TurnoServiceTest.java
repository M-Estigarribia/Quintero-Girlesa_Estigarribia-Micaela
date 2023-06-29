package com.backend.integrador.service.impl;

import com.backend.integrador.dto.TurnoDto;
import com.backend.integrador.exceptions.BadRequestException;
import com.backend.integrador.exceptions.ConflictException;
import com.backend.integrador.exceptions.ResourceNotFoundException;
import com.backend.integrador.service.ITurnoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServiceTest {
    @Autowired
    ITurnoService turnoService;

    @Test
    @Order(1)
    void siNoExisteOdontologoNiPaciente_NoDeberiaRegistrarTurno() throws ConflictException, BadRequestException, ResourceNotFoundException {

        assertThrows(BadRequestException.class, () -> turnoService.guardarTurno(1L, 1L, LocalDateTime.now()));
    }

    @Test
    @Order(2)
    void noDeberiaListarNiUnTurno() {
        List<TurnoDto> turnoDtoList = turnoService.listarTurnos();

        assertEquals(0, turnoDtoList.size());
    }

    @Test
    @Order(3)
    void siNoExisteTurnoConId1_NoEncontrarDichoTurno() throws ResourceNotFoundException {

        assertThrows(ResourceNotFoundException.class, () -> turnoService.buscarTurnoPorId(1L));
    }
}