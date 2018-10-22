package com.datasource.affinity.calculateAffinity.model;

public class AppComDataSrc {
	private long appCompId; // application component
	private long dataSrcId; // data source
	private long expDataTransfer; // expected transfer
	private double normExpDataTransfer; // normalized expected transfer
	private long numTransfer; // number transfer
	private double normNumTransfer; // normalized transfer

	private double coupleValue; // final couple value for each application component and data source

	public AppComDataSrc(long appCompId, long dataSrcId, long expDataTransfer, long numTransfer) {
		this.appCompId = appCompId;
		this.dataSrcId = dataSrcId;
		this.expDataTransfer = expDataTransfer;
		this.numTransfer = numTransfer;
	}

	public AppComDataSrc(long appCompId, long dataSrcId, long expDataTransfer, double normExpDataTransfer,
			long numTransfer, double normNumTransfer) {
		this.appCompId = appCompId;
		this.dataSrcId = dataSrcId;
		this.expDataTransfer = expDataTransfer;
		this.normExpDataTransfer = normExpDataTransfer;
		this.numTransfer = numTransfer;
		this.normNumTransfer = normNumTransfer;
	}

	public AppComDataSrc() {

	}

	public long getAppCompId() {
		return appCompId;
	}

	public void setAppCompId(long appCompId) {
		this.appCompId = appCompId;
	}

	public long getDataSrcId() {
		return dataSrcId;
	}

	public void setDataSrcId(long dataSrcId) {
		this.dataSrcId = dataSrcId;
	}

	public long getExpDataTransfer() {
		return expDataTransfer;
	}

	public void setExpDataTransfer(long expDataTransfer) {
		this.expDataTransfer = expDataTransfer;
	}

	public double getNormExpDataTransfer() {
		return normExpDataTransfer;
	}

	public void setNormExpDataTransfer(double normExpDataTransfer) {
		this.normExpDataTransfer = normExpDataTransfer;
	}

	public long getNumTransfer() {
		return numTransfer;
	}

	public void setNumTransfer(long numTransfer) {
		this.numTransfer = numTransfer;
	}

	public double getNormNumTransfer() {
		return normNumTransfer;
	}

	public void setNormNumTransfer(double normNumTransfer) {
		this.normNumTransfer = normNumTransfer;
	}

	public double getCoupleValue() {
		return coupleValue;
	}

	public void setCoupleValue(double coupleValue) {
		this.coupleValue = coupleValue;
	}

}
