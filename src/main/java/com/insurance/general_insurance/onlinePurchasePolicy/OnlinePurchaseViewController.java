package com.insurance.general_insurance.onlinePurchasePolicy;

import com.insurance.general_insurance.ProductCatalogue.CatalogueService;
import com.insurance.general_insurance.ProductCatalogue.Policy;
import com.insurance.general_insurance.user.dto.UserProfileDTO;
import com.insurance.general_insurance.user.service.UserService;
import com.insurance.general_insurance.vehicle.entity.Vehicle;
import com.insurance.general_insurance.vehicle.repository.VehicleRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/purchase")
public class OnlinePurchaseViewController {

    private final OnlinePurchaseService onlinePurchaseService;
    private final UserService userService;
    private final CatalogueService catalogueService;
    private final VehicleRepository vehicleRepository;


    public OnlinePurchaseViewController(OnlinePurchaseService onlinePurchaseService, UserService userService, CatalogueService catalogueService, VehicleRepository vehicleRepository) {
        this.onlinePurchaseService = onlinePurchaseService;
        this.userService = userService;
        this.catalogueService = catalogueService;
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/purchase-policy")
    public String purchasePolicyForm(@RequestParam Long policyId, @RequestParam Long vehicleId, Model model, Principal principal) {
        try {
            UserProfileDTO userProfile = userService.getUserProfile(principal.getName());
            Policy policy = catalogueService.getPolicyDetails(policyId);
            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);
            model.addAttribute("user", userProfile);
            model.addAttribute("policy", policy);
            model.addAttribute("vehicle", vehicle);
            model.addAttribute("purchaseRequest", new OnlinePurchaseRequest());
            return "online_policy_purchase";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }


    @PostMapping("/confirm")
    public String confirmPurchase(@AuthenticationPrincipal UserDetails userDetails,
                                  OnlinePurchaseRequest purchaseRequest,
                                  RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            onlinePurchaseService.purchasePolicy(
                    userDetails.getUsername(),
                    purchaseRequest.getPolicyId(),
                    purchaseRequest.getVehicleId(),
                    purchaseRequest.getPaymentMethod()
            );
            redirectAttributes.addFlashAttribute("successMessage", "Policy purchased successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error purchasing policy: " + e.getMessage());
        }

        return "redirect:/dashboard";
    }
}