package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Data
public class CompanyDTO {
    private Integer id;
    private String siret;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}