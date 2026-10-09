import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'

export const useAplicacionesStore = defineStore({

    id: 'aplicaciones',
    state: () => ({
        aplicaciones: []
    }),

    actions: {

        
        async postAplicacion(uuid,pasantiaId,curriculumId) {
            try {

                const response = await axios.post(RutaApi + '/estudiante/'+uuid+'/pasantia/'+pasantiaId+'/curriculum/'+curriculumId)
                if (response.data.code == '200') {
                    return response.data
                } else {
                    return response.data;
                }
            } catch (error) {
                console.log(error)
            }
        },
        
        async getAplicacionesPasantias(uuid) {
            try {

                const response = await axios.get(RutaApi + '/estudiante/'+uuid+'/aplicaciones/pasantias')
                if (response.data.code == '200') {
                    return response.data.response;
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        
    },
})