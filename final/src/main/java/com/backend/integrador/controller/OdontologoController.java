package com.backend.integrador.controller;

import com.backend.integrador.dto.OdontologoDto;
import com.backend.integrador.entity.Odontologo;
import com.backend.integrador.exceptions.*;
import com.backend.integrador.service.IOdontologoService;
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
@RequestMapping("/odontologos")
public class OdontologoController {
    private final IOdontologoService odontologoService;

    @Autowired
    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    //GET
    @Operation(summary = "Lista todos los odontólogos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontólogos obtenidos correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))})
    })
    @GetMapping
    public List<OdontologoDto> listarOdontologos() {
        return odontologoService.listarOdontologos();
    }

    @Operation(summary = "Busca un odontólogo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontólogo obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarOdontologoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        ResponseEntity response;
        OdontologoDto odontologoDto = odontologoService.buscarOdontologoPorId(id);
        if(odontologoDto != null) response = new ResponseEntity<>(odontologoDto, null, HttpStatus.OK);
        else response = new ResponseEntity<>("No se encontró ningún odontólogo con ID " + id + " registrado en la base de datos.", HttpStatus.BAD_REQUEST);
        return response;
    }

    //POST
    @Operation(summary = "Guarda un nuevo odontólogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontólogo guardado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "409", description = "Odontólogo duplicado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))})
    })
    @PostMapping("/registrar")
    public ResponseEntity<OdontologoDto> guardarOdontologo(@Valid @RequestBody Odontologo odontologo) throws ConflictException {
        OdontologoDto odontologoDto = odontologoService.guardarOdontologo(odontologo);
        return new ResponseEntity<OdontologoDto>(odontologoDto, null, HttpStatus.CREATED);
    }


    //PUT
    @Operation(summary = "Actualiza un odontólogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontólogo actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))})
    })
    @PutMapping("/actualizar")
    public ResponseEntity<OdontologoDto> actualizarOdontologo(@Valid @RequestBody Odontologo odontologo)  throws ResourceNotFoundException {
        OdontologoDto odontologoDto = odontologoService.actualizarOdontologo(odontologo);
        return new ResponseEntity<>(odontologoDto, null, HttpStatus.OK);
    }

    //DELETE
    @Operation(summary = "Elimina un odontólogo por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Odontólogo eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Id incorrecto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))})
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return new ResponseEntity<>("Odontólogo eliminado correctamente", HttpStatus.NO_CONTENT);
    }

}
