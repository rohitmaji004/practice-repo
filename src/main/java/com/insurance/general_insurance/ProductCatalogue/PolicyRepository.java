package com.insurance.general_insurance.ProductCatalogue;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;
@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long>{
	
	@Query("select p from Policy p where p.user.id=:userId")
	public List<Policy> getAllPolciesByUserId(Long userId);
	@Query("select count(p) from Policy p")
	public Long policyCount();
	@Query("SELECT p FROM Policy p " +
		       "WHERE (:id IS NULL OR p.id = :id) " +
		       "AND (:policyName IS NULL OR LOWER(p.policyName) LIKE LOWER(CONCAT('%', :policyName, '%'))) " +
		       "AND (:policyType IS NULL OR LOWER(p.policyType) LIKE LOWER(CONCAT('%', :policyType, '%'))) " +
		       "AND (:minCoverage IS NULL OR p.coverageAmount >= :minCoverage) " +
		       "AND (:maxCoverage IS NULL OR p.coverageAmount <= :maxCoverage) " +
		       "AND (:minPremium IS NULL OR p.premiumAmount >= :minPremium) " +
		       "AND (:maxPremium IS NULL OR p.premiumAmount <= :maxPremium) " +
		       "AND (:startDate IS NULL OR p.startDate >= :startDate)")
	public List<Policy> filterPolicies(@Param("policyType") String policyType, 
			@Param("minCoverage") Double minCoverage,
			@Param("maxCoverage") Double maxCoverage,
			@Param("minPremium") Double minPremium,
			@Param("maxPremium") Double maxPremium,
			@Param("id") Long id,
			@Param("policyName") String policyName,
			@Param("startDate") LocalDate startDate);
	
}
