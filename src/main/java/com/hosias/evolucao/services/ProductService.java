package com.hosias.evolucao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosias.evolucao.entities.Product;
import com.hosias.evolucao.repositories.ProductRepository;


@Service
public class ProductService {
//buscar usuarios de duas formas uma por id e outra por ussuario name
	
	@Autowired // para q o spring faça a injeçao de dependencia 
	private ProductRepository repository;
	
	public List<Product> findAll(){
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		
		return obj.get();
	}

}
