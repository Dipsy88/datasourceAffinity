package com.datasource.affinity.calculateAffinity.extras;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

// Class to read the properties from the properties file

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

	public int getRecordsConsider() {
		return recordsConsider;
	}

	public void setRecordsConsider(int recordsConsider) {
		this.recordsConsider = recordsConsider;
	}

	public long getBestDataRead() {
		return bestDataRead;
	}

	public void setBestDataRead(long bestDataRead) {
		this.bestDataRead = bestDataRead;
	}

	public long getWorstDataRead() {
		return worstDataRead;
	}

	public void setWorstDataRead(long worstDataRead) {
		this.worstDataRead = worstDataRead;
	}

	public long getBestDataWrite() {
		return bestDataWrite;
	}

	public void setBestDataWrite(long bestDataWrite) {
		this.bestDataWrite = bestDataWrite;
	}

	public long getWorstDataWrite() {
		return worstDataWrite;
	}

	public void setWorstDataWrite(int worstDataWrite) {
		this.worstDataWrite = worstDataWrite;
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public int getTimePurge() {
		return timePurge;
	}

	public void setTimePurge(int timePurge) {
		this.timePurge = timePurge;
	}

	public int getNumRecordGenerate() {
		return numRecordGenerate;
	}

	public void setNumRecordGenerate(int numRecordGenerate) {
		this.numRecordGenerate = numRecordGenerate;
	}

}
