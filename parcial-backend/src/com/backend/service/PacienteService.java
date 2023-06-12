package com.backend.service;

import com.backend.dao.IDao;
import com.backend.entity.Paciente;

import java.util.List;

public class PacienteService {

    private IDao<Paciente> pacienteIDao;

    public PacienteService(IDao<Paciente> pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteIDao.guardarPaciente(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteIDao.listarPacientes();
    }
}
