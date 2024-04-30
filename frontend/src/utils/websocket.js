import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

const SUBSCRIPTIONS = {};

let socket = null;
let stompCLient;
let prefixUrl = '';

const connect = () => {
/*    return new Promise((resolve, reject) => {
        socket = new SockJS(
            'http://localhost:9090/ws'
        );
        stompCLient = Stomp.over(socket);
        stompCLient.connect(
            {
                Authorization: 'Bearer JWT'              
            },
            () => {
                const { sessionId } = socket._transport.url.match(/\/(?<sessionId>[^/]+?)\/websocket$/).groups;
                prefixUrl = `/user/${sessionId}`;
                resolve();
            },
            err => {
                reject(err);
            }
        );
    });
  */  
}; 

const subscribe = (suffixUrl, callback) => {
    const url = prefixUrl + suffixUrl;
    //const url = '/user/Admin' + suffixUrl;
    const subscription = stompCLient.subscribe(url, (response) => {
        if(typeof callback !== 'function'){
            return;
        }

        let json = response.body;

        if(typeof json === 'string'){
            try{
                json = JSON.parse(json);
            }catch{
                console.error('Erro fatal.');
            }
        }
        callback(json);
    });

    SUBSCRIPTIONS[suffixUrl] = subscription;
        
};

export {
    connect,
    subscribe
};
