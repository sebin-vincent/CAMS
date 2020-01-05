package com.litmus7.club.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class QueryManager {
	
	public static String getQuery(String query_Key) throws FileNotFoundException, IOException {
		String sql;
		
		File file=new File("query.properties");
		try(FileInputStream in_Stream=new FileInputStream(file);){
			Properties properties = new Properties();
			properties.load(in_Stream);
			sql=properties.getProperty(query_Key);
		}
		
		return sql;
		
	}

}
