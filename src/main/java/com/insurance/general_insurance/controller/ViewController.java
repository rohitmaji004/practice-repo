package com.insurance.general_insurance.controller;

import com.insurance.general_insurance.user.dto.UserProfileDTO;
import com.insurance.general_insurance.user.dto.UserRegistrationRequest;
import com.insurance.general_insurance.user.service.UserService;
import com.insurance.general_insurance.vehicle.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ViewController {

    private final UserService userService;
    private final VehicleService vehicleService;

    public ViewController(UserService userService, VehicleService vehicleService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
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
    public String registerUser(@ModelAttribute("user") UserRegistrationRequest request, RedirectAttributes redirectAttributes) {

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
            if (userProfile.getRole() != null && userProfile.getRole().equalsIgnoreCase("ADMIN")) {
                model.addAttribute("allVehicles", vehicleService.getAllVehicles());
            }
            return "dashboard";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
}
























//package com.insurance.general_insurance.controller;
//
//import com.insurance.general_insurance.user.dto.UserRegistrationRequest;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class ViewController {
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new UserRegistrationRequest());
//        return "register";
//    }
//
//    // Redirect the root URL to the login page
//    @GetMapping("/")
//    public String index() {
//        return "redirect:/login";
//    }
//}
