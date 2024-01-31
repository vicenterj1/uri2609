package com.devsuperior.uri2609.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.uri2609.dto.CategorySumDTO;
import com.devsuperior.uri2609.entities.Category;
import com.devsuperior.uri2609.projections.CategorySumProjection;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(nativeQuery = true, value = "SELECT categories.name, sum(products.amount)"
			+ "	FROM categories"
			+ "	inner join products on products.id_categories = categories.id"
			+ "	group by categories.name")
	List<CategorySumProjection> searchSQL();
	
	
	//quando tem relacionamento para 1, pode-se navegar pelo objeto
	//mas a CATEGORIA tem relacionamento para muitos com PRODUTOS
	//nesse caso, fazendo a busca por PRODUTO, fica mais facil e não preciso do INNER JOIN
	//o construtor deve ter sido implementado
	@Query("SELECT new com.devsuperior.uri2609.dto.CategorySumDTO(obj.category.name, SUM(obj.amount)) "
			+ "FROM Product obj "
			+ "GROUP BY obj.category.name "
			)
	List<CategorySumDTO> searchJPQL();
}
