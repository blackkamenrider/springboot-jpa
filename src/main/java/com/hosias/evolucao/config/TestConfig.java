package com.hosias.evolucao.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hosias.evolucao.entities.User;
import com.hosias.evolucao.repositories.UserRepository;

@Configuration  //para dizer ao spring q esta é uma classe específica de cofiguraçao
@Profile("test") //para rodar quando eu estiver no pefil de teste
public class TestConfig implements CommandLineRunner {// este implemento command tem um metodo que implementado ele tudo que estiver nele será executado quando iniciado a aplicaçao

/* esta classe é para popular nosso banco de dados com alguns objetos para isso preciso acessar o db e qm faz isso aqui e a classe UserRepository
neste caso vou usar uma injeçao de dependencia	*/
	
	@Autowired // o spring faz a dependencia da class atual com a classe usada a baixo pra criar um obj
	private UserRepository userRepository;

@Override
public void run(String... args) throws Exception {
	// TODO Acodigo escrito aqui será executado quando a aplicaçao for iniciada
	
	User u1 = new User(null, "Maria Brown", "maria@gmail.com", "988888888", "123456");
	User u2 = new User(null, "Alex Green", "alex@gmail.com", "977777777", "123456"); 
// no primeiro argumento coloquei null porq o id será incrementado pelo db
	
	userRepository.saveAll(Arrays.asList(u1,u2)); // esta linha de fato faz a execuçao
	
}
}
