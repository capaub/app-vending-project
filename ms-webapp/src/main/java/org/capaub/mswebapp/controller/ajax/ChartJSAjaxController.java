package org.capaub.mswebapp.controller.ajax;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/stockIn")
    public @ResponseBody ModelAndView globalStock(HttpSession session, Model model) {
        Integer companyId = (Integer) session.getAttribute("companyId");
        String chart = chartJSService.getChartStockIn(companyId);
        return new ModelAndView("fragments/_chart", "jsonData", chart);
    }

    @GetMapping("/stockOut")
    public @ResponseBody ModelAndView stockOut(HttpSession session, Model model) {
        Integer companyId = (Integer) session.getAttribute("companyId");
        String chart = chartJSService.getChartStockOut(companyId);
        return new ModelAndView("fragments/_chart", "jsonData", chart);
    }


    @GetMapping("/vendingStock/{vendingId}")
    public @ResponseBody ModelAndView stockVending(@PathVariable String vendingId, Model model) {
        String chart = chartJSService.getDataVendingStock(vendingId);
        return new ModelAndView("fragments/_chart", "jsonData", chart);
    }

}