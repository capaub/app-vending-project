package org.capaub.mswebapp.controller.fullview;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.ImageServerService;
import org.capaub.mswebapp.service.SessionService;
import org.capaub.mswebapp.service.client.ProductClient;
import org.capaub.mswebapp.service.dto.BatchDTO;
import org.capaub.mswebapp.service.dto.GoodsDTO;
import org.capaub.mswebapp.service.dto.GoodsWithBatchesInfoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductClient productClient;
    private final ImageServerService imageServerService;
    private final SessionService sessionService;

    @GetMapping("/stock")
    public ModelAndView getProductsForCompany(
            @RequestParam(value = "fromCreateBatch", required = false) Boolean fromCreateBatch,
            Model model) {

        Integer companyId = sessionService.getCompanyId();

        Map<String, GoodsWithBatchesInfoDTO> products = productClient.getProductsForCompany(companyId);

        model.addAttribute("companyId", companyId);

        if (Boolean.TRUE.equals(fromCreateBatch)) {
            return new ModelAndView("fragments/stock", "products", products);
        }

        model.addAttribute("fragmentPath","fragments/stock");

        return new ModelAndView("index","products", products);
    }

    @PostMapping("/createBatch")
    public String createBatch(@RequestBody BatchDTO batchDTO) throws IOException {
        Integer companyId = sessionService.getCompanyId();
        productClient.createBatch(batchDTO, companyId);
        GoodsDTO goodsDTO = productClient.getGoods(batchDTO.getBarcode(), companyId);
        imageServerService.saveImage(goodsDTO);
        return "redirect:/products/stock?fromCreateBatch=true";
    }
}