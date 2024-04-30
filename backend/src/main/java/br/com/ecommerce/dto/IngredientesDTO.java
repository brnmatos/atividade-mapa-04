package br.com.ecommerce.dto;

import br.com.ecommerce.model.User;
import br.com.ecommerce.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
public @Data class IngredientesDTO {

    private List<User> carnes;
    private List<Produto> opcionais;

}
