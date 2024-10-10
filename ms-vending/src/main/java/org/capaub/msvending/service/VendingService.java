package org.capaub.msvending.service;

import lombok.AllArgsConstructor;
import org.capaub.msvending.entities.Vending;
import org.capaub.msvending.mapper.VendingMapper;
import org.capaub.msvending.repository.VendingRepository;
import org.capaub.msvending.service.DTO.VendingDTO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VendingService {
    private final VendingRepository vendingRepository;
    private final VendingMapper vendingMapper;

    public VendingDTO createVending(VendingDTO vendingDTO) {
        Vending vendingSaved = vendingRepository.save(vendingMapper.toVending(vendingDTO));
        return vendingMapper.toVendingDTO(vendingSaved);
    }

    public VendingDTO getVendingById(Integer id) {
        Vending vending = vendingRepository.findById(id).orElse(null);
        return vendingMapper.toVendingDTO(vending);
    }

}
