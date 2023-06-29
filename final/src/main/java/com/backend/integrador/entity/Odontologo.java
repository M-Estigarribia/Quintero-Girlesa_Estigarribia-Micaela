package com.backend.integrador.entity;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(name = "ODONTOLOGOS")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre del odontólogo no puede ser nulo.")
    @NotBlank(message = "Especifica el nombre del odontólogo.")
    @Size(min=2, max = 50, message = "El nombre del odontólogo puede tener un mínimo de {min} hasta un máximo de {max} caracteres.")
    @Pattern(regexp = "(?=.*[A-z].*[A-z])[A-z0-9\\s]+", message = "El nombre del odontólogo precisa al menos 2 letras.")
    private String nombre;

    @NotNull(message = "El apellido del odontólogo no puede ser nulo.")
    @NotBlank(message = "Especifica el apellido del odontólogo.")
    @Size(min=2, max = 50, message = "El apellido del odontólogo puede tener un mínimo de {min} hasta un máximo de {max} caracteres.")
    @Pattern(regexp = "(?=.*[A-z].*[A-z])[A-z0-9\\s]+", message = "El apellido del odontólogo precisa al menos 2 letras.")
    private String apellido;

    @NotNull(message = "La matrícula del odontólogo no puede ser nula.")
    @NotBlank(message = "Especifica la matrícula del odontólogo.")
    @Pattern(regexp = "^ODO-\\d{10}$", message = "La matrícula tiene formato 'ODO-xxxxxxxxxx' ('ODO-' seguido de 10 números.)")
    private String matricula;

    public Odontologo(){
    }

    public Odontologo (String nombre, String apellido, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}