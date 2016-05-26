package org.tanna.inhouse.EmergencyApp.Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://188.166.227.51:3306/emergency"; //jdbc:mysql://localhost/emergency_app";
    static final String USER = "euser";
    static final String PASS = "o#k3wAG3gS%T";

    /*
    public static ResultSet selectQuery(String strQuery) {
        ResultSet myRs;
        myRs = null;
            try {
                Connection conn = null;
                Statement stmt = null;
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection("jdbc:mysql://localhost/emergency_app", "root", "");
                System.out.println("Creating statement...");
                stmt = conn.createStatement();
                myRs = stmt.executeQuery(strQuery);
            }
            catch (Exception exception) {
                System.out.println(exception);
            }
        return myRs;
    }

    public static boolean insertQuery(String strQuery) {
        try {
            Connection conn = null;
            Statement stmt = null;
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/emergency_app", "root", "");
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            stmt.executeUpdate(strQuery);
            return true;
        }
        catch (Exception exception) {
            System.out.println(exception);
            return false;
        }
    }

    public static boolean updateQuery(String strQuery) {
        try {
            Connection conn = null;
            Statement stmt = null;
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/emergency_app", "root", "");
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            stmt.executeUpdate(strQuery);
            return true;
        }
        catch (Exception exception) {
            System.out.println(exception);
            return false;
        }
    }

    public static boolean deleteQuery(String strQuery) {
            try {
                Connection conn = null;
                Statement stmt = null;
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection("jdbc:mysql://localhost/emergency_app", "root", "");
                System.out.println("Creating statement...");
                stmt = conn.createStatement();
                stmt.executeUpdate(strQuery);
                return true;
            }
            catch (Exception exception) {
                System.out.println(exception);
                return false;
            }
       
    }
    */
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
    	  Connection conn = null;
          Class.forName("com.mysql.jdbc.Driver");
          System.out.println("Connecting to database...");
          conn = DriverManager.getConnection(DB_URL, USER, PASS);
          return conn;
    }
}