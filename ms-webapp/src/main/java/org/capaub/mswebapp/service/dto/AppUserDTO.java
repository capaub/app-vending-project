package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<String> authorities;
    private Integer addressId;
    private Integer companyId;
    private Date createdAt;
    private Date updatedAt;
    private Date connectedAt;
}