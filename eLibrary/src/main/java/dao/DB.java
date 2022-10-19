package dao;

import java.sql.*;

public class DB {
	public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
		String hostName = "localhost";
		String dbName = "myelibrary";
		String userName = "root";
		String password = "qaz123";
		return getMySQLConnection(hostName, dbName, userName, password);
	}

	public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password)
			throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver");

		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

		Connection connect = DriverManager.getConnection(connectionURL, userName, password);
		return connect;
	}
}
