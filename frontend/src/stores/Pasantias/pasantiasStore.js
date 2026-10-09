import { defineStore } from 'pinia'
import axios from 'axios'
import qs from 'qs'
import RutaApi from '@/assets/rutaApi.js'

export const usePasantiasStore = defineStore({

    id: 'pasantias',
    state: () => ({
    }),

    actions: {
        async getPasantias(pagina, tamanio, terminoDeBusqueda, areas, idCarrera) {

            try {
                const response = await axios.get(RutaApi + '/pasantia', {
                    params: {
                        pagina: pagina,
                        tamanio: tamanio,
                        terminoDeBusqueda: terminoDeBusqueda,
                        areas: areas,
                        idCarrera: idCarrera
                    },
                    paramsSerializer: params => {
                        return qs.stringify(params, { arrayFormat: 'repeat' })
                    }
                })
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }

            } catch (error) {
                console.log(error)
            }
        },
        async getPasantiaById(id, ref) {
                try {
                    const config = ref ? { params: { ref } } : {}
                    const response = await axios.get(RutaApi + '/pasantia/' + id, config)
                    if (response.data.code == '200') {
                        return response.data.response
                    } else {
                        return null;
                    }
                } catch (error) {
                    console.log(error)
                }
            },
        async getPasantiaRelacionada(id){
            try {
                const response = await axios.get(RutaApi + '/pasantia/' + id + '/relacionadas')
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
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
        async getCarreras() {
            try {
                const response = await axios.get(RutaApi + '/pasantia/carreras')
                if (response.data.code == '200') {
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