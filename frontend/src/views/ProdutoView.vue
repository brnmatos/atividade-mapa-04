<template>
  <div>
    <Navbar :logo="logo_src" :alt="app_name" :perfil="tipoPerfilLogado" :usuario="idUsuario"/>

    <div id="app" class="main-banner">
      <div class="produto">
        <div class="imagem">
          <img :src="'data:image/jpeg;base64,' + produto.imagem" alt="Imagem do Produto">
        </div>
        <div class="info-produto">
          <div class="nome">{{ produto.nomeProduto }}</div>
          <div class="descricao">{{ produto.descricaoProduto }}</div>
          <div class="valor">R$ {{ produto.valorProduto }}</div>
          <div class="vendedor-info">
            <div class="vendedor">Vendedor: {{ produto.nomeUsuarioVendedor }}</div>
            <div class="email">E-mail: {{ produto.emailUsuarioVendedor }}</div>
          </div>
          <button class="comprar" @click="enviarEmailDeCompra()">Comprar</button>
        </div>
      </div>
    </div>


  </div>

</template>

<script>
import {getVisualizacaoProduto, enviarEmailCompra} from '@/services/ComprarService.js';
import Navbar from '@/components/Navbar.vue'
import {getPerfilLogado} from '@/services/login.js';

export default {
  name: 'produto-visualizar',
  components: {
    Navbar
  },
  data() {
        return {
            idProduto: 0,
            idVendedor: 0,
            produto: {},
            statusEmpresa: [],
            totalContratosOriginacao: {
                totalContratosOriginacao: null,
                totalImoveis: null,
                totalVolumeEstimado: null,
                totalVolumeEntregue: null
            },
            contratosTabela: [],
            paginacao: {},
            params: {
                page: 0,
                size: 20,
                sort: 'statusSubEmpresa',
                por: 'desc'
            },
            filtro: {
                numeroContrato: '',
                idsCultura: [],
                idsSafra: [],
                codigosStatus: [],
                dataContrato: ''
            },
            skeletonBlocoInforDados: true,
            CheckListagemContratoErro: false,
            selectedFile: null,
            imageUrl: null,
            logo_src: "/img/logo.png",
            app_name: "Fast - Ecommerce",
            usuario: null,
            idUsuario: null,
            tipoPerfilLogado: null
        };
    },
    methods: {
      filtrarProdutosView() {
        getVisualizacaoProduto(this.idProduto)
            .then(response => {
                if (response) {
                    this.produto = response;
                    this.idVendedor = response.idUsuarioVendedor;
                } else {
                    console.error('Resposta do serviço não possui dados esperados.');
                }
            })
            .catch(error => {
                console.error('Erro ao filtrar produtos:', error);
            });
        },

      enviarEmailDeCompra(){

        enviarEmailCompra(this.idVendedor, this.idProduto, this.idUsuarioLogado).then(response => {
          alert('E-mail encaminhado com sucesso.');
          this.$router.push({
                      path:
                          '/produtos/' + this.idUsuarioLogado
                  });
          })
          .catch(error => {
              console.error('Erro ao enviar imagem para o backend:', error);
              // Faça algo com o erro, se necessário
          });
      },

      getPerfilLogado(){
          getPerfilLogado(this.idUsuarioLogado).then(response => {        
                this.tipoPerfilLogado = response;
              })
              .catch(error => {
                  console.error('Erro ao enviar imagem para o backend:', error);
                  // Faça algo com o erro, se necessário
              });
        }
  },
  mounted(){
    this.idUsuarioLogado = this.$route.params.id;
    this.idProduto = this.$route.params.idProduto;
    
    this.getPerfilLogado();

    this.idUsuario = this.idUsuarioLogado;
    this.filtrarProdutosView();
  }
}
</script>
<style scoped>
#app {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80%;
}

.produto {
  display: flex;
  align-items: center;
  width: 80%;
  max-width: 1200px;
}

.imagem {
  flex: 0 0 50%;
  max-width: 50%;
  position: relative;
}

.imagem img {
  width: 100%;
  height: auto;
}

.info-produto {
  flex: 0 0 50%;
  max-width: 50%;
  padding-left: 20px;
}

.nome {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.descricao {
  font-size: 16px;
  margin-bottom: 10px;
}

.valor {
  font-size: 18px;
  margin-bottom: 10px;
}

.vendedor-info {
  margin-bottom: 10px;
}

.vendedor,
.email {
  font-size: 16px;
  margin-bottom: 5px;
}

.comprar {
  background-color: #28a745;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.comprar:hover {
  background-color: #218838;
}

.main-banner {
        background-image:url('/public/img/fundo.jpg');
        background-size: cover;
        height: 300px;
    }

</style>