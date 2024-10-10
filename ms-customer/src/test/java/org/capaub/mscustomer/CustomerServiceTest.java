//package org.capaub.mscustomer;
//
//import org.capaub.mscustomer.entity.Customer;
//import org.capaub.mscustomer.repository.CustomerRepository;
//import org.capaub.mscustomer.service.CustomerService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class CustomerServiceTest {
//
//    @InjectMocks
//    private CustomerService customerService;
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    public CustomerServiceTest() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testCreateCustomer_ShouldThrowException_WhenCustomerExists() {
//        // Arrange
//        Customer customer = new Customer();
//        customer.setSiret("123456789");
//
//        when(customerRepository.findBySiret("123456789")).thenReturn(Optional.of(customer));
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> {
//            customerService.createCustomer(customer);
//        });
//    }
//
//    @Test
//    public void testCreateCustomer_ShouldSaveCustomer_WhenCustomerDoesNotExist() {
//        // Arrange
//        Customer customer = new Customer();
//        customer.setSiret("123456789");
//
//        when(customerRepository.findBySiret("123456789")).thenReturn(Optional.empty());
//        when(customerRepository.save(customer)).thenReturn(customer);
//
//        // Act
//        Customer result = customerService.createCustomer(customer);
//
//        // Assert
//        assertNotNull(result);
//        verify(customerRepository, times(1)).save(customer);
//    }
//
//    @Test
//    public void testUpdateCustomer_ShouldThrowException_WhenCustomerDoesNotExist() {
//        // Arrange
//        Customer customer = new Customer();
//        customer.setId(1);
//
//        when(customerRepository.findById(1)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> {
//            customerService.updateCustomer(customer);
//        });
//    }
//
//    // Ajoute plus de tests pour chaque méthode du service
//}
