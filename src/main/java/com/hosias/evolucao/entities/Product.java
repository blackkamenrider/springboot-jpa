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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "tb_product")  //mapeamento relacional do jpa
public class Product implements Serializable{
	//Serializable obrigatorio se quer q seus objts trafeguem como bytes em rede e etc, e como é serializable precisa de um numero de serie linha a baixo...
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // dizendo que essa chave ID será incrementada pelo DB
	private Long id;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;
	
	  
	/*@Transient // impede o jpa de interpretar esse codigo aqui. por enquanto. Deu na coleçao a baixo um erro porq tenho q fazer um mapeamento pra transformar essas coleçoes que tem nas duas classes product e 
	category, na tabela de associaçao que tem la no modelo relacional DB */
	
	@ManyToMany //muitos para muitos
	@JoinTable(name = "tb_product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "categoy_id")  ) /*neste jointable falo qual será o nome da tabela e quais sao as chaves estrangeiras que vao ligar essa tabela (product) com a outra tabela  (Category).
	estou criando aqui uma tabela de associaçao "tb_product_category" e eu tenho q falar qual será o nome da chave estrangeira referente a tabela de produto(que neste caso é a tabela que estou associando), neste caso dei o nome de "product_id".
	Na tabela do banco de dados vai ter as chaves estrangeiras das duas tabelas existentes aqui, neste caso aqui é produto e categoria,entao preciso definir isso tbm aqui com o inverseJoinColumn, o inverseJoinColumn é para definir a chave estrangeira da outra entidade
	como estou do lado do product coloco aqui a chave da categoria (escolhi o nome "categoy_id") e la na categoria coloco a chave (nome da chave) do produto.
	agora lá do outro lado (category) preciso colocar uma referencia deste mapeamento que acabei de fazer, para isso faço @ManyToMany(mappedBy = "categories"), este categories é a coleçao a baixo declarada nesta classe*/
	
	private Set<Category> categories = new HashSet<>(); /*set é uma coleçao que representa um conjunto, vou usar para me garantir que nao terei mais de uma ocorrencia da mesma categoria
	 instanciei aqui porq preciso q ela inicia vazia e nao nula. instanciada. usei o hasset ao inves do set porq o set é uma interface e nao pode ser instanciado, entao preciso colcoar uma class corrspondente a ele.
	 estou aqui fazendo a associaçao de muitos para muitos (essa classe coma a classe Category) já aproveiro que a category já esta criada e vou lá e já faço a associaçao tbm   */
	
// tipo set trabalha com teoria de conjunto. nao aceita figurinhas repetidas
//agora para mapear isto no banco farei parecido com a classe order, farei o mapeamento atraves do @OneToMany(mappedBy = "id.order" e ao inves de ser id.order sera id.product)
	@OneToMany(mappedBy = "id.product")
	private Set<OrderItem> items = new HashSet<>();
	
	
	public Product() {
		
	}
	
// nos construtores nao irei colocar a coleçao porq já estou instanciando ela a cima

	public Product(Long id, String name,String description, Double price, String imgUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	

	public Set<Category> getCategories() {
		return categories;
	}
	
//aqui q esta associado com OrderItem	
	@JsonIgnore 
	public Set<Order> getOrders(){
		Set<Order> set = new HashSet<>();
		for(OrderItem x : items) {
			set.add(x.getOrder());
		}
		return set;
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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}


}

/* Para testar td isso preciso inserir no banco uma instancia e ver se foi inserido. primeiro passo: vou fazer o repository. pacote repository ->
   neste caso que já existe um copio e colo um já existente e faço as modificaçoes, no nome para salvar coloco product -> abro para editar e troco por product, agora pego 
   o tipo da interface nesse caso ProductRepository -> vou classe configuraçao(TestConfig) vou injetar um @Autowired private ProductRepository produtcRepository; -> peguei no material de apoio os objetos para 
   serem inseridos no banco -> vou na classe TestConfig e colo os objetos neste caso a baixo dos objetos Category cat3. ->
   agora vou em services -> copio e colo um já existente e modifico ele para Produc neste caso. (uso ctrl+f para mudar td de uma vez)
   mesma coisa faço no pacote resource, nao esquecer de modificar o caminho exemplo : /products --> ctrl+f e trocar td por Product (product neste caso somente), nao esquecer de colocar case sensetive 
   agora posso testar no postman*/
