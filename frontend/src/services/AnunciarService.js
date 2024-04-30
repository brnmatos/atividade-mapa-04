import http from '@/utils/http';

const baseUrl = 'http://localhost:9090/backend/vendedor';

export function anunciar(body){
    return http({
        url: baseUrl + '/anunciar-produto',
        data: body,
        method: 'post'
    });
}

export function enviarImagem(imagem, produtoId){
    return http({
        url: baseUrl + '/imagem-produto?produtoId=' + produtoId,
        data: imagem,
        method: 'post'
    });
}