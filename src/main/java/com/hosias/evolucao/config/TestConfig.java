package com.hosias.evolucao.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hosias.evolucao.entities.Category;
import com.hosias.evolucao.entities.Order;
import com.hosias.evolucao.entities.User;
import com.hosias.evolucao.entities.enums.OrderStatus;
import com.hosias.evolucao.repositories.CategoryRepository;
import com.hosias.evolucao.repositories.OrderRepository;
import com.hosias.evolucao.repositories.UserRepository;

@Configuration  //para dizer ao spring q esta é uma classe específica de cofiguraçao
@Profile("test") //para rodar quando eu estiver no pefil de teste
public class TestConfig implements CommandLineRunner {// este implemento command tem um metodo que implementado ele tudo que estiver nele será executado quando iniciado a aplicaçao

/* esta classe é para popular nosso banco de dados com alguns objetos para isso preciso acessar o db e qm faz isso aqui e a classe UserRepository
neste caso vou usar uma injeçao de dependencia	*/
	
	@Autowired // o spring faz a dependencia da class atual com a classe usada a baixo pra criar um obj
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired // injeçao de dependencia
	private CategoryRepository categoryRepository;
	
@Override
public void run(String... args) throws Exception {
	// TODO Acodigo escrito aqui será executado quando a aplicaçao for iniciada

//objetos Ctegory do diagrama. observa-se q ela -e independente	
	Category cat1 = new Category(null, "Electronics");
	Category cat2 = new Category(null, "Books");
	Category cat3 = new Category(null, "Computers");
	
// mandando gravar no banco esses objetos
	categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
	
	
	User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
	User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456"); 
// no primeiro argumento coloquei null porq o id será incrementado pelo db
	
/*aqui estou fazendo uma associaçao dna classe Order eu coloquei um atributo user client.(ir na classe e ler o comenatrio) entao este u1 que coloquei no argumento a baixo
	u1 é um usuario nas linhas a cima e tudo isso faz uma referencia uma classe com outra */
	
	Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"),OrderStatus.PAID, u1);
	Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"),OrderStatus.WAITING_PAYMENT, u2);
	Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"),OrderStatus.WAITING_PAYMENT, u1);
	
	userRepository.saveAll(Arrays.asList(u1,u2)); // esta linha de fato faz a execuçao
	orderRepository.saveAll(Arrays.asList(o1, o2, o3));
	
}
}
