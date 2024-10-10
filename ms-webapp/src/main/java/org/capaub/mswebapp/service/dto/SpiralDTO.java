package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpiralDTO {
    private Integer id;
    private String spiralLocation;
    private Integer trayId;
    private Integer vendingId;
    private Integer spiralTypeId;
    private Date createdAt;
    private Date updatedAt;
}
