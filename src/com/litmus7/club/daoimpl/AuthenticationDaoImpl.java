package com.litmus7.club.daoimpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.litmus7.club.daointerface.Authentication;
import com.litmus7.club.exception.InvalidDataException;
import com.litmus7.club.jdbcconnector.DataBaseConnector;
import com.litmus7.club.utility.QueryManager;

public class AuthenticationDaoImpl implements Authentication {
	private ResultSet result_Set = null;
	private PreparedStatement statement = null;
	

	@Override
	public String userLogin(String username) throws SQLException, IOException, InvalidDataException {

		
		String password;
		DataBaseConnector connector=new DataBaseConnector();
		Connection connection=connector.getDatabaseConnection();
		
		final String sql=QueryManager.getQuery("get_Login_credential");
		
		try {
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new SQLException("Query preparation failed while fetching user credentials..");
		}
		statement.setString(1,username);
		try {
			result_Set = statement.executeQuery();
		} catch (SQLException e) {
			throw new SQLException("Query execution failed while fetching user credentials");
		}
		
		if(!(result_Set.next())){
			throw new InvalidDataException("Username is not registered!");
		}else {
			password=result_Set.getString("password");
		}
		statement.close();
		result_Set.close();
		connector.closeDatabaseConnection(connection);
		
		
		return password;
	}

}
