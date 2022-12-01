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
	
//salvar no DB um usuário
	
//(linha a baixo vai tetornar um usuario salvo) insere no DB um novo obj user, depois de feito  agora preciso ir no userResource para fazer um endpoint para inserir
	public User insert(User obj) {
		
		return repository.save(obj); // este save por padrao já retorna o objt salvo entao, neste caso basta eu colocar o return na frente
	}
	
// deleçao do usuário
	
	public void delete(Long id) {
		
		repository.deleteById(id);
	// agora preciso ir no userResource e criar o endpoint que fará a exclusao pra deletar o usuario	
	}

}
