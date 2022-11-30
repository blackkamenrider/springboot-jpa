package com.hosias.evolucao.entities;

import java.io.Serializable;
import java.util.Objects;

import com.hosias.evolucao.entities.pk.OrderItemPk;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order_item") //mapeamneto
public class OrderItem implements Serializable{
	//Serializable obrigatorio se quer q seus objts trafeguem como bytes em rede e etc e como é serializable precisa de um numero de serie linha a baixo...
	
		private static final long serialVersionUID = 1L;
//o primeiro atributo será o identificador, que é ocorrespondente da chave primária, o tipo dele será OrderItemPk

/*neste caso do id composto eu nao irei colocar a notaçao 	@id @GeneratedValue(strategy = GenerationType.IDENTITY) // dizendo que essa chave ID será incrementada pelo DB
	neste caso irei colocar a notaçao @EmbeddedId*/
	
	@EmbeddedId	
	private OrderItemPk id;
	private Integer quantity;
	private Double price;
	
	public OrderItem(){
	
	}

// no construtor com argumento 	vai ter um Order e um Product e vamos atribuir o pedido e o produto neste item de pedido(dentro do construtor parametrizado) 
	public OrderItem(Order order,Product product, Integer quantity, Double price) { // construtorr sem o id
		
		id.setOrder(order); // aqui estou atribuindo o produto e o pedido neste item de pedido(quando alguem chamar instanciando será associado os produtos e pedidos )
		id.setProduct(product);
		this.quantity = quantity;
		this.price = price;
	}
	
//preciso fazer os geters e seters do product e order (explicaçao detalhada nas ultimas linhas)	
	
	public Order getOrder() {
		return id.getOrder();
	}

	public void setOrder(Order order) {
		id.setOrder(order);
	}
	
//product
	
	public Product getProduct() {
		return id.getProduct();
	}

	public void setProduct(Product product) {
		id.setProduct(product);
	}
	
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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
		OrderItem other = (OrderItem) obj;
		return Objects.equals(id, other.id);
	}		
}


/* Preciso fazer os geters e seters do product e do order mesmo que nao tenha eles diretamente nesta classe(eu tenho apenas o id)
  para o mundo exterior o meu item de pedido(OrderItem) nao vai dar pra mim um get id com um campo composto.
  ele vai ter dar um por um, pedido e produto. */
