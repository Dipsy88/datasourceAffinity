package com.datasource.affinity.calculateAffinity.extras;

import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

// Class to read the properties from the properties file

@Getter
@Setter
public class GetPropertyValues {
	private int recordsConsider = 0; // number of instances to consider from the database
	private long bestDataRead = 0;
	private long worstDataRead = 0;
	private long bestDataWrite = 0;
	private long worstDataWrite = 0;
	private int timeInterval = 0; // time interval in seconds to update
	private String function = ""; // algorithm to calculate for the latest
	private int timePurge = 0; // time to purge old records in seconds
	private int numRecordGenerate = 0; // number of records to generate each second

//	read the config files and populate the elements
	public void readValues() {
		try {
			Properties properties = new Properties();
			String propertiesFile = "config.properties";

			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);

			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propertiesFile + "' not found");
			}

			// get the property value
			recordsConsider = Integer.parseInt(properties.getProperty("recordsConsider"));
			bestDataRead = Long.parseLong(properties.getProperty("bestDataRead"));
			worstDataRead = Long.parseLong(properties.getProperty("worstDataRead"));
			bestDataWrite = Long.parseLong(properties.getProperty("bestDataRead"));
			worstDataWrite = Long.parseLong(properties.getProperty("worstDataRead"));
			timeInterval = Integer.parseInt(properties.getProperty("timeInterval"));
			function = properties.getProperty("function");
			timePurge = Integer.parseInt(properties.getProperty("timePurge"));
			numRecordGenerate = Integer.parseInt(properties.getProperty("numRecordGenerate"));
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

}
