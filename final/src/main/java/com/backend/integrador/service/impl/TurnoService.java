package com.backend.integrador.service.impl;

import com.backend.integrador.dto.OdontologoDto;
import com.backend.integrador.dto.PacienteDto;
import com.backend.integrador.dto.TurnoDto;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.entity.Turno;
import com.backend.integrador.exceptions.*;
import com.backend.integrador.repository.TurnoRepository;
import com.backend.integrador.service.ITurnoService;
import com.backend.integrador.utils.JsonPrinter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    private final ObjectMapper objectMapper;


    @Autowired
    public TurnoService(TurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService, ObjectMapper objectMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.objectMapper = objectMapper;
    }

    @Override
    public TurnoDto guardarTurno(Long pacienteId, Long odontologoId, LocalDateTime localDateTime) throws BadRequestException, ResourceNotFoundException, ConflictException {
        TurnoDto nuevoTurnoDto = null;
        Paciente paciente = objectMapper.convertValue(pacienteService.buscarPacientePorId(pacienteId), Paciente.class);
        Odontologo odontologo =objectMapper.convertValue(odontologoService.buscarOdontologoPorId(odontologoId), Odontologo.class);

        if(paciente == null || odontologo == null) {
            if(paciente == null && odontologo == null){
                LOGGER.error("Ni el paciente ni el odontólogo no se encuentran registrados en la base de datos.");
                throw new BadRequestException("Ni el paciente ni el odontólogo no se encuentran registrados en la base de datos.");
            }
            else if (paciente == null) {
                LOGGER.error("El paciente no se encuentra registrado en la base de datos.");
                throw new BadRequestException("El paciente no se encuentra registrado en la base de datos.");

            } else {
                LOGGER.error("El odontólogo no se encuentra registrado en la base de datos.");
                throw new BadRequestException("El odontólogo no se encuentra registrado en la base de datos.");
            }
        } else if (turnoRepository.countByPacienteIdAndOdontologoIdAndFechaHora(pacienteId, odontologoId, localDateTime) > 0) {
            LOGGER.error("El paciente "+ paciente +" ya tiene un turno reservado con el odontólogo " + odontologo + " para la fecha y hora " + localDateTime + ".");
            throw new ConflictException("El paciente "+ paciente +" ya tiene un turno reservado con el odontólogo " + odontologo + " para la fecha " + localDateTime.toString().replace("T", ", ")+ " horas.");
        } else {
            Turno turno = new Turno(paciente, odontologo, localDateTime);
            turnoRepository.save(turno);
            nuevoTurnoDto = TurnoDto.fromTurno(turno);
            LOGGER.info("Turno guardado exitosamente. {}", JsonPrinter.toString(nuevoTurnoDto));
        }
        return nuevoTurnoDto;
    }
    @Override
    public TurnoDto buscarTurnoPorId(Long id) throws ResourceNotFoundException {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoDto turnoDto = null;
        if (turnoBuscado != null) {
            turnoDto = TurnoDto.fromTurno(turnoBuscado);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoDto));

        } else {
            LOGGER.info("No se encontró ningún turno con ID  " + id + " registrado en la base de datos.");
            throw new ResourceNotFoundException("No se encontró ningún turno con ID " + id + " registrado en la base de datos.");
        }

        return turnoDto;
    }

    @Override
    public List<TurnoDto> listarTurnos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDto> turnosDto = turnos.stream()
                .map(TurnoDto::fromTurno).toList();

        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnosDto));
        return turnosDto;
    }

    @Override
    public TurnoDto actualizarTurno(Turno turno) throws ResourceNotFoundException, BadRequestException {
        Turno turnoBuscado = turnoRepository.findById(turno.getId()).orElse(null);
        TurnoDto turnoActualizadoDto = null;

        if (turnoBuscado != null) {
            Paciente paciente = objectMapper.convertValue(pacienteService.buscarPacientePorId(turno.getPaciente().getId()), Paciente.class);
            Odontologo odontologo = objectMapper.convertValue(odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId()), Odontologo.class);
            if(paciente == null || odontologo == null) {
                if(paciente == null && odontologo == null){
                    LOGGER.error("Ni el paciente ni el odontólogo no se encuentran registrados en la base de datos.");
                    throw new BadRequestException("Ni el paciente ni el odontólogo no se encuentran registrados en la base de datos.");
                }
                else if (paciente == null) {
                    LOGGER.error("El paciente no se encuentra registrado en la base de datos.");
                    throw new BadRequestException("El paciente no se encuentra registrado en la base de datos.");

                } else {
                    LOGGER.error("El odontólogo no se encuentra registrado en la base de datos.");
                    throw new BadRequestException("El odontólogo no se encuentra registrado en la base de datos.");
                }
            } else {
                turnoBuscado.setPaciente(paciente);
                turnoBuscado.setOdontologo(odontologo);
                turnoBuscado.setFechaHora(turno.getFechaHora());
                Turno turnoActualizado = turnoRepository.save(turnoBuscado);
                turnoActualizadoDto = TurnoDto.fromTurno(turnoActualizado);
                LOGGER.warn("Turno actualizado exitosamente: {}", JsonPrinter.toString(turnoActualizadoDto));
            }
        } else {
            LOGGER.error("No fue posible actualizar los datos. El turno ingresado no se encuentra registrado en la base de datos.");
            throw new ResourceNotFoundException("No fue posible actualizar los datos. El turno no se encuentra registrado en la base de datos.");
        }
        return turnoActualizadoDto;
    }


    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException  {
        if(buscarTurnoPorId(id) != null){
            turnoRepository.deleteById(id);
            LOGGER.warn("El turno con id " + id + " fue eliminado exitosamente.");
        } else {
            LOGGER.error("No se ha encontrado al turno con ID " + id);
            throw new ResourceNotFoundException("No se ha encontrado al turno con ID " + id);
        }
    }
}
