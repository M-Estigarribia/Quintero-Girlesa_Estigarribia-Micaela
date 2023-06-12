package com.backend.entity;

public class Paciente {
    private int id;
    private String nombre;
    private String apellido;
    private String seguroSocial;

    public Paciente(int id, String nombre, String apellido, String seguroSocial) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.seguroSocial = seguroSocial;
    }

    public Paciente(String nombre, String apellido, String seguroSocial) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.seguroSocial = seguroSocial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getSeguroSocial() {
        return seguroSocial;
    }

    public void setSeguroSocial(String seguroSocial) {
        this.seguroSocial = seguroSocial;
    }

    @Override
    public String toString() {
        return "\n Id: " + id + " Paciente " + nombre + " " + apellido + ", Seguro Social :" + seguroSocial + ".";
    }
}
