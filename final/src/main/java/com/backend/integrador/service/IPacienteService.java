package com.backend.integrador.service;

import com.backend.integrador.dto.PacienteDto;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.exceptions.*;

import java.util.List;

public interface IPacienteService {
    //POST
    PacienteDto guardarPaciente(Paciente paciente) throws ConflictException;

    //GET
    PacienteDto buscarPacientePorId(Long id);

    List<PacienteDto> listarPacientes();

    //PUT
    PacienteDto actualizarPaciente(Paciente paciente) throws ResourceNotFoundException;

    //DELETE
    void eliminarPaciente(Long id) throws ResourceNotFoundException;
}
