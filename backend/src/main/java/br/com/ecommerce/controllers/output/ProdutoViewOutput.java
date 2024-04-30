package br.com.ecommerce.controllers.output;

import br.com.ecommerce.model.Produto;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;

@Data
public class ProdutoViewOutput{

    private int idProduto;
    private String nomeProduto;
    private String descricaoProduto;
    private Double valorProduto;
    private int idUsuarioVendedor;
    private String nomeUsuarioVendedor;
    private String emailUsuarioVendedor;
    private String imagem;

    public ProdutoViewOutput(Produto produto) {
        this.idProduto = produto.getId();
        this.nomeProduto = Objects.nonNull(produto.getNome()) ? produto.getNome() : "";
        this.descricaoProduto = produto.getDescricao();
        this.valorProduto = produto.getValor();
        this.idUsuarioVendedor = produto.getUser().getId();
        this.nomeUsuarioVendedor = produto.getUser().getNome();
        this.emailUsuarioVendedor = produto.getUser().getEmail();
        this.imagem = buscarImagemPeloUrl(produto.getId());
    }


    private static String UPLOADED_FOLDER = "src/main/resources/static/assets/images/produtos/";

    private static String buscarImagemPeloUrl(int idProduto) {

        Path path = Paths.get(UPLOADED_FOLDER + String.valueOf(idProduto) + ".jpg");
        try {
            byte[] imagemBytes = Files.readAllBytes(path);

            return Base64.getEncoder().encodeToString(imagemBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
