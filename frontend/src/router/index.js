import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import Home from "../views/General/Home.vue";
import Pasantias from "../views/General/Pasantias.vue";
import PasantiasDetalle from "../views/General/PasantiasDetalle.vue";
import Empresas from "../views/General/Empresas.vue";
import EmpresasDetalle from "../views/General/EmpresasDetalle.vue";
import Contactanos from "../views/General/Contactanos.vue";

//estudiante
import PerfilEstudiante from "@/views/Estudiante/PerfilEstudiante.vue";
import EditarPerfilEstudiante from "@/views/Estudiante/EditarPerfilEstudiante.vue";
import SolicitudesEstudiante from "@/views/Estudiante/SolicitudesEstudiante.vue";
import PasantiaAplicadaEstudiante from "@/views/Estudiante/PasantiaAplicadaEstudiante.vue";
//empresa
import PasantiasEmpresaView from "@/views/Empresa/PasantiasEmpresaView.vue";
import InformacionEmpresaView from "@/views/Empresa/EditarInformacionEmpresaView.vue";
import InformacionPasantiaEmpresaView from "@/views/Empresa/InformacionPasantiaEmpresaView.vue";
import CrearPasantiaEmpresaView from "@/views/Empresa/CrearPasantiaEmpresaView.vue";
import EditarPasantiaEmpresaView from "@/views/Empresa/EditarPasantiaEmpresaView.vue";
import PerfilEstudianteEmpresa from "@/views/Empresa/PerfilEstudianteEmpresa.vue";
import PerfilUsuarioEmpresaView from "@/views/Empresa/PerfilUsuarioEmpresaView.vue";

//administrador
import DashboardAdministrador from "@/views/Administrador/DashboardAdministrador.vue";
import SolicitudEmpresaAdministrador from "@/views/Administrador/SolicitudEmpresaAdministrador.vue";
import SolicitudPasantiaAdministrador from "@/views/Administrador/SolicitudPasantiaAdministrador.vue";
import PasantiaAdministrador from "@/views/Administrador/PasantiaAdministrador.vue";
import EmpresaAdministrador from "@/views/Administrador/EmpresaAdministrador.vue";
import EmpresaDetalleAdministrador from "@/views/Administrador/EmpresaDetalleAdministrador.vue";
import PasantiaDetalleAdministrador from "@/views/Administrador/PasantiaDetalleAdministrador.vue";
import EmpresaSolicitudUsuarioAdministrador from "@/views/Administrador/EmpresaSolicitudUsuarioAdministrador.vue";
import PerfilEstudianteAdministrador from "@/views/Administrador/PerfilEstudianteAdministrador.vue";
import PasantiaSinAplicanteAdministrador from "@/views/Administrador/PasantiaSinAplicanteAdministrador.vue";
//completar registro
import FinishRegisterEstudiante from "@/views/CompletarRegistro/CompletarRegistroEstudianteView.vue";
import FinishRegisterEmpresa from "@/views/CompletarRegistro/CompletarRegistroEmpresaView.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/pasantias",
    name: "Pasantias",
    component: Pasantias,
    meta: { requiresAuth: true }
  },
  {
    path: "/pasantias/:id/detalle",
    name: "pasantias-detalle",
    component: PasantiasDetalle,
    meta: { requiresAuth: true, roles: ['ESTUDIANTE'] }
  },
  {
    path: "/empresas",
    name: "empresas",
    component: Empresas,
    meta: { requiresAuth: true }
  },
  {
    path: "/empresas/:id/detalle",
    name: "empresas-detalle",
    component: EmpresasDetalle,
    meta: { requiresAuth: true }
  },
  {
    path: "/no-autorizado-padron",
    name: "NoAutorizadoPadron",
    component: () => import("@/views/General/NoAutorizadoPadron.vue"),
  },
  {
    path: "/contactanos",
    name: "contactanos",
    component: Contactanos,
  },

  //estudiante
  {
    path: "/perfil/estudiante",
    name: "perfil-estudiante",
    component: PerfilEstudiante,
    meta: { requiresAuth: true, roles: ['ESTUDIANTE'] }
  },
  {
    path: "/perfil/estudiante/editar",
    name: "perfil-estudiante-editar",
    component: EditarPerfilEstudiante,
    meta: { requiresAuth: true, roles: ['ESTUDIANTE'] }
  },
  {
    path: "/estudiante/solicitudes",
    name: "solicitudes-estudiante",
    component: SolicitudesEstudiante,
    meta: { requiresAuth: true, roles: ['ESTUDIANTE'] }
  },
  {
    path: "/estudiante/:id/aplicacion",
    name: "PasantiaAplicadaEstudiante",
    component: PasantiaAplicadaEstudiante,
    meta: { requiresAuth: true, roles: ['ESTUDIANTE'] }
  },

  //empresa
  {
    path: "/empresa/administrador/pasantias",
    name: "PasantiasEmpresaView",
    component: PasantiasEmpresaView,
    meta: { requiresAuth: true, roles: ['EMPRESA'] }
  },
  {
    path: "/empresa/administrador/informacion",
    name: "InformacionEmpresaView",
    component: InformacionEmpresaView,
    meta: { requiresAuth: true, roles: ['EMPRESA'] }
  },
  //No proteger
  {
    path: "/empresa/administrador/pasantias/informacion/:id",
    name: "InformacionPasantiaEmpresaView",
    component: InformacionPasantiaEmpresaView,
    props: true,
  },
  {
    path: "/empresa/administrador/pasantias/crear",
    name: "CrearPasantiaEmpresaView",
    component: CrearPasantiaEmpresaView,
    meta: { requiresAuth: true, roles: ['EMPRESA'] }
  },
  {
    path: "/empresa/administrador/pasantias/:id/editar",
    name: "EditarPasantiaEmpresaView",
    component: EditarPasantiaEmpresaView,
    props: true,
    meta: { requiresAuth: true, roles: ['EMPRESA'] }
  },
  {
    path: "/empresa/administrador/perfil",
    name: "PerfilEmpresaView",
    component: PerfilUsuarioEmpresaView,
    meta: { requiresAuth: true, roles: ['EMPRESA'] }
  },

  {
    path: "/empresa/estudiante/:idEstudiante/solicitud/:idSolicitud",
    name: "PerfilEstudianteEmpresa",
    component: PerfilEstudianteEmpresa,
    meta: { requiresAuth: true, roles: ['EMPRESA'] }
  },

  //administrador
  {
    path: "/administrador/dashboard",
    name: "DashboardAdministrador",
    component: DashboardAdministrador,
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },
  {
    path: "/administrador/empresa",
    name: "EmpresaAdministrador",
    component: EmpresaAdministrador,
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },
  {
    path: "/administrador/solicitud/empresa",
    name: "SolicitudEmpresaAdministrador",
    component: SolicitudEmpresaAdministrador,
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },
  {
    path: "/administrador/empresa/:id/detalle",
    name: "EmpresaDetalleAdministrador",
    component: EmpresaDetalleAdministrador,
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },
  {
    path: "/administrador/pasantia",
    name: "PasantiaAdministrador",
    component: PasantiaAdministrador,
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },
  {
    path: "/administrador/pasantia/sinaplicantes",
    name: "PasantiaSinAplicanteAdministrador",
    component: PasantiaSinAplicanteAdministrador,
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },
  {
    path: "/administrador/solicitud/pasantia",
    name: "SolicitudPasantiaAdministrador",
    component: SolicitudPasantiaAdministrador,
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },
  {
    path: "/administrador/pasantia/:id/detalle",
    name: "PasantiaDetalleAdministrador",
    component: PasantiaDetalleAdministrador,
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },
  {
    path: "/administrador/empresa/:idEmpresa/solicitud/:idSolicitud/usuario/:idUsuario",
    name: "EmpresaSolicitudUsuarioAdministrador",
    component: EmpresaSolicitudUsuarioAdministrador,
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },
  {
    path: "/administrador/estudiante/:idEstudiante/solicitud/:idSolicitud",
    name: "PerfilEstudianteAdministrador",
    component: PerfilEstudianteAdministrador,
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },
  {
    path: "/administrador/estudiantes",
    alias: "/administrador/estudiantes/padron",
    name: "PadronEstudiantesAdministrador",
    component: () => import("@/views/Administrador/PadronEstudiantesView.vue"),
    meta: { requiresAuth: true, roles: ['ADMIN'] }
  },

  //completado de registro
  {
    path: "/finish/register-estudiante",
    name: "FinishRegisterEstudiante",
    component: FinishRegisterEstudiante,
    meta: { requiresAuth: true, }
  },
  {
    path: "/finish/register-empresa",
    name: "FinishRegisterEmpresa",
    component: FinishRegisterEmpresa,
    meta: { requiresAuth: true, }
  },
];

const router = (keycloak) => {
  console.log('router ' + keycloak);
  const vueRouter = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
  });

  vueRouter.beforeEach(async (to, from, next) => {
    if (to.meta.requiresAuth) {
      if (!keycloak.authenticated) {
        keycloak.login({
          redirectUri: window.location.origin + to.fullPath
        })
      }else{
        const roles = to.meta.roles || [];
        const hasRole = roles.length ? roles.some(role => keycloak.hasResourceRole(role)) : true;
        
        if(!hasRole){
          return next('/');
        }else{
          return next();
        }
      }
    }else{
      return next();
    }
  });
  return vueRouter;
};


export default router;
