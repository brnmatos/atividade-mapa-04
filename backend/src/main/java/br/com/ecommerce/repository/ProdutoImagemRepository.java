package br.com.ecommerce.repository;

import br.com.ecommerce.model.ProdutoImagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("produtoImagemRepository")
public interface ProdutoImagemRepository extends JpaRepository<ProdutoImagem, Integer> {

}
