package com.datasource.affinity.calculateAffinity.runner;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.datasource.affinity.calculateAffinity.dbModel.ApplicationComponent_DataSource_Data;
import com.datasource.affinity.calculateAffinity.extras.GetPropertyValues;
import com.datasource.affinity.calculateAffinity.repository.ApplicationComponent_DataSource_DataRepository;

@SpringBootApplication
@EnableJpaAuditing
public class Generator {
	@Autowired
	ApplicationComponent_DataSource_DataRepository applicationComponent_DataSource_DataRepository;

	public void generateData(GetPropertyValues propValues) {
		long numAC = generateRandomNum(1, 3);
		long numDS = generateRandomNum(1, 3);

		long amountRead = calculateVal(propValues.getWorstDataRead(), propValues.getBestDataRead());
		long amountWrite = calculateVal(propValues.getWorstDataWrite(), propValues.getBestDataWrite());

		ApplicationComponent_DataSource_Data data = new ApplicationComponent_DataSource_Data(numAC, numDS, amountRead,
				amountWrite);
		applicationComponent_DataSource_DataRepository.save(data);
	}

	// calculate values within range making sure 10% data is 0
	public long calculateVal(long min, long max) {
		long retVal = 0;
		int numData = generateRandomNum(1, 10);
		boolean isDataZero = numData > 1 ? false : true;
		if (!isDataZero)
			retVal = ThreadLocalRandom.current().nextLong(min, max + 1);

		return retVal;
	}

	// generate random num
	public int generateRandomNum(int min, int max) {
		int retNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return retNum;
	}

}
