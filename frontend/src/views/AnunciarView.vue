<template>
  <div>
    <Navbar :logo="logo_src" :alt="app_name" :perfil="tipoPerfilLogado" :usuario="idUsuario"/>

    <div class="main-banner"> 

      <div class="login-container">
      <h2>Anunciar</h2>
      <br>
      <br>
      <input type="text" class="login-input" placeholder="Nome Produto" v-model="nomeProduto" required>
      <input type="text" class="login-input" placeholder="Descrição Produto" v-model="descricaoProduto" required>
      <input type="number" class="login-input" placeholder="Valor" v-model="valorProduto" required>
      <br>
      <br>
      <div>
        <input type="file" @change="handleFileInputChange" accept="image/*" />
        <button @click="uploadImage">Upload</button>
      </div>
      <br>
      <br>                   
      <button class="login-button" @click="cadastrar()">Cadastrar</button>
    </div>

    </div>
    
  </div>
</template>

<script>
import {anunciar, enviarImagem} from '@/services/AnunciarService.js'
import {getPerfilLogado} from '@/services/login.js';

import Navbar from '@/components/Navbar.vue'

export default {
  name: 'CadastrarProdutoView',
  components: {
    Navbar
  },
  data() {
        return {
            nomeProduto: null,
            descricaoProduto: null,
            valorProduto: null,
            paginacao: {},
            params: {
                page: 0,
                size: 20,
                sort: 'statusSubEmpresa',
                por: 'desc'
            },
            selectedFile: null,
            imageUrl: null,
            logo_src: "/img/logo.png",
            app_name: "Fast - Ecommerce",
            tipoPerfilLogado: "",
            idUsuarioLogado: 0,
            idUsuario: 0,
            usuario: null
        };
    },
    methods: {
      cadastrar(){

        const modal = {
          "nomeProduto": this.nomeProduto,
          "descricaoProduto": this.descricaoProduto, 
          "valorProduto": this.valorProduto,
          "idUsuario": this.idUsuarioLogado 
        };

        
        anunciar(modal).then(response => {
                const produto = response;

                this.enviarImagemParaBackend(this.selectedFile, produto);
                

            }).catch(error => {
                console.error('Erro ao processar a requisição:', error);
            });

      },

      handleFileInputChange(event) {
        this.selectedFile = event.target.files[0];
        this.imageUrl = URL.createObjectURL(this.selectedFile);
      },

      // Função para enviar a imagem para o backend
      enviarImagemParaBackend(selectedFile, produto){
          // Cria um objeto FormData
          let formData = new FormData();
          // Adiciona a imagem ao objeto FormData
          formData.append('imagem', selectedFile);

          enviarImagem(formData, produto.id).then(response => {
            alert('Registro inserido com sucesso.');
            this.$router.push({
                        path:
                            '/produtos/' + this.idUsuarioLogado
                    });
          })
          .catch(error => {
              console.error('Erro ao enviar imagem para o backend:', error);
          });
      },

      uploadImage() {
        if (!this.selectedFile) {
          alert('Por favor, selecione um arquivo.');

          return;
        }
     },
      getPerfilLogado(){
  
        getPerfilLogado(this.idUsuarioLogado).then(response => {
              this.tipoPerfilLogado = response;
            })
            .catch(error => {
                console.error('Erro ao enviar imagem para o backend:', error);
            });
        
      }
  },
  mounted(){
    this.idUsuarioLogado = this.$route.params.id;
        if(this.idUsuarioLogado === undefined){
          this.$router.push({
                        path:
                            '/login'
                    });
        }

        this.idUsuarioLogado = this.$route.params.id;

        if(!isNaN(this.idUsuarioLogado)){
          this.getPerfilLogado();
          this.idUsuario = this.idUsuarioLogado;
        }
  }
}
</script>
<style scoped>
.login-container {
  max-width: 300px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.login-input {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.login-button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.login-button:hover {
  background-color: #0056b3;
}

.login-error {
  color: red;
  margin-top: 10px;
}

.mensagem-sucesso {
  color: green;
  background-color: #d4edda;
  border-color: #c3e6cb;
  padding: 10px;
  margin-bottom: 10px;
}

.main-banner {
        background-image:url('/public/img/fundo.jpg');
        background-size: cover;
        height: 100%;
    }
</style>