<template>
  <div id="main-banner">
      <h1>Nova Vida - Atibaia</h1>
  </div>

</template>

<script>
  export default {
      name: "Banner"
  }

</script>

<style scoped>

  #main-banner {
      background-image:url('/public/img/radio.jpg');
      
      background-size: cover;
      height: 250px;
      display: flex;
      align-items: center;
      justify-content: flex-start;
  }
  
  #main-banner h1 {
      color:#fff;
      text-align:center;
      font-size:60px;
      padding:0px 40px;
  }

</style>