import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'

export const useAreasStore = defineStore({
    id: 'areas',
    state: () => ({
        user: null,
        token: null,
        isAuthenticated: false,
    }),
   
    actions: {
        async getAreas() {
            try {
                const response = await axios.get(RutaApi + '/pasantia/areas')
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
    },
    })