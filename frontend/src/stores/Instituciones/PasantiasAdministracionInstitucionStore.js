import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'


export const usePasantiasAdministracionInstitucionStore = defineStore({
    id: 'pasantiasAdministracionInstitucion',
    state: () => ({
        pasantias: []
    }),
    actions: {
        async fetchPasantiaInstitucionByUUID(uuid,id) {
            try {
                const response = await axios.get(RutaApi + '/usuario/'+uuid+'/pasantia/' + id+'/detalle')
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async postPasantiaInstitucion(uuid,idInstitucion, data) {
            try {
                const response = await axios.post(RutaApi + '/usuario/'+uuid+'/institucion/'+idInstitucion+'/pasantia', data)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async putPasantiaInstitucion(uuid,idInstitucion, idPasantia, data) {
            try {
                const response = await axios.put(RutaApi + '/usuario/'+uuid+'/institucion/'+idInstitucion+'/pasantia/'+idPasantia, data)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async aceptarPasantia(uuid, idPasantia, idAplicacionPasantia, data) {
            try {
                const response = await axios.put(RutaApi + '/usuario/'+uuid+'/pasantia/'+idPasantia+'/aplicacion/'+idAplicacionPasantia+'/aceptar', data)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async rechazarPasantia(uuid, idPasantia, idAplicacionPasantia) {
            try {
                const response = await axios.delete(RutaApi + '/usuario/'+uuid+'/pasantia/'+idPasantia+'/aplicacion/'+idAplicacionPasantia+'/rechazar')
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async terminarPasantia(uuid, idPasantia) {
            try {
                const response = await axios.put(RutaApi + '/usuario/'+uuid+'/pasantia/'+idPasantia+'/finalizar/sin-seleccion')
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        }
    }
})
