package com.hosias.evolucao.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hosias.evolucao.entities.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order") // minha classe se chama Order e esta mesma palavra e uma palavra reservada do sistema spring entao , uso esta anotation para dar outro nome a  minha tabela no banco de dados
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // dizendo que essa chave ID será incrementada pelo DB
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT") // para garantir q meu moment seja mostrado lá no json como formato string do iso 8601
	private Instant moment; // java oito em diante usa-se o instant ao invez de Date para instanciar uma data de tempo real
	
	private Integer orderStatus;
	
	//@JsonIgnore // se eu colocar deste lado esta anotaçao (o outro lado é o User) quando chamado ela vem vinculado a outra classe , no caso a outra é o muitos e essa é o um. se eu colcoar do lado de lá vem associado essa na outra
	@ManyToOne // muitos para um 
	@JoinColumn(name = "client_id") /* resolvendo o lado de muitos para um essas anotaçoes vai fazre referencia com chave estrangeira no db.
	aqui nesta classe order pedido tem um cliente que é o usuario, daí coloquei uma anotaçao muitos para um (@ManyToOne) e o @JoinColumn(name = "client_id" estou falando que na tabela pedidos lá no banco eu vou ter uma chave estrangeira 
	chamada client_id que vai conter o id do usuário associado a este pedido(order) */
	private User client;
	
	//esta linha de codigo eu explico no final dos codigos final da pagina
	@OneToMany(mappedBy = "id.order")
	private Set<OrderItem> items = new HashSet<>(); // como ele é uma coleçao já instanciei 
	
	public Order() {
		
	}

	public Order(Long id, Instant date,OrderStatus orderStatus, User client) {
		super();
		this.id = id;
		this.moment = date;
		setOrderStatus(orderStatus); 
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDate() {
		return moment;
	}

	public void setDate(Instant date) {
		this.moment = date;
	}

	
	
	public OrderStatus getOrderStatus() {
	//preciso transformar em Integer em TIPO ENUM	
		
		return OrderStatus.valueOf(orderStatus);
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		if(orderStatus != null) {
			this.orderStatus = orderStatus.getCode();
		}
		
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}
	
	public Set<OrderItem> getItems() {
		return items;
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

/* PRECISAMOS FAZER UMA ASSOCIAÇAO ONE-TO-MANY aqui na classe Order, porque na classe order q é o pedido eu tenho uma associaçao com varios items entao, na verdade dentro da minha classe pedido
 * eu quero ter la uma operaçao getItems para retornar pra mim os orderItems associados  a este pedido.
 * daí la no meu OrderItem eu tenho um OrderItemPk que é o meu id e este orderItemPk , ele q vai ter uma associaçao de muitos para um com o pedido. Entao quando eu for mapear meu order associado com orderItem 
 *  eu farei um macete: coloco o @OneToMany() no Private Set<OrderItem> items e no argumeto coloco (mappedBy = "id.order") 
 * isto porque no OrderItem eu tenho id (id.order) e o id por sua vez que tem o pedido. por isso que coloquei o id.order .
 * depois de terminado meu pedido agora conhece os itens dele*/
