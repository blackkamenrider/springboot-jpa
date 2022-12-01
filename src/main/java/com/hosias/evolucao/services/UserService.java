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
	
// updater user
	public User update(Long id, User obj) {//recebe o id q sera atualizado e o obj com os dados da mudança
		User entity = repository.getReferenceById(id); /* getReferenceById ele vai instanciar pra mim porem nao vai no bancod e dados ainda, ele só vai deixar pra mim um objeto monitorado pelo jpa para eu trabalhar com ele e em seguida posso efetuar alguma operaçao com o banco de dados.
		é melhor doq usar o findById, porq o findById necessariamente vai no banco de dados e traz o objeto de la pra gente */
	
// entao aqui vou ter q pegar este objeto entity e atualizar com os dados que vieram com obj do parametro pra isso vou criar uma funçao updateData()
	
		updateData(entity, obj);
		return repository.save(entity);	
		
	
	}

private void updateData(User entity, User obj) {
//aqui irei atualizar os dados do entity com os dados vendo pelo obj
	entity.setName(obj.getName()); //atualizei o nome do meu entity
	entity.setEmail(obj.getEmail());
	entity.setPhone(obj.getPhone());
//agora preciso ir no meu userRessource e criar o endpoint que fará a atualizaçao	
	
	}

}
