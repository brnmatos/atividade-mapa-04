<template>
<div>
    
        <span>Minha Authority é:</span>
        <b> {{ myAuthority }} </b>

        <span> Meu nome é: </span>
        <input type='text' v-model='myName' :disabled='isConnected'/>
        <button v-on:click="connect" :disabled='isConnected'> Conectar </button>
    
        <br>
        <br>
    <div v-for="message in messages">
        <span> {{ message.time }} - </span> 
        <span>{{ message.from === myName ? 'eu' : message.from }}: </span>
        <span>{{ message.content }} </span>
    </div>

        <br>
        <br>
        <br>
        <input v-model="newMessage" @keypress.enter="sendNewMessage" :disabled="!isConnected"/>
        <button v-on:click="sendNewMessage" :disabled="!isConnected"> Enviar </button>
</div>        
</template>

<script>
import { notify } from '@/services/ChatService';
import { subscribe } from '@/utils/websocket';
//import { getMyAuthority } from '@/utils/security.util';

export default {
    name: 'Home',
    data(){
        return {
            //myAuthority: getMyAuthority(),
            myAuthority: 'User',
            myName: '',
            messages: [],
            newMessage: '',
            isConnected: false
        };
    }, 
    methods: {
        connect(){
            subscribe('/message.new', (message) => {
                this.messages.push(message);
            });
            this.isConnected = true;
        },
        sendNewMessage(){
            notify({
                name: this.myName,
                authority: this.myAuthority,
                message: this.newMessage
            }).then(() => {
                this.messages.push(this.newMessage);
                this.newMessage = '';
            });
        }
    }
};
</script>