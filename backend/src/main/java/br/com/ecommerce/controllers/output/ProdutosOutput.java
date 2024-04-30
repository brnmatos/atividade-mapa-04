package br.com.ecommerce.controllers.output;

import br.com.ecommerce.controllers.projection.FiltrarProdutosProjection;
import lombok.Builder;
import lombok.Data;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class ProdutosOutput{

    private List<Item> itens;

    @Data
    public static class Item {
        private int idProduto;
        private String nomeProduto;
        private String descricaoProduto;
        private Double valorProduto;
        private String nomeUsuario;
        private String emailUsuario;
        private String urlImagem;
        private String imagem;

        public Item(FiltrarProdutosProjection item) {
            this.idProduto = item.getIdProduto();
            this.nomeProduto = Objects.nonNull(item.getNomeProduto()) ? item.getNomeProduto() : "";
            this.descricaoProduto = item.getDescricaoProduto();
            this.valorProduto = item.getValorProduto();
            this.nomeUsuario = item.getNomeUsuario();
            this.emailUsuario = item.getEmailUsuario();
            this.urlImagem = item.getUrlImagem();
            this.imagem = buscarImagemPeloUrl(item.getIdProduto());
        }
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
