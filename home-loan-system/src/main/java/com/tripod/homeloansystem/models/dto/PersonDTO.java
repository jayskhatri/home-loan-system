package com.tripod.homeloansystem.models.dto;

import java.util.List;

import lombok.Data;

@Data
public class PersonDTO {
    private Long personId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String dateOfBirth;
    private Boolean isAdmin;
    private List<Long> loanApplications;
}
