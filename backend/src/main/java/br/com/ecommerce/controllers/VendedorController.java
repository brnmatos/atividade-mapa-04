package br.com.ecommerce.controllers;

import br.com.ecommerce.controllers.input.ProdutoInput;
import br.com.ecommerce.model.Produto;
import br.com.ecommerce.model.ProdutoImagem;
import br.com.ecommerce.model.User;
import br.com.ecommerce.repository.ProdutoImagemRepository;
import br.com.ecommerce.repository.ProdutoRepository;
import br.com.ecommerce.repository.UserRepository;
import br.com.ecommerce.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/backend/vendedor")
public class VendedorController {

    private static String UPLOADED_FOLDER = "src/main/resources/static/assets/images/produtos/";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoImagemRepository produtoImagemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<String> home(){
        String retorno = "ESSA É A PÁGINA DE PEDIDOS, ESSE CONTROLLER PERMITE OS ENDPOINTS: /backend/v1/pedidos" +
                ", /backend/v1/pedidos/listar, /backend/v1/pedidos/cadastrar, /backend/v1/pedidos/atualizar, " +
                " /backend/v1/pedidos/excluir";

        return ResponseEntity.status(HttpStatus.OK).body(retorno);
    }

    @PostMapping (value = "/anunciar-produto")
    public ResponseEntity<Produto> anunciarProduto(@RequestBody ProdutoInput input){

        if (Objects.nonNull(input)){
            Produto produto = new Produto();
            produto.setNome(input.getNomeProduto());
            produto.setDescricao(input.getDescricaoProduto());
            produto.setValor(input.getValorProduto());

            Optional<User> usuarioLogado = userRepository.findById(input.getIdUsuario());

            if(usuarioLogado.isPresent()){
                produto.setUser(usuarioLogado.get());
                return ResponseEntity.ok(produtoRepository.saveAndFlush(produto));
            }

        }

        return ResponseEntity.ok(new Produto());

    }

    @PostMapping (value = "/imagem-produto")
    public ResponseEntity<String> imagemProduto(@RequestPart("imagem") MultipartFile imagem, @RequestParam("produtoId") int idProduto) throws IOException {
        if (Objects.nonNull(idProduto) && idProduto > 0){

            Optional<Produto> produto = produtoRepository.findById(idProduto);

            if(produto.isPresent()){
                String filenameImagem = produto.get().getId() + ".jpg";
                ProdutoImagem produtoImagem = new ProdutoImagem();
                produtoImagem.setProduto(produto.get());
                produtoImagem.setCaminho("assets/images/produtos/" + filenameImagem);

                produtoImagemRepository.saveAndFlush(produtoImagem);

                saveUploadedFiles(imagem, filenameImagem);

                return ResponseEntity.status(HttpStatus.OK).body("ANÚNCIO INCLUIDO COM SUCESSO!");
            }

        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERRO AO LOCALIZAR O PRODUTO OU IMAGEM.");
    }

        @PostMapping(value = "/enviar-email-compra", produces = {MediaType.APPLICATION_JSON_VALUE})
        public ResponseEntity<String> atualizarPedidos(@RequestParam("usuarioVendedorId") int idUsuario,
                                                       @RequestParam("produtoId") int produtoId,
                                                       @RequestParam("compradorId") int compradorId){

            Optional<Produto> produtoVendido = produtoRepository.findById(produtoId);
            Optional<User> usuarioVendedor = userRepository.findById(idUsuario);
            Optional<User> usuarioComprador = userRepository.findById(compradorId);

            String toVendedor = usuarioVendedor.get().getEmail();
            String subjectVendedor = "COMPRA REALIZADA NA PLATAFORMA FAST ~~ PRODUTO: "+ produtoVendido.get().getNome();
            String textVendedor = "Prezado(a), " + usuarioVendedor.get().getNome() + "\n \n" +
                    "Seu produto: " + produtoVendido.get().getNome() + " / " + produtoVendido.get().getDescricao() + ". \n " +
                    "Acaba de ser vendido. Pelo valor solicitado na plataforma:" + produtoVendido.get().getValor() + ". \n " +
                    "Por gentileza, entrar em contato com o comprador e combinar forma de pagamento e entrega. \n \n" +
                    "Atenciosamente, FAST. \n \n Sua compra/venda de forma rápida sem impedimentos.";

            String toComprador = usuarioComprador.get().getEmail();
            String subjectComprador = "COMPRA REALIZADA NA PLATAFORMA FAST ~~ PRODUTO: "+ produtoVendido.get().getNome();
            String textComprador = "Prezado(a), " + usuarioComprador.get().getNome() + "\n \n" +
                    "Você acaba de comprar o produto: " + produtoVendido.get().getNome() + " / " + produtoVendido.get().getDescricao() + ". \n " +
                    "Pelo valor solicitado na plataforma:" + produtoVendido.get().getValor() + ". \n " +
                    "Por gentileza, aguarde o contato do vendedor para combinar forma de pagamento e entrega. \n \n" +
                    "Atenciosamente, FAST. \n \n" +
                    "Sua compra/venda de forma rápida sem impedimentos.";
            try {
                emailService.sendEmail(toVendedor, subjectVendedor, textVendedor);
                emailService.sendEmail(toComprador, subjectComprador, textComprador);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            produtoRepository.produtoIndisponivel(produtoId);

            return ResponseEntity.status(HttpStatus.OK).body("E-mail encaminhado com sucesso para o Vendedor!");
        }

    private void saveUploadedFiles(MultipartFile file, String nameFile) throws IOException {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + nameFile);
            Files.write(path, bytes);

    }
    @GetMapping(value = "/status/listar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ProdutoImagem>> listarStatusPedidos(){

        List<ProdutoImagem> listaProdutoImagems = produtoImagemRepository.findAll();

        return ResponseEntity.ok(listaProdutoImagems);
    }

}
