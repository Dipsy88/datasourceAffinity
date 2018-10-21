package com.datasource.affinity.calculateAffinity.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.Async;

import com.datasource.affinity.calculateAffinity.dbModel.ApplicationComponent;
import com.datasource.affinity.calculateAffinity.dbModel.ApplicationComponentDataSourceData;
import com.datasource.affinity.calculateAffinity.dbModel.DataSource;
import com.datasource.affinity.calculateAffinity.extras.GetPropertyValues;
import com.datasource.affinity.calculateAffinity.model.AppComDataSrc;
import com.datasource.affinity.calculateAffinity.repository.ApplicationComponentDataSourceDataRepository;
import com.datasource.affinity.calculateAffinity.repository.ApplicationComponentRepository;
import com.datasource.affinity.calculateAffinity.repository.DataSourceRepository;

//to enable the autowire functionality
@SpringBootApplication
@EnableJpaAuditing
public class AffinityController {
	@Autowired
	private ApplicationComponentRepository acRepository;
	@Autowired
	private DataSourceRepository dsRepository;
	@Autowired
	private ApplicationComponentDataSourceDataRepository acDSDataRepo;
	private GetPropertyValues propValues = new GetPropertyValues(); // store config

	private double wtRead = 0.5;

	@Async
	public void run() {
		storeInternally();
		long a = 3;
	}

	public void storeInternally() {
		List<ApplicationComponent> acList = new ArrayList<ApplicationComponent>();
		List<DataSource> dsList = new ArrayList<DataSource>();

		acList = acRepository.findAll();
		dsList = dsRepository.findAll();
		for (int i = 0; i < acList.size(); i++) {
			for (int j = 0; j < dsList.size(); j++) {
				List<ApplicationComponentDataSourceData> acDSDataReadList = acDSDataRepo
						.findByAppCompIdAndDataSourceIdDataReadHigherZero(acList.get(i).getId(), dsList.get(j).getId(),
								propValues.getRecordsConsider());
				List<ApplicationComponentDataSourceData> acDSDataWriteList = acDSDataRepo
						.findByAppCompIdAndDataSourceIdDataWriteHigherZero(acList.get(i).getId(), dsList.get(j).getId(),
								propValues.getRecordsConsider());
				if (acDSDataReadList.size() > 0 || acDSDataWriteList.size() > 0) {
					long predVal = getPrediction(acDSDataReadList, acDSDataWriteList);
					AppComDataSrc appComDataSrc = new AppComDataSrc(acList.get(i).getId(), dsList.get(j).getId(),
							predVal);
					int a;
					a = 4;
				}

			}

		}

//		acDSDataList = acDSDataRepo.findByAppCompIdAndDataSourceIdOrderByIdDesc(1L, 2L,
//				PageRequest.of(0, propValues.getRecordsConsider()));

	}

	public long getPrediction(List<ApplicationComponentDataSourceData> acDSDataReadList,
			List<ApplicationComponentDataSourceData> acDSDataWriteList) {
		long predVal = (long) (wtRead * sumRecordsDataReadEqWt(acDSDataReadList))
				+ (long) ((1 - wtRead) * sumRecordsDataWriteEqWt(acDSDataWriteList));
		return predVal;
	}

	// sum records higher than 0 for data read
	public long sumRecordsDataReadEqWt(List<ApplicationComponentDataSourceData> acDSDataList) {
		long retTotal = 0L;
		for (ApplicationComponentDataSourceData acDSData : acDSDataList)
			retTotal += (1. / acDSDataList.size()) * acDSData.getDataRead();
		return retTotal;
	}

	// sum records higher than 0 for data write
	public long sumRecordsDataWriteEqWt(List<ApplicationComponentDataSourceData> acDSDataList) {
		long retTotal = 0L;
		for (ApplicationComponentDataSourceData acDSData : acDSDataList)
			retTotal += (1. / acDSDataList.size()) * acDSData.getDataWrite();
		return retTotal;
	}

	public GetPropertyValues getPropValues() {
		return propValues;
	}

	public void setPropValues(GetPropertyValues propValues) {
		this.propValues = propValues;
	}
}
