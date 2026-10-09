<template>
  <router-view></router-view>
  <loading
    v-if="!$keycloak_loaded"
    :can-cancel="false"
    :is-full-page="true"
  />
</template>

<script>
import Loading from 'vue-loading-overlay'
import 'vue-loading-overlay/dist/css/index.css'

export default {
  name: "App",
  components: {
    Loading
  }
};
</script>

<style></style>
