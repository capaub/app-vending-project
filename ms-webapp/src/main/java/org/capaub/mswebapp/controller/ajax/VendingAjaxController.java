package org.capaub.mswebapp.controller.ajax;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.SessionService;
import org.capaub.mswebapp.service.VendingService;
import org.capaub.mswebapp.service.dto.VendingDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/vendings/ajax")
public class VendingAjaxController {
    private final VendingService vendingService;
    private final SessionService sessionService;

    @PostMapping("/create")
    public ModelAndView createVending(@RequestBody VendingDTO vending) {
        Integer companyId = sessionService.getCompanyId();
        vending.setCompanyId(companyId);
        List<VendingDTO> vendingListDTO = vendingService.createVending(vending);
        return new ModelAndView("fragments/_vendings", "vendings", vendingListDTO);
    }
}
