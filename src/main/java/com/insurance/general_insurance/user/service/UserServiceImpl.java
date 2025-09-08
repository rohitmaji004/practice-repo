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
    @Transactional(readOnly = true) // Important: Keeps the session open
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
        dto.setRole(user.getRole().name());

        // This is where the magic happens. Because the method is transactional,
        // we can access the lazy collection here.
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
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(encodedPassword);


        System.out.println("DEBUG (Register): Encoder instance ID: " + System.identityHashCode(passwordEncoder));


        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setAddress(request.getAddress());

        // Set the default role for a new user
        newUser.setRole(Role.USER);

        return userRepository.save(newUser);
    }

    @Override
    public User updateUser( String userEmail ,UserUpdateRequest request) throws Exception{
        User existingUser = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new Exception("user not found"));

        if (request.getEmail() != null && !request.getEmail().equals(userEmail)) {
            Optional<User> userWithNewEmail = userRepository.findByEmail(request.getEmail());
            if (userWithNewEmail.isPresent()) {
                throw new Exception("Email is already in use by another account.");
            }
            existingUser.setEmail(request.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getAddress() != null) {
            existingUser.setAddress(request.getAddress());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void changePassword(String userEmail, ChangePasswordRequest request) throws Exception{
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()->new Exception("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new Exception("Incorrect old password.");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new Exception("New password cannot be the same as the old password.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) throws Exception{
        if (!userRepository.existsById(userId)) {
            throw new Exception("User with ID " + userId + " not found.");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}