package com.hosias.evolucao.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hosias.evolucao.services.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice //vai interceptar pra mim as exceçoes que acontecerem para q este objeto possa executar um possivel tratamento 
public class ResourceExceptionHandler {
	
/*primeira excessao será a que criamos no services.exceptions, minha resposta sera do tipo StandardError que é a classe que criei no pacote 
	Resources.exceptions, o metodo resourceNotFound() foi escolhido este nome apenas para ficar igual a classe de excessao, ele vai receber como argumento uma excessao daquele tipo personalizado depois um objeto do tipo HttpServletRequest  */ 
	
	
	@ExceptionHandler(ResourceNotFoundException.class)/* para ficar capaz de receptar a requisiçao que deu a  excessao e fazer com que caia aqui. dentro dos parenteses coloco o nome da excessao que estou interceptando e coloco .class.
	Entao com esta anotation aqui estou falando que esse meu etodo aqui "resourceNotFound" ele vai interceptar qualquer exceçao desse tipo que coloquei em parenteses, e vai fazer o tratamento que estiver aqui dentro do metodo   */
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI()); //status.value() coloquei o .value para passar para inteiro para corrigir um erro
	
		return ResponseEntity.status(status).body(err); //no caso para retornar uma resposta com o codigo personalizado usei o metodo .status este status aqui vai aceitar um status q eu definir no caso eu coloquei a variavel status que criei a cima daí chamo na frente .body e quem vai ser o corpo da resposta ? vai ser o objeto de erro "err" 
	
	}//este ficou sendo nosso tratamento personalizado da exceçao ResourceNotFoundException
	
}
