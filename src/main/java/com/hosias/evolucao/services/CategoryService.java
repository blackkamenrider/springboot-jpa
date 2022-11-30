package com.hosias.evolucao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosias.evolucao.entities.Category;
import com.hosias.evolucao.repositories.CategoryRepository;


@Service
public class CategoryService {
//buscar usuarios de duas formas uma por id e outra por ussuario name
	
	@Autowired // para q o spring faça a injeçao de dependencia 
	private CategoryRepository repository;
	
	public List<Category> findAll(){
		return repository.findAll();
	}
	
	public Category findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		
		return obj.get();
	}

}
