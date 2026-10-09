import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'

export const useCarrerasStore = defineStore({
    id: 'carreras',
    state: () => ({
        user: null,
        token: null,
        isAuthenticated: false,
    }),
   
    actions: {
        async getCarreras() {
            try {
                const response = await axios.get(RutaApi + '/pasantia/carreras')
                if (response.data.code == '200') {
                    //Ordenar por nombre
                    response.data.response.sort((a, b) => {
                        return a.nombre.localeCompare(b.nombre);
                    });
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        }
    },
    })