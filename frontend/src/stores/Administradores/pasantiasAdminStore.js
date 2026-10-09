import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'

export const usePasantiasAdminStore = defineStore({
    id: 'pasantiasAdmin',
    state: () => ({
    }),

    actions: {
        async getPasantias(pagina, tamanio, terminoDeBusqueda, active, kkid) {

            try {
                const response = await axios.get(RutaApi + '/admin/'+kkid+'/pasantia', {
                    params: {
                        page: pagina,
                        tamanio: tamanio,
                        search: terminoDeBusqueda,
                        active: active
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
        async getPasantiasSinAplicantes(pagina, tamanio, terminoDeBusqueda, kkid) {
                
                try {
                    const response = await axios.get(RutaApi + '/admin/'+kkid+'/pasantia/sinaplicantes', {
                        params: {
                            page: pagina,
                            size: tamanio,
                            search: terminoDeBusqueda
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



/**
 @PutMapping("/pasantia/{idPasantias}/aceptar")
    public ResponseEntity<ResponseDto<PasantiasDto>> aceptarPasantia(
            @PathVariable String uuid,
            @PathVariable Integer idPasantias
    ) {
        return handleRequest(() -> administradorBl.aceptarPasantia(idPasantias));
    }
 */

    async aceptarPasantia(idPasantias, kkid) {
        try {
            const response = await axios.put(RutaApi + '/admin/'+kkid+'/pasantia/'+idPasantias+'/aceptar')
            if (response.data.code == '200') {
                return response.data.response
            } else {
                return null;
            }
        } catch (error) {
            console.log(error)
        }
    },

    /***
     @GetMapping("/pasantia/{idPasantia}")
    public ResponseEntity<ResponseDto<PasantiaConEmpresaYPostulantes>> getPasantia(
            @PathVariable String uuid,
            @PathVariable Integer idPasantia
    ) {
        return handleRequest(() -> pasantiaBl.obtenerPasantia(idPasantia));
    }
     */

    async obtenerPasantiaPorId(idPasantia, kkid) {
        try {
            const response = await axios.get(RutaApi + '/admin/'+kkid+'/pasantia/'+idPasantia)
            if (response.data.code == '200') {
                return response.data.response
            } else {
                return null;
            }
        } catch (error) {
            console.log(error)
        }
    },

    /**
       @DeleteMapping("/pasantia/{idPasantias}/aceptar")
    public ResponseEntity<ResponseDto<PasantiasDto>> rechazarPasantia(
            @PathVariable String uuid,
            @PathVariable Integer idPasantias
    ) {
        return handleRequest(() -> administradorBl.rechazarPasantia(idPasantias));
    }


     */
    async rechazarPasantia(idPasantias, kkid) {
        try {
            const response = await axios.delete(RutaApi + '/admin/'+kkid+'/pasantia/'+idPasantias+'/aceptar')
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