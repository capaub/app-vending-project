package org.capaub.msvending.controller;

import lombok.AllArgsConstructor;
import org.capaub.msvending.service.DTO.VendingDTO;
import org.capaub.msvending.service.VendingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vendings")
public class VendingController {
    private final VendingService vendingService;

    @PostMapping("/create")
    public ResponseEntity<VendingDTO> createVending(@RequestBody VendingDTO vendingDTO) {
        VendingDTO createdVending = vendingService.createVending(vendingDTO);
        return new ResponseEntity<>(createdVending, HttpStatus.CREATED);
    }

    @GetMapping("/getVendingById/{id}")
    public ResponseEntity<VendingDTO> getVendingById(@PathVariable Integer id) {
        VendingDTO vendingDTO = vendingService.getVendingById(id);
        return new ResponseEntity<>(vendingDTO, HttpStatus.OK);
    }
}
