package database;
import java.sql.*;

public class Connect {
    private final String USERNAME = "root";      // Default XAMPP
    private final String PASSWORD = "";          // Kosong untuk XAMPP default
    private final String DATABASE = "laundry_db"; // Nama database 
    private final String HOST = "localhost:3306";
    private final String CONNECT = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
    
    private Connection connection;
    private Statement statement;
    private static Connect connect;
    
    private ResultSet rs;
    private ResultSetMetaData rsm;
    private PreparedStatement ps;
    
    public static Connect getInstance() {
        if(connect == null) return new Connect();
        return connect;
    }

    private Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECT, USERNAME, PASSWORD);
            statement = connection.createStatement();
            System.out.println("✅ Database connected to: " + DATABASE);
        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
    }
    
    public ResultSet execQuery(String query) {
        try {
            rs = statement.executeQuery(query);
            rsm = rs.getMetaData();
        } catch (SQLException e) {
            System.out.println("❌ Query error: " + e.getMessage());
            System.out.println("Query: " + query);
        }
        
        return rs;
    }
    
    public void execUpdate(String query) {
        try {
            statement.executeUpdate(query);
            System.out.println("✅ Query executed: " + query.substring(0, Math.min(50, query.length())) + "...");
        } catch (SQLException e) {
            System.out.println("❌ Update error: " + e.getMessage());
        }
    }
    
    public PreparedStatement prepareStatement(String query) {
        try {
            ps = connection.prepareStatement(query);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return ps;
    }
}