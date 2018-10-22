package com.datasource.affinity.calculateAffinity.dbModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "applicationcomponent_datasource_data")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationComponentDataSourceData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "appcomp_id", nullable = false)
	private Long appCompId;
	@Column(name = "datasource_id", nullable = false)
	private Long dataSourceId;
	@Column(name = "data_read")
	private Long dataRead;
	@Column(name = "data_write")
	private Long dataWrite;
	@Column(name = "timestamp", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
//	@CreatedDate
	private Date timestamp;

	public ApplicationComponentDataSourceData(Long appCompId, Long dataSourceId, Long dataRead, Long dataWrite,
			Date timestamp) {
		super();
		this.appCompId = appCompId;
		this.dataSourceId = dataSourceId;
		this.dataRead = dataRead;
		this.dataWrite = dataWrite;
		this.timestamp = timestamp;
	}

	public ApplicationComponentDataSourceData() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAppCompId() {
		return appCompId;
	}

	public void setAppCompId(Long appCompId) {
		this.appCompId = appCompId;
	}

	public Long getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Long dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public Long getDataRead() {
		return dataRead;
	}

	public void setDataRead(Long dataRead) {
		this.dataRead = dataRead;
	}

	public Long getDataWrite() {
		return dataWrite;
	}

	public void setDataWrite(Long dataWrite) {
		this.dataWrite = dataWrite;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
