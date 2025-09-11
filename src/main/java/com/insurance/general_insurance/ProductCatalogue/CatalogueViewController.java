package com.insurance.general_insurance.ProductCatalogue;
import com.insurance.general_insurance.user.*;
import com.insurance.general_insurance.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/catalogue/view")
public class CatalogueViewController {

	@Autowired
	private CatalogueService catalogueService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/list") // This is working
	 public String showAllPolicies(Model model) {
       List<Policy> policies = catalogueService.getAllPolicies();
       long policyCount = policyService.getPolicyCount();  
       long customerCount = userService.getCustomerCount();
       model.addAttribute("policyCount",policyCount);
       model.addAttribute("customerCount",customerCount);
       model.addAttribute("policies", policies); // Add list to model
       return "catalogue/adminDashboard"; // Thymeleaf template name
   }
	
	
	
	 @GetMapping("/add") // This fetches the add policy form.. WORKING
	    public String showAddForm(Model model) {
	        model.addAttribute("policy", new Policy()); // empty form object
	        return "catalogue/add-policy"; // Thymeleaf template name (add.html)
	    }
	
	
	@PostMapping("/add") // WORKING
	public String addPolicy(@ModelAttribute Policy policy, RedirectAttributes redirectAttributes) {
	    Policy newPolicy = catalogueService.addPolicy(policy);

	    
	    redirectAttributes.addFlashAttribute("successMessage",
	        "Policy '" + newPolicy.getPolicyName() + "' added successfully!");

	    // redirect back to the list page
	    return "redirect:/catalogue/view/list";
	}
 
	@GetMapping("/remove/{policyId}") // WORKING only for the ones that dont have any assigned user_id or vehicle_id
	public String removePolicy(@PathVariable Long policyId, RedirectAttributes redirectAttributes) {
	    catalogueService.removePolicy(policyId);

	    redirectAttributes.addFlashAttribute("successMessage", "Policy removed successfully!");
	    return "redirect:/catalogue/view/list";
	}
    
	// EDITING A POLICY
	@GetMapping("/edit/{policyId}")
	public String showEditForm(@PathVariable Long policyId, Model model) {
	    Policy policy = policyService.getPolicyDetails(policyId);
	    model.addAttribute("policy", policy);
	    return "catalogue/edit-policy"; 
	}
	@PostMapping("/edit/{policyId}")
	public String updatePolicy(@PathVariable Long policyId,@ModelAttribute("policy") Policy policy) {
	    policyService.updatePolicy(policyId,policy);
	    return "redirect:/catalogue/view/list"; // back to list after update
	}

	
	// View a policy
	@GetMapping("/fetch/{policyId}")
	public String viewPolicyDetails(@PathVariable Long policyId, Model model) {
	    Policy policy = catalogueService.getPolicyDetails(policyId);
	    model.addAttribute("policy", policy);
	    return "catalogue/view-policy"; // this should match the Thymeleaf template name
	}

	// Filtering
	@GetMapping("/filter") 
	public String filterPolicies(Model model)
	{
		model.addAttribute("id", null);
	    model.addAttribute("policyName", null);
	    model.addAttribute("policyType", null);
	    model.addAttribute("minCoverage", null);
	    model.addAttribute("maxCoverage", null);
	    model.addAttribute("minPremium", null);
	    model.addAttribute("maxPremium", null);
	    model.addAttribute("startDate", null);

	    // wmpty list initially before filtering
	    model.addAttribute("policies", List.of());

	    return "catalogue/filter-policy";
	}
	@GetMapping("/filter/search")
	public String filterPolicies(
	        @RequestParam(required = false) String policyType,
	        @RequestParam(required = false) Double minCoverage,
	        @RequestParam(required = false) Double maxCoverage,
	        @RequestParam(required = false) Double minPremium,
	        @RequestParam(required = false) Double maxPremium,
	        @RequestParam(required = false) Long id,
	        @RequestParam(required = false) String policyName,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	        Model model
	) {
	    List<Policy> policies = catalogueService.filterPolicies(policyType, minCoverage, maxCoverage, minPremium, maxPremium, id, policyName, startDate      
	    );

	  
	    model.addAttribute("id", id);
	    model.addAttribute("policyName", policyName);
	    model.addAttribute("policyType", policyType);
	    model.addAttribute("minCoverage", minCoverage);
	    model.addAttribute("maxCoverage", maxCoverage);
	    model.addAttribute("minPremium", minPremium);
	    model.addAttribute("maxPremium", maxPremium);
	    model.addAttribute("startDate", startDate);

	    // Add results
	    model.addAttribute("policies", policies);

	    return "catalogue/filter-policy"; 
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 @GetMapping("/{policyId}") // working
	    public String getPolicyDetails(@PathVariable Long policyId, Model model) {
	        Policy policy = catalogueService.getPolicyDetails(policyId);
	        model.addAttribute(policy);
	        return "adminDashboard";
	    } 
	 
	 
	 
	
}
