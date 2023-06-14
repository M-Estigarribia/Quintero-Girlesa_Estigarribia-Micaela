package com.backend.integrador.repository;

import java.util.List;

public interface IDao<T> {

    T guardar(T t);

    List<T> listarTodos();

    void eliminar(int id);

    T buscarPorId(int id);

    T buscarPorCriterio(String criterio);

    T actualizar (T t);
    Paciente guardarPaciente(Paciente paciente);
    List<Paciente> listarPacientes();
    Paciente modificarPaciente(Paciente paciente);
    boolean eliminarPaciente(int id);
}
