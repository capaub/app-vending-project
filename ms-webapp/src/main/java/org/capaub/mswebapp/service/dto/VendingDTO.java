package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Data
public class VendingDTO {
    private Integer id;
    private String brand;
    private String model;
    private String name;
    private Integer nbMaxTray;
    private Integer nbMaxSpiral;
    private String qrCode;
    private Integer companyId;
    private Date updatedAt;
    private Date createdAt;
}