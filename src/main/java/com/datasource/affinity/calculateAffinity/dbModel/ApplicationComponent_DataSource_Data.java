package com.datasource.affinity.calculateAffinity.dbModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "applicationcomponent_datasource_data")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationComponent_DataSource_Data {
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

	public ApplicationComponent_DataSource_Data(Long appcompId, Long dataSourceId, Long dataRead, Long dataWrite) {
		super();
		this.appCompId = appcompId;
		this.dataSourceId = dataSourceId;
		this.dataRead = dataRead;
		this.dataWrite = dataWrite;
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

}
