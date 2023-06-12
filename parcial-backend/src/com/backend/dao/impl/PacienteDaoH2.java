package com.backend.dao.impl;
import com.backend.dao.H2Connection;
import com.backend.dao.IDao;
import com.backend.entity.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDaoH2 implements IDao<Paciente> {

    private static final Logger LOGGER = Logger.getLogger(PacienteDaoH2.class);

    @Override
    public Paciente guardarOdontologo(Paciente paciente) {
        Connection connection = null;

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement ps = connection.prepareStatement("INSERT INTO PACIENTES(NOMBRE, APELLIDO, SEGUROSOCIAL) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setString(3, paciente.getSeguroSocial());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                paciente.setId(rs.getInt(1));
            }

            connection.commit();
            LOGGER.info("Se ha registrado al paciente: " + paciente);

        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());
            exception.printStackTrace();

            if(connection != null) {
                try {
                    connection.rollback();
                    System.out.println("No se pudo guardar al paciente.");
                } catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            }
            catch (Exception exception) {
                LOGGER.error("Ocurrió un problema al intentar cerrar la base de datos. " + exception.getMessage());
                exception.printStackTrace();
            }
        }
        return paciente;
    }

    @Override
    public List<Paciente> listarOdontologos() {
        Connection connection = null;
        List<Paciente> pacienteList = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PACIENTES");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Paciente paciente = new Paciente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                pacienteList.add(paciente);
            }
            LOGGER.info("Listado de todos los pacientes: " + pacienteList);

        } catch (Exception exception){
            LOGGER.error(exception.getMessage());
            exception.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (Exception exception){
                LOGGER.error("Ocurrió un problema al intentar cerrar la base de datos. " + exception.getMessage());
                exception.printStackTrace();
            }
        }
        return pacienteList;
    }
}