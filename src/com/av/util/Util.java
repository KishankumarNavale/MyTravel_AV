package com.av.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.Driver;

public class Util {
	private static java.sql.Connection connection;
	private Matcher matcher;
	private Pattern pattern;

	static String filePathCreationDateWise;
        static String filePathFollowUpDateWise;
        static String filePathTripWise;
        static String DbBackUpfilePath;
	static String DbBackUpfilePath1;
	static String DbBackUpfilePath2;
	static String uName;
	static String dbName;
        static String logDirSuccess;
        static String logDirError;

    public static String getLogDirSuccess() {
        return logDirSuccess;
    }

    public static String getLogDirError() {
        return logDirError;
    }
       
    public static String getFilePathCreationDateWise() {
        return filePathCreationDateWise;
    }

    public static String getFilePathFollowUpDateWise() {
        return filePathFollowUpDateWise;
    }

    public static String getFilePathTripWise() {
        return filePathTripWise;
    }
	

	public static String getDbName() {
		return dbName;
	}

	public static String getuName() {
		return uName;
	}

	public static void setuName(String uName) {
		Util.uName = uName;
	}

	public static String getpWord() {
		return pWord;
	}

	public static void setpWord(String pWord) {
		Util.pWord = pWord;
	}

	static String pWord;

	public static String getDbBackUpfilePath() {
		return DbBackUpfilePath;
	}

	public static void setDbBackUpfilePath(String dbBackUpfilePath) {
		DbBackUpfilePath = dbBackUpfilePath;
	}

	public static String getDbBackUpfilePath1() {
		return DbBackUpfilePath1;
	}

	public static void setDbBackUpfilePath1(String dbBackUpfilePath1) {
		DbBackUpfilePath1 = dbBackUpfilePath1;
	}

	public static String getDbBackUpfilePath2() {
		return DbBackUpfilePath2;
	}

	public static void setDbBackUpfilePath2(String dbBackUpfilePath2) {
		DbBackUpfilePath2 = dbBackUpfilePath2;
	}


	String password;
	String dbNAme;
	String userName;
	String hostName;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public Connection openConnection() throws SQLException, IOException {
		getPropValues();
		Driver driverClassRef = new Driver();
		DriverManager.registerDriver(driverClassRef);

		String dbUrl = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?user=" + userName + "&password=" + password
				+ "";
		connection = DriverManager.getConnection(dbUrl);

		return connection;

	}

	public void closeConnection() throws SQLException {

		connection.close();

	}

	public boolean emailValidator(final String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	InputStream inputStream;

	public void getPropValues() throws IOException {

		try {
			Properties prop = new Properties();
			// String propFileName = "C:/Users/Kiran
			// Banakar/Desktop/AnandVihari/config.properties";

			inputStream = new FileInputStream(new File("E:/APP/Config/config.properties"));
			// inputStream =
			// getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file ' not found in the classpath");
			}

			Date time = new Date(System.currentTimeMillis());

			// get the property value and print it out
			userName = prop.getProperty("userName");
			setuName(userName);
			password = prop.getProperty("password");
			setpWord(password);
			filePathCreationDateWise = prop.getProperty("filePathCreationDateWise");
                        filePathFollowUpDateWise = prop.getProperty("filePathFollowUpDateWise");
                        filePathTripWise = prop.getProperty("filePathTripWise");
			DbBackUpfilePath = prop.getProperty("DbBackUpfilePath");
			DbBackUpfilePath1 = prop.getProperty("DbBackUpfilePath1");
			DbBackUpfilePath2 = prop.getProperty("DbBackUpfilePath2");
                        logDirSuccess=prop.getProperty("logDirSuccess");
                        logDirError=prop.getProperty("logDirError");
                        dbName = prop.getProperty("dbName");
			hostName = prop.getProperty("hostName");
                        
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}

	}
}
