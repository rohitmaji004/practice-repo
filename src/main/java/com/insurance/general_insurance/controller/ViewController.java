package com.insurance.general_insurance.controller;

import com.insurance.general_insurance.Premium.Premium;
import com.insurance.general_insurance.Premium.PremiumService;
import com.insurance.general_insurance.ProductCatalogue.CatalogueService;
import com.insurance.general_insurance.ProductCatalogue.Policy;
import com.insurance.general_insurance.user.dto.UserProfileDTO;
import com.insurance.general_insurance.user.dto.UserRegistrationRequest;
import com.insurance.general_insurance.user.service.UserService;
import com.insurance.general_insurance.vehicle.entity.Vehicle;
import com.insurance.general_insurance.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ViewController {

    private final UserService userService;
    private final CatalogueService catalogueService;
    private final VehicleRepository vehicleRepository;
    private final PremiumService premiumService;

    public ViewController(UserService userService, CatalogueService catalogueService, VehicleRepository vehicleRepository, PremiumService premiumService) {
        this.userService = userService;
        this.catalogueService = catalogueService;
        this.vehicleRepository = vehicleRepository;
        this.premiumService = premiumService;
    }



    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationRequest request,
                               RedirectAttributes redirectAttributes) {

        System.out.println("Received registration request for email: " + request.getEmail());

        try {
            userService.registerUser(request);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        try {
            UserProfileDTO userProfile = userService.getUserProfile(principal.getName());
            model.addAttribute("user", userProfile);
            return "dashboard";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        try {
            UserProfileDTO userProfile = userService.getUserProfile(principal.getName());
            model.addAttribute("user", userProfile);
            return "profile";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/settings")
    public String showSettings(Model model, Principal principal) {
        try {
            UserProfileDTO userProfile = userService.getUserProfile(principal.getName());
            model.addAttribute("user", userProfile);
            return "settings";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }


    @GetMapping("/user/catalogue")
    public String userCatalogueView(Model model)
    {
        List<Policy> policies = catalogueService.getAllPolicies();
        model.addAttribute("policies", policies);
        return "user-catalogue-view";
    }

    @GetMapping("/user/my-premiums")
    public String showMyPremiums(Model model, Principal principal) {
        try {
            UserProfileDTO userProfile = userService.getUserProfile(principal.getName());
            List<Premium> premiums = premiumService.getPremiumsForUser(userProfile.getId());
            model.addAttribute("premiums", premiums);
            return "my_premiums";
        } catch (Exception e) {
            // Log the exception e.getMessage() for debugging
            return "redirect:/login?error";
        }
    }

    @GetMapping("/user/catalogue/{vehicleId}")
    public String userCatalogueView(@PathVariable Long vehicleId, Model model) {
        List<Policy> policies = catalogueService.getAllPolicies();
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);
        model.addAttribute("policies", policies);
        model.addAttribute("vehicle", vehicle);
        return "user-catalogue-view";
    }

    @GetMapping("/user/policyView/{policyId}")
    public String userPolicyView(@PathVariable Long policyId, Model model)
    {
        Policy policy = catalogueService.getPolicyDetails(policyId);
        model.addAttribute("policy", policy);
        return "user-policy-view";
    }

}

