package org.capaub.mswebapp.service.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VendingLocationDTO {
    private Integer id;
    private String location;
    private Integer vendingId;
    private Date createdAt;
    private Date updatedAt;
}
