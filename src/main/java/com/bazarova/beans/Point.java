package com.bazarova.beans;

import com.bazarova.util.DBManager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name="Point")
@ViewScoped
public class Point {

    private String r;
    private String x;
    private String y;
    private Boolean hit;

    public Point() {
        System.out.println("Starting Point...");

    }


    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getR() {
        return r;
    }

    public Boolean getHit() {
        return hit;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setR(String r) {
        this.r = r;
    }

    public void setHit(Boolean h) {
        this.hit=h;
    }
    public void setHit() {
        double x;
        double y;
        double r;
        try {
            x = Double.parseDouble(this.x.replace(',', '.'));
            y = Double.parseDouble(this.y.replace(',', '.'));
            r = Double.parseDouble(this.r.replace(',', '.'));
        } catch (Exception e) {
            this.hit = false;
            return;
        }
        if ((x >= 0 && y >= 0 && (x * x + y * y) <= r * r) ||
                (x >-r && y < r && y >= 0 && x < 0) ||
                (x > 0 && y < 0 && y > x - r / 2)
        ) {
            this.hit = true;
        } else this.hit = false;
    }

    public List<Point> getAllResults() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connection connection = DBManager.getConnection();
        String stm = "select * from Points";
        List<Point> lst=new ArrayList<Point>();
        try {
            pst = connection.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();

            while(rs.next()) {
                int id = rs.getInt("ID");
                String x = rs.getString("Xvalue");
                String y = rs.getString("Yvalue");
                String r = rs.getString("Rvalue");
                Boolean hit = Boolean.parseBoolean(rs.getString("Match"));

                System.out.println("X = "+x+"; Y = "+y+"; R = "+r+"; Match: "+hit+";");
                Point p=new Point();
                p.setY(y);
                p.setR(r);
                p.setX(x);
                p.setHit(hit);

                lst.add(p);
            }
            return lst;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void addPoint(){
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String xstr = requestParameterMap.get("xvalue");
        String ystr = requestParameterMap.get("yvalue");
        String rstr = requestParameterMap.get("rvalue");
        if (xstr!=null && !xstr.equals("")&&ystr!=null && !ystr.equals("")&&rstr!=null && !rstr.equals("")) {
            Point p=new Point();
            p.setX(xstr);
            p.setR(ystr);
            p.setY(rstr);
            p.setHit();
            add(p);
            System.out.println("added to db");

        }else{
            System.out.println("not added to db");
        }
    }

    public static void add(Point p){
        PreparedStatement pst = null;
        Connection connection = DBManager.getConnection();
        String stm = "INSERT INTO Points (Xvalue,Yvalue,Rvalue,Match) VALUES ('\"+p.getX()+\"','\"+p.getY()+\"','\"+p.getR()+\"','\"+p.getHit()+\"');";
        try {
            pst = connection.prepareStatement(stm);
            pst.executeUpdate(stm);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
