package com.backend.integrador.service;

import com.backend.integrador.dto.TurnoDto;
import com.backend.integrador.entity.Turno;
import com.backend.integrador.exceptions.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ITurnoService {
    //POST
    TurnoDto guardarTurno(Long pacienteId, Long odontologoId, LocalDateTime localDateTime) throws BadRequestException, ResourceNotFoundException, ConflictException;

    //GET
    TurnoDto buscarTurnoPorId(Long id) throws ResourceNotFoundException;

    List<TurnoDto> listarTurnos();

    //PUT
    TurnoDto actualizarTurno(Turno turno) throws ResourceNotFoundException, BadRequestException;

    //DELETE
    void eliminarTurno(Long id) throws ResourceNotFoundException;

}
