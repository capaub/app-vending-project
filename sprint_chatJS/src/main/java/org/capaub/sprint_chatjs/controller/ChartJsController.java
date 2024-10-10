package org.capaub.sprint_chatjs.controller;

import org.capaub.sprint_chatjs.entity.Vending;
import org.capaub.sprint_chatjs.repository.VendingRepository;
import org.capaub.sprint_chatjs.repository.VendingStockRepository;
import org.capaub.sprint_chatjs.service.ChartJsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ChartJsController {

    private final VendingRepository vendingRepository;
    private final VendingStockRepository vendingStockRepository;
    private final ChartJsService chartJsService;

    public ChartJsController(VendingRepository vendingRepository, VendingStockRepository vendingStockRepository, ChartJsService chartJsService) {
        this.vendingRepository = vendingRepository;
        this.vendingStockRepository = vendingStockRepository;
        this.chartJsService = chartJsService;
    }

    @GetMapping("/")
    public ModelAndView vending() {

        Vending vending = new Vending();
        Vending vendingrep = vendingRepository.findById(1).get();
        vending.setBrand(vendingrep.getBrand());
        vending.setModel(vendingrep.getModel());
        vending.setId(vendingrep.getId());
        return new ModelAndView("dashboard", "vending", vending);
    }

    @GetMapping("/vendingList")
    public ModelAndView vendingList() {
        List<Vending> vendingList = vendingRepository.findAll();
        return new ModelAndView("dashboard","vending",vendingList);
    }

    @GetMapping("/vendings")
    public List<Vending> vendings() {

        return vendingRepository.findAll();
    }

    @GetMapping("/vendingStock/{id}")
    public ModelAndView getVendingStock(@PathVariable int id) {
        String jsonData = chartJsService.convertListMapToJson(vendingStockRepository.getAllVendingStock(id));
        return new ModelAndView("vendingStock", "jsonData", jsonData);
    }
}