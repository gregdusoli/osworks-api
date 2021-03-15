package com.gtechmedia.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtechmedia.osworks.domain.exception.DomainException;
import com.gtechmedia.osworks.domain.model.Customer;
import com.gtechmedia.osworks.domain.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	public Customer save(Customer customer) {
		Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
		
		if (existingCustomer != null && !existingCustomer.equals(customer)) {
			throw new DomainException("Já existe um cliente cadastrado com este e-mail.");
			
		}
		
		return customerRepository.save(customer);
	}
	
	public void delete(Long customerId) {
		customerRepository.deleteById(customerId);
	}
	
}
