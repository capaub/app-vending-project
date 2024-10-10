package org.capaub.msvending.service.DTO;

import lombok.*;

@Data
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {
    private Integer id;
    private String siret;
    private String name;
}
