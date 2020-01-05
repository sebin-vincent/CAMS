package com.litmus7.club.jdbcconnector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class DataBaseConnector {
	private final String properties_FileName = "dbConnector.properties";
	
	private String db_Url;
	private String userName;
	private String password;
	
	private Connection connection=null;
	
	public Connection getDatabaseConnection() throws SQLException, IOException {
		
		setDatabaseParameters(properties_FileName);
		
		try {
			connection=DriverManager.getConnection(db_Url, userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException("Connection establsiment failed..");
		}
		return connection;
	}
	
	
	
	public void closeDatabaseConnection(Connection connection) throws SQLException {
		
		try {
			connection.close();
		} catch (SQLException e) {
			throw new SQLException("Your database connection is not valid...");
		}
		
	}
	
	
	
	
	
	
	private void setDatabaseParameters(String properties_FileName)throws IOException{
		File file=new File(properties_FileName);
		try(FileInputStream inStream =new FileInputStream(file)){
			Properties properties = new Properties();
			properties.load(inStream);
			db_Url= "jdbc:mysql://localhost/" + properties.getProperty("dataBase_Name");
			userName=properties.getProperty("userName");
			password=properties.getProperty("password");
		} catch (FileNotFoundException e) {
			
			throw new FileNotFoundException("property file location not found");
			
		} catch (IOException e) {
			
			throw new IOException("IoException occured while reading property File");
		}
	}

}
