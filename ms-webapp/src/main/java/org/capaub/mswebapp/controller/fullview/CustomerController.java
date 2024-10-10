package org.capaub.mswebapp.controller.fullview;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.dto.CustomerDTO;
import org.capaub.mswebapp.service.CustomerService;
import org.capaub.mswebapp.service.dto.CustomerWithAddressToSaveDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/getAllCustomersByCompanyId")
    public ModelAndView showCustomers(HttpSession session, Model model) {
        Integer companyId = (Integer) session.getAttribute("companyId");
        List<CustomerDTO> customersDTO = customerService.getAllCustomersByCompanyId(companyId);

        model.addAttribute("fragmentPath","fragments/customers");
        return new ModelAndView("index","customers", customersDTO);
    }

    @GetMapping("/create")
    public ModelAndView createCustomer() {
        return new ModelAndView("fragments/_addCustomer", "newCustomer", new CustomerDTO());
    }
    @PostMapping("/create")
    public String createCustomer(@RequestBody CustomerWithAddressToSaveDTO customerDTOToSave, HttpSession session) {
        Integer companyId = (Integer) session.getAttribute("companyId");
        customerDTOToSave.setCompanyId(companyId);
        customerService.createCustomer(customerDTOToSave);
        return "redirect:getAllCustomersByCompanyId";
    }
}