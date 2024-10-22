package org.capaub.mswebapp.controller.fullview;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.SessionService;
import org.capaub.mswebapp.service.VendingService;
import org.capaub.mswebapp.service.dto.VendingDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/vendings")
public class VendingController {

    private final SessionService sessionService;
    private final VendingService vendingService;

    @GetMapping("/getAllVending")
    public ModelAndView getAllVending(Model model) {
        Integer companyId = sessionService.getCompanyId();
        List<VendingDTO> allVending = vendingService.getAllVending(companyId);

        model.addAttribute("fragmentPath","fragments/vendings");

        return new ModelAndView("index","vendings", allVending);
    }

    @GetMapping("/create")
    public ModelAndView createVending() {
        return new ModelAndView("fragments/vendings", "newCustomer", new VendingDTO());
    }
}
