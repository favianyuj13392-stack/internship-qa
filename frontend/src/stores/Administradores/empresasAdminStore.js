import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'

export const useEmpresasAdminStore = defineStore({
        
    id: 'empresasAdmin',
    state: () => ({
    }),

    actions: {
        async getEmpresas(pageValue,sizeValue,searchValue, active, kkid,sector) {
            try {                    //añade los params page, size

                const response = await axios.get(RutaApi + '/admin/'+kkid+'/instituciones',
                    {
                        params: {
                            page: pageValue,
                            size: sizeValue,
                            search: searchValue,
                            active: active,
                            sector: sector
                        }
                    }
                )


                if (response.data.code == '200') {
                    this.empresas = response.data.response
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async getSolicitudesByIdEmpresa(id, kkid) {
            try {
                const response = await axios.get(RutaApi + '/admin/'+kkid+'/instituciones/' + id + '/usuarios/solicitudes')
                console.log(response)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async getSolicitudes(search, kkid) {
            try {
                const response = await axios.get(RutaApi + '/admin/'+kkid+'/instituciones/usuarios/solicitudes',
                    {
                        params: {
                            search: search
                        }
                    }
                )
                console.log(response)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async getAllInformation(idInstitucion,idSolicitud,idUsuario,kkid) {
            try {
                const response = await axios.get(RutaApi + '/admin/'+kkid+'/institucion/'+idInstitucion+'/solicitud/'+idSolicitud+'/usuario/'+idUsuario)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async aceptarSolicitud(idInstitucion,idSolicitud,idUsuario,kkid) {
            try {
                const response = await axios.put(RutaApi + '/admin/'+kkid+'/instituciones/'+idInstitucion+'/solicitud/'+idSolicitud+'/usuarios/'+idUsuario+'/aceptar')
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async eliminarSolitud(idInstitucion,idSolicitud,idUsuario,kkid) {
            try {
                const response = await axios.delete(RutaApi + '/admin/'+kkid+'/instituciones/'+idInstitucion+'/solicitud/'+idSolicitud+'/usuarios/'+idUsuario+'/aceptar')
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },
        async getEmpresaById(id,kkid) {
            try {
                const response = await axios.get(RutaApi + '/admin/'+kkid+'/instituciones/' + id)
                console.log(response)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },


        async getEmpresasRelacionadas(id, kkid) {
            try {
                const response = await axios.get(RutaApi + '/admin/'+ kkid+'/institucion/' + id + '/relacionadas')
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null;
                }
            } catch (error) {
                console.log(error)
            }
        },

        async fetchUserByUUID(kkid){
            try{
                const response = await axios.get(RutaApi + '/institucion/usuario/'+kkid)
                if(response.data.code == '200'){
                    return response.data.response
                }else{
                    return null;
                } 
            }catch (error){
                console.log(error)
            }
        },

        async updateUsuarioEmpresa(usuario, kkid){
            try{
                const response = await axios.put(RutaApi + '/institucion/usuario/'+kkid, usuario)
                if(response.data.code == '200'){
                    return response.data.response
                }else{
                    return null;
                } 
            }catch (error){
                console.log(error)
            }
        },
        async getSectores(){
            try {
                const response = await axios.get(RutaApi + '/institucion/sectores')
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