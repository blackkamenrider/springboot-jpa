package com.hosias.evolucao.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StandardError implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT") // explicaçao deste anotation nas ultimas linhas
	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path; // o caminho q eu fiz a requisiçao e que deu erro
	
	public StandardError() {
		
	}

	public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
}
/*  esta mensagem e dada antes do tratamento com essa classe
	 
	 	"timestamp": " uma data aqui ",	 
		"Status": 500,  
		"error": "Internal server Error",
		"message": "No value present", 
		"path": "/users/	
		
 * esta  classe esta sendo criada para tratar os erros que vem do postman vindo de uma requisiçao http
 * esta classe foi criada no pacote resource com extensao exception porq os erros que vamos tratar esta ou virá de uma requisiçao 
 * e as requiçoes sao feitas pelo nosso resource. Aqui vamos lidar com a mensagem de erro "timestamp": " uma data aqui ",  "Status": 500,  "error": "Internal server Error", "message": "No value present", "path": "/users/um id q nao existe e gerou um erro .
 * quando terminado essa classe, esta mensagem de erro citado acima "timestamp": virá com a nova mensagem criada na classe ResourceNotFoundException do pacote services.exception
 * . confira no postman com o endereço http://localhost:8080/users/7 (este numero 7 é um usuario q nao existe entao quando for executar este teste usa-se um usuario que nao exista no dia do teste)
 * na mensagem "timestamp": "2022-12-02T11:54:06.818507200Z", posso formatar se quiser usando @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT") neste caso usarei nesta classe. colocarei em cima do private Instante timestamp e a mensagem formatada será 
 * "timestamp": "2022-12-02T12:10:49Z",  
 * Agora um detalhe, trabalhamos aqui a excessao para o metodo get do http mas, no metodo delete haverá este erro aqui "
 * 
 * "timestamp": "2022-12-02T12:25:11.681+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/users/7" 
    
    posso tratar tbm se eu quiser, e ao inves dele retornar um erro 500 posso fazer ele tbm retornar um 404 que no http significa nao encontrado.
    */ 
