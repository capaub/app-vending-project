package org.capaub.mswebapp.controller.ajax;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.config.VendingConfig;
import org.capaub.mswebapp.service.SessionService;
import org.capaub.mswebapp.service.StockVendingSagaOrchestrator;
import org.capaub.mswebapp.service.VendingService;
import org.capaub.mswebapp.service.dto.BuildVendingDTO;
import org.capaub.mswebapp.service.dto.VendingMongoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/vendings/ajax")
public class VendingAjaxController {
    private final VendingService vendingService;
    private final SessionService sessionService;
    private final StockVendingSagaOrchestrator stockVendingSagaOrchestrator;

    @PostMapping("/create")
    public ModelAndView createVending(@RequestBody VendingMongoDTO vending) {
        Integer companyId = sessionService.getCompanyId();
        vending.setCompanyId(companyId);
        List<VendingMongoDTO> vendingListDTO = vendingService.createVending(vending);
        return new ModelAndView("fragments/_vendings", "vendings", vendingListDTO);
    }

    @GetMapping("/build/{vendingId}")
    public String buildVending(@PathVariable String vendingId, Model model) {
        BuildVendingDTO data = vendingService.getDataForBuildVending(vendingId);

        Map<Integer, String> numToAlpha = VendingConfig.NUM_TO_ALPHA;

        model.addAttribute("dataVendingBuild", data);
        model.addAttribute("NUM_TO_ALPHA", numToAlpha);

        return "fragments/vendingId";
    }

    @GetMapping("/build/{vendingId}/fromCustomer")
    public String buildVendingFromCustomer(@PathVariable String vendingId, Model model) {
        BuildVendingDTO data = vendingService.getDataForBuildVending(vendingId);

        Map<Integer, String> numToAlpha = VendingConfig.NUM_TO_ALPHA;

        model.addAttribute("dataVendingBuild", data);
        model.addAttribute("NUM_TO_ALPHA", numToAlpha);

        return "fragments/vendingByQrcode";
    }

    @PostMapping("/addBatchToVending/{location}/{vendingId}/{batchId}/{quantity}")
    public @ResponseBody void addBatchToVending(@PathVariable String location,
                                                @PathVariable String vendingId,
                                                @PathVariable Integer batchId,
                                                @PathVariable Integer quantity) {
        stockVendingSagaOrchestrator.executeStockVendingSaga(location, vendingId, batchId, quantity);
    }
}