package com.gtechmedia.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gtechmedia.osworks.domain.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	List<Customer> findByName(String name);
	List<Customer> findByNameContaining(String name);
	Customer findByEmail(String email);
}
