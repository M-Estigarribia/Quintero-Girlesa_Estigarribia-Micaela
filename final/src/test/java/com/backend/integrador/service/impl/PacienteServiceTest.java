package com.backend.integrador.service.impl;

import com.backend.integrador.dto.PacienteDto;
import com.backend.integrador.entity.Domicilio;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.exceptions.ConflictException;
import com.backend.integrador.exceptions.ResourceNotFoundException;
import com.backend.integrador.service.IPacienteService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacienteServiceTest {
    @Autowired
    IPacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaRegistrarUnPaciente() throws ConflictException {
        Paciente paciente = new Paciente("Nombre", "Prueba", 123456L, LocalDate.now(), new Domicilio("Calle", 204L, "Localidad", "Provincia"));
        PacienteDto pacienteResult = pacienteService.guardarPaciente(paciente);

        assertEquals(1, paciente.getId());
    }

    @Test
    @Order(2)
    void deberiaListarSolamenteUnPaciente() {
        List<PacienteDto> pacienteDtoList = pacienteService.listarPacientes();

        assertEquals(1, pacienteDtoList.size());
    }

    @Test
    @Order(3)
    void deberiaEliminarElPacienteConId1() throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(1L);

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.eliminarPaciente(1L));
    }
}