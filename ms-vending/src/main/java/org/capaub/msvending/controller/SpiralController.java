package org.capaub.msvending.controller;

import lombok.AllArgsConstructor;
import org.capaub.msvending.entities.Spiral;
import org.capaub.msvending.mapper.SpiralMapper;
import org.capaub.msvending.service.DTO.SpiralDTO;
import org.capaub.msvending.service.SpiralService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/spirals")
public class SpiralController {
    private final SpiralService spiralService;
    private final SpiralMapper spiralMapper;

    @PostMapping("/create")
    public ResponseEntity<SpiralDTO> createSpiral(@RequestBody SpiralDTO spiralDTO) {
        SpiralDTO spiralSaved = spiralService.createSpiral(spiralDTO);
        return new ResponseEntity<>(spiralSaved, HttpStatus.CREATED);
    }

    @GetMapping("/getSpiralById/{id}")
    public ResponseEntity<SpiralDTO> getSpiralById(@PathVariable Integer id) {
        SpiralDTO spiralDTO = spiralService.getSpiralById(id);
        return new ResponseEntity<>(spiralDTO,HttpStatus.OK);
    }
}
