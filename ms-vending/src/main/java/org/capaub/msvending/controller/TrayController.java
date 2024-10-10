package org.capaub.msvending.controller;


import lombok.AllArgsConstructor;
import org.capaub.msvending.entities.Tray;
import org.capaub.msvending.service.DTO.TrayDTO;
import org.capaub.msvending.service.TrayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trays")
public class TrayController {
    private final TrayService trayService;

    @PostMapping("/create")
    public ResponseEntity<TrayDTO> createTray(@RequestBody TrayDTO trayDTO) {
        TrayDTO traySaved = trayService.createTray(trayDTO);
        return new ResponseEntity<>(traySaved, HttpStatus.CREATED);
    }

    @GetMapping("/getTrayById/{id}")
    public ResponseEntity<TrayDTO> getTrayById(@PathVariable Integer id) {
        TrayDTO trayDTO = trayService.getTrayById(id);
        return new ResponseEntity<>(trayDTO,HttpStatus.OK);
    }
}
