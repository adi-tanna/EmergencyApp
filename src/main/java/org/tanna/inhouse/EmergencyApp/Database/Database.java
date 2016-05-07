package org.tanna.inhouse.EmergencyApp.Database;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/emergency_app";
    static final String USER = "root";
    static final String PASS = "";

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
}