package org.capaub.msvending.service;

import lombok.AllArgsConstructor;
import org.capaub.msvending.entities.Tray;
import org.capaub.msvending.mapper.TrayMapper;
import org.capaub.msvending.repository.TrayRepository;
import org.capaub.msvending.service.DTO.TrayDTO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrayService {
    private final TrayRepository trayRepository;
    private final TrayMapper trayMapper;

    public TrayDTO createTray(TrayDTO trayDTO) {
        Tray traySaved = trayRepository.save(trayMapper.toTray(trayDTO));
        return trayMapper.toTrayDTO(traySaved);
    }

    public TrayDTO getTrayById(Integer id) {
        Tray tray = trayRepository.findById(id).get();
        return trayMapper.toTrayDTO(tray);
    }

}
