package br.com.ecommerce.repository;

import br.com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmailAndSenha(String email, String senha);


}
