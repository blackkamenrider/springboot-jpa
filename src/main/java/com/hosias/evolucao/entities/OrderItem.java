package com.hosias.evolucao.entities;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hosias.evolucao.entities.pk.OrderItemPk;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order_item")   // mapeamneto
public class OrderItem implements Serializable{
	//Serializable obrigatorio se quer q seus objts trafeguem como bytes em rede e etc e como é serializable precisa de um numero de serie linha a baixo...
	
		private static final long serialVersionUID = 1L;
//o primeiro atributo será o identificador, que é ocorrespondente da chave primária, o tipo dele será OrderItemPk

/*neste caso do id composto eu nao irei colocar a notaçao 	@id @GeneratedValue(strategy = GenerationType.IDENTITY) // dizendo que essa chave ID será incrementada pelo DB
	neste caso irei colocar a notaçao @EmbeddedId*/
	
	@EmbeddedId	
	private OrderItemPk id = new OrderItemPk(); // sempre que formos criar uma classe auxiliar que é o id composto, tem q instanciar ela aqui... caso contrario dará um erro por que antes de instanciar ela aponta nulo
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
	
	@JsonIgnore // os dois lados se chamando cria-se um loop infinito, desta forma coloquei para um dos lado nao ser chamado e assim quebra o loop
	public Order getOrder() {
		return id.getOrder();
	}

	public void setOrder(Order order) {
		id.setOrder(order);
	}
	
//product
	//@JsonIgnore //retirei daqui para ser usado do outro lado.para inverter os resultados
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

/* este é um subTotal de OrderItem como esta no diagrama. Porém, aqui na plataforma java Interprise (JAVA EE) oq vale é o get entao, para isso aparecer no meu resultado do json tenho q colocar na frente do nome da funçao (methodo) a palavra get */	
	public Double getSubTotal() {
		return price * quantity;
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

/* PRECISAMOS FAZER UMA ASSOCIAÇAO ONE-TO-MANY na classe Order, porque na classe order q é o pedido eu tenho uma associaçao com varios items entao, na verdade dentro da minha classe pedido
 * eu quero ter la uma operaçao getItems para retornar pra mim os orderItems associados  a este pedido*/
