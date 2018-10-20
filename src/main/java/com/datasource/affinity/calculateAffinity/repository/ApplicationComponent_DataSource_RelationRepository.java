package com.datasource.affinity.calculateAffinity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datasource.affinity.calculateAffinity.dbModel.ApplicationComponent_DataSource_Relation;

@Repository
public interface ApplicationComponent_DataSource_RelationRepository
		extends JpaRepository<ApplicationComponent_DataSource_Relation, Long> {

}
