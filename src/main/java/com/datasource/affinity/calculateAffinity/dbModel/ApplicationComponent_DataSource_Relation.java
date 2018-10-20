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
@Table(name = "applicationcomponent_datasource_relation")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationComponent_DataSource_Relation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "appcomp_id", nullable = false)
	private Long appCompId;
	@Column(name = "ds_id", nullable = false)
	private Long dataSourceId;
	@Column(name = "affinity", nullable = false)
	private Long affinity;

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

	public Long getAffinity() {
		return affinity;
	}

	public void setAffinity(Long affinity) {
		this.affinity = affinity;
	}

}
