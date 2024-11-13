package org.capaub.mswebapp.controller.fullview;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.ChartJSService;
import org.capaub.mswebapp.service.CustomerService;
import org.capaub.mswebapp.service.VendingService;
import org.capaub.mswebapp.service.dto.CustomerDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/chartJS")
public class ChartJSController {

    private final ChartJSService chartJSService;
    private final CustomerService customerService;
    private final VendingService vendingService;

    @GetMapping("/globalStockIn")
    public @ResponseBody ModelAndView globalStock(HttpSession session, Model model) {
        Integer companyId = (Integer) session.getAttribute("companyId");
        String chart = chartJSService.getChartStockIn(companyId);
        List<Map<String, Object>> customerInfoList = customerService.getAllCustomersByCompanyId(companyId).stream()
                .map(customer -> {

                    Map<String, Object> customerMap = new HashMap<>();
                    customerMap.put("id", customer.getId());
                    customerMap.put("name", customer.getCompanyName());

                    Map<String, String> vendingMap = vendingService.getVendingsByCustomer(customer.getId());

                    customerMap.put("vendings",  vendingMap != null ? vendingMap : new HashMap<>());
                    return customerMap;
                })
                .collect(Collectors.toList());

        model.addAttribute("customers", customerInfoList);
        model.addAttribute("fragmentPath", "fragments/chart");
        return new ModelAndView("index", "jsonData", chart);
    }

}