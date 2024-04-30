<template>

    <div class="produtos" >
        <div v-for="produto in produtos" :key="produto.id" class="produto" @click="visualizarProduto(produto)">
            <img :src="'data:image/jpeg;base64,' + produto.imagem" alt="Imagem do Produto">
            <div class="descricao">{{ produto.descricaoProduto }}</div>
            <div class="valor">R$ {{ produto.valorProduto }}</div>
        </div>
    </div>

    <b>{{ qtdProdutos }}</b>

</template>
    
<script>
import {filtrarProdutos, getVisualizacaoProduto} from '@/services/ComprarService.js';
import { ElTable, ElTableColumn } from 'element-plus';

export default {
    name: 'TabelaProdutos',
    props: ["usuario"],
    mounted() {
        this.filtrarProdutosView();
    },
    components: {
        ElTable,
        ElTableColumn
    },
    computed: {
        qtdProdutos() {
            return this.paginacao.total === 1
                ? '1 produto'
                : `${this.paginacao.total} produtos`;
        }
    },

    data() {
        return {
            produtos: [],
            paginacao: {
                        pagina: 0,
                        ordenacao: 'id',
                        tamanho: 10,
                        total: 0
                    }
            }

    },
    methods: {

        filtrarProdutosView() {
            filtrarProdutos().then(response => {

                if (response && response.itens) {
                    this.produtos = response.itens;
                } else {
                    console.error('Resposta do serviço não possui dados esperados.');
                }
            })
            .catch(error => {
                console.error('Erro ao filtrar produtos:', error);
            });
        },

        getImagemProduto(urlProduto) {
            getImagemProduto(urlProduto)
            .then(response => {
                this.produtos = response.itens;
            })
            .catch(error => {
                console.error('Erro ao filtrar produtos:', error);
            });
        },

        visualizarProduto(novaPagina) {
            this.$router.push({
                        path:
                            '/produto-visualizar/' + this.usuario + '/' + novaPagina.idProduto
                    });
        }

    }
};
</script>
<style lang="scss">
#tabela-gestao-contrato-originacao {

    .el-table {
        border-top-right-radius: 11px;
        border-top-left-radius: 11px;
    }
    .status-processamento {
        max-width: 148px;
        word-break: break-word;
        font-size: 13px;
        line-height: 17px;
    }
    .tabela-gestao-contrato-originacao__paginacao {
        display: flex;
        justify-content: space-between;
        align-items: center;
        background: var(--white);
        padding: 14px 11px;
        border-bottom-left-radius: 11px;
        border-bottom-right-radius: 11px;

        .pagina {
            color: var(--primary);
            font-weight: 600;
        }
    }
    .tabela-gestao-icone {
        i {
            font-size: 27px;
        }
    }
}

.produtos {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}

.produto {
    width: 200px;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 10px;
    text-align: center;
}

.produto img {
    max-width: 100%;
    height: auto;
    border-radius: 5px;
    margin-bottom: 10px;
}

.produto .descricao {
    font-weight: bold;
    margin-bottom: 5px;
}

.produto .valor {
    color: green;
    font-size: 1.2em;
}
</style>