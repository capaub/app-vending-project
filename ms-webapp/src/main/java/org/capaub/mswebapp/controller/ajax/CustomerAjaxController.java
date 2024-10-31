package org.capaub.mswebapp.controller.ajax;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.CustomerService;
import org.capaub.mswebapp.service.SessionService;
import org.capaub.mswebapp.service.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/customers/ajax")
public class CustomerAjaxController {
    private final CustomerService customerService;
    private final SessionService sessionService;

    @GetMapping("/backToCustomers")
    public ModelAndView ShowCustomer(Model model) {
        Integer companyId = sessionService.getCompanyId();
        CustomerDataVendingsDTO vendingsDataForCustomer = customerService.getVendingsByCustomers(companyId);
        List<CustomerDTO> customers = customerService.getAllCustomersByCompanyId(companyId);

        model.addAttribute("customers", customers);
        model.addAttribute("vendings", vendingsDataForCustomer.getVendingData());
        model.addAttribute("status", vendingsDataForCustomer.getStatus());
        model.addAttribute("customersStatus", vendingsDataForCustomer.getCustomersStatus());

        return new ModelAndView("fragments/customers");
    }

    @PostMapping("/getAvailableVending")
    public ResponseEntity<Map<String, List<VendingMongoDTO>>> getAvailableVending(HttpSession session, Model model) {
        Integer companyId = (Integer) session.getAttribute("companyId");
        List<VendingMongoDTO> availableVending = customerService.getAvailableVending(companyId);
        Map<String, List<VendingMongoDTO>> response = new HashMap<>();
        response.put("availableVending", availableVending);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addVendingToCustomer/{vendingId}/{customerId}/{vendingName}")
    public String addVending(@PathVariable String vendingId,
                             @PathVariable Integer customerId,
                             @PathVariable String vendingName,
                             Model model) {

        List<CustomerDTO> customers = new ArrayList<>();
        customers.add(customerService.getCustomers(customerId));

        CustomerDataVendingsDTO customerDataVendingsDTO = customerService.addVending(vendingId, customerId, vendingName);

        model.addAttribute("customers", customers);
        model.addAttribute("vendings", customerDataVendingsDTO.getVendingData());
        model.addAttribute("status", customerDataVendingsDTO.getStatus());
        model.addAttribute("customersStatus", customerDataVendingsDTO.getCustomersStatus());

        return "fragments/_customers";
    }

    @GetMapping("/refreshCustomer/afterAddingVending/{customerId}")
    public String ajaxShowCustomers(@PathVariable Integer customerId, Model model) {
        CustomerDataVendingsDTO vendingsDataForCustomer = customerService.getVendingsByCustomer(customerId);
        List<CustomerDTO> customers = new ArrayList<>();
        customers.add(customerService.getCustomers(customerId));

        model.addAttribute("customers", customers);
        model.addAttribute("vendings", vendingsDataForCustomer.getVendingData());
        model.addAttribute("status", vendingsDataForCustomer.getStatus());
        model.addAttribute("customersStatus", vendingsDataForCustomer.getCustomersStatus());

        return "fragments/_customers";
    }
}