package com.hosias.evolucao.entities.pk;

import java.io.Serializable;
import java.util.Objects;

import com.hosias.evolucao.entities.Order;
import com.hosias.evolucao.entities.Product;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Embeddable // neste caso aqui por ser uma classe auxiliar de chave primaria composta
public class OrderItemPk implements Serializable{
	//Serializable obrigatorio se quer q seus objts trafeguem como bytes em rede e etc e como é serializable precisa de um numero de serie linha a baixo...
	
		private static final long serialVersionUID = 1L;
//basicamente esta classe terá uma referencia para produto outra par pedidos
	
/* esta classe OrderItmPk é uma classe auxiliar que será a chave primaria do item pedido,  esta classe vai ter uma referencia para as duas classes (pedidos e produtos)*/
	
// estes dois a baixo sera muitos para um	
	@ManyToOne //muitos para um	
	@JoinColumn(name = "order_id") // qual vai ser o nome da chave estrangeira na tabela do banco de dados relacional ? (R: como parametro eu respondo) "order_id".
	private Order order;
	
	@ManyToOne //muitos para um	
	@JoinColumn(name = "product_id")
	private Product product;
	
	//obs: esta classe auxiliar nao terá os metodos construtores	vou colocar apenas os gets e seters e o hashcod equals normalmente e neste caso eu preciso colocar os dois atributos para comparaçao
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public int hashCode() {
		return Objects.hash(order, product);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItemPk other = (OrderItemPk) obj;
		return Objects.equals(order, other.order) && Objects.equals(product, other.product);
	}
	

	
	
	
}


/* Sempre que eu precisar de criar uma classe auxiliar para ser uma chave primaria composta eu coloco no pacote .Pk*/

/*Fazer o item de pedido muitos para muitos com atributos extras.
 do produto para order(pedido) olhando no diagrama vemos que temos dados extras no meio  (orderItem) porque o produto no pedido ele tem uma quantidade (essa quantidade é um dado da associaçao e nao exclusivamente do produto ou pedido).
 No banco de dados aprende-se que vamos ter a tabela de associaçao com atributos extras.
 Neste OrderItem tem quantidade e preço para fins de histórico.
 Exemplo: Se amanha mudar o preço, preciso saber quanto era o preço do produto quando foi feito o pedido.
 por isso é repetido o preço na classe orderitem(classe auxiliar). Entao crio esta classe e nela precisa ter uma associaçao para produtos e para peeidos(order). porém a um problema, no paradígma orientado a objetos eu nao tenho conceitos de 
 chave primaria composta, o atributo adentificador do meu objeto é um só, entao, na verdade eu vou ter que criar uma classe auxiliar para representar o par produto/pedido, isso porq o par produto e pedido é quem vai identificar o objeto item de pedido
 o objeto item de pedido nao tem uma chave primaria propria que é o nº auto incrementavel , quem vai identificar o item de pedido é o par produto pedido.
 */