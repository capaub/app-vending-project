package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpiralTypeDTO {
    private Integer id;
    private String label;
    private Integer maxCap;
    private Date createdAt;
    private Date updatedAt;
}
