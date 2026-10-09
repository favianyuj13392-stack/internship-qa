<template>
  <div class="min-h-screen bg-slate-50 flex flex-col justify-center items-center px-4 py-12">
    <div class="max-w-md w-full bg-white rounded-2xl shadow-xl p-8 text-center border border-slate-100">
      <div class="w-20 h-20 mx-auto bg-amber-100 text-amber-600 rounded-full flex items-center justify-center mb-6">
        <i class="uil uil-exclamation-triangle text-4xl"></i>
      </div>
      
      <h2 class="text-2xl font-bold text-slate-800 mb-2">Acceso No Habilitado</h2>
      <div class="inline-block px-3 py-1 bg-blue-50 text-blue-700 text-xs font-semibold rounded-full mb-4">
        Cuenta Institucional UCB
      </div>
      
      <p class="text-slate-600 text-sm leading-relaxed mb-6">
        Tu cuenta institucional <strong>@ucb.edu.bo</strong> está autenticada, pero aún no figura en el 
        <strong>Padrón Oficial de Prácticas Preprofesionales</strong> de la USEI para este período.
      </p>

      <div class="bg-slate-50 rounded-xl p-4 text-left border border-slate-200 mb-6 text-xs text-slate-500 space-y-2">
        <div class="flex items-start gap-2">
          <i class="uil uil-info-circle text-blue-600 text-base mt-0.5"></i>
          <span>Solo los estudiantes habilitados por la Dirección de Carrera pueden postular a pasantías.</span>
        </div>
        <div class="flex items-start gap-2">
          <i class="uil uil-envelope text-blue-600 text-base mt-0.5"></i>
          <span>Si cumplís los requisitos académicos, comunicate con la <strong>Unidad de Servicios Estudiantiles Integrales (USEI)</strong>.</span>
        </div>
      </div>

      <div class="flex flex-col gap-3">
        <a 
          href="mailto:usei.lapaz@ucb.edu.bo"
          class="w-full py-2.5 px-4 bg-cyan-600 hover:bg-cyan-700 text-white font-medium rounded-lg shadow-sm transition duration-200 flex items-center justify-center gap-2 text-sm"
        >
          <i class="uil uil-envelope-alt"></i> Contactar a USEI
        </a>
        <button 
          @click="logout"
          class="w-full py-2.5 px-4 bg-slate-100 hover:bg-slate-200 text-slate-700 font-medium rounded-lg transition duration-200 text-sm"
        >
          Cerrar Sesión
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getCurrentInstance } from 'vue'

const { appContext } = getCurrentInstance()
const keycloak = appContext.config.globalProperties.$keycloak

const logout = () => {
  if (keycloak) {
    keycloak.logout({ redirectUri: window.location.origin })
  } else {
    window.location.href = '/'
  }
}
</script>
