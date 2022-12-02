package com.hosias.evolucao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hosias.evolucao.entities.User;
import com.hosias.evolucao.repositories.UserRepository;
import com.hosias.evolucao.services.exception.DatabaseException;
import com.hosias.evolucao.services.exception.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


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
		try {
			repository.deleteById(id);
		}
		catch( EmptyResultDataAccessException e /*RuntimeException e*/){//explicaçao no final da pagina
			//e.printStackTrace(); 			
			throw new ResourceNotFoundException(id);
		}
		catch (DataIntegrityViolationException e /*RuntimeException e*/) { // troquei esse RuntimeException q é generico de mais pela excessao de violaçao  de integridade de dados que peguei no console depois de executar a RuntimeException enviando um printStackTrace()
				//e.printStackTrace(); 
/* para enviar uma excessao personalizada deste tipo (integridade de dados) teremos que cria-la. vou criar a DatabaseException no pacote services.exception. farei ela extender da RuntimeException. 
	depois de pronta a classe vou lançar o throw new DatabaseException(msg) que é minha excessao de banco de dados e passarei pra ela com msg a mensagem que veio do spring DataIntegrityViolationException */	
		
		throw new DatabaseException(e.getMessage());/*como dei o nome de 'e' para minha excessao entao usei o e.getMessage() so que , agora estou lançando uma excessao da minha camada de serviço
agora pra terminar de tratar manualmente esta excessao, preciso ir no ResourceExceptionHandler e acrescentar lá um tratamento especifico para a DatabaseException */			
		}
		
	// agora preciso ir no userResource e criar o endpoint que fará a exclusao pra deletar o usuario	
	}
	
// updater user
	public User update(Long id, User obj) {//recebe o id q sera atualizado e o obj com os dados da mudança
		
		try {
			User entity = repository.getReferenceById(id); /* getReferenceById ele vai instanciar pra mim porem nao vai no bancod e dados ainda, ele só vai deixar pra mim um objeto monitorado pelo jpa para eu trabalhar com ele e em seguida posso efetuar alguma operaçao com o banco de dados.
			é melhor doq usar o findById, porq o findById necessariamente vai no banco de dados e traz o objeto de la pra gente */
	
			// entao aqui vou ter q pegar este objeto entity e atualizar com os dados que vieram com obj do parametro pra isso vou criar uma funçao updateData()
	
			updateData(entity, obj);
			return repository.save(entity);	
		}
		catch(EntityNotFoundException e/*RuntimeException e*/) {
			//e.printStackTrace(); // vi que o erro veio de EntityNotFoundException
			throw new ResourceNotFoundException(id);
					
		}
	
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
 * 
 *  quando busco pelo id q nao existe me retorna um erro 500 e para tratar o erro 500 para transformar no erro 404, no metodo de delete vou colocar um try e no catch vou colocar o tipo mais generico de RuntimeException colcoarei o mais generico porq excessao de erro de execuçao vai casar o RuntimeException, neste caso vou imprimir na tela o printStacktrace para
 *   eu ver qual excessao esta sendo chamada, executo novamente o codigo vou no postman e envio uma requisiçao 
 *  de delete em um usuario q nao existe, antes dava o erro 500 como mencionei no comentario da classe StandardError nas ultimas linhas, agora nao dará erro no postman porq foi capturado com meu try catch e mostrado na tela do console o erro com o comando printStacktrace, olhando o nome da excessao que gerou vejo que é "EmptyResultDataAccessException" 
 *  entao vou pegar esse nome de excessao e vou trocar lá no catch que tem RuntimeException por ela porq agora vou capturar especificamente essa excessao, e vou lançar a minha ResourceNotFoundException(id) com throw new ResourceNotFoundException(id); Depois de feito isso no postman retornará o erro 404 (no http significa nao encontrado).
 *  
 *  Agora vou tentar deletar um usuario que tem pedido associado a ele e dará um erro 500 (erro nao tratado), para eu ver qual erro o spring esta me retornando farei:
 *  ainda no metodo delete depois de capturar com cath a excessao especifica, eu mandar capturar com outro catch qualquer outra RuntimeException, qualquer outra runtimeexception q ocorrer vou mandar imprimir o resultado desta excessao com o printStackTrace() para eu ver de onde vem exatamente a excessao.
 *  
 *  No User update o erro mais comun é quando se dar um put (http)  e manda atualizar um id que nao existe, ele irá retornar um erro 500. Fazemos da mesma forma q fizemos nos outros casos.
 *  colocamos strackTrace e olhar de onde vem a excessao daí coloca-la em um catch */



