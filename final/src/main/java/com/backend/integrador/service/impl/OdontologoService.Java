package com.backend.integrador.service.impl;

import com.backend.integrador.dto.OdontologoDto;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.exceptions.ConflictException;
import com.backend.integrador.exceptions.ResourceNotFoundException;
import com.backend.integrador.repository.OdontologoRepository;
import com.backend.integrador.service.IOdontologoService;
import com.backend.integrador.utils.JsonPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final OdontologoRepository odontologoRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository, ObjectMapper objectMapper) {
        this.odontologoRepository = odontologoRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public OdontologoDto guardarOdontologo(Odontologo odontologo) throws ConflictException {
        OdontologoDto nuevoOdontologoDto = null;

        boolean esOdontologoDuplicado = odontologoRepository.existsByMatricula(odontologo.getMatricula());

        if(esOdontologoDuplicado) {
            LOGGER.error("No se pudo guardar al odontólogo, la matrícula ingresada ya existe en la base de datos.");
            throw new ConflictException("No se pudo guardar al odontólogo, la matrícula ingresada ya existe en la base de datos.");
        } else {
            Odontologo nuevoOdontologo = odontologoRepository.save(odontologo);
            nuevoOdontologoDto = objectMapper.convertValue(nuevoOdontologo, OdontologoDto.class);

            LOGGER.info("Odontólogo guardado exitosamente. {}", JsonPrinter.toString(nuevoOdontologoDto));
        }
        return nuevoOdontologoDto;
    }

    @Override
    public OdontologoDto buscarOdontologoPorId(Long id) {
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoDto odontologoDto = null;
        if (odontologoBuscado != null) {
            odontologoDto = objectMapper.convertValue(odontologoBuscado, OdontologoDto.class);
            LOGGER.info("Odontólogo encontrado: {}", JsonPrinter.toString(odontologoDto));

        } else {
            LOGGER.info("No se encontró ningún odontólogo con ID " + id + " registrado en la base de datos.");
//            throw new ResourceNotFoundException("No se encontró ningún odontólogo con ID " + id + " registrado en la base de datos.");
        }
        return odontologoDto;
    }

    @Override
    public List<OdontologoDto> listarOdontologos() {
        List<Odontologo> odontologos = odontologoRepository.findAll();
        List<OdontologoDto> odontologosDto = odontologos.stream()
                .map(odontologo -> {
                    return new OdontologoDto(odontologo.getId(), odontologo.getNombre(), odontologo.getApellido(), odontologo.getMatricula());
                })
                .toList();

        LOGGER.info("Listado de todos los odontólogos: {}", JsonPrinter.toString(odontologosDto));
        return odontologosDto;
    }

    @Override
    public OdontologoDto actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException {
        Odontologo odontologoBuscado = odontologoRepository.findById(odontologo.getId()).orElse(null);
        OdontologoDto odontologoActualizadoDto = null;

        if (odontologoBuscado != null) {
            odontologoBuscado = odontologo;
            odontologoRepository.save(odontologoBuscado);
            odontologoActualizadoDto = objectMapper.convertValue(odontologoBuscado, OdontologoDto.class);

            LOGGER.warn("Odontólogo actualizado exitosamente: {}", JsonPrinter.toString(odontologoActualizadoDto));

        } else  {
            LOGGER.error("No fue posible actualizar los datos. El odontólogo ingresado no se encuentra registrado en la base de datos.");
            throw new ResourceNotFoundException("No fue posible actualizar los datos. El odontólogo no se encuentra registrado en la base de datos.");
        }
        return odontologoActualizadoDto;
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        if(buscarOdontologoPorId(id) != null){
            odontologoRepository.deleteById(id);
            LOGGER.warn("El odontólogo con id " + id + " fue eliminado exitosamente.");
        } else {
            LOGGER.error("No se ha encontrado al odontólogo con ID " + id);
            throw new ResourceNotFoundException("No se ha encontrado al odontólogo con ID " + id);
        }
    }
}