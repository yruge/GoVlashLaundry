package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class User {
	private Connect connect = Connect.getInstance();
	protected int userID;
	protected String userName;
	protected String userEmail;
	protected String userPassword;
	protected String userGender;
	protected String userDOB;
	protected String userRole;
	
	public User(int userID, String userName, String userEmail, String userPassword, String userGender, String userDOB,
			String userRole) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userGender = userGender;
		this.userDOB = userDOB;
		this.userRole = userRole;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public int getUserID() {
	    return userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
			return userEmail;
		}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserDOB() {
		return userDOB;
	}

	public void setUserDOB(String userDOB) {
		this.userDOB = userDOB;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public void addUser(String userName, String userEmail, String userPassword, String userGender, String userDOB, String userRole) {
		String query = "INSERT INTO users (userName, userEmail, userPassword, userGender, UserDOB, userRole)" +
						"VALUES ('"+ userName +"', '"+ userEmail +"', '"+ userPassword +"', '"+ userGender +"', '"+ userDOB +"', '"+ userRole +"')";
		connect.execUpdate(query);
	}
	
	public void addEmployee(String userName, String userEmail, String userPassword, String userGender, String userDOB, String userRole) {
		String query = "INSERT INTO users (userName, userEmail, userPassword, userGender, UserDOB, userRole)" +
						"VALUES ('"+ userName +"', '"+ userEmail +"', '"+ userPassword +"', '"+ userGender +"', '"+ userDOB +"', '"+ userRole +"')";
		connect.execUpdate(query);
	}
	
	public ArrayList<User> getUsersByRole (String role) {
		ArrayList<User> users = new ArrayList<>();
		String query = "SELECT * FROM users WHERE userRole = '"+ role + "'";
		ResultSet rs = connect.execQuery(query);
		
		try {
			while(rs.next())
				{
				int id = rs.getInt("userID");
				String name = rs.getString("userName");
	            String email = rs.getString("userEmail");
	            String password = rs.getString("userPassword");
	            String gender = rs.getString("userGender");
	            String dob = rs.getString("userDOB");
	            String userRole = rs.getString("userRole");
	            
	            User userss = new User(id, name, email, password, gender, dob, userRole);
	            users.add(userss);
				} 
			} catch (SQLException e) {
		        e.printStackTrace();
			}
		return users;
	}
	
	public User login(String email, String password) {
	    User user = null; // Start as null (Login failed state)
	    
	    // 1. Query ONLY matches Email and Password
	    // We do NOT check role here. We fetch it.
	    String query = String.format("SELECT * FROM users WHERE userEmail = '%s' AND userPassword = '%s'", email, password);
	    
	    ResultSet rs = connect.execQuery(query);
	    
	    try {
	        // 2. If a row is found...
	        if (rs.next()) {
	            user = new User(); // Create the empty object
	            
	            // 3. Fill the object with Data from DB
	            user.setUserID(rs.getInt("userID"));
	            user.setUserName(rs.getString("userName"));
	            user.setUserEmail(rs.getString("userEmail"));
	            user.setUserPassword(rs.getString("userPassword"));
	            user.setUserRole(rs.getString("userRole")); // <--- THIS is where we get the role
	            // Don't forget DOB and Gender if you need them later
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    // 4. Return the filled user (or null if not found)
	    return user;
	}
}
