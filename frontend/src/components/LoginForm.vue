<template>
  <div>
    <div class="login-container">
      <h2>Login</h2>
      <input type="text" class="login-input" placeholder="Username" v-model="username" required>
      <input type="password" class="login-input" placeholder="Password" v-model="password" required>
      <button class="login-button" @click="login">Login</button>
      <p v-if="error" class="login-error">{{ error }}</p>
    </div>

  </div>        
  </template>
  
  <script>
  import { realizarLogin } from '@/services/login';
  
  export default {
      name: 'LoginForm',
      data() {
        return {
          username: '',
          password: '',
          error: ''
        };
      },
      methods: {
        login() {
          const formData = { "login": this.username, 
                          "senha": this.password };
          
          var usuarioLogado = null;
          
          realizarLogin(formData).then(response => {
                usuarioLogado = response;
                
                this.$router.push({
                        path:
                            '/homeAutenticado/' + response.id
                    });

            }).catch(catchError => {
                console.error(catchError);
                this.error = 'Credenciais inválidas. Por favor, tente novamente.';
            });

        }
      }
  };
  </script>
  <style scoped>
  .login-container {
    max-width: 300px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.login-input {
    width: 100%;
    padding: 10px;
    margin-bottom: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.login-button {
    width: 100%;
    padding: 10px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.login-button:hover {
    background-color: #0056b3;
}

.login-error {
    color: red;
    margin-top: 10px;
}
</style>