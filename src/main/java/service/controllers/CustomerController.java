package service.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import service.SprocketCustomerManagement;
import service.models.Customer;

@RestController
@CrossOrigin
@RequestMapping(CustomerController.CUSTOMERS_URL)
public class CustomerController {
	public static final String CUSTOMERS_URL = "/customers";
	
	@Autowired
	SprocketCustomerManagement management;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Customer>> getCustomers() {
		List<Customer> customers = management.getCustomers();
		
		if (customers != null)
			return ResponseEntity.status(HttpStatus.OK).body(customers);
		else
			return ResponseEntity.notFound().build();
	}
	
	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") int customerId) {
		Customer customer = management.getCustomer(customerId);
		
		if (customer != null)
			return ResponseEntity.status(HttpStatus.OK).body(customer);
		else
			return ResponseEntity.notFound().build();
	}
	
	@RequestMapping(value = "/{customerId}", method = RequestMethod.PUT)
	public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") int customerId, @Valid @RequestBody Customer customer) {
		//Will use the customerId from the url, not from the object supplied
		customer.setCustomerId(customerId);
		
		Customer updatedCustomer = management.updateCustomer(customer);
		
		if (updatedCustomer != null)
			return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
		else
			return ResponseEntity.badRequest().build();
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
		Customer newCustomer = management.addCustomer(customer);
		
		if (newCustomer != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
		else
			return ResponseEntity.badRequest().build();
	}
}
