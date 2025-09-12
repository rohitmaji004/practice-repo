package com.insurance.general_insurance.Premium;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * This controller handles web page requests for the Premium feature.
 * It's responsible for showing HTML pages to the user.
 */
@Controller
@RequestMapping("/premiums") // All URLs in this class will start with /premiums
public class PremiumViewController {

    private final PremiumService premiumService;

    // The PremiumService is automatically injected by Spring
    public PremiumViewController(PremiumService premiumService) {
        this.premiumService = premiumService;
    }

    /**
     * Handles GET requests to /premiums/dashboard.
     * Fetches all premiums and displays them on the dashboard page.
     */
    @GetMapping("/dashboard")
    public String showPremiumDashboard(Model model) {
        // 1. Get all premiums from the database via the service
        List<Premium> premiums = premiumService.getAllPremiums();
        // 2. Add the list of premiums to the model so the HTML can access it
        model.addAttribute("premiums", premiums);
        // 3. Return the path to the HTML template to display
        return "premium/premium_dashboard";
    }

    /**
     * Handles GET requests to /premiums/add.
     * Displays the form for adding a new premium.
     */
    @GetMapping("/add")
    public String showAddPremiumForm(Model model) {
        // Add an empty Premium object to the model to bind to the form
        model.addAttribute("premium", new Premium());
        // Return the path to the add_premium.html template
        return "premium/add_premium";
    }

    /**
     * Handles POST requests from the add premium form.
     * Saves the new premium and redirects back to the dashboard.
     */
    @PostMapping("/add")
    public String addPremium(@ModelAttribute Premium premium, RedirectAttributes redirectAttributes) {
        // 1. Spring automatically creates the 'premium' object from the form data
        // 2. Save the new premium object to the database
        premiumService.createPremium(premium);
        // 3. Add a success message that will be displayed on the dashboard after redirecting
        redirectAttributes.addFlashAttribute("successMessage", "Premium added successfully!");
        // 4. Redirect the user back to the dashboard page
        return "redirect:/premiums/dashboard";
    }
}