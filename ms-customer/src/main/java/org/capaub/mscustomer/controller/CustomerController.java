package org.capaub.mscustomer.controller;

import lombok.AllArgsConstructor;
import org.capaub.mscustomer.entities.Customer;
import org.capaub.mscustomer.service.CustomerService;
import org.capaub.mscustomer.service.DTO.CustomerDTO;
import org.capaub.mscustomer.service.DTO.CustomerWithAddressToSaveDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerWithAddressToSaveDTO customerWithAddressToSaveDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerWithAddressToSaveDTO);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/getAllCustomersByCompanyId/{companyId}")
    public ResponseEntity<List<Customer>> getAllCustomerByCompanyId(@PathVariable Integer companyId) {
        List<Customer> customer = customerService.getAllCustomersByCompanyId(companyId);
        return ResponseEntity.ok(customer);
    }
    // Autres endpoints avec les annotations @Valid et @RequestBody
}
