package com.hosias.evolucao.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order") // minha classe se chama Order e esta mesma palavra e uma palavra reservada do sistema spring entao , uso esta anotation para dar outro nome a  minha tabela no banco de dados
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // dizendo que essa chave ID será incrementada pelo DB
	private Long id;
	private Instant moment; // java oito em diante usa-se o instant ao invez de Date para instanciar uma data de tempo real
	
	
	@ManyToOne 
	@JoinColumn(name = "client_id") // resolvendo o lado de muitos para um essas anotaçoes vai faze referencia com chave estrangeira no db
	private User client;

	public Order() {
		
	}

	public Order(Long id, Instant date, User client) {
		super();
		this.id = id;
		this.date = date;
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
