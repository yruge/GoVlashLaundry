package database;
import java.sql.*;

public class Connect {
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "laundry_db";
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
		// TODO Auto-generated constructor stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(CONNECT, USERNAME, PASSWORD);
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
		try {
			rs = statement.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void execUpdate(String query) {
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public PreparedStatement prepareStatement(String query) {
		try {
			ps = connection.prepareStatement(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return ps;
	}

}
