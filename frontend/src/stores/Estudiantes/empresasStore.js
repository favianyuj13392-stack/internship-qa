import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'

export const useEmpresasStore = defineStore({
    
        id: 'empresas',
        state: () => ({
        }),
    
        actions: {
            async getEmpresas(pageValue,sizeValue,searchValue, sector) {
                try {                    //añade los params page, size

                    const response = await axios.get(RutaApi + '/institucion',
                        {
                            params: {
                                page: pageValue,
                                size: sizeValue,
                                search: searchValue,
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
            async getEmpresaById(id) {
                try {
                    const response = await axios.get(RutaApi + '/institucion/' + id)
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


            async getEmpresasRelacionadas(id) {
                try {
                    const response = await axios.get(RutaApi + '/institucion/' + id + '/relacionadas')
                    if (response.data.code == '200') {
                        return response.data.response
                    } else {
                        return null;
                    }
                } catch (error) {
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
            },
            async getEmpresasNombre(){
                try {
                    const response = await axios.get(RutaApi + '/institucion/nombre')
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