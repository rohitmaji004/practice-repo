package com.insurance.general_insurance.user.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    private String email;
    private String phoneNumber;
    private String address;
}
