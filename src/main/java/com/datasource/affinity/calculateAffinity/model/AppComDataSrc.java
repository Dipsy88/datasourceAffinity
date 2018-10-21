package com.datasource.affinity.calculateAffinity.model;

public class AppComDataSrc {
	private long appCompId; // application component
	private long dataSrcId; // data source
	private long expTransfer; // expected transfer
	private long normExpTransfer; // normalized expected transfer

	public AppComDataSrc(long appCompId, long dataSrcId, long expTransfer) {
		this.appCompId = appCompId;
		this.dataSrcId = dataSrcId;
		this.expTransfer = expTransfer;
	}

	public AppComDataSrc(long appCompId, long dataSrcId, long expTransfer, long normExpTransfer) {
		this.appCompId = appCompId;
		this.dataSrcId = dataSrcId;
		this.expTransfer = expTransfer;
		this.normExpTransfer = normExpTransfer;
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

	public long getExpTransfer() {
		return expTransfer;
	}

	public void setExpTransfer(long expTransfer) {
		this.expTransfer = expTransfer;
	}

	public long getNormExpTransfer() {
		return normExpTransfer;
	}

	public void setNormExpTransfer(long normExpTransfer) {
		this.normExpTransfer = normExpTransfer;
	}

}
