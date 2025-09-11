package com.insurance.general_insurance.ProductCatalogue;
import com.insurance.general_insurance.user.*;
import com.insurance.general_insurance.user.entity.User;
import com.insurance.general_insurance.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

	@Autowired
    private PolicyRepository policyRepository;

	@Autowired
	private UserRepository userRepository;
   
    // Admin can create a new policy; Ignore the PolicyDTO I directly using the entity object
    public Policy createPolicy(Policy policyDTO) {
        Policy policy = new Policy();
        policy.setPolicyName(policyDTO.getPolicyName());
        policy.setPolicyType(policyDTO.getPolicyType());
        policy.setCoverageAmount(policyDTO.getCoverageAmount());
        policy.setPremiumAmount(policyDTO.getPremiumAmount());
        policy.setDescription(policyDTO.getDescription());
        return policyRepository.save(policy);
    }
    public Long getPolicyCount()
    {
    	return policyRepository.policyCount();
    }
    // Admin can do this only
    public Policy updatePolicy(Long policyId, Policy policyDTO) {
        Optional<Policy> policyOptional = policyRepository.findById(policyId);
        if (policyOptional.isPresent()) {
            Policy policy = policyOptional.get();
            policy.setPolicyName(policyDTO.getPolicyName());
            policy.setPolicyType(policyDTO.getPolicyType());
            policy.setCoverageAmount(policyDTO.getCoverageAmount());
            policy.setPremiumAmount(policyDTO.getPremiumAmount());
            policy.setDescription(policyDTO.getDescription());
            return policyRepository.save(policy);
        } else {
            throw new RuntimeException("Policy not found!");
        }
    }

    // Admin can do this only; deleting a policy
    public void deletePolicy(Long policyId) {
        policyRepository.deleteById(policyId);
    }

    // Get policy details by ID
    public Policy getPolicyDetails(Long policyId) {
        return policyRepository.findById(policyId).orElseThrow(() -> new RuntimeException("Policy not found!"));
    }

    // Get all policies (for Admin)
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    // Get policies by user (if needed)
    // This is for user and admin; both can view the policy currently mapped to a particular userID
    // admin can see everyone's but user can see only his own.
    public List<Policy> getUserPolicies(Long userId) {
        // Logic for fetching user-specific policies (if required)
        return policyRepository.getAllPolciesByUserId(userId);  
        // Wrote a JPQL for this in the policyRepsitory.
    }

    //So when a user purchases a policy that policy is added to the list of policy for that user id
    //
//    public String purchasePolicy(Long userId, Long policyId) {
//    	
//    	/*So fo rthis first check if the User object for the usrId exists or not 
//    	 then also check foe whether policyId is exsting or not. Then add the policy object to the 
//    	 List<Policy> of the user objext*/
//    	User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
//
//       
//        Policy policy = policyRepository.findById(policyId)
//                .orElseThrow(() -> new RuntimeException("Policy not found with id: " + policyId));
//
//        //policy.setUser(user);
//
//        policyRepository.save(policy);
//
//        user.getPolicies().add(policy);
//
//        return "Policy purchased successfully for user " + user.getFirstName();
//        
//    }
}
