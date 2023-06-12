package com.backend.service;

import com.backend.dao.impl.PacienteDaoH2;
import com.backend.entity.Paciente;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacienteServiceTest {

    private static Connection connection = null;

    private PacienteService pacienteService = new PacienteService(new PacienteDaoH2());

    @Test
    public void deberiaGuardarUnPaciente() {
        Paciente pacientePrueba = new Paciente(555555, "Pepito", "Perez", 25);

        Paciente pacienteResult = pacienteService.guardarPaciente(pacientePrueba);

        assertEquals(555555, pacienteResult.getDni());
    }

    @Test
    void listarPacientes() {
        List<Paciente> pacienteListPrueba = pacienteService.listarPacientes();

        assertFalse(pacienteListPrueba.isEmpty());

    }
}
