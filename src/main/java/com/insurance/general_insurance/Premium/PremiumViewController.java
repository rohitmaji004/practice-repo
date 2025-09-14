package com.insurance.general_insurance.Premium;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/premiums")
public class PremiumViewController {

    private final PremiumService premiumService;

    public PremiumViewController(PremiumService premiumService) {
        this.premiumService = premiumService;
    }

    @GetMapping("/pay/{premiumId}")
    public String payPremium(@PathVariable Long premiumId, RedirectAttributes redirectAttributes) {
        try {
            // In a real app, this would redirect to a payment gateway.
            // Here, we simulate a successful payment immediately.
            premiumService.makePayment(premiumId, "ONLINE");
            redirectAttributes.addFlashAttribute("successMessage", "Payment successful! Your next premium has been scheduled.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Payment failed: " + e.getMessage());
        }
        return "redirect:/my-premiums";
    }

    // --- Admin Facing Methods ---

    @GetMapping("/dashboard")
    public String showPremiumDashboard(Model model) {
        List<Premium> premiums = premiumService.getAllPremiums();
        model.addAttribute("premiums", premiums);
        return "Premium/premium_dashboard"; // Corrected path
    }

    @GetMapping("/add")
    public String showAddPremiumForm(Model model) {
        model.addAttribute("premium", new Premium());
        return "Premium/add_premium"; // Corrected path
    }

    @PostMapping("/add")
    public String addPremium(@ModelAttribute Premium premium, RedirectAttributes redirectAttributes) {
        premiumService.createPremium(premium);
        redirectAttributes.addFlashAttribute("successMessage", "Premium added successfully!");
        return "redirect:/premiums/dashboard";
    }
}

