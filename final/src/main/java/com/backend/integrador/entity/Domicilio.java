package com.backend.integrador.entity;


import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "DOMICILIOS")

public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La calle no puede ser nula")
    @NotBlank(message = "Especifica la calle")
    @Size(min=2, max = 50, message = "La calle puede tener un mínimo de {min} hasta un máximo de {max} caracteres.")
    @Pattern(regexp = "(?=.*[A-z].*[A-z])[A-z0-9\\s]+", message = "La calle precisa al menos 2 letras.")
    private String calle;

    @NotNull(message = "El número no puede ser nulo")
    @Positive(message = "El número de calle no puede ser un número negativo.")
    @Digits(integer=10, fraction = 0, message = "El nro calle puede tener hasta {integer} cifras.")
    private Long numero;

    @NotNull(message = "La localidad no puede ser nula")
    @NotBlank(message = "Especifica la localidad")
    @Size(min=2, max = 50, message = "La localidad puede tener un mínimo de {min} hasta un máximo de {max} caracteres.")
    @Pattern(regexp = "(?=.*[A-z].*[A-z])[A-z0-9\\s]+", message = "La localidad precisa al menos 2 letras.")
    private String localidad;

    @NotNull(message = "La provincia no puede ser nula")
    @NotBlank(message = "Especifica la provincia")
    @Size(min= 3 , max = 50, message = "La provincia puede tener un mínimo de {min} hasta un máximo de {max} caracteres.")
    @Pattern(regexp = "(?=.*[A-z].*[A-z])[A-z0-9\\s]+", message = "La provincia precisa al menos 2 letras.")
    private String provincia;

    public Domicilio() {
    }

    public Domicilio( String calle, Long numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Long getId() {
        return id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
