const { defineConfig } = require('@vue/cli-service')
const httpPath = process.env.VUE_APP_HTTP_PATH;
const development = process.env.NODE_ENV === 'development';



module.exports = defineConfig({
  transpileDependencies: true
})
