package com.datasource.affinity.calculateAffinity.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.Async;

import com.datasource.affinity.calculateAffinity.extras.GetPropertyValues;
import com.datasource.affinity.calculateAffinity.repository.ApplicationComponentDataSourceDataRepository;
import com.datasource.affinity.calculateAffinity.runner.Generator;

//to enable the autowire functionality
@SpringBootApplication
@EnableJpaAuditing
public class GeneratorController {
	@Autowired
	Generator generator;
	@Autowired
	ApplicationComponentDataSourceDataRepository acDSDataRepository;

	private GetPropertyValues propValues = new GetPropertyValues(); // store config

	@Async
	public void run() throws InterruptedException {
		long currentTime = System.currentTimeMillis();

		// delete all records
//		deleteData();// delete the old data

		// generate data
		long numRecordGenerate = propValues.getNumRecordGenerate();
		long sleepTime = 1000 / numRecordGenerate;

		while (true) {
			Thread.sleep(sleepTime); // number of records generated per second
			generator.generateData(propValues);

			long newTime = System.currentTimeMillis();
			if ((newTime - currentTime) >= propValues.getTimePurge() * 1000) {

				Date date = new Date(currentTime);
//				deleteData(date);// delete the old data
				currentTime = newTime;
			}
		}

	}

	public void deleteData() {
		acDSDataRepository.deleteAll();
	}

	public GetPropertyValues getPropValues() {
		return propValues;
	}

	public void setPropValues(GetPropertyValues propValues) {
		this.propValues = propValues;
	}

}
