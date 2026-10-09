import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'
export const useInstitucionesStore = defineStore({
    id: 'instituciones',
    state: () => ({
    }),
    actions: {
        async getAllInstitucionesWithOnlyName(){
            try {
                const response = await axios.get(RutaApi + '/institucion/nombre')
                if(response.data.code == '200'){
                    return response.data.response
                }else{
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async registrarUsuarioInstitucion(usuarioInstitucion){
            try{
                const response = await axios.post(RutaApi + '/usuario/institucion', usuarioInstitucion)
                console.log(response)
                if(response.data.code == '200'){
                    return response.data.response
                }else{
                    return null;
                }
            }
            catch(error){
                console.log(error)
            }
        },
        async obtenerEmpresaPorId(id){
            try {
                const response = await axios.get(RutaApi + '/institucion/' + id)
                if(response.data.code == '200'){
                    return response.data.response
                }else{
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        }
    },
});