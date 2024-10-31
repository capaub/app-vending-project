package org.capaub.mswebapp.service.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
public class CustomerDataVendingsDTO {
    @JsonProperty("vending")
    private Map<String, Map<String, VendingMongoDTO>> vendingData = new HashMap<>();

    @JsonProperty("status")
    private Map<String, String> status = new HashMap<>();

    @JsonProperty("customersStatus")
    private Map<String, String> customersStatus = new HashMap<>();

    @JsonAnySetter
    public void addVendingData(String key, Map<String, VendingMongoDTO> value) {
        if (!value.isEmpty()) {
            this.vendingData.put(key, value);
        }
        this.vendingData.put(key, new HashMap<>());

    }
}