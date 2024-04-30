package br.com.ecommerce.service;

import br.com.ecommerce.model.Perfil;
import br.com.ecommerce.model.User;
import br.com.ecommerce.repository.PerfilRepository;
import br.com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PerfilRepository perfilRepository;

    public Optional<User> login(String login, String senha){

        Optional<User> usuarioLogado = userRepository.findUserByEmailAndSenha(login, senha);

        return usuarioLogado;

    };

    public String getPerfil(int idUsuario){

        Optional<User> usuarioLogado = userRepository.findById(idUsuario);

        Optional<Perfil> perfil = perfilRepository.findById(usuarioLogado.get().getTipoPerfil());

        return perfil.get().getTipo();

    };

}
