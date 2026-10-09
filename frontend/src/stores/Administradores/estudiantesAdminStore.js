import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'

export const useEstudiantesAdminStore = defineStore({
    id: 'estudiantesAdmin',
    state: () => ({
        estudiantes: [],
        totalEstudiantes: 0,
        estudiante: {},
        padron: [],
        totalPadron: 0,
        totalPaginasPadron: 0,
        importaciones: [],
        carreras: [],
        resumenSimulacion: null,
        cargandoPadron: false,
        kpisDashboard: null,
        alcancePasantiaActual: null,
        fichaEstudianteActual: null,
        cargandoKpis: false,
        cargandoAlcance: false,
        cargandoFicha: false,
    }),
    actions: {
        async fetchDashboardKpis(uuid) {
            this.cargandoKpis = true
            try {
                const response = await axios.get(`${RutaApi}/admin/${uuid}/dashboard/estudiantes/kpi`)
                if (response.data.code === '200') {
                    this.kpisDashboard = response.data.response
                    return this.kpisDashboard
                }
                return null
            } catch (error) {
                console.error('Error al obtener KPIs del dashboard:', error)
                return null
            } finally {
                this.cargandoKpis = false
            }
        },
        async fetchPasantiaAlcance(uuid, idPasantia) {
            this.cargandoAlcance = true
            try {
                const response = await axios.get(`${RutaApi}/admin/${uuid}/pasantia/${idPasantia}/alcance`)
                if (response.data.code === '200') {
                    this.alcancePasantiaActual = response.data.response
                    return this.alcancePasantiaActual
                }
                return null
            } catch (error) {
                console.error('Error al obtener alcance de pasantía:', error)
                return null
            } finally {
                this.cargandoAlcance = false
            }
        },
        async notificarEstudiantesPasantia(uuid, idPasantia) {
            try {
                const response = await axios.post(`${RutaApi}/admin/${uuid}/pasantia/${idPasantia}/notificar`)
                if (response.data.code === '200') {
                    return response.data.response
                }
                throw new Error(response.data.errorMessage || 'Error al notificar estudiantes')
            } catch (error) {
                console.error('Error al notificar estudiantes de la pasantía:', error)
                throw error
            }
        },
        async fetchEstudianteActividad(uuid, idPadron) {
            this.cargandoFicha = true
            try {
                const response = await axios.get(`${RutaApi}/admin/${uuid}/estudiantes/${idPadron}/actividad`)
                if (response.data.code === '200') {
                    this.fichaEstudianteActual = response.data.response
                    return this.fichaEstudianteActual
                }
                return null
            } catch (error) {
                console.error('Error al obtener ficha de actividad del estudiante:', error)
                return null
            } finally {
                this.cargandoFicha = false
            }
        },
        async descargarSinAccesoExcel(uuid) {
            try {
                const response = await axios.get(`${RutaApi}/admin/${uuid}/estudiantes/sin-acceso/excel`, {
                    responseType: 'blob'
                })
                const blob = new Blob([response.data], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                })
                const link = document.createElement('a')
                link.href = window.URL.createObjectURL(blob)
                link.download = 'estudiantes_sin_acceso_usei.xlsx'
                link.click()
                window.URL.revokeObjectURL(link.href)
            } catch (error) {
                console.error('Error al descargar reporte de estudiantes sin acceso:', error)
                throw error
            }
        },
        async fetchUserByUUID(uuid, id) {
            try {
                const response = await axios.get(RutaApi + '/admin/' + uuid + '/estudiante/' + id)
                if (response.data.code === '200') {
                    return response.data.response
                }
                return null
            } catch (error) {
                console.error(error)
                return null
            }
        },
        async getSolicitudByIdSolitud(uuid, id) {
            try {
                const response = await axios.get(RutaApi + '/admin/' + uuid + '/solicitud/' + id)
                if (response.data.code === '200') {
                    return response.data.response
                }
                return null
            } catch (error) {
                console.error(error)
                return null
            }
        },
        async fetchPadron(uuid, params = {}) {
            this.cargandoPadron = true
            try {
                const response = await axios.get(RutaApi + '/admin/' + uuid + '/estudiantes/padron', { params })
                if (response.data.code === '200') {
                    const data = response.data.response
                    this.padron = data.content || []
                    this.totalPadron = data.totalElements || 0
                    this.totalPaginasPadron = data.totalPages || 0
                    return data
                }
                return null
            } catch (error) {
                console.error('Error al obtener padrón:', error)
                return null
            } finally {
                this.cargandoPadron = false
            }
        },
        async actualizarEstudiantePadron(uuid, id, payload) {
            try {
                const response = await axios.put(RutaApi + '/admin/' + uuid + '/estudiantes/padron/' + id, payload)
                if (response.data.code === '200') {
                    return response.data.response
                }
                return null
            } catch (error) {
                console.error('Error al actualizar estudiante en padrón:', error)
                throw error
            }
        },
        async importarPadron(uuid, file, modo = 'SIMULACION', sincronizacionCompleta = false) {
            try {
                const formData = new FormData()
                formData.append('file', file)
                const url = `${RutaApi}/admin/${uuid}/estudiantes/importaciones?modo=${modo}&sincronizacionCompleta=${sincronizacionCompleta}`
                const response = await axios.post(url, formData, {
                    headers: { 'Content-Type': 'multipart/form-data' }
                })
                if (response.data.code === '200') {
                    if (modo === 'SIMULACION') {
                        this.resumenSimulacion = response.data.response
                    }
                    return response.data.response
                }
                throw new Error(response.data.errorMessage || 'Error en importación')
            } catch (error) {
                console.error('Error al procesar importación de padrón:', error)
                throw error
            }
        },
        async descargarPlantilla(uuid) {
            try {
                const response = await axios.get(RutaApi + '/admin/' + uuid + '/estudiantes/plantilla', {
                    responseType: 'blob'
                })
                const blob = new Blob([response.data], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                })
                const link = document.createElement('a')
                link.href = window.URL.createObjectURL(blob)
                link.download = 'plantilla_padron_estudiantes.xlsx'
                link.click()
                window.URL.revokeObjectURL(link.href)
            } catch (error) {
                console.error('Error al descargar plantilla:', error)
                throw error
            }
        },
        async descargarReporteErrores(uuid, importId) {
            try {
                const response = await axios.get(`${RutaApi}/admin/${uuid}/estudiantes/importaciones/${importId}/errores`, {
                    responseType: 'blob'
                })
                const blob = new Blob([response.data], {
                    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                })
                const link = document.createElement('a')
                link.href = window.URL.createObjectURL(blob)
                link.download = `errores_importacion_${importId}.xlsx`
                link.click()
                window.URL.revokeObjectURL(link.href)
            } catch (error) {
                console.error('Error al descargar reporte de errores:', error)
                throw error
            }
        },
        async fetchCarreras() {
            try {
                const response = await axios.get(RutaApi + '/pasantia/carreras')
                if (response.data && response.data.response) {
                    this.carreras = response.data.response
                }
            } catch (error) {
                console.error('Error al cargar carreras:', error)
            }
        }
    }
})