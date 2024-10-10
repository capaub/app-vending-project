package org.capaub.msvending.controller;

import lombok.AllArgsConstructor;
import org.capaub.msvending.entities.SpiralType;
import org.capaub.msvending.service.DTO.SpiralTypeDTO;
import org.capaub.msvending.service.SpiralTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/spiralTypes")
public class SpiralTypeController {
    private final SpiralTypeService spiralTypeService;

    @PostMapping("/create")
    public ResponseEntity<SpiralTypeDTO> createSpiralType(@RequestBody SpiralTypeDTO spiralTypeDTO){
        SpiralTypeDTO spiralTypeDTOSaved = spiralTypeService.createSpiralType(spiralTypeDTO);
        return new ResponseEntity<>(spiralTypeDTOSaved, HttpStatus.CREATED);
    }

    @GetMapping("/getSpiralTypeById/{id}")
    public ResponseEntity<SpiralTypeDTO> findSpiralTypeById(@PathVariable Integer id) {
        SpiralTypeDTO spiralTypeDTO = spiralTypeService.getSpiralTypeById(id);
        return new ResponseEntity<>(spiralTypeDTO,HttpStatus.OK);
    }
}
