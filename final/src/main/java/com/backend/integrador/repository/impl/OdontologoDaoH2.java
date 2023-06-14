package com.backend.dao.impl;

import com.backend.entity.Odontologo;
import com.backend.dao.IDao;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    private static final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);

    private final Connection connection;

    public OdontologoDaoH2(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        // Tu implementación aquí, ejemplo:
        // INSERT INTO odontologo (nombre, apellido, matricula) VALUES (?, ?, ?)
        // Y usa PreparedStatement para insertar los valores
    }

    @Override
    public Odontologo actualizarOdontologo(Odontologo odontologo) {
        // Tu implementación aquí, ejemplo:
        // UPDATE odontologo SET nombre = ?, apellido = ?, matricula = ? WHERE id = ?
        // Y usa PreparedStatement para insertar los valores
    }

    @Override
    public boolean eliminarOdontologo(int id) {
        // Tu implementación aquí, ejemplo:
        // DELETE FROM odontologo WHERE id = ?
        // Y usa PreparedStatement para insertar los valores
    }

    @Override
    public Odontologo obtenerOdontologo(int id) {
        // Tu implementación aquí, ejemplo:
        // SELECT * FROM odontologo WHERE id = ?
        // Y usa PreparedStatement y transformarAResultadoOdontologo para convertir el resultado a un Odontologo
    }

    @Override
    public List<Odontologo> listarOdontologos() {
        // Tu implementación aquí, ejemplo:
        // SELECT * FROM odontologo
        // Y usa PreparedStatement y transformarAResultadoOdontologo para convertir el resultado a una lista de Odontologos
    }

    private Odontologo transformarAResultadoOdontologo(ResultSet resultSet) throws SQLException {
        // Transforma el resultado SQL a un objeto Odontologo
    }
}