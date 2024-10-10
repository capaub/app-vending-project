package org.capaub.mscustomer.service.DTO;

import lombok.*;

import java.util.Date;

@Data
public class CustomerWithAddressToSaveDTO {
    private String siret;
    private String companyName;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String country;
    private Date createdAt;
    private Date updatedAt;
    private Integer companyId;
}
