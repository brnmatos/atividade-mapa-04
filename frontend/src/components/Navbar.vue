<template>
  <div>
    <div id="nav">
      <router-link id="logo-url" :to=getRotaHome()>
        <img id="logo" :src="logo" :alt="alt">
      </router-link>
      <router-link :to=getRotaHome()>Página Inicial</router-link>
      <router-link :to=getRotaLogin() v-if="isLogged()">Login</router-link>
      <router-link :to=getRotaLogout() v-else>Logout</router-link>
      <router-link :to=getRotaAnunciar() v-if="isVendedor()">Anunciar</router-link>
      <router-link :to=getRotaComprador() v-if="isComprador()">Produtos</router-link>
    </div>
  </div>
</template>

<script>

export default {
  name: "Navbar",
  props: ["logo", "alt", "perfil", "usuario"],
  
  components: {
  },

  data() {
    return {

    }
  },

  methods: {

    isVendedor(){
      if(this.perfil === 'Admin' || this.perfil === 'Vendedor'){
        return true;
      }

      return false;
    },
    isComprador(){
      if(this.perfil === 'Admin' || this.perfil === 'Comprador'){
        return true;
      }
      
      return false;
    },
    isLogged(){
      if(isNaN(this.usuario)){
          return true;
        }
      return false;
    },
    getRotaLogin(){
      return "/login";
    },
    getRotaLogout(){
      return "/";
    },

    getRotaHome(){
    
      if(isNaN(this.usuario)){
          return "/";
        }

      return "/homeAutenticado/"+this.usuario;
    },

    getRotaAnunciar(){
      return "/anunciar/"+this.usuario;
    },

    getRotaComprador(){
      return "/produtos/"+this.usuario;
    },
  
  },

  created() {
  }

}
</script>

<style scoped>
  #nav {
    background-color: #222;
    border-bottom: 4px solid #111;
    padding: 15px 50px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
  #nav {
    margin: auto;
    margin-left: 0;
  }
  #logo-url {
    margin: auto !important;
    margin-left: 0 !important;
    margin-right: 0 !important;
  }

  #logo {
    width: 40px;
    height: 40px;
  }
  #nav a {
    color: #FFF;
    text-decoration: none;
    margin: 12px;
    transition: .5s;
  }
  #nav a:hover {
    color: #FFF;
  }

  .nomeMusica{
    color: #FFF;
    text-decoration: none;
    transition: .5s;
    margin: auto !important;
    margin-left: 10px !important;
  }
</style>