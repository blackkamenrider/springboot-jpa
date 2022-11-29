package com.hosias.evolucao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hosias.evolucao.entities.User;

@SpringBootApplication
public class EvolucaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvolucaoApplication.class, args);
		
		//User criandoBancoH2 = new User(1L, "Joana Marks", "joana@nada.com", "3234261959", "54321");
		
		
	}

}
