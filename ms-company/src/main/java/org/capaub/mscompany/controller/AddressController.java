package org.capaub.mscompany.controller;

import org.capaub.mscompany.service.AddressService;
import org.capaub.mscompany.service.dto.AddressDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        AddressDTO savedAddress = addressService.createAddress(addressDTO);
        return ResponseEntity.ok(savedAddress);
    }

    @GetMapping("/getAddressById/{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Integer id) {
        AddressDTO address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/getAllAddressesByCompanyId/{companyId}")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(@PathVariable Integer companyId) {
        List<AddressDTO> Addresses = addressService.getAllAddressesByCompanyId(companyId);
        return ResponseEntity.ok(Addresses);
    }

    // autres endpoints...
}