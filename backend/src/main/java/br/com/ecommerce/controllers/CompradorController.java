package br.com.ecommerce.controllers;

import br.com.ecommerce.controllers.output.ProdutoViewOutput;
import br.com.ecommerce.controllers.output.ProdutosOutput;
import br.com.ecommerce.controllers.projection.FiltrarProdutosProjection;
import br.com.ecommerce.model.Produto;
import br.com.ecommerce.repository.UserRepository;
import br.com.ecommerce.repository.PerfilRepository;
import br.com.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/backend/comprador")
@CrossOrigin(origins = "http://localhost:8080")
public class CompradorController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping("/filtrar-produtos")
    ProdutosOutput filtrarTodosProdutos() {

        List<FiltrarProdutosProjection> output = produtoRepository.buscarProdutos();


        return ProdutosOutput.builder()
                .itens(output.stream().map(item -> new ProdutosOutput.Item(item)).collect(Collectors.toList()))
                .build();

    }

    @GetMapping("/visualizar-produto")
    ProdutoViewOutput visualizarProduto(@RequestParam("idProduto") int idProduto) {

        Optional<Produto> produto = produtoRepository.findById(idProduto);

        ProdutoViewOutput output = new ProdutoViewOutput(produto.get());

        return output;

    }



}
