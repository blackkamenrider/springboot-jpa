package com.hosias.evolucao.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hosias.evolucao.entities.User;
import com.hosias.evolucao.services.UserService;

@RestController // pra que esta classe seja um recurso web e que e implementado por um controlador rest
@RequestMapping(value = "/users") //caminho do recurso coloquei users porq a classe criada foi userResource
public class UserResource {
/*aqui estou implementando uma arquiterura basica.Uma camada de recursos que vao ser os controladores rest, estes controladores rest vao depender de uma camda de serviço
 e que por sua vez vai depender da camada de acesso a dados (data repositories)*/
	
	@Autowired
	private UserService service;
	
// response entities é um tipo especifico do spring para retornar resposta de requisiçao  web
	@GetMapping //para informar que que este metodo responde a requisiçao do tipo get do http
	public ResponseEntity<List<User>> findAll() {
		
		List<User> list = service.findAll();
		
		
		return ResponseEntity.ok().body(list);  // ResponseEntity.ok() para reornar uma resposta com sucesso no http; body(u) para retornar o corpo da resposta esse usuario u
	
// este é um controlador rest que responde no caminho /users		
	}
	
	//criar um metodo um endpoint para buscar um usuario por id
	
	@GetMapping("/{id}") // porq vai ser uma requisoçao do tipo get e signfica q vai receber um parametro id dentro da url
	public ResponseEntity<User> findById(@PathVariable Long id){
	//esta anotaçao dentro do parametro é para o spring aceitar isso como parametro na url
	
		User obj = service.findById(id);
		
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User obj){
	//para dizer q o objeto obj vai ser entregue lá  no formato Json na hora q fizer a requisiçao e esse Json deserializado para o objeto User daqui do meu java eu preciso colocar um anotaion na frente @RequestBody(isso no paramentro do method) 
/*agora vou chamar o meu service que já enjetei a cima, para ele executar a operaçao insert da classe UserService */
		
	obj = service.insert(obj);
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	return ResponseEntity.created(uri).body(obj); // passo o uri depois o obj q acabei de inserir no DB
	
/*.created e ele espera receber um objeto do tipo URI porq o padrao http quando vc vai retornar um 2001(codigo que diz q foi inserido usando metodo post),é esperado q a resposta ela contém um cabeçalho chamado location contendo o endereço do novo recurso q foi inserido. aqui no spring boot 
 *  tem uma forma padrao de gerar esse endereço. exemplo: crio uma variaável do tipo URI , (vou chamar de uri) e estancio chamando a classe ServletUriComponentsBuilder e chamando o metodo dela fromCurrentRequest() depois chamo o metodo path, este metodo path vai receber um padrao como parametro para montarmos a URL, no caso 
 *  o recurso que formos inserir vai ser um recurso que vai ter o caminho /users e depois / e o id novo q inserirmos
 *  entao, no path ficaentre ""  /{id}, depois chamo o metodo buildAndExpand(), esse metodo espera que informemos o id que inserimos, neste caso o id q foi inserido esta no obj (obj = service.insert(obj);), entao no paramentro do metodo buildAndExpand() colocamos obj.getId() (pronto pegamos o id), agora chamo o metodo .toUri() para ele converter para o obj do tipo URI 
 *  agora de td pronto recebemos como retorno o codigo 201 , podemos confirmar no postman e na aba readers --> location do postman vemos depois de uma barra o numero id q foi inserido
 *  e se colcarmos para buscar com metodo get veremos o novo inserido */			
	}
	
	
}

/* o nosso userResource ele é um controlador Rest  (@RestController) e que tem este caminho aqui @RequestMapping(value = "/users"), a operaçao para buscar todos os usuarios no banco de dados  nos fizemos o metodo 
 *  ResponseEntity<List<User>> findAll(), com a notation @GetMapping, ou seja se eu chamar la no meu postam o caminho /users com o metodo get (getmapping) ele vai me retornar todos os usuarios.
 *  agora se eu inserir uma barra e colocar um numero de um id (@GetMapping("/{id}")) ele vai me retornar um usuario especifico. Esses dois endpoint servem para recuperar dados do banco, neste caso usamos o metodo get do http pra isso , por isso a notation getmapping.
 *  no caso de inserir usamos o metodo post. no caso ao inves deusar @getmapping usaremos PostMapping.
*/
