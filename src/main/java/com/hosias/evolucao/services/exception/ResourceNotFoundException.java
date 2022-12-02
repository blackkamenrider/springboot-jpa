package com.hosias.evolucao.services.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(Object id) { // aqui vou passar como parametro um id que nao encontrei 
		
	super("Resource not found. id " + id);	/*chamando aqui o construtor da super classe com o metodo super() dentro dele coloquei uma string com uma mensagem e contateno como id q nao encontrei
		essa vai ser uma mensagem padrao da minha exceçao personalizada ResourceNotFoundException*/
	}
	
}

/*esta classe será uma sub class RuntimeException do java obs: esta classe nao te obriga a tratar */
