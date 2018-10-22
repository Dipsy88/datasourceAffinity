package com.datasource.affinity.calculateAffinity.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.datasource.affinity.calculateAffinity.dbModel.ApplicationComponentDataSourceData;

@Repository
public interface ApplicationComponentDataSourceDataRepository
		extends JpaRepository<ApplicationComponentDataSourceData, Long> {
	String databaseName = "applicationcomponent_datasource_data";

	List<ApplicationComponentDataSourceData> findByAppCompIdAndDataSourceIdOrderByIdDesc(Long appCompId,
			Long dataSourceId, Pageable pageable);

	@Query(value = "select *  from " + databaseName
			+ " where appcomp_id = :acId and datasource_id = :dsId and (data_read > 0 or data_write > 0) ORDER BY id desc LIMIT :maxRecords", nativeQuery = true)
	List<ApplicationComponentDataSourceData> findByAppCompIdAndDataSourceIdDataHigherZero(@Param("acId") Long acId,
			@Param("dsId") Long dsId, @Param("maxRecords") int maxRecords);

	@Query(value = "select *  from " + databaseName
			+ " where appcomp_id = :acId and datasource_id = :dsId and data_read > 0 ORDER BY id desc LIMIT :maxRecords", nativeQuery = true)
	List<ApplicationComponentDataSourceData> findByAppCompIdAndDataSourceIdDataReadHigherZero(@Param("acId") Long acId,
			@Param("dsId") Long dsId, @Param("maxRecords") int maxRecords);

	@Query(value = "select *  from " + databaseName
			+ " where appcomp_id = :acId and datasource_id = :dsId and data_write > 0 ORDER BY id desc LIMIT :maxRecords", nativeQuery = true)
	List<ApplicationComponentDataSourceData> findByAppCompIdAndDataSourceIdDataWriteHigherZero(@Param("acId") Long acId,
			@Param("dsId") Long dsId, @Param("maxRecords") int maxRecords);
}
