<template>
  <div>
    <Navbar :logo="logo_src" :alt="app_name" :perfil="tipoPerfilLogado" :usuario="idUsuario"/>
  
    <div class="main-banner">
      <TabelaProdutos :usuario="idUsuario"></TabelaProdutos>
    </div>
    

  </div>
</template>

<script>
import TabelaProdutos from '@/components/TabelaProdutos';
import Navbar from '@/components/Navbar.vue'
import {getPerfilLogado} from '@/services/login.js';

export default {
  name: 'Produtos',
  components: {
    TabelaProdutos,
    Navbar
  },

  data(){
    return {
      produtos: [],
      logo_src: "/img/logo.png",
      app_name: "Fast - Ecommerce",
      tipoPerfilLogado: "",
      idUsuarioLogado: 0,
      idUsuario: null
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

    this.getPerfilLogado();
    this.idUsuario = this.idUsuarioLogado;
  },

  methods: {

    getPerfilLogado(){
          getPerfilLogado(this.idUsuarioLogado).then(response => {        
                this.tipoPerfilLogado = response;
              })
              .catch(error => {
                  console.error('Erro ao enviar imagem para o backend:', error);
              });
        }
  }
}


</script>

<style>
.main-banner {
        background-image:url('/public/img/fundo.jpg');
        background-size: cover;
        height: 100%;
    }
</style>