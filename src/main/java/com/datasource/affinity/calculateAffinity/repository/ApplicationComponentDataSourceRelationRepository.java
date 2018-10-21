package com.datasource.affinity.calculateAffinity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datasource.affinity.calculateAffinity.dbModel.ApplicationComponentDataSourceRelation;

@Repository
public interface ApplicationComponentDataSourceRelationRepository
		extends JpaRepository<ApplicationComponentDataSourceRelation, Long> {

}
