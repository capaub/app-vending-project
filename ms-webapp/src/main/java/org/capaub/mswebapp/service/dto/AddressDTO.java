package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Integer id;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String country;
    private Integer companyId;
    private Date createdAt;
    private Date updatedAt;
}