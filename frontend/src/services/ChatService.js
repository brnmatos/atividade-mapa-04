import http from '@/utils/http';

const baseUrl = 'http://localhost:9090/api/ws';

export function notify(body){
    return http({
        url: baseUrl + '/notify',
        data: body,
        method: 'post'
    });
}