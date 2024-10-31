package org.capaub.mswebapp.controller.ajax;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.ProductService;
import org.capaub.mswebapp.service.SessionService;
import org.capaub.mswebapp.service.dto.DataForAddBatchDTO;
import org.capaub.mswebapp.service.dto.DataToChangeBatchDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/products/ajax")
public class ProductAjaxController {
    private final ProductService productService;
    private final SessionService sessionService;

    @GetMapping("/forAddBatch/{vendingId}/{location}/{vendingTags}")
    public ModelAndView addBatch(Model model,
                                 @PathVariable String vendingId,
                                 @PathVariable String location,
                                 @PathVariable String vendingTags) {

        Integer companyId = sessionService.getCompanyId();

        List<DataForAddBatchDTO> allBatch = productService.getAllBatchAddingToVending(companyId);

        model.addAttribute("batches", allBatch);
        model.addAttribute("vendingId", vendingId);
        model.addAttribute("location", location);
        model.addAttribute("vendingTags", vendingTags);

        return new ModelAndView("fragments/_addBatchesToVending");
    }

    @GetMapping("/refreshBatch/{batchId}")
    public  @ResponseBody DataToChangeBatchDTO refreshBatch(@PathVariable Integer batchId) {
        return productService.getBatchToChangeInfo(batchId);
    }
}