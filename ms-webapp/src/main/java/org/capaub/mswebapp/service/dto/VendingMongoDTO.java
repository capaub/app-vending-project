package org.capaub.mswebapp.service.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VendingMongoDTO {
    private String vendingId;
    private String brand;
    private String model;
    private String name;
    private Integer nbMaxTray;
    private Integer nbMaxSpiral;
    private Integer companyId;
    private Date createdAt;
    private Date updatedAt;
    private List<LocationDTO> locations;

    @Data
    public static class LocationDTO {
        private String tray;
        private List<SpiralDTO> spirals;
    }

    @Data
    public static class SpiralDTO {
        private String spiral;
        private Integer quantity;
        private Integer batchId;
    }
}