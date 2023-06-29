package com.backend.integrador.service;

import com.backend.integrador.dto.OdontologoDto;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.exceptions.ConflictException;
import com.backend.integrador.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IOdontologoService {
    //POST
    OdontologoDto guardarOdontologo(Odontologo Odontologo) throws ConflictException;

    //GET
    OdontologoDto buscarOdontologoPorId(Long id);

    List<OdontologoDto> listarOdontologos();

    //PUT
    OdontologoDto actualizarOdontologo(Odontologo Odontologo) throws ResourceNotFoundException;

    //DELETE
    void eliminarOdontologo(Long id) throws ResourceNotFoundException;



}
