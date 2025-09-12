package com.insurance.general_insurance.Premium;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/premiums")
public class PremiumController {

    private final PremiumService premiumService;

    public PremiumController(PremiumService premiumService) {
        this.premiumService = premiumService;
    }

    @PostMapping("/add")
    public ResponseEntity<Premium> addPremium(@RequestBody Premium premium) {
        try {
            Premium savedPremium = premiumService.createPremium(premium);
            return ResponseEntity.ok(savedPremium);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Premium>> getAllPremiums() {
        List<Premium> premiums = premiumService.getAllPremiums();
        return ResponseEntity.ok(premiums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Premium> getPremiumById(@PathVariable Long id) {
        try {
            Premium premium = premiumService.getPremiumById(id);
            return ResponseEntity.ok(premium);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Premium> updatePremium(@PathVariable Long id, @RequestBody Premium premiumDetails) {
        try {
            Premium updatedPremium = premiumService.updatePremium(id, premiumDetails);
            return ResponseEntity.ok(updatedPremium);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePremium(@PathVariable Long id) {
        try {
            premiumService.deletePremium(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}