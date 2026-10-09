import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import "@/assets/scss/tailwind.scss";
import "@/assets/libs/@mdi/font/css/materialdesignicons.min.css";
import "@/assets/libs/@iconscout/unicons/css/line.css";
import authConfig from '../auth_config.json'
import Keycloak from 'keycloak-js'
import {LoadingPlugin} from 'vue-loading-overlay';
import 'vue-loading-overlay/dist/css/index.css';

import axios from 'axios'



const app = createApp(App)
const pinia = createPinia()
pinia.use(({ store }) => {
  store.$keycloak = app.config.globalProperties.$keycloak
})
app.use(pinia)


app.use(LoadingPlugin, {
  color: '#0891b2',
  loader: 'dots',
  width: 64,
  height: 64,
  backgroundColor: '#ffffff',
  opacity: 0.5,
  zIndex: 999,
  isFullPage: true,
  canCancel: false,
});



function tokenInterceptor () {
  axios.interceptors.request.use(async config => {
    if (app.config.globalProperties.$keycloak && app.config.globalProperties.$keycloak.authenticated) {
      try {
        await app.config.globalProperties.$keycloak.updateToken(30)
      } catch (error) {
        console.error('Failed to refresh token', error)
        app.config.globalProperties.$keycloak.login()
      }
      config.headers.Authorization = `Bearer ${app.config.globalProperties.$keycloak.token}`
    }
    return config
  }, error => {
    return Promise.reject(error)
  })
}














const keycloak = new Keycloak({
    url: authConfig.url,
    realm: authConfig.realm,
    clientId: authConfig.clientId,
});

keycloak.authenticated = false;


app.config.globalProperties.$keycloak = keycloak;
app.config.globalProperties.$keycloak_loaded = false;





try {
    keycloak.onReady = (auth) => {
        tokenInterceptor();
            app.config.globalProperties.$keycloak = keycloak;
        app.config.globalProperties.$keycloak_loaded = true;
        
  
        // app.mount("#app");
        console.log("Keycloak is ready", keycloak);

        

    };

    const keycloakVar=  keycloak.init({
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
        redirectUri: window.location.origin,
        checkLoginIframe: false,
    }).then(authenticated  => {
        app.use(router(keycloak));
        app.mount("#app");
    })

    



    
} catch (error) {
    console.error("Keycloak error", error);
    keycloak.logout({
        redirectUri: window.location.origin,
      });
    
}


 



  
  


