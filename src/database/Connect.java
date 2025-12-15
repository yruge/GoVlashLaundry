package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    // Konfigurasi Database XAMPP Default
    private final String USERNAME = "root";
    private final String PASSWORD = ""; 
    private final String DATABASE = "govlash_laundry"; // Sesuaikan dengan nama DB di phpMyAdmin
    private final String HOST = "localhost:3306"; 
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
    
    private Connection con;
    private static Connect instance;

    private Connect() {
        try {  
            // Load Driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);  
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC tidak ditemukan! Pastikan library mysql-connector sudah ditambahkan.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Gagal terhubung ke Database! Pastikan XAMPP (MySQL) sudah Start.");
            e.printStackTrace();
        }
    }
    
    public static Connect getInstance() {
        if(instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    public Connection getConnection() {
        return con;
    }
}