package com.backend.dao.impl;

import com.backend.dao.H2Connection;
import com.backend.dao.IDao;
import com.backend.entity.Odontologo;
import org.apache.log4j.Logger;
import org.h2.command.Prepared;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {

    private static final Logger LOGGER = Logger.getLogger(OdontologoDaoH2.class);

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        Connection connection = null;

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement ps = connection.prepareStatement("INSERT INTO ODONTOLOGOS(MATRICULA, NOMBRE, APELLIDO) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, odontologo.getMatricula());
            ps.setString(2, odontologo.getNombre());
            ps.setString(3, odontologo.getApellido());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                odontologo.setId(rs.getInt(1));
            }

            connection.commit();
            LOGGER.info("Se ha registrado al odontólogo: " + odontologo);

        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());
            exception.printStackTrace();

            if(connection != null) {
                try {
                    connection.rollback();
                    System.out.println("No se pudo guardar el odontólogo.");
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
        return odontologo;
    }

    @Override
    public List<Odontologo> listarOdontologos() {
        Connection connection = null;
        List<Odontologo> odontologoList = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ODONTOLOGOS");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Odontologo odontologo = new Odontologo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4));
                odontologoList.add(odontologo);
            }
            LOGGER.info("Listado de todos los odontólogos: " + odontologoList);

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
        return odontologoList;
    }
}
