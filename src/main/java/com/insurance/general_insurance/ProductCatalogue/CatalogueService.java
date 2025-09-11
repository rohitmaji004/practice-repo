package com.insurance.general_insurance.ProductCatalogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CatalogueService {

    private final PolicyRepository policyRepository;

    @Autowired
    public CatalogueService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    // Catalogue viewing ; can be done by both customers and admin.
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    // Catalogur filtering; also can be done by admin and customers for viewing purpose
    // Custom finder methods.
    // have to write a custom sql here ; for that i have to use jpql(Java persistence query langauge) 
    //@Query for the particular function in the repository interface, also it will be an abstract method
    public List<Policy> filterPolicies(String policyType,
    		Double minCoverage,
    		Double maxCoverage,
    		Double minPremium,
    		Double maxPremium,
    		Long id,
    		String policyName,
    		LocalDate startDate) {
        return policyRepository.filterPolicies(policyType, minCoverage, maxCoverage, minPremium, maxPremium, id, policyName, startDate);
    }

    // This can be done by both admin and users.
    public Policy getPolicyDetails(Long policyId) {
        return policyRepository.findById(policyId).orElseThrow(() -> new RuntimeException("Policy not found!"));
    }

    // ONLY ADMIN CAN ADD NEW PLOCY
    public Policy addPolicy(Policy policy) {
    	// Lohic to check if the current user is admin or not ,
    	// if not then i have to throw an Permission Denial exception.
    	//And if admin then go ahead ad add the policy.
        return policyRepository.save(policy);
    }

    // Remove a policy; same as the above one,
    public void removePolicy(Long policyId) {
        policyRepository.deleteById(policyId);
    }
}