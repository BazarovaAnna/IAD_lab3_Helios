package com.bazarova.util;

import com.bazarova.beans.Point;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class DBManager  extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String x = req.getParameter("xvalue");
        String y = req.getParameter("yvalue");
        String r = req.getParameter("rvalue");
        if (x!=null && !x.equals("")&&y!=null && !y.equals("")&&r!=null && !r.equals("")) {
            Point p = new Point();
            p.setR(r);
            p.setX(x);
            p.setY(y);
            p.setHit();
            add(p);
            System.out.println("added to db");

        }else{
            System.out.println("not added to db");
        }
    }

    public static void add(Point p){
        PreparedStatement pst = null;
        Connection connection = getConnection();
        String stm = "INSERT INTO Points (Xvalue,Yvalue,Rvalue,Match) VALUES ('\"+p.getX()+\"','\"+p.getY()+\"','\"+p.getR()+\"','\"+p.getHit()+\"');";
        try {
            pst = connection.prepareStatement(stm);
            pst.executeUpdate(stm);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
