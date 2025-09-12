package com.insurance.general_insurance.user.service;

import com.insurance.general_insurance.user.dto.ChangePasswordRequest;
import com.insurance.general_insurance.user.dto.UserProfileDTO;
import com.insurance.general_insurance.user.dto.UserUpdateRequest;
import com.insurance.general_insurance.user.entity.Role;
import com.insurance.general_insurance.user.entity.User;
import com.insurance.general_insurance.user.dto.UserRegistrationRequest;
import com.insurance.general_insurance.user.repository.UserRepository;
import com.insurance.general_insurance.vehicle.dto.VehicleDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile(String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));

        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole().name().replace("ROLE_", "")); // Remove ROLE_ prefix for display

        List<VehicleDTO> vehicleDTOs = user.getVehicles().stream().map(vehicle -> {
            VehicleDTO vDto = new VehicleDTO();
            vDto.setId(vehicle.getId());
            vDto.setRegistrationNumber(vehicle.getRegistrationNumber());
            vDto.setVehicleType(vehicle.getVehicleType());
            vDto.setOwnerName(vehicle.getOwnerName());
            return vDto;
        }).collect(Collectors.toList());

        dto.setVehicles(vehicleDTOs);

        return dto;
    }

    @Override
    public User registerUser(UserRegistrationRequest request) throws Exception {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new Exception("User with email " + request.getEmail() + " already exists.");
        }

        User newUser = new User();
        if (request.getUserId() != null) {
            newUser.setId(request.getUserId());
        }
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setAddress(request.getAddress());
        newUser.setRole(Role.ROLE_USER); // Use ROLE_USER
        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(String userEmail, UserUpdateRequest request) throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void changePassword(String userEmail, ChangePasswordRequest request) throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteUser(Long userId) throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    public Long getCustomerCount() {
    	return userRepository.customerCount();
    }

    @Override
    public List<User> getAllUsers() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User adminUpdateUser(Long userId, UserUpdateRequest request) throws Exception {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}