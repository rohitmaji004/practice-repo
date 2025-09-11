package com.insurance.general_insurance.ProductCatalogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing the Catalogue of available insurance policies.
 * This includes viewing, filtering, and adding/removing policies.
 */
@RestController
@RequestMapping("/catalogue") // URL path for the catalogue module
public class CatalogueController {

	
    private final CatalogueService catalogueService;
	
    @Autowired
    private final PolicyService policyService;

    @Autowired
    public CatalogueController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
		this.policyService = new PolicyService();
    }
    
    
    // CRUD ADMIN ONLY
    @PostMapping("/add") // working
    public ResponseEntity<Policy> addPolicy(@RequestBody Policy policy) {
        Policy newPolicy = catalogueService.addPolicy(policy);
        return new ResponseEntity<>(newPolicy, HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{policyId}") // This is also working
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long policyId, @RequestBody Policy updatedPolicy) {
        Policy policy = policyService.updatePolicy(policyId, updatedPolicy);
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }
    
    @DeleteMapping("/remove/{policyId}") // working
    public ResponseEntity<Void> removePolicy(@PathVariable Long policyId) {
        catalogueService.removePolicy(policyId);
        return ResponseEntity.noContent().build();
    }
    
    
    
    // Operations allowed for Admin n=and users.
    
    
    
    
    @GetMapping("/list") // This is working
    public ResponseEntity<List<Policy>> getAllPolicies() {
        List<Policy> policies = catalogueService.getAllPolicies();
        return new ResponseEntity<>(policies, HttpStatus.OK);
    }
    
    @GetMapping("/{policyId}") // working
    public ResponseEntity<Policy> getPolicyDetails(@PathVariable Long policyId) {
        Policy policy = catalogueService.getPolicyDetails(policyId);
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }  
    

    // Admin: Filter and search policies based on type, coverage, price, etc.
//    @GetMapping("/filter") // Working
//    public ResponseEntity<List<Policy>> filterPolicies(
//        @RequestParam(required = false) String policyType,
//        @RequestParam(required = false) Double maxPrice
//    ) {
//        List<Policy> policies = catalogueService.filterPolicies(policyType, maxPrice);
//        return new ResponseEntity<>(policies, HttpStatus.OK);
//    }

    
   

    
}
