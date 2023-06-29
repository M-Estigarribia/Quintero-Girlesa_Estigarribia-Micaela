package com.backend.integrador.controller;

import com.backend.integrador.dto.TurnoDto;

import com.backend.integrador.entity.Turno;
import com.backend.integrador.exceptions.*;

import com.backend.integrador.service.ITurnoService;
import com.backend.integrador.utils.LocalDateTimeAdapter;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private final ITurnoService turnoService;

    @Autowired
    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    //GET
    @Operation(summary = "Lista todos los turnos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turnos obtenidos correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))})
    })
    @GetMapping
    public List<TurnoDto> listarTurnos() {
        return turnoService.listarTurnos();
    }


    @Operation(summary = "Busca un turno por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> buscarTurnoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        TurnoDto turnoDto = turnoService.buscarTurnoPorId(id);
        return new ResponseEntity<>(turnoDto, null, HttpStatus.OK);
    }

    //POST

    @Operation(summary = "Guarda un nuevo turno, pasando ID de paciente, ID de odontólogo y un LocalDateTime")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno guardado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "409", description = "Turno duplicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))})
    })
    @PostMapping("/registrar")
    public ResponseEntity<TurnoDto> guardarTurno(@Valid @RequestBody Map<String, Object> turnoRequest) throws ConflictException, BadRequestException, ResourceNotFoundException {
        Integer pacienteIdInteger = (Integer) turnoRequest.get("pacienteId");
        Long pacienteId = pacienteIdInteger != null ? pacienteIdInteger.longValue() : null;
        Integer odontologoIdInteger = (Integer) turnoRequest.get("odontologoId");
        Long odontologoId = odontologoIdInteger != null ? odontologoIdInteger.longValue() : null;
        String localDateTimeString = (String) turnoRequest.get("fechaHora");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeString, formatter);

        TurnoDto nuevoTurnoDto = turnoService.guardarTurno(pacienteId, odontologoId, localDateTime);
        return new ResponseEntity<TurnoDto>(nuevoTurnoDto, null, HttpStatus.CREATED);
    }


    //PUT
    @Operation(summary = "Actualiza un turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))})
    })
    @PutMapping("/actualizar")
    public ResponseEntity<TurnoDto> actualizarTurno(@Valid @RequestBody Turno turno)  throws ResourceNotFoundException, BadRequestException {
        TurnoDto turnoDtoActualizado = turnoService.actualizarTurno(turno);
        return new ResponseEntity<>( turnoDtoActualizado,null, HttpStatus.OK);
    }

    //DELETE
    @Operation(summary = "Elimina un turno por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Turno eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Id incorrecto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoDto.class))})
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return new ResponseEntity<>("Turno eliminado correctamente", HttpStatus.NO_CONTENT);
    }
}
