package com.backend.integrador.service.impl;

import com.backend.integrador.dto.DomicilioDto;
import com.backend.integrador.dto.PacienteDto;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.exceptions.*;
import com.backend.integrador.repository.PacienteRepository;
import com.backend.integrador.service.IPacienteService;
import com.backend.integrador.utils.JsonPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PacienteService implements IPacienteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository pacienteRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, ObjectMapper objectMapper) {
        this.pacienteRepository = pacienteRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public PacienteDto guardarPaciente(Paciente paciente) throws ConflictException {
        PacienteDto nuevoPacienteDto = null;

        int esPacienteDuplicado = pacienteRepository.countByNombreAndApellidoAndDni(paciente.getNombre(), paciente.getApellido(), paciente.getDni());

        if(esPacienteDuplicado > 0) {
            LOGGER.error("El paciente ingresado ya existe en la base de datos.");
            throw new ConflictException("El paciente ingresado ya existe en la base de datos.");
        } else {
            Paciente nuevoPaciente = pacienteRepository.save(paciente);
            DomicilioDto domicilioDto = objectMapper.convertValue(nuevoPaciente.getDomicilio(), DomicilioDto.class);
            nuevoPacienteDto = objectMapper.convertValue(nuevoPaciente, PacienteDto.class);
            nuevoPacienteDto.setDomicilio(domicilioDto);
            LOGGER.info("Paciente guardado exitosamente. {}", JsonPrinter.toString(nuevoPacienteDto));
        }
        return nuevoPacienteDto;
    }

    @Override
    public PacienteDto buscarPacientePorId(Long id) {
        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        PacienteDto pacienteDto = null;
        if (pacienteBuscado != null) {
            DomicilioDto domicilioDto = objectMapper.convertValue(pacienteBuscado.getDomicilio(), DomicilioDto.class);
            pacienteDto = objectMapper.convertValue(pacienteBuscado, PacienteDto.class);
            pacienteDto.setDomicilio(domicilioDto);
            LOGGER.info("Paciente encontrado: {}", JsonPrinter.toString(pacienteDto));

        } else {
            LOGGER.info("No se encontró ningún paciente con ID " + id + " registrado en la base de datos.");
            //throw new ResourceNotFoundException("No se encontró ningún paciente con ID  " + id + " registrado en la base de datos.");
        }

        return pacienteDto;
    }

    @Override
    public List<PacienteDto> listarPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteDto> pacientesDto = pacientes.stream()
                .map(paciente -> {
                    DomicilioDto domicilioDto = objectMapper.convertValue(paciente.getDomicilio(), DomicilioDto.class);
                    return new PacienteDto(paciente.getId(), paciente.getNombre(), paciente.getApellido(), paciente.getDni(), paciente.getFechaIngreso(), domicilioDto);
                })
                .toList();

        LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(pacientesDto));
        return pacientesDto;
    }

    @Override
    public PacienteDto actualizarPaciente(Paciente paciente) throws ResourceNotFoundException {
        Paciente pacienteBuscado = pacienteRepository.findById(paciente.getId()).orElse(null);
        PacienteDto pacienteActualizadoDto = null;

        if (pacienteBuscado != null) {
            pacienteBuscado = paciente;
            pacienteRepository.save(pacienteBuscado);

            DomicilioDto domicilioDto = objectMapper.convertValue(pacienteBuscado.getDomicilio(), DomicilioDto.class);
            pacienteActualizadoDto = objectMapper.convertValue(pacienteBuscado, PacienteDto.class);
            pacienteActualizadoDto.setDomicilio(domicilioDto);
            LOGGER.warn("Paciente actualizado exitosamente: {}", JsonPrinter.toString(pacienteActualizadoDto));

        } else  {
            LOGGER.error("No fue posible actualizar los datos. El paciente ingresado no se encuentra registrado en la base de datos.");
            throw new ResourceNotFoundException("No fue posible actualizar los datos. El paciente no se encuentra registrado en la base de datos.");
        }
        return pacienteActualizadoDto;
    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException  {
        if(buscarPacientePorId(id) != null){
            pacienteRepository.deleteById(id);
            LOGGER.warn("El paciente con id " + id + " fue eliminado exitosamente.");
        } else {
            LOGGER.error("No se ha encontrado al paciente con ID " + id);
            throw new ResourceNotFoundException("No se ha encontrado al paciente con ID " + id);
        }
    }
}
