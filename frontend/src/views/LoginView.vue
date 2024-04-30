<template>
  <div>
    <Navbar :logo="logo_src" :alt="app_name" />
    <div class="main-banner">
      <login-form />
    </div>
  </div>
</template>

<script>
import Banner from '../components/Banner'
import LoginForm from '../components/LoginForm'
import Navbar from '@/components/Navbar.vue'
import {getPerfilLogado} from '@/services/login.js';

export default {
  name: 'Login',
  components: {
    Banner,
    LoginForm,
    Navbar
  },
  data() {
        return {
            logo_src: "/img/logo.png",
            app_name: "Fast - Ecommerce",
            tipoPerfilLogado: "",
            idUsuarioLogado: 0
        }
      },

    methods: {
      getPerfilLogado(){
          getPerfilLogado(this.idUsuarioLogado).then(response => {        
                this.tipoPerfilLogado = response;
              })
              .catch(error => {
              });
        }
    },
    mounted(){
      this.idUsuarioLogado = this.$route.params.id;

      if(!isNaN(this.idUsuarioLogado)){
        this.getPerfilLogado();
        this.idUsuario = this.idUsuarioLogado;
      }
    }
}
</script>
<style>

.main-banner {
        background-image:url('/public/img/fundo.jpg');
        background-size: cover;
        height: 350px;
    }

</style>