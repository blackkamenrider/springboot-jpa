package com.hosias.evolucao.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "tb_category")
public class Category implements Serializable{
	//Serializable obrigatorio se quer q seus objts trafeguem como bytes em rede e etc e como é serializable precisa de um numero de serie linha a baixo...
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
//a baixo farei a associaçao de muitos para muitos dessa classe com a Product
		//private List<Product> products = new ArrayList<>(); // nao irei usar o list porq ela permite elementos repetidos
	//@Transient // impede o jpa de interpretar esse codigo aqui. por enquanto. preciso fazer um mapeameno
	
	@JsonIgnore // evitar o erro do loop. como coloquei deste lado, quando eu buscar no postman /products entao, virá pindurado (associado) o categories poré, quando eu colocar categories nao vira pendurado(associado) o product
	@ManyToMany(mappedBy = "categories")
	private Set<Product> products = new HashSet<>(); /* usarei o set porq ele usa teoria dos conjuntos. preciso criar os gets  dele porq quando é coleçao nao uso os seters por 
		quando uso o seters ele troca a coleçao a cada vez q uso e isso nao faz sentido*/
	
	public Category() {
		
	}

	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Product> getProducts() {
		return products;
	}
	
	/*public void setProducts(Set<Product> products) {
	this.products = products;
	} */                              // apaguei porq nao em uma coleçao nao se usa o seter
	
	
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
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}
}
