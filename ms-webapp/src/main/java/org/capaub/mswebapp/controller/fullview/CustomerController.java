package org.capaub.mswebapp.controller.fullview;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.capaub.mswebapp.service.SessionService;
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
    private final SessionService sessionService;

    @GetMapping("/getAllCustomersByCompanyId")
    public ModelAndView showCustomers(HttpSession session, Model model) {
        Integer companyId = sessionService.getCompanyId();
        List<CustomerDTO> customersDTO = customerService.getAllCustomersByCompanyId(companyId);

        model.addAttribute("companyId", companyId);
        model.addAttribute("fragmentPath","fragments/customers");
        return new ModelAndView("index","customers", customersDTO);
    }

    @GetMapping("/create")
    public ModelAndView createCustomer() {
        return new ModelAndView("fragments/_addCustomer", "newCustomer", new CustomerDTO());
    }
    @PostMapping("/create")
    public String createCustomer(@RequestBody CustomerWithAddressToSaveDTO customerDTOToSave,
                                 HttpSession session,
                                 HttpServletRequest request) {

        Integer companyId = (Integer) session.getAttribute("companyId");
        customerDTOToSave.setCompanyId(companyId);
        customerService.createCustomer(customerDTOToSave);

        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        if (isAjax) {
            return "redirect:/customers/ajax/getAllCustomersByCompanyId";
        }
        return "redirect:getAllCustomersByCompanyId";
    }
}