package br.com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table
public @Data class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="email")
    private String email;

    @Column(name="senha")
    private String senha;

    @Column(name="nome")
    private String nome;

    @Column(name="tipoPerfil")
    private int tipoPerfil;

}
