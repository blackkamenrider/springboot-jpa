package com.hosias.evolucao.services.exception;

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg) {//vai gerar uma excessao com uma mensagem q eu mandar 
		super(msg); //chamando o construtor da classe mae para passar essa mensagem
	}
	
}
