package com.insurance.general_insurance.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * A Data Transfer Object (DTO) for carrying user registration data.
 * This is the object that the API controller will receive from the client.
 */


@Getter
@Setter
public class UserRegistrationRequest {
    private Long userId; // Added userId field
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}
