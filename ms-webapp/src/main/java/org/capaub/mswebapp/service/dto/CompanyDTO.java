package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {
    private Integer id;
    private String siret;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}