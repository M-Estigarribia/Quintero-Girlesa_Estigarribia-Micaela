package com.backend.integrador.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "PACIENTES")

public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre del paciente no puede ser nulo.")
    @NotBlank(message = "Especifica el nombre del paciente.")
    @Size(min=2, max = 50, message = "El nombre del paciente puede tener un mínimo de {min} hasta un máximo de {max} caracteres.")
    @Pattern(regexp = "(?=.*[A-z].*[A-z])[A-z0-9\\s]+", message = "El nombre del paciente precisa al menos 2 letras.")
    private String nombre;

    @NotNull(message = "El apellido del paciente no puede ser nulo.")
    @NotBlank(message = "Especifica el apellido del paciente.")
    @Size(min= 2,max = 50, message = "El apellido del paciente puede tener un mínimo de {min} hasta un máximo de {max} caracteres.")
    @Pattern(regexp = "(?=.*[A-z].*[A-z])[A-z0-9\\s]+", message = "El apellido del paciente precisa al menos 2 letras.")
    private String apellido;

    @NotNull(message = "El dni del paciente no puede ser nulo.")
    @Positive(message = "El dni no puede ser un número negativo.")
    @Digits(integer=10, fraction = 0, message = "El dni del paciente puede tener hasta {integer} cifras.")
    private Long dni;

    @NotNull(message = "La fecha de ingreso del paciente no puede ser nula.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "La fecha no puede ser anterior a la actual.")
    private LocalDate fechaIngreso;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "domicilio_id")
    @Valid
    private Domicilio domicilio;

    public Paciente() {
    }

    public Paciente(String nombre, String apellido, Long dni, LocalDate fechaIngreso, Domicilio domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getDni() { return dni; }
    public void setDni(Long dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
}
