package org.capaub.msvending.service;

import lombok.AllArgsConstructor;
import org.capaub.msvending.entities.Spiral;
import org.capaub.msvending.entities.Tray;
import org.capaub.msvending.mapper.SpiralMapper;
import org.capaub.msvending.mapper.SpiralTypeMapper;
import org.capaub.msvending.mapper.TrayMapper;
import org.capaub.msvending.mapper.VendingMapper;
import org.capaub.msvending.repository.SpiralRepository;
import org.capaub.msvending.service.DTO.SpiralDTO;
import org.capaub.msvending.service.DTO.SpiralTypeDTO;
import org.capaub.msvending.service.DTO.TrayDTO;
import org.capaub.msvending.service.DTO.VendingDTO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpiralService {
    private final SpiralRepository spiralRepository;
    private final SpiralMapper spiralMapper;

    private final TrayMapper trayMapper;
    private final SpiralTypeMapper spiralTypeMapper;
    private final VendingMapper vendingMapper;

    private final SpiralTypeService spiralTypeService;
    private final TrayService trayService;
    private final VendingService vendingService;

    public SpiralDTO createSpiral(SpiralDTO spiralDTO) {
        VendingDTO vendingDTO = vendingService.getVendingById(spiralDTO.getVendingId());
        TrayDTO trayDTO = trayService.getTrayById(spiralDTO.getTrayId());
        SpiralTypeDTO spiralTypeDTO = spiralTypeService.getSpiralTypeById(spiralDTO.getSpiralTypeId());

        Spiral spiral = new Spiral();
        spiral.setSpiralType(spiralTypeMapper.toSpiralType(spiralTypeDTO));
        spiral.setSpiralLocation(spiralDTO.getSpiralLocation());
        spiral.setTray(trayMapper.toTray(trayDTO));
        spiral.setVending(vendingMapper.toVending(vendingDTO));

        Spiral spiralSaved = spiralRepository.save(spiral);

        return spiralMapper.toSpiralDTO(spiralSaved);
    }

    public SpiralDTO getSpiralById(Integer id) {
        Spiral spiral = spiralRepository.findById(id).get();
        return spiralMapper.toSpiralDTO(spiral);
    }
}
