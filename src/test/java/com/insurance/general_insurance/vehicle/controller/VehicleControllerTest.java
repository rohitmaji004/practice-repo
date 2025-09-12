package com.insurance.general_insurance.vehicle.controller;

import com.insurance.general_insurance.vehicle.dto.UpdateVehicleAdminRequest;
import com.insurance.general_insurance.vehicle.dto.VehicleRegistrationRequest;
import com.insurance.general_insurance.vehicle.entity.Vehicle;
import com.insurance.general_insurance.vehicle.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class VehicleControllerTest {
    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController controller;

    private UserDetails adminUser;
    private UserDetails normalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminUser = User.withUsername("admin@gmail.com").password("password").roles("ADMIN").build();
        normalUser = User.withUsername("user@gmail.com").password("password").roles("USER").build();
    }

    @Test
    void getMyVehicles_success() throws Exception {
        when(vehicleService.getVehiclesForUser(anyString())).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = controller.getMyVehicles(adminUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    void getMyVehicles_unauthenticated() {
        ResponseEntity<?> response = controller.getMyVehicles(null);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void registerVehicle_success() throws Exception {
        VehicleRegistrationRequest req = new VehicleRegistrationRequest();
        when(vehicleService.registerVehicle(anyString(), any())).thenReturn(new Vehicle());
        ResponseEntity<?> response = controller.registerVehicle(adminUser, req);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void registerVehicle_unauthenticated() {
        VehicleRegistrationRequest req = new VehicleRegistrationRequest();
        ResponseEntity<?> response = controller.registerVehicle(null, req);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void updateVehicleByRegistration_admin_success() throws Exception {
        UpdateVehicleAdminRequest req = new UpdateVehicleAdminRequest();
        when(vehicleService.updateVehicleByRegistrationNumber(anyString(), anyString(), anyLong())).thenReturn(new Vehicle());
        ResponseEntity<?> response = controller.updateVehicleByRegistration(adminUser, "ABC123", req);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateVehicleByRegistration_notAdmin_forbidden() {
        UpdateVehicleAdminRequest req = new UpdateVehicleAdminRequest();
        ResponseEntity<?> response = controller.updateVehicleByRegistration(normalUser, "ABC123", req);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteVehicleByRegistration_admin_success() throws Exception {
        doNothing().when(vehicleService).deleteVehicleByRegistrationNumber(anyString());
        ResponseEntity<?> response = controller.deleteVehicleByRegistration(adminUser, "ABC123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteVehicleByRegistration_notAdmin_forbidden() {
        ResponseEntity<?> response = controller.deleteVehicleByRegistration(normalUser, "ABC123");
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void getAllVehicles_admin_success() {
        when(vehicleService.getAllVehicles()).thenReturn(List.of());
        ResponseEntity<?> response = controller.getAllVehicles(adminUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllVehicles_notAdmin_forbidden() {
        ResponseEntity<?> response = controller.getAllVehicles(normalUser);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
