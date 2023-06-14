package com.backend.service;

import com.backend.dao.IDao;
import com.backend.entity.Paciente;

import java.util.List;
package com.backend.service;

import com.backend.dao.IDao;
import com.backend.entity.Paciente;

import java.util.List;

public class PacienteService {

    private IDao pacienteIDao;

    public PacienteService(IDao pacienteIDao) {
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteIDao.guardarPaciente(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteIDao.listarPacientes();
    }

    public Paciente modificarPaciente(Paciente paciente) {
        return pacienteIDao.modificarPaciente(paciente);
    }

    public boolean eliminarPaciente(int id) {
        return pacienteIDao.eliminarPaciente(id);
    }
}