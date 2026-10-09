import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'

export const useAuthStore = defineStore({
    id: 'auth',
    state: () => ({
        user: null,
        token: null,
        isAuthenticated: false,
    }),
   
    actions: {
        async checkExistencia(uuid){
            const response = await axios.get(RutaApi + '/usuario/'+uuid+'/existencia')
            if(response.data.code == '200'){
                return response.data.response
            }else{
                return null;
            }
           
        }
    },
    })