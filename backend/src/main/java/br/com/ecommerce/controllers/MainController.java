package br.com.ecommerce.controllers;

import br.com.ecommerce.model.User;
import br.com.ecommerce.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/backend")
public class MainController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("login");
        String senha = loginData.get("senha");

        Optional<User> usuarioLogado = loginService.login(email, senha);

        User user = usuarioLogado.isPresent() ? usuarioLogado.get() : new User();

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/getPerfil")
    String getPerfil(@RequestParam("idUsuario") int idUsuario) {

        return loginService.getPerfil(idUsuario);

    }

}
