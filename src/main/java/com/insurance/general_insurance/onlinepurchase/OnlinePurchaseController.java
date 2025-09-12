package com.insurance.general_insurance.onlinepurchase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchases")
public class OnlinePurchaseController {

    private final OnlinePurchaseService onlinePurchaseService;

    public OnlinePurchaseController(OnlinePurchaseService onlinePurchaseService) {
        this.onlinePurchaseService = onlinePurchaseService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchasePolicy(@RequestBody OnlinePurchaseRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        try {
            OnlinePurchase newPurchase = onlinePurchaseService.purchasePolicy(userDetails.getUsername(), request);
            return new ResponseEntity<>(newPurchase, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}