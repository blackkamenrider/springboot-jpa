package com.hosias.evolucao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hosias.evolucao.entities.User;
import com.hosias.evolucao.repositories.UserRepository;
import com.hosias.evolucao.services.exception.ResourceNotFoundException;


@Service
public class UserService {
//buscar usuarios de duas formas uma por id e outra por ussuario name
	
	@Autowired // para q o spring faça a injeçao de dependencia 
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public User findById(Long id) {//aqui pode gerar um erro caso nao exista o id, explicarei melhor no final da pagina
		Optional<User> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ResourceNotFoundException(id)); /*aqui eu estava usando o .get que dava uma exceçao 500 caso o objeto user estivesse vazio(ou objeto user nao estivesse presente). Vou trocar o .get por outro metodo do 
		optional chamado orElseThrow, esse metodo faz o seguinte: na tentativa de dar o get se nao tiver usuario ele vai lançar uma exceçao, e para lançar a excessao correta vou fazer uma expressao lambda que o () -> new ResourceNotFoundException(id) */
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

/*no findBuId quando se busca por um id que nao existe atravez do metodo get do http o banco retorna um erro 500, nao é bom que deixemos este erro sem tratamento, porque quando nao se encontra o certo e retornar o 404, o 404 no http é nao encontrado, este erro aconteceu 
 * porq no metodo findbyis na linha "Optional<User> obj = repository.findById(id)" foi criado um objeto obj vindo do banco com infbyid(id), e o get() da linha do return me retorna uma excessao caso esse usuario estiver vazio, entao preciso tratar isso, e para tratar criamos uma classe ResourceNotFoundException no pacote service crio extensao service.exception
 *  */


