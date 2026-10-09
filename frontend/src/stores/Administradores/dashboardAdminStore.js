import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'

export const useDashboardAdminStore = defineStore({
    id: 'dashboard',
    state: () => ({}),
    actions: {
        // Consumo de KPI sin parámetros
        async getKPIWithoutParams(kkid) {
            try {
                const response = await axios.get(`${RutaApi}/admin/${kkid}/dashboard/KPI`)
                console.log(response)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null
                }
            } catch (error) {
                console.log(error)
                return null
            }
        },
        // Consumo de KPI con parámetros de carrera
        async getKPIWithCareerParams(kkid, idCarrera) {
            try {
                const response = await axios.get(`${RutaApi}/admin/${kkid}/dashboard/KPI/carrera`, {
                    params: { idCarrera }
                })
                console.log(response)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null
                }
            } catch (error) {
                console.log(error)
                return null
            }
        },
        // Consumo de KPI con parámetros de fecha
        async getKPIWithDateParams(kkid, fechaInicio, fechaFin) {
            try {
                const response = await axios.get(`${RutaApi}/admin/${kkid}/dashboard/KPI/fecha`, {
                    params: { fechaInicio, fechaFin }
                })
                console.log(response)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null
                }
            } catch (error) {
                console.log(error)
                return null
            }
        },
        // Consumo de KPI con parámetros de empresa
        async getKPIWithEmpresaParams(kkid, idEmpresa) {
            try {
                const response = await axios.get(`${RutaApi}/admin/${kkid}/dashboard/KPI/empresa`, {
                    params: { idEmpresa }
                })
                console.log(response)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null
                }
            } catch (error) {
                console.log(error)
                return null
            }
        },
        // Consumo de KPI con parámetros de sector
        async getKPIWithSectorParams(kkid, sector) {
            try {
                const response = await axios.get(`${RutaApi}/admin/${kkid}/dashboard/KPI/sector`, {
                    params: { sector }
                })
                console.log(response)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null
                }
            } catch (error) {
                console.log(error)
                return null
            }
        },
        // Consumo de KPI con parámetros de área
        async getKPIWithAreaParams(kkid, area) {
            try {
                const response = await axios.get(`${RutaApi}/admin/${kkid}/dashboard/KPI/area`, {
                    params: { area }
                })
                console.log(response)
                if (response.data.code == '200') {
                    return response.data.response
                } else {
                    return null
                }
            } catch (error) {
                console.log(error)
                return null
            }
        }
    }
});