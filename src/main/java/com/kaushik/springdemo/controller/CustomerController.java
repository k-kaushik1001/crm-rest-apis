package com.kaushik.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaushik.springdemo.entity.Customer;
import com.kaushik.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {
	@Autowired
	CustomerService service;
	
	@PostMapping("/customers")
	public Customer createCustomer(@RequestBody Customer c) {
		service.saveCustomer(c);
		
		return c;
	}
	
	@GetMapping("/customers")
	public List<Customer> getCustomers() throws Exception {
		List<Customer> customers = service.getCustomers();
		if(customers == null) {
			throw new Exception("Not Found");
		}
		return customers;
	}
	
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) throws Exception {
		Customer currentCustomer = service.getCustomer(customerId);
		if(currentCustomer == null) {
			throw new Exception("Not Found");
		}
		return currentCustomer;
	}
	
	@PutMapping("/customers/{customerId}")
	public Customer updateCustomer(@PathVariable int customerId, @RequestBody Customer updatedCustomer) throws Exception {
		Customer existingCustomer = service.getCustomer(customerId);
		if(existingCustomer == null) {
			throw new Exception("Invalid customer ID");
		}
		existingCustomer.setFirstName(updatedCustomer.getFirstName());
		existingCustomer.setLastName(updatedCustomer.getLastName());
		existingCustomer.setEmail(updatedCustomer.getEmail());
		
		service.saveCustomer(existingCustomer);
		return existingCustomer;
	}
	
	@DeleteMapping("/customers/{customerId}")
	public int deleteCustomer(@PathVariable int customerId) throws Exception {
		Customer c = service.getCustomer(customerId);
		if(c == null) {
			throw new Exception("Invalid ID");
		}
		service.deleteCustomer(customerId);
		
		return customerId;
	}
}
