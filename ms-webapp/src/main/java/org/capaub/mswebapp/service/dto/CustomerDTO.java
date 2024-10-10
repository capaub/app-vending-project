package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;


@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Integer id;
    private String siret;
    private String companyName;
    private String email;
    private String phone;
    private String firstname;
    private String lastname;
    private Date createdAt;
    private Date updatedAt;
    private Integer addressId;
    private Integer companyId;
}
