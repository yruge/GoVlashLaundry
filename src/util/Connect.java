package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private final String DATABASE = "govlash";
	private final String HOST = "localhost:3306";
	private final String URL = "jdbc:mysql://" + HOST + "/" + DATABASE;
	
	private Connection con;
	private static Connect instance;
	
	private Connect() {
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Connected to DB!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connect getConnection() {
		if(instance == null) {
			instance = new Connect();
		}
		return instance;
	}
	
	public Connection getCon() {
		return con;
	}
}
