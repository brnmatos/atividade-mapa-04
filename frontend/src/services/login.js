import http from '@/utils/http';

const baseUrl = '/backend';

export function realizarLogin(formData){
    return http({
        url: baseUrl + '/login',
        data: formData,
        method: 'post'
    });
}

export function getPerfilLogado(idUsuario){
    return http({
        url: baseUrl + `/getPerfil?idUsuario=${idUsuario}`,
        method: 'get'
    });
}