package org.capaub.mswebapp.controller.ajax;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.CustomerService;
import org.capaub.mswebapp.service.dto.CustomerDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/customers/ajax")
public class CustomerAjaxController {
    private final CustomerService customerService;

    @GetMapping("/getAllCustomersByCompanyId")
    public ModelAndView ajaxShowCustomers(HttpSession session) {
        Integer companyId = (Integer) session.getAttribute("companyId");
        List<CustomerDTO> customersDTO = customerService.getAllCustomersByCompanyId(companyId);

        return new ModelAndView("fragments/customers","customers", customersDTO);
    }
}
