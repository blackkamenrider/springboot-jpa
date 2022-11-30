package com.hosias.evolucao.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hosias.evolucao.entities.Category;
import com.hosias.evolucao.entities.Order;
import com.hosias.evolucao.entities.OrderItem;
import com.hosias.evolucao.entities.Payment;
import com.hosias.evolucao.entities.Product;
import com.hosias.evolucao.entities.User;
import com.hosias.evolucao.entities.enums.OrderStatus;
import com.hosias.evolucao.repositories.CategoryRepository;
import com.hosias.evolucao.repositories.OrderItemRepository;
import com.hosias.evolucao.repositories.OrderRepository;
import com.hosias.evolucao.repositories.ProductRepository;
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
	
	@Autowired 
	private ProductRepository productRepository; // aqui faço a injeçao e mais a baixo coloco os codigos para enviar para o banco
	
	@Autowired 
	private OrderItemRepository orderItemRepository;
	
@Override
public void run(String... args) throws Exception {
	// TODO codigo escrito aqui será executado quando a aplicaçao for iniciada

//objetos Category do diagrama. observa-se q ela -e independente	
	Category cat1 = new Category(null, "Electronics");
	Category cat2 = new Category(null, "Books");
	Category cat3 = new Category(null, "Computers");
	
	Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, "");
	Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, "");
	Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, "");
	Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, "");
	Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, "");
	
// mandando gravar no banco esses objetos
	categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
	productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
	
	
//vou dizer aqui de qual categoria pertence um produto. preciso fazer em todos produtos.
	
	p1.getCategories().add(cat2); // o produto p1 dentro da coleçao de categoria dele esta lá o objeto cat2. fiz aqui uma associaçao entre objetos
	p2.getCategories().add(cat1);
	p2.getCategories().add(cat3); 
	p3.getCategories().add(cat3);
	p4.getCategories().add(cat3);
	p5.getCategories().add(cat2);
	
	/* estou trabalhando aqui a cima  no paradigma orientado a objetos, estou acessando objetos relacionados e trabalhando com eles,
	na hora de salvar no banco temos q salvar no paradígma relacional */
	
	productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5)); // para salvar novamente no banco agora com as associaçoes feitas.

// é desta forma q se trabalha com o JPA diferentemente se estivesse trabalhando direto no sql	
	
	
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
	
//estopu falando aqui em baixo: OrderItem oi1 ele é um item de pedido que é do pedido o1, p1, quantidade 2, o preço estou reproduzinho o preço do p1 p1.getPrice()	
	OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
	OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
	OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
	OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());
	
/*pronto agora vou salvar no DB mas, para isso preciso de um repository, irei criar um copiando um existente e colando e fazendo as modificaçoes (agora será OrderItemRepository). depois 
	preciso injetar esse repository no começo deste codigo, para isso vou colocar @Autowired private OrderItemRepository orderItemRepository; agora posso salvar no banco de dados 
	estes objetos que criei a cima com o comando orderItemRepository.SaveAll(Arrays.asList(oi1, oi2, oi3, oi4));*/
	
	orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));
	
	Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
/*pra salvar um objeto dependente numa relaçao um para um, nao vamos chamar o repository do proprio objt, vamos fazer associaçao de mao dupla em memoria entao, vou chamar meu pedido o1.setPayment(pay1); passando pay1 como argumento*/
	
	o1.setPayment(pay1);// eu associei aqui meu pedido um com o pagamento pay1, feito isso vou mandar salvar novamentre o pedido (o1) daí o JPA vai tratar de salvar pra gente o pagamento deste pedido. È assim q funciona no jpa
	orderRepository.save(o1); //passo novamente o objt o1 e ele salva no banco
}
}
