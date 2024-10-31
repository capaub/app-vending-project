package org.capaub.sprintchartjs.controller;

import lombok.AllArgsConstructor;
import org.capaub.sprintchartjs.repository.ChartDataRepository;
import org.capaub.sprintchartjs.service.ChartJsService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/data")
public class ChartJsController {

    private final ChartDataRepository chartDataRepository;
    private final ChartJsService chartJsService;

    @GetMapping("/generalStock/{companyId}")
    public String getStockIn(@PathVariable Integer companyId) {
        return chartJsService.getDataStockIn(companyId);
    }

    @GetMapping("/stockOut/{companyId}")
    public String getStockOut(@PathVariable Integer companyId) {
        return chartJsService.getDataStockIn(companyId);
    }
}