package com.hosias.evolucao.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hosias.evolucao.entities.User;

@RestController // pra que esta classe seja um recurso web e que e implementado por um controlador rest
@RequestMapping(value = "/users") //caminho do recurso coloquei users porq a classe criada foi userResource
public class UserResource {
/*aqui estou implementando uma arquiterura basica.Uma camada de recursos que vao ser os controladores rest, estes controladores rest vao depender de uma camda de serviço
 e que por sua vez vai depender da camada de acesso a dados (data repositories)*/
	
	
// response entities é um tipo especifico do spring para retornar resposta de requisiçao  web
	@GetMapping //para informar que que este metodo responde a requisiçao do tipo get do http
	public ResponseEntity<User> findAll() {
		User u = new User(1L, "Mirian", "mirian@taboo.com", "3234265541", "1234");
		
		return ResponseEntity.ok().body(u);  // ResponseEntity.ok() para reornar uma resposta com sucesso no http; body(u) para retornar o corpo da resposta esse usuario u
	
// este é um controlador rest que responde no caminho /users		
	}
	
}
