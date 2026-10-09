<template>
  <div v-if="visible" class="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm p-4 overflow-y-auto">
    <div class="relative w-full max-w-4xl bg-white dark:bg-slate-800 rounded-2xl shadow-2xl border border-slate-200 dark:border-slate-700 overflow-hidden my-8">
      <!-- Modal Header -->
      <div class="px-6 py-5 bg-gradient-to-r from-cyan-600 to-blue-700 text-white flex justify-between items-center">
        <div class="flex items-center gap-3">
          <div class="size-12 rounded-full bg-white/20 flex items-center justify-center text-xl font-bold">
            <i class="uil uil-user-check"></i>
          </div>
          <div>
            <h3 class="text-xl font-bold leading-snug">
              {{ estudiante?.nombres }} {{ estudiante?.apellidos }}
            </h3>
            <p class="text-xs text-cyan-100 flex items-center gap-2">
              <span><i class="uil uil-envelope text-sm"></i> {{ estudiante?.correo }}</span>
              <span>•</span>
              <span><i class="uil uil-graduation-cap text-sm"></i> {{ estudiante?.nombreCarrera || 'Sin carrera asignada' }}</span>
            </p>
          </div>
        </div>
        <button
          @click="$emit('cerrar')"
          class="p-2 text-white/80 hover:text-white rounded-lg hover:bg-white/10 transition"
        >
          <i class="uil uil-times text-2xl"></i>
        </button>
      </div>

      <!-- Loading State -->
      <div v-if="cargando" class="py-16 text-center text-slate-400">
        <i class="uil uil-spinner-alt animate-spin text-4xl mb-3 text-cyan-600"></i>
        <p class="text-sm font-medium">Cargando expediente de actividad del estudiante...</p>
      </div>

      <div v-else-if="ficha" class="p-6 space-y-6 max-h-[75vh] overflow-y-auto">
        <!-- Quick Stats Cards -->
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
          <div class="p-4 bg-slate-50 dark:bg-slate-900/60 rounded-xl border border-slate-200 dark:border-slate-700 text-center">
            <p class="text-xs font-semibold uppercase text-slate-500 dark:text-slate-400">Total Accesos</p>
            <p class="text-2xl font-black text-cyan-600 mt-1">{{ ficha.totalAccesos || 0 }}</p>
          </div>
          <div class="p-4 bg-slate-50 dark:bg-slate-900/60 rounded-xl border border-slate-200 dark:border-slate-700 text-center">
            <p class="text-xs font-semibold uppercase text-slate-500 dark:text-slate-400">Pasantías Vistas</p>
            <p class="text-2xl font-black text-blue-600 mt-1">{{ ficha.pasantiasVistas?.length || 0 }}</p>
          </div>
          <div class="p-4 bg-slate-50 dark:bg-slate-900/60 rounded-xl border border-slate-200 dark:border-slate-700 text-center">
            <p class="text-xs font-semibold uppercase text-slate-500 dark:text-slate-400">Postulaciones</p>
            <p class="text-2xl font-black text-emerald-600 mt-1">{{ ficha.postulaciones?.length || 0 }}</p>
          </div>
          <div class="p-4 bg-slate-50 dark:bg-slate-900/60 rounded-xl border border-slate-200 dark:border-slate-700 text-center">
            <p class="text-xs font-semibold uppercase text-slate-500 dark:text-slate-400">Última Conexión</p>
            <p class="text-xs font-bold text-slate-700 dark:text-slate-200 mt-2">
              {{ formatoFecha(ficha.ultimoAcceso) }}
            </p>
          </div>
        </div>

        <!-- Navigation Tabs -->
        <div class="flex border-b border-slate-200 dark:border-slate-700">
          <button
            @click="tabActiva = 'vistas'"
            :class="[
              'px-5 py-2.5 text-sm font-semibold transition border-b-2 flex items-center gap-2',
              tabActiva === 'vistas'
                ? 'border-cyan-600 text-cyan-600 dark:text-cyan-400'
                : 'border-transparent text-slate-500 hover:text-slate-700 dark:text-slate-400'
            ]"
          >
            <i class="uil uil-eye"></i> Pasantías Consultadas ({{ ficha.pasantiasVistas?.length || 0 }})
          </button>
          <button
            @click="tabActiva = 'postulaciones'"
            :class="[
              'px-5 py-2.5 text-sm font-semibold transition border-b-2 flex items-center gap-2',
              tabActiva === 'postulaciones'
                ? 'border-cyan-600 text-cyan-600 dark:text-cyan-400'
                : 'border-transparent text-slate-500 hover:text-slate-700 dark:text-slate-400'
            ]"
          >
            <i class="uil uil-file-alt"></i> Postulaciones ({{ ficha.postulaciones?.length || 0 }})
          </button>
          <button
            @click="tabActiva = 'accesos'"
            :class="[
              'px-5 py-2.5 text-sm font-semibold transition border-b-2 flex items-center gap-2',
              tabActiva === 'accesos'
                ? 'border-cyan-600 text-cyan-600 dark:text-cyan-400'
                : 'border-transparent text-slate-500 hover:text-slate-700 dark:text-slate-400'
            ]"
          >
            <i class="uil uil-history"></i> Historial de Accesos ({{ ficha.historialAccesos?.length || 0 }})
          </button>
        </div>

        <!-- Tab 1: Pasantías Vistas -->
        <div v-if="tabActiva === 'vistas'" class="space-y-3">
          <div v-if="!ficha.pasantiasVistas || ficha.pasantiasVistas.length === 0" class="py-8 text-center text-slate-400">
            <i class="uil uil-file-search text-3xl mb-1"></i>
            <p class="text-sm">El estudiante aún no ha visualizado ninguna pasantía.</p>
          </div>
          <div v-else class="overflow-x-auto">
            <table class="w-full text-left text-sm">
              <thead class="bg-slate-50 dark:bg-slate-900 text-slate-500 uppercase text-xs font-semibold">
                <tr>
                  <th class="px-4 py-3">Pasantía</th>
                  <th class="px-4 py-3">Canal Inicial</th>
                  <th class="px-4 py-3">Primera Vista</th>
                  <th class="px-4 py-3">Última Vista</th>
                  <th class="px-4 py-3 text-center">Veces</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-slate-100 dark:divide-slate-700">
                <tr v-for="vista in ficha.pasantiasVistas" :key="vista.idvista" class="hover:bg-slate-50/50 dark:hover:bg-slate-700/30">
                  <td class="px-4 py-3 font-semibold text-slate-800 dark:text-slate-200">
                    {{ vista.tituloPasantia || 'Pasantía #' + vista.idPasantia }}
                  </td>
                  <td class="px-4 py-3">
                    <span
                      :class="[
                        'inline-flex items-center gap-1 px-2.5 py-0.5 rounded-full text-xs font-bold',
                        vista.origen === 'CORREO'
                          ? 'bg-purple-100 text-purple-700 dark:bg-purple-900/40 dark:text-purple-300'
                          : 'bg-blue-100 text-blue-700 dark:bg-blue-900/40 dark:text-blue-300'
                      ]"
                    >
                      <i :class="vista.origen === 'CORREO' ? 'uil uil-envelope' : 'uil uil-globe'"></i>
                      {{ vista.origen || 'WEB' }}
                    </span>
                  </td>
                  <td class="px-4 py-3 text-slate-500 text-xs">{{ formatoFecha(vista.primeraVista) }}</td>
                  <td class="px-4 py-3 text-slate-500 text-xs">{{ formatoFecha(vista.ultimaVista) }}</td>
                  <td class="px-4 py-3 text-center">
                    <span class="px-2 py-0.5 bg-slate-100 dark:bg-slate-700 font-bold rounded text-xs text-slate-700 dark:text-slate-300">
                      {{ vista.veces }}x
                    </span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Tab 2: Postulaciones -->
        <div v-if="tabActiva === 'postulaciones'" class="space-y-3">
          <div v-if="!ficha.postulaciones || ficha.postulaciones.length === 0" class="py-8 text-center text-slate-400">
            <i class="uil uil-file-times-alt text-3xl mb-1"></i>
            <p class="text-sm">El estudiante aún no ha enviado postulaciones a convocatorias.</p>
          </div>
          <div v-else class="space-y-3">
            <div
              v-for="post in ficha.postulaciones"
              :key="post.idAplicacionPasantias"
              class="p-4 bg-slate-50 dark:bg-slate-900/40 rounded-xl border border-slate-200 dark:border-slate-700 flex justify-between items-center"
            >
              <div>
                <h4 class="font-bold text-slate-900 dark:text-white">
                  {{ post.pasantiasDto?.titulo || 'Convocatoria #' + post.idPasantias }}
                </h4>
                <p class="text-xs text-slate-500 mt-0.5">
                  <span class="font-medium text-slate-700 dark:text-slate-300">{{ post.pasantiasDto?.institucion?.nombre || 'Empresa' }}</span>
                  • Postulado el {{ formatoFecha(post.fechaAplicacion) }}
                </p>
              </div>
              <div>
                <span
                  :class="[
                    'px-3 py-1 rounded-full text-xs font-bold inline-flex items-center gap-1',
                    post.activo
                      ? 'bg-emerald-100 text-emerald-700 dark:bg-emerald-900/40 dark:text-emerald-300'
                      : 'bg-amber-100 text-amber-700 dark:bg-amber-900/40 dark:text-amber-300'
                  ]"
                >
                  <i :class="post.activo ? 'uil uil-check-circle' : 'uil uil-clock'"></i>
                  {{ post.activo ? 'Aceptado / Preseleccionado' : 'Postulación Enviada' }}
                </span>
              </div>
            </div>
          </div>
        </div>

        <!-- Tab 3: Historial de Accesos -->
        <div v-if="tabActiva === 'accesos'" class="space-y-3">
          <div v-if="!ficha.historialAccesos || ficha.historialAccesos.length === 0" class="py-8 text-center text-slate-400">
            <i class="uil uil-history text-3xl mb-1"></i>
            <p class="text-sm">No se han registrado sesiones de inicio en la plataforma.</p>
          </div>
          <div v-else class="overflow-x-auto">
            <table class="w-full text-left text-sm">
              <thead class="bg-slate-50 dark:bg-slate-900 text-slate-500 uppercase text-xs font-semibold">
                <tr>
                  <th class="px-4 py-3">Fecha y Hora</th>
                  <th class="px-4 py-3">Canal</th>
                  <th class="px-4 py-3">Dirección IP</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-slate-100 dark:divide-slate-700">
                <tr v-for="evento in ficha.historialAccesos" :key="evento.idevento" class="hover:bg-slate-50/50 dark:hover:bg-slate-700/30">
                  <td class="px-4 py-3 font-medium text-slate-800 dark:text-slate-200 text-xs">
                    {{ formatoFecha(evento.fechaHora) }}
                  </td>
                  <td class="px-4 py-3 text-xs">
                    <span class="px-2 py-0.5 bg-cyan-100 dark:bg-cyan-900/40 text-cyan-700 dark:text-cyan-300 rounded font-semibold">
                      {{ evento.origen || 'PORTAL' }}
                    </span>
                  </td>
                  <td class="px-4 py-3 text-slate-500 font-mono text-xs">{{ evento.ip || 'N/D' }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- Modal Footer -->
      <div class="px-6 py-4 bg-slate-50 dark:bg-slate-900 border-t border-slate-200 dark:border-slate-700 flex justify-end">
        <button
          @click="$emit('cerrar')"
          class="px-5 py-2 bg-slate-200 hover:bg-slate-300 dark:bg-slate-700 dark:hover:bg-slate-600 text-slate-700 dark:text-slate-200 rounded-lg text-sm font-semibold transition"
        >
          Cerrar Expediente
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FichaActividadModal',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    cargando: {
      type: Boolean,
      default: false
    },
    estudiante: {
      type: Object,
      default: () => ({})
    },
    ficha: {
      type: Object,
      default: null
    }
  },
  emits: ['cerrar'],
  data() {
    return {
      tabActiva: 'vistas'
    }
  },
  methods: {
    formatoFecha(fechaStr) {
      if (!fechaStr) return 'Nunca'
      const fecha = new Date(fechaStr)
      if (isNaN(fecha.getTime())) return fechaStr
      return fecha.toLocaleString('es-BO', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      })
    }
  }
}
</script>
