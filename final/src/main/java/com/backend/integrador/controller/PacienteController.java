package com.backend.integrador.controller;

import com.backend.integrador.dto.PacienteDto;
import com.backend.integrador.entity.Paciente;
import com.backend.integrador.exceptions.*;
import com.backend.integrador.service.IPacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final IPacienteService pacienteService;

    @Autowired
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //GET
    @Operation(summary = "Lista todos los pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pacientes obtenidos correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))})
    })
    @GetMapping
    public List<PacienteDto> listarPacientes() {
        return pacienteService.listarPacientes();
    }


    @Operation(summary = "Busca un paciente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPacientePorId(@PathVariable Long id) {
        ResponseEntity response;
        PacienteDto pacienteDto = pacienteService.buscarPacientePorId(id);
        if(pacienteDto != null) response = new ResponseEntity<>(pacienteDto, null, HttpStatus.OK);
        else response = new ResponseEntity<>("No se encontró ningún paciente con ID " + id + " registrado en la base de datos.", HttpStatus.BAD_REQUEST);
        return response;
    }

    //POST

    @Operation(summary = "Guarda un nuevo paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente guardado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "409", description = "Paciente duplicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))})
    })
    @PostMapping("/registrar")
    public ResponseEntity<PacienteDto> guardarPaciente(@Valid @RequestBody Paciente paciente) throws ConflictException {
        PacienteDto pacienteDto = pacienteService.guardarPaciente(paciente);
        return new ResponseEntity<PacienteDto>(pacienteDto, null, HttpStatus.CREATED);
    }


    //PUT
    @Operation(summary = "Actualiza un paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))})
    })
    @PutMapping("/actualizar")
    public ResponseEntity<PacienteDto> actualizarPaciente(@Valid @RequestBody Paciente paciente)  throws ResourceNotFoundException {
        PacienteDto pacienteDto = pacienteService.actualizarPaciente(paciente);
        return new ResponseEntity<>(pacienteDto, null, HttpStatus.OK);
    }

    //DELETE
    @Operation(summary = "Elimina un paciente por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Id incorrecto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))})
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return new ResponseEntity<>("Paciente eliminado correctamente", HttpStatus.NO_CONTENT);
    }
}
