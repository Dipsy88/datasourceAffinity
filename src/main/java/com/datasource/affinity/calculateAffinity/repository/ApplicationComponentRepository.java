package com.datasource.affinity.calculateAffinity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datasource.affinity.calculateAffinity.dbModel.ApplicationComponent;

@Repository
public interface ApplicationComponentRepository extends JpaRepository<ApplicationComponent, Long> {

}
