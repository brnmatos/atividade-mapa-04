package br.com.ecommerce.controllers.input;

import lombok.Data;

@Data
public class ProdutoInput {

    private String nomeProduto;
    private String descricaoProduto;
    private double valorProduto;
    private int idUsuario;

}
