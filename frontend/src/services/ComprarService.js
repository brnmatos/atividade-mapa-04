import http from '@/utils/http';

const baseUrl = 'http://localhost:9090/backend/';

export function filtrarProdutos() {
    return http({
      url: `${baseUrl}comprador/filtrar-produtos`,
      method: 'get'
  });
}

export function getVisualizacaoProduto(idProduto) {
    return http({
      url: `${baseUrl}comprador/visualizar-produto?idProduto=${idProduto}`,
      method: 'get'
  });
}

export function enviarEmailCompra(usuarioVendedorId, produtoId, compradorId){
    return http({
        url:   `${baseUrl}vendedor/enviar-email-compra?usuarioVendedorId=${usuarioVendedorId}&produtoId=${produtoId}&compradorId=${compradorId}`,
        method: 'post'
    });
}

export function enviarImagem(imagem, produtoId){
    return http({
        url: baseUrl + 'comprador/imagem-produto?produtoId=' + produtoId,
        data: imagem,
        method: 'post'
    });
}