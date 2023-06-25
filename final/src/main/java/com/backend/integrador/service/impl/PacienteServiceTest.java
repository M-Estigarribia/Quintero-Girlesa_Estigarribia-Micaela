package com.backend.service;

import com.backend.dao.impl.PacienteDaoH2;
import com.backend.entity.Paciente;
import com.backend.entity.Domicilio;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacienteServiceTest {

    private PacienteService pacienteService = new PacienteService(new PacienteDaoH2());

    @Test
    public void deberiaGuardarUnPaciente() {
        Domicilio domicilioPrueba = new Domicilio("Calle 123", "456", "Ciudad", "País");
        Paciente pacientePrueba = new Paciente("Pepito", "Perez", "12345678", LocalDate.now(), domicilioPrueba);

        Paciente pacienteResult = pacienteService.guardarPaciente(pacientePrueba);

        assertEquals("Pepito", pacienteResult.getNombre());
    }

    @Test
    void listarPacientes() {
        List<Paciente> pacienteListPrueba = pacienteService.listarPacientes();

        assertFalse(pacienteListPrueba.isEmpty());

    }
}