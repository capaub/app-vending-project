package org.capaub.mscustomer.service.DTO;

import lombok.*;

@Data
public class AddressDTO {
    private Integer id;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String country;
    private Integer companyId;
}
