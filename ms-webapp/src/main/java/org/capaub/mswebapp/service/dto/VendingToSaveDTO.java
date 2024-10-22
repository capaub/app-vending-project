package org.capaub.mswebapp.service.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VendingToSaveDTO {
    private String brand;
    private String model;
    private String name;
    private Integer nbMaxTray;
    private Integer nbMaxSpiral;
    private Integer companyId;
}
