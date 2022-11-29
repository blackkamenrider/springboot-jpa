package com.hosias.evolucao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosias.evolucao.entities.User;
import com.hosias.evolucao.repositories.UserRepository;


@Service
public class UserService {
//buscar usuarios de duas formas uma por id e outra por ussuario name
	
	@Autowired // para q o spring faça a injeçao de dependencia 
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		
		return obj.get();
	}

}
