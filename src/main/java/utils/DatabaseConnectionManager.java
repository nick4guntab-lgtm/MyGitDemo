package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionManager {
	public static void dbConnection() throws SQLException {

		String dbURL = "jdbc:mysql://localhost:3306/my_db";
		String dbUser = "name";
		String dbPass = "password";

		Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery("SELECT * FROM patients WHERE allergies=0");

		String name = "";
		String status = "";

		while (result.next()) {
			name = result.getString("name");
			status = result.getString("status");
		}

		System.out.println("Name of the user is " + name + " and status is '" + status + "'");

		conn.close();

	}
}
