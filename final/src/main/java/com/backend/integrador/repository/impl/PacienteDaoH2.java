package com.backend.dao.impl;

import com.backend.dao.IDao;
import com.backend.entity.Paciente;
import org.apache.log4j.Logger;

import java.util.List;

public class PacienteDaoEnMemoria implements IDao {

    private static final Logger LOGGER = Logger.getLogger(PacienteDaoEnMemoria.class);

    private List<Paciente> pacienteRepository;

    public PacienteDaoEnMemoria(List<Paciente> pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {
        pacienteRepository.add(paciente);
        LOGGER.info("Se ha registrado al paciente: " + paciente);
        return paciente;
    }

    @Override
    public List<Paciente> listarPacientes() {
        LOGGER.info("Listado de todos los pacientes: " + pacienteRepository);
        return pacienteRepository;
    }

    @Override
    public Paciente modificarPaciente(Paciente paciente) {
        // Implementar este método para actualizar los datos del paciente en la lista de pacientes
    }

    @Override
    public boolean eliminarPaciente(int id) {
        // Implementar este método para eliminar el paciente de la lista de pacientes según el id proporcionado
    }

}