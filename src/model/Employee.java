package model;

import database.Connect;

public class Employee extends User {

	public Employee(int userID, String userName, String userEmail, String userPassword, String userGender,
			String userDOB, String userRole) {
		super(userID, userName, userEmail, userPassword, userGender, userDOB, "Employee");
		// TODO Auto-generated constructor stub
	}
}
