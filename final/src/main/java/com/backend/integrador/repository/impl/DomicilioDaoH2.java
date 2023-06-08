package com.backend.integrador.repository.impl;

import com.backend.integrador.repository.H2Connection;
import com.backend.integrador.repository.IDao;
import com.backend.integrador.entity.Domicilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioDaoH2 implements IDao<Domicilio> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomicilioDaoH2.class);

    @Override
    public Domicilio guardar(Domicilio domicilio) {
        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement ps = connection.prepareStatement("INSERT INTO DOMICILIOS(CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, domicilio.getCalle());
            ps.setInt(2, domicilio.getNumero());
            ps.setString(3, domicilio.getLocalidad());
            ps.setString(4, domicilio.getProvincia());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()){
                domicilio.setId(rs.getInt(1));
            }

            connection.commit();

            LOGGER.info("Se ha registrado el domicilio:" + domicilio);
        } catch (Exception e) {
            LOGGER.error( e.getMessage());
            e.printStackTrace();

            if(connection != null) {
                try {
                    connection.rollback();
                    System.out.println("No se pudo guardar la dirección.");
                    e.printStackTrace();
                } catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                LOGGER.error("Ocurrió un problema al intentar cerrar la base de datos. " + e.getMessage());
                e.printStackTrace();
            }
        }
        return domicilio;
    }

    @Override
    public List<Domicilio> listarTodos() {
        Connection connection = null;
        List<Domicilio> domicilioList = new ArrayList<>();

        try {
            connection = H2Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM DOMICILIOS");

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Domicilio domicilio = new Domicilio(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
                domicilioList.add(domicilio);
            }
            LOGGER.info("Listado de todos los domicilios: " + domicilioList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ocurrió un problema al intentar cerrar la base de datos. " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return domicilioList;
    }

    @Override
    public void eliminar(int id) {
        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("DELETE FROM DOMICILIOS WHERE ID = ?");
            ps.setInt(1, id);
            ps.execute();
            connection.commit();
            LOGGER.info("Se ha eliminado el domicilio con id: " + id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if(connection != null) {
                try {
                    connection.rollback();
                    System.out.println("No se pudo borrar la dirección.");
                    e.printStackTrace();
                } catch (SQLException ex) {
                    LOGGER.error(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ocurrió un problema al intentar cerrar la base de datos. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Domicilio buscarPorId(int id) {
        Connection connection = null;
        Domicilio domicilio = null;

        try {
            connection = H2Connection.getConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM DOMICILIOS WHERE ID = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                domicilio = new Domicilio(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
            }
            LOGGER.info("Se encontró el domicilio con id: " + id + "; los datos del domicilio son: " + domicilio);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ocurrió un problema al intentar cerrar la base de datos. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return domicilio;
    }

    @Override
    public Domicilio buscarPorCriterio(String criterio) {
        return null;
    }

    @Override
    public Domicilio actualizar(Domicilio domicilio) {
        return null;
    }
}
