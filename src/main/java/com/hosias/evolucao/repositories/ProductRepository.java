package com.hosias.evolucao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hosias.evolucao.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
//apenas criando este cabeçalho já me dá varias funçoes para trabalhar com usuário por causa da herança
	
	// neste caso nao preciso implentar essa interface porq o jparepository com meu argumento já faz isso 
}
