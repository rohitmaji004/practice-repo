package com.insurance.general_insurance.ProductCatalogue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing individual policies.
 * This includes actions like creating, updating, and deleting policies.
 */
@RestController
@RequestMapping("/policies") // URL path for the policy management module
public class PolicyController {

    private final PolicyService policyService;

    @Autowired
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // Admin: Create a new policy (for insurance admin to define new policies)
    @PostMapping("/create") // This is also working
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {
        Policy newPolicy = policyService.createPolicy(policy);
        return new ResponseEntity<>(newPolicy, HttpStatus.CREATED);
    }

    // Admin: Update an existing policy (Admin can change details like price,
    // coverage)
    @PutMapping("/update/{policyId}") // This is also working
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long policyId, @RequestBody Policy updatedPolicy) {
        Policy policy = policyService.updatePolicy(policyId, updatedPolicy);
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }

    // Admin: Delete a policy (Admin can remove a policy from the system)
    @DeleteMapping("/delete/{policyId}") // This is not working
    public ResponseEntity<Void> deletePolicy(@PathVariable Long policyId) {
        policyService.deletePolicy(policyId);
        return ResponseEntity.noContent().build();
    }

    // User: View details of a specific policy (User can view policy information)
    @GetMapping("/{policyId}") // This is working
    public ResponseEntity<Policy> getPolicyDetails(@PathVariable Long policyId) {
        Policy policy = policyService.getPolicyDetails(policyId);
        return new ResponseEntity<>(policy, HttpStatus.OK);
    }


    // User: Update their personal information on a policy (For example, contact
    // info)
    // @PutMapping("/update/user/{policyId}")
    // public ResponseEntity<Policy> updateUserPolicyInfo(
    // @PathVariable Long policyId,
    // @RequestBody Policy updatedPolicy) {
    // Policy policy = policyService.updateUserPolicyInfo(policyId, updatedPolicy);
    // return new ResponseEntity<>(policy, HttpStatus.OK);
    // }
}