package org.capaub.mscompany.controller;

import lombok.AllArgsConstructor;
import org.capaub.mscompany.service.AppUserService;
import org.capaub.mscompany.service.dto.AppUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping("/create")
    public ResponseEntity<AppUserDTO> createUser(@RequestBody AppUserDTO appUserDTO) {
        AppUserDTO savedAppUser = appUserService.createUser(appUserDTO);
        return ResponseEntity.ok(savedAppUser);
    }
    @PutMapping("/{email}/update-connected-at")
    public ResponseEntity<?> updateConnectedAt(@PathVariable String email) {
        try {
            appUserService.updateConnectedAt(email);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<AppUserDTO> getUserById(@PathVariable Integer id) {
        AppUserDTO appUser = appUserService.getUserById(id);
        return ResponseEntity.ok(appUser);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<AppUserDTO> getUserByEmail(@PathVariable String email) {
        AppUserDTO appUser = appUserService.getUserByEmail(email);
        return ResponseEntity.ok(appUser);
    }

    @GetMapping("/getAllUsersByCompanyId/{companyId}")
    public ResponseEntity<List<AppUserDTO>> getAllUsersByCompanyId(@PathVariable Integer companyId) {
        List<AppUserDTO> appUser = appUserService.getAllUsersByCompanyId(companyId);
        return ResponseEntity.ok(appUser);
    }


    // autres endpoints...
}