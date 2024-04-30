package br.com.ecommerce.repository;

import br.com.ecommerce.model.Perfil;
import br.com.ecommerce.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("perfilRepository")
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {


}
