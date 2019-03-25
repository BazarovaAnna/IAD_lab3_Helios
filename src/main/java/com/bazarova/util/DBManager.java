package com.bazarova.util;

import java.sql.*;

public class DBManager{

    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USERNAME = "s243845";
    private static final String PASSWORD = "qym531";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection completed.");
        } catch (SQLException ex ) {
            System.out.println(ex.getMessage());
        } catch ( ClassNotFoundException f) {
            System.out.println(f.getMessage());
        }
        return con;
    }

}
