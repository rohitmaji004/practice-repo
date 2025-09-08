package com.insurance.general_insurance.user.service;


import com.insurance.general_insurance.user.dto.ChangePasswordRequest;
import com.insurance.general_insurance.user.dto.UserProfileDTO;
import com.insurance.general_insurance.user.dto.UserUpdateRequest;
import com.insurance.general_insurance.user.entity.User;
import com.insurance.general_insurance.user.dto.UserRegistrationRequest;

import java.util.List;

public interface UserService {

    UserProfileDTO getUserProfile(String userEmail) throws Exception;

    User registerUser(UserRegistrationRequest request) throws Exception;

    User updateUser(String userEmail, UserUpdateRequest request) throws Exception;

    void changePassword(String userEmail, ChangePasswordRequest request) throws Exception;

    void deleteUser(Long userId) throws Exception;

    List<User> getAllUsers();
}
