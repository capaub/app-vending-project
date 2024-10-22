package org.capaub.mswebapp.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VendingDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("model")
    private String model;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nbMaxTray")
    private Integer nbMaxTray;
    @JsonProperty("nbMaxSpiral")
    private Integer nbMaxSpiral;
    @JsonProperty("qrCode")
    private String qrCode;
    @JsonProperty("companyId")
    private Integer companyId;
    @JsonProperty("updatedAt")
    private Date updatedAt;
    @JsonProperty("createdAt")
    private Date createdAt;
}
