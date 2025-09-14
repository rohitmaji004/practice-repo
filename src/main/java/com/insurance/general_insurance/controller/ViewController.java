package com.insurance.general_insurance.controller;

import java.security.Principal;
import java.util.List;

import com.insurance.general_insurance.ProductCatalogue.CatalogueService;
import com.insurance.general_insurance.ProductCatalogue.Policy;
import com.insurance.general_insurance.vehicle.entity.Vehicle;
import com.insurance.general_insurance.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.insurance.general_insurance.user.dto.UserProfileDTO;
import com.insurance.general_insurance.user.dto.UserRegistrationRequest;
import com.insurance.general_insurance.user.service.UserService;

@Controller
public class ViewController {

    private final UserService userService;
    private final CatalogueService catalogueService;
    private final VehicleRepository vehicleRepository;

    public ViewController(UserService userService, CatalogueService catalogueService, VehicleRepository vehicleRepository) {
        this.userService = userService;
        this.catalogueService = catalogueService;
        this.vehicleRepository = vehicleRepository;
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

    // New endpoint for the online policy purchase form
//    @GetMapping("/purchase-policy")
//    public String purchasePolicyForm(@RequestParam Long policyId, @RequestParam Long vehicleId, Model model, Principal principal) {
//        try {
//            UserProfileDTO userProfile = userService.getUserProfile(principal.getName());
//            Policy policy = catalogueService.getPolicyDetails(policyId);
//            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);
//            model.addAttribute("user", userProfile);
//            model.addAttribute("policy", policy);
//            model.addAttribute("vehicle", vehicle);
//            return "online_policy_purchase";
//        } catch (Exception e) {
//            return "redirect:/login?error";
//        }
//    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }


    // Adding the catalogue view page for user
    @GetMapping("/user/catalogue")
    public String userCatalogueView(Model model)
    {
        List<Policy> policies = catalogueService.getAllPolicies();
        model.addAttribute("policies", policies);
        return "user-catalogue-view";
    }

    @GetMapping("/user/catalogue/{vehicleId}")
    public String userCatalogueView(@PathVariable Long vehicleId, Model model) {
        List<Policy> policies = catalogueService.getAllPolicies();
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);
        model.addAttribute("policies", policies);
        model.addAttribute("vehicle", vehicle);
        return "user-catalogue-view";
    }

    // Users to view individual policy.
    @GetMapping("/user/policyView/{policyId}")
    public String userPolicyView(@PathVariable Long policyId, Model model)
    {
        Policy policy = catalogueService.getPolicyDetails(policyId);
        model.addAttribute("policy", policy);
        return "user-policy-view";
    }

}