<template>
  <navbar :container="'container'" :lightNav="'nav-light justify-end'" :lightLogo="true" />
  <!-- Start Hero -->
  <section class="relative table w-full py-36 bg-[url('../../assets/images/hero/bg.webp')] bg-top bg-no-repeat bg-cover">
    <div class="absolute inset-0 bg-cyan-900/90"></div>
    <div class="container">
      <div class="grid grid-cols-1 text-center mt-10">
        <h3 class="md:text-3xl text-2xl md:leading-snug tracking-wide leading-snug font-medium text-white" v-if="this.$keycloak.authenticated">
          Pasantías de {{ this.$keycloak.tokenParsed.given_name +" "+this.$keycloak.tokenParsed.family_name }} en {{ this.institucion.nombre }}
        </h3>
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
  </section>

  <!--end section-->
  <div class="relative">
    <div
      class="shape absolute start-0 end-0 sm:-bottom-px -bottom-[2px] overflow-hidden z-1 text-white dark:text-slate-900">
      <svg class="w-full h-auto" viewBox="0 0 2880 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M0 48H1437.5H2880V0H2160C1442.5 52 720 0 720 0H0V48Z" fill="currentColor"></path>
      </svg>
    </div>
  </div>


  <!-- End Hero -->
  <section class="relative md:py-24 py-16">
    <div class="container">
      <button style="min-width: 100%; min-height: 3rem; margin-top: 0rem; margin-bottom: 2rem;"
      @click="$router.push('/empresa/administrador/pasantias/crear')"  
      class="btn btn-sm bg-cyan-600 hover:bg-cyan-700 border-cyan-600 dark:border-cyan-600 text-white rounded-md">Crear
        pasantía</button>

      <div class="grid md:grid-cols-12 grid-cols-1 mt-4 mb-8">
        <div class="md:col-span-12 text-center">
          <nav aria-label="Page navigation example">
            <ul class="inline-flex items-center -space-x-px">
              <li>
                <a @click="filterPasantias('Aprobados')" :class="{
                  'size-[95px] inline-flex justify-center items-center bg-cyan-600 text-white border-cyan-600': filter === 'Aprobados',
                  'size-[95px] inline-flex justify-center items-center text-slate-400 bg-white dark:bg-slate-900 border-gray-100 dark:border-gray-800 hover:text-white hover:bg-cyan-600 hover:border-cyan-600': filter !== 'Aprobados'
                }">Aprobados</a>
              </li>
              <li>
                <a @click="filterPasantias('sinAprobar')" :class="{
                  'size-[95px] inline-flex justify-center items-center bg-cyan-600 text-white border-cyan-600': filter === 'sinAprobar',
                  'size-[95px] inline-flex justify-center items-center text-slate-400 bg-white dark:bg-slate-900 border-gray-100 dark:border-gray-800 hover:text-white hover:bg-cyan-600 hover:border-cyan-600': filter !== 'sinAprobar'
                }">Sin Aprobar</a>
              </li>
            </ul>

          </nav>
        </div>
        <!--end col-->
      </div>
      <div class="grid md:grid-cols-12 grid-cols-1 gap-[30px]">


        <!--TARTJETAZOOO-->
        <div class="lg:col-span-12 md:col-span-12">
          <div class="grid lg:grid-cols-3 md:grid-cols-3 gap-[30px]">
            <div v-for="item in filteredDatas" :key="item"
              class="group p-6 rounded-lg border border-cyan-600/20 dark:border-cyan-600/40 bg-white dark:bg-slate-900 hover:bg-cyan-600/[0.02] hover:dark:bg-cyan-600/5 hover:shadow-md hover:shadow-cyan-600/5 transition-all duration-500">
              <div class="flex justify-between items-start">
                <div>
                  <div
                    class="size-14 flex items-center justify-center bg-white dark:bg-slate-900 shadow dark:shadow-gray-700 rounded-full mb-2">
                    <img :src="item.institucion.logoEmpresa" class="size-8" alt="" />
                  </div>
                  <a @click="goToPasantia(item. idPasantias)"
                    class="text-lg hover:text-cyan-600 font-semibold transition-all duration-500">{{ item.titulo
                    }}</a>
                </div>

                <div class="flex items-center">
                  <a @click="goToPasantia(item. idPasantias)"
                    class="btn btn-icon rounded-full bg-cyan-600/5 group-hover:bg-cyan-600 border-cyan-600/10 text-cyan-600 group-hover:text-white ms-1"><i
                      class="uil uil-arrow-up-right"></i></a>
                </div>
              </div>

              <div class="mt-3">
             
                <p class="text-slate-400 mt-2">
                  Fecha de Cierre: {{ item.fechaCierre }}
                </p>

                <div class="mt-3">
                  <a href="" v-for="area in item.areas">
                    <span
                      class="bg-rose-500/5 hover:bg-rose-500/20 dark:bg-rose-500/10 hover:dark:bg-rose-500/30 inline-block text-rose-500 px-4 text-[14px] font-medium rounded-full mt-2 me-1 transition-all duration-500">{{
                      area }}</span>
                  </a>
                  <a href="" v-for="carrera in item.carreras">
                    <span
                      class="bg-purple-600/5 hover:bg-purple-600/20 dark:bg-purple-600/10 hover:dark:bg-purple-600/30 inline-block text-purple-600 px-4 text-[14px] font-medium rounded-full mt-2 me-1 transition-all duration-500">{{
                      carrera.nombre }}</span>
                  </a>
                  <a href="">
                    <span
                      class="bg-cyan-600/5 hover:bg-cyan-600/20 dark:bg-cyan-600/10 hover:dark:bg-cyan-600/30 inline-block text-cyan-600 px-4 text-[14px] font-medium rounded-full mt-2 transition-all duration-500"><i
                        class="uil uil-map-marker"></i>
                      {{ item.institucion.direccion }}</span>
                  </a>
                </div>
              </div>
            </div>
            <!--end content-->
          </div>
          <!--end grid-->
          <!--end grid-->
          <!--PAGINACIONNN-->

          <!--end grid-->
          <!--FIN DE PAGINACION-->
        </div>
      </div>
    </div>
  </section>
  <footers />
  <switcher />
</template>
<script>
import navbar from "@/components/Empresa/NavBarEmpresa.vue";

import explore from "@/components/General/Home/explore-job.vue";
import footers from "@/components/footer/footer.vue";

import switcher from "@/components/General/switcher.vue";
import { useInstitucionesAdministracionStore } from "@/stores/Instituciones/InstitucionesAdministracionStore.js";
import Swal from "sweetalert2";
export default {
  setup() {
    const institucionesAdministracionStore = useInstitucionesAdministracionStore();
    return {
      institucionesAdministracionStore,
    };
  },

  computed: {
    filteredDatas() {
      if (this.datas.length > 0) {
        return this.datas.filter((data) => {
          if (this.filter === "Aprobados") {
            return data.activo === true;
          } else if (this.filter === "sinAprobar") {
            return data.activo === false;
          } else {
            return data;
          }
        });
      }
    },
  },


  methods: {
    filterPasantias(filter) {
      this.filter = filter;
    },

    goToPasantia(id) {
      this.$router.push('/empresa/administrador/pasantias/informacion/'+id);
    },

    async fetchPasantias() {
      let loader = this.$loading.show();
      const response = await this.institucionesAdministracionStore.fetchPasantiasInstitucionByUUID(this.$keycloak.tokenParsed.sub);
      loader.hide();
      if (response == null) {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'No se pudo cargar las pasantías',
        })

      }
      this.datas = response;
      console.log(response);
    },
    async fetchInstituciones() {
        let loader = this.$loading.show();
       const response =  await this.institucionesAdministracionStore.fetchInstitucionByUUID(this.$keycloak.idTokenParsed.sub);
        loader.hide();
       console.log(response);
       
       if(response==null){
        Swal.fire({
          title: "Error",
          text: "No se pudo cargar la informacion de la empresa",
          icon: "error",
          confirmButtonText: "Ok",
        });
        //this.$keycloak.logout();
        }
        this.institucion = response;
      },

  },
  mounted() {
    this.fetchPasantias();
    this.fetchInstituciones();
  },

  data() {
    return {
      institucion: {
        nombre: "",
      },
      filter: "Aprobados",
      datas: [{
        activo: true,
        titulo: "Pasantía en Desarrollo de Software",
        job: "Desarrollador de Software",
        fechaCierre: "2021-10-10",
        areas: ["Desarrollo de Software", "Desarrollo Web"],
        carreras: [{ nombre: "Ingeniería en Sistemas", id: 1 }],
        institucion: {
          logoEmpresa: "https://cdn-icons-png.flaticon.com/512/25/25231.png",
          direccion: "Calle 123",
        },
      }],
    };
  },
  components: {
    navbar,

    explore,
    footers,
    switcher,
  },
};
</script>

<style lang="scss" scoped></style>