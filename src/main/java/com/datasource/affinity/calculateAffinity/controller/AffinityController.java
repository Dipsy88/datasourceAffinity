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
	// set default; maybe modify just for experiments
	private final int MIN_RANGE = 0;
	private final int MAX_RANGE = 1;
	private final double WT_READ = 0.5;
	private final int WT_AI = 1;
	private final double WT_DATA_TRANSFER = 0.5;

	@Autowired
	private ApplicationComponentRepository acRepository;
	@Autowired
	private DataSourceRepository dsRepository;
	@Autowired
	private ApplicationComponentDataSourceDataRepository acDSDataRepo;
	private GetPropertyValues propValues = new GetPropertyValues(); // store config

	private long minDataTransPrediction = Long.MAX_VALUE;
	private long maxDataTransPrediction = 0;
	private long minNumTransPrediction = Long.MAX_VALUE;
	private long maxNumTransPrediction = 0;

	@Async
	public void run() {
		storeInternally();
	}

	public void storeInternally() {
		List<ApplicationComponent> acList = new ArrayList<ApplicationComponent>();
		List<DataSource> dsList = new ArrayList<DataSource>();

		acList = acRepository.findAll();
		dsList = dsRepository.findAll();

		List<AppComDataSrc> appComDataSrcList = new ArrayList<AppComDataSrc>();
		for (int i = 0; i < acList.size(); i++) {
			for (int j = 0; j < dsList.size(); j++) {
				List<ApplicationComponentDataSourceData> acDSDataList = acDSDataRepo
						.findByAppCompIdAndDataSourceIdDataHigherZero(acList.get(i).getId(), dsList.get(j).getId(),
								propValues.getRecordsConsider());
//				List<ApplicationComponentDataSourceData> acDSDataReadList = acDSDataRepo
//						.findByAppCompIdAndDataSourceIdDataReadHigherZero(acList.get(i).getId(), dsList.get(j).getId(),
//								propValues.getRecordsConsider());
//				List<ApplicationComponentDataSourceData> acDSDataWriteList = acDSDataRepo
//						.findByAppCompIdAndDataSourceIdDataWriteHigherZero(acList.get(i).getId(), dsList.get(j).getId(),
//								propValues.getRecordsConsider());
				if (acDSDataList.size() > 0) {
					long predVal = dataPrediction(acDSDataList);
					long predNumTransfer = numTransferPrediction(acDSDataList);
					AppComDataSrc appComDataSrc = new AppComDataSrc(acList.get(i).getId(), dsList.get(j).getId(),
							predVal, predNumTransfer);

					appComDataSrcList.add(appComDataSrc);

				}
			}
		}

		for (AppComDataSrc appComDataSrc : appComDataSrcList) {
			double norDataTransfer = normalizeDataTransfer(appComDataSrc.getExpDataTransfer());
			appComDataSrc.setNormExpDataTransfer(norDataTransfer);

			double norNumTransfer = normalizeNumTransfer(appComDataSrc.getNumTransfer());
			appComDataSrc.setNormNumTransfer(norNumTransfer);

			// final couple value
			double coupleVal = calculateCouple(appComDataSrc.getNormExpDataTransfer(),
					appComDataSrc.getNormNumTransfer());
			appComDataSrc.setCoupleValue(coupleVal);
		}

		int a;
		a = 3;
	}

	// calculate the final couple value
	public double calculateCouple(double dataTransferVal, double numTransferVal) {
		double retVal = 0;
		retVal = WT_DATA_TRANSFER * dataTransferVal + (1 - WT_DATA_TRANSFER) * numTransferVal;
		return retVal;
	}

	// calculate sum of number of transfer
	public long numTransferPrediction(List<ApplicationComponentDataSourceData> acDSDataList) {
		long predVal = 0L;
		predVal = (long) (WT_READ * sumNumTransferRead(acDSDataList))
				+ (long) ((1 - WT_READ) * sumNumTransferWrite(acDSDataList));
		this.maxNumTransPrediction = this.maxNumTransPrediction > predVal ? this.maxNumTransPrediction : predVal;
		this.minNumTransPrediction = this.minNumTransPrediction < predVal ? this.minNumTransPrediction : predVal;
		return predVal;
	}

	// calculate the sum of data transfers for read
	public long sumNumTransferRead(List<ApplicationComponentDataSourceData> acDSDataList) {
		long retTotal = 0L;
		for (ApplicationComponentDataSourceData acDSData : acDSDataList) {
			int count = 0;
			if (acDSData.getDataRead() > 0)
				count++;
			retTotal += WT_AI * count;
//			retTotal += (1. / acDSDataList.size()) * count;
		}
		return retTotal;
	}

	// calculate the sum of data transfers for write
	public long sumNumTransferWrite(List<ApplicationComponentDataSourceData> acDSDataList) {
		long retTotal = 0L;
		for (ApplicationComponentDataSourceData acDSData : acDSDataList) {
			int count = 0;
			if (acDSData.getDataWrite() > 0)
				count++;
			retTotal += WT_AI * count;
//			retTotal += (1. / acDSDataList.size()) * count;
		}
		return retTotal;
	}

	// normalize the value
	public double normalizeNumTransfer(long val) {
		double retVal = 0.;
		retVal = (val - this.minNumTransPrediction)
				* ((MAX_RANGE - MIN_RANGE) / (double) (this.maxNumTransPrediction - this.minNumTransPrediction))
				+ MIN_RANGE;
		return retVal;
	}

	// normalize the value
	public double normalizeDataTransfer(long val) {
		double retVal = 0.;
		retVal = (val - this.minDataTransPrediction)
				* ((MAX_RANGE - MIN_RANGE) / (double) (this.maxDataTransPrediction - this.minDataTransPrediction))
				+ MIN_RANGE;
		return retVal;
	}

	public long dataPrediction(List<ApplicationComponentDataSourceData> acDSDataList) {
		long predVal = (long) (WT_READ * sumRecordsDataReadEqWt(acDSDataList))
				+ (long) ((1 - WT_READ) * sumRecordsDataWriteEqWt(acDSDataList));
		this.maxDataTransPrediction = this.maxDataTransPrediction > predVal ? this.maxDataTransPrediction : predVal;
		this.minDataTransPrediction = this.minDataTransPrediction < predVal ? this.minDataTransPrediction : predVal;
		return predVal;
	}

	// sum records higher than 0 for data read
	public long sumRecordsDataReadEqWt(List<ApplicationComponentDataSourceData> acDSDataList) {
		long retTotal = 0L;
		for (ApplicationComponentDataSourceData acDSData : acDSDataList)
			retTotal += WT_AI * acDSData.getDataRead();
//			retTotal += (1. / acDSDataList.size()) * acDSData.getDataRead();
		return retTotal;
	}

	// sum records higher than 0 for data write
	public long sumRecordsDataWriteEqWt(List<ApplicationComponentDataSourceData> acDSDataList) {
		long retTotal = 0L;
		for (ApplicationComponentDataSourceData acDSData : acDSDataList)
			retTotal += WT_AI * acDSData.getDataWrite();
		return retTotal;
	}

	public GetPropertyValues getPropValues() {
		return propValues;
	}

	public void setPropValues(GetPropertyValues propValues) {
		this.propValues = propValues;
	}
}
