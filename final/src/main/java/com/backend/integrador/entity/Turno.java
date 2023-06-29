package com.backend.integrador.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="TURNOS")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="paciente_id", nullable = false)
    @NotNull(message = "El paciente no puede ser nulo.")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="odontologo_id", nullable = false)
    @NotNull(message = "El odontólogo no puede ser nulo.")
    private Odontologo odontologo;

    @NotNull(message = "Especifica la fecha y hora del turno.")
    @FutureOrPresent(message = "La fecha no puede ser anterior a la actual.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaHora;

    public Turno(){
    }

    public Turno( Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaHora = fechaHora;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
