package br.com.ecommerce.repository;

import br.com.ecommerce.controllers.projection.FiltrarProdutosProjection;
import br.com.ecommerce.model.Produto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("produtoRepository")
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query(value = "" +
            "SELECT " +
            " p.id as idProduto," +
            " p.nome as nomeProduto," +
            " p.descricao as descricaoProduto," +
            " p.valor as valorProduto, " +
            " u.nome as nomeUsuario," +
            " u.email as emailUsuario," +
            " i.caminho as urlImagem " +
            " FROM " +
            " produto p " +
            " INNER JOIN user u ON u.id = p.user_id " +
            " INNER JOIN produto_imagem i ON i.produto_id = p.id ", nativeQuery = true)
    List<FiltrarProdutosProjection> buscarProdutos();

    @Modifying
    @Query("UPDATE Produto p SET p.disponivel = false WHERE p.id = ?1")
    void produtoIndisponivel(int produtoId);

}
