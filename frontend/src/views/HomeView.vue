<template>
  <div>
    <Navbar :logo="logo_src" :alt="app_name" :perfil="tipoPerfilLogado" :usuario="idUsuario"/>
    <div>
      <Banner />
    </div>
  </div>
</template>

<script>
import Banner from '../components/Banner'
import Navbar from '@/components/Navbar.vue'
import {getPerfilLogado} from '@/services/login.js';

export default {
  name: 'Home',
  components: {
    Banner,
    Navbar
  },
  data() {
        return {
            logo_src: "/img/logo.png",
            app_name: "Fast - Ecommerce",
            tipoPerfilLogado: "",
            idUsuario: null,
            idUsuarioLogado: 0
        }
      },

      methods: {

        getPerfilLogado(){
          getPerfilLogado(this.idUsuarioLogado).then(response => {        
                this.tipoPerfilLogado = response;
              })
              .catch(error => {
                  console.error('Erro ao enviar imagem para o backend:', error);
                  // Faça algo com o erro, se necessário    this.idUsuarioLogado = this.$route.params.id;
                  if(this.idUsuarioLogado === undefined){
                    this.$router.push({
                                  path:
                                      '/login'
                              });
                    }
                    this.getPerfilLogado();                  
                    this.idUsuario = this.idUsuarioLogado;
                    
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

        if(!isNaN(this.idUsuarioLogado)){
          this.getPerfilLogado();
          this.idUsuario = this.idUsuarioLogado;
        }
        
      }
}
</script>