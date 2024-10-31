package org.capaub.mswebapp.service.dto;

import lombok.Data;

@Data
public class CompleteRegistrationDTO {
    private String siret;
    private String name;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String country;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}