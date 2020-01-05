package com.litmus7.club.daointerface;

import java.io.IOException;
import java.sql.SQLException;

import com.litmus7.club.exception.InvalidDataException;

public interface Authentication {
	
	public String userLogin(String username) throws SQLException, IOException, InvalidDataException;
	

}
