package org.capaub.mswebapp.controller.ajax;

import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.ChartJSService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("/chartJS/ajax")
public class ChartJSAjaxController {

    private final ChartJSService chartJSService;

    @GetMapping("/globalStock/{companyId}")
    public @ResponseBody ModelAndView globalStock(@PathVariable Integer companyId, Model model) {
        String chart = chartJSService.getChartStockIn(companyId);
        model.addAttribute("fragmentPath", "fragments/chartStock");
        return new ModelAndView("index", "jsonData", chart);
    }

    @GetMapping("/stockOut/{companyId}")
    public @ResponseBody ModelAndView stockOut(@PathVariable Integer companyId, Model model) {
        String chart = chartJSService.getChartStockOut(companyId);
        model.addAttribute("fragmentPath", "fragments/chartStock");
        return new ModelAndView("index", "jsonData", chart);
    }

}