package com.hosias.evolucao.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hosias.evolucao.entities.Order;
import com.hosias.evolucao.services.OrderService;

@RestController // pra que esta classe seja um recurso web e que e implementado por um controlador rest
@RequestMapping(value = "/orders") //caminho do recurso coloquei users porq a classe criada foi userResource
public class OrderResource {
/*aqui estou implementando uma arquiterura basica.Uma camada de recursos que vao ser os controladores rest, estes controladores rest vao depender de uma camda de serviço
 e que por sua vez vai depender da camada de acesso a dados (data repositories)*/
	
	@Autowired
	private OrderService service;
	
// response entities é um tipo especifico do spring para retornar resposta de requisiçao  web
	@GetMapping //para informar que que este metodo responde a requisiçao do tipo get do http
	public ResponseEntity<List<Order>> findAll() {
		
		List<Order> list = service.findAll();
		
		
		return ResponseEntity.ok().body(list);  // ResponseEntity.ok() para reornar uma resposta com sucesso no http; body(u) para retornar o corpo da resposta esse usuario u
	
// este é um controlador rest que responde no caminho /users		
	}
	
	//criar um metodo um endpoint para buscar um usuario por id
	
	@GetMapping("/{id}") // porq vai ser uma requisoçao do tipo get e signfica q vai receber um parametro id dentro da url
	public ResponseEntity<Order> findById(@PathVariable Long id){
	//esta anotaçao dentro do parametro é para o spring aceitar isso como parametro na url
	
		Order obj = service.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
}
