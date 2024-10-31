package org.capaub.mscustomer.service.DTO;

import org.springframework.stereotype.Component;

@Component
public class CompanyDTO {
    private Integer id;
    private String siret;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}