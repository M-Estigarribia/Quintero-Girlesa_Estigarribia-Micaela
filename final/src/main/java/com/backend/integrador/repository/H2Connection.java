package com.backend.integrador.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connection {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/entregaParcialc9", "GirMica", "GirMica");
    }

    public static Connection crearDb() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/entregaParcialc9;INIT=RUNSCRIPT FROM 'create.sql'", "GirMica", "GirMica");
    }
}
