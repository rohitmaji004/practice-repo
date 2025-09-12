package com.insurance.general_insurance.vehicle.controller;

import com.insurance.general_insurance.vehicle.dto.UpdateVehicleAdminRequest;
import com.insurance.general_insurance.vehicle.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleDashboardControllerTest {
    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleDashboardController controller;

    private UserDetails adminUser;
    private UserDetails normalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminUser = User.withUsername("admin@gmail.com").password("password").roles("ADMIN").build();
        normalUser = User.withUsername("user@gmail.com").password("password").roles("USER").build();
    }

    @Test
    void updateVehicleByRegistrationForm_admin_success() throws Exception {
        UpdateVehicleAdminRequest req = new UpdateVehicleAdminRequest();
        req.setRegistrationNumber("ABC123");
        req.setOwnerName("New Owner");
        req.setOwnerId(2L);
        // No need to mock for void method, just let it pass
        String result = controller.updateVehicleByRegistrationForm(adminUser, req);
        assertEquals("redirect:/dashboard?success=vehicle_updated", result);
    }

    @Test
    void updateVehicleByRegistrationForm_notAdmin_forbidden() {
        UpdateVehicleAdminRequest req = new UpdateVehicleAdminRequest();
        String result = controller.updateVehicleByRegistrationForm(normalUser, req);
        assertEquals("redirect:/dashboard?error=Only admin can update vehicles", result);
    }

    @Test
    void updateVehicleByRegistrationForm_admin_exception() throws Exception {
        UpdateVehicleAdminRequest req = new UpdateVehicleAdminRequest();
        req.setRegistrationNumber("ABC123");
        req.setOwnerName("New Owner");
        req.setOwnerId(2L);
        doThrow(new RuntimeException("Vehicle not found")).when(vehicleService).updateVehicleByRegistrationNumber(anyString(), anyString(), anyLong());
        String result = controller.updateVehicleByRegistrationForm(adminUser, req);
        assertEquals("redirect:/dashboard?error=Vehicle not found", result);
    }

    @Test
    void deleteVehicleByRegistrationForm_admin_success() throws Exception {
        UpdateVehicleAdminRequest req = new UpdateVehicleAdminRequest();
        req.setRegistrationNumber("ABC123");
        doNothing().when(vehicleService).deleteVehicleByRegistrationNumber(anyString());
        String result = controller.deleteVehicleByRegistrationForm(adminUser, req);
        assertEquals("redirect:/dashboard?success=vehicle_deleted", result);
    }

    @Test
    void deleteVehicleByRegistrationForm_notAdmin_forbidden() {
        UpdateVehicleAdminRequest req = new UpdateVehicleAdminRequest();
        String result = controller.deleteVehicleByRegistrationForm(normalUser, req);
        assertEquals("redirect:/dashboard?error=Only admin can delete vehicles", result);
    }

    @Test
    void deleteVehicleByRegistrationForm_admin_exception() throws Exception {
        UpdateVehicleAdminRequest req = new UpdateVehicleAdminRequest();
        req.setRegistrationNumber("ABC123");
        doThrow(new RuntimeException("Vehicle not found")).when(vehicleService).deleteVehicleByRegistrationNumber(anyString());
        String result = controller.deleteVehicleByRegistrationForm(adminUser, req);
        assertEquals("redirect:/dashboard?error=Vehicle not found", result);
    }
}
