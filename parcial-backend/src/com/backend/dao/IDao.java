package com.backend.dao;

import java.util.List;

public interface IDao<T> {

    // Acá precisamos métodos para agregar, buscar, eliminar, listar. Si devuelven o tienen por parámetro una clase, usamos la T.
    T guardarOdontologo(T t);
    List<T> listarOdontologos();
}
