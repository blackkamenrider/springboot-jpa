package com.hosias.evolucao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosias.evolucao.entities.Order;
import com.hosias.evolucao.repositories.OrderRepository;


@Service
public class OrderService {
//buscar usuarios de duas formas uma por id e outra por ussuario name
	
	@Autowired // para q o spring faça a injeçao de dependencia 
	private OrderRepository repository;
	
	public List<Order> findAll(){
		return repository.findAll();
	}
	
	public Order findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		
		return obj.get();
	}

}
