<template>
  <navbar
    :container="'container'"
    :lightLogo="true"
    :lightNav="'nav-light justify-end'"
  />
  <!-- Start Hero -->
  <section
    class="relative table w-full py-40 bg bg-center bg-no-repeat bg-cover"
    :style="{ backgroundImage: `url(${data?.fotoInstitucion ? data.fotoInstitucion : image})` }"
  >
    <div class="absolute inset-0 bg-cyan-900/60"></div>
  </section>
  <!--end section-->
  <div class="relative">
    <div
      class="shape absolute start-0 end-0 sm:-bottom-px -bottom-[2px] overflow-hidden z-1 text-white dark:text-slate-900"
    >
      <svg
        class="w-full h-auto"
        viewBox="0 0 2880 48"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M0 48H1437.5H2880V0H2160C1442.5 52 720 0 720 0H0V48Z"
          fill="currentColor"
        ></path>
      </svg>
    </div>
  </div>
  <!-- End Hero -->

  <section class="relative mb:pb-24 pb-16 -mt-16 z-1" v-if="isDataLoaded">
    <div class="container">
      <div class="grid grid-cols-1">
        <div
          class="md:flex justify-between items-center shadow dark:shadow-gray-700 rounded-md p-6 bg-white dark:bg-slate-900"
        >
          <div class="flex items-center">
            <img
              :src="data?.logoEmpresa ? data?.logoEmpresa : logoEmpresa"
              class="size-20 shadow dark:shadow-gray-700 rounded-md bg-slate-50 dark:bg-slate-800"
              alt=""
            />

            <div class="ms-4">
              <h5 class="text-xl font-bold">
                {{ data?.nombre ? data?.nombre : "Skype" }}
              </h5>
              <h6 class="text-base text-slate-400">
                <i class="uil uil-map-marker"></i>
                {{ data?.direccion ? data.direccion : "Canberra, Australia" }}
              </h6>
            </div>
          </div>

          <div class="md:mt-0 mt-4">
            <a
             href="#pasantias"
              class="btn btn-sm bg-cyan-600 hover:bg-cyan-700 border-cyan-600 dark:border-cyan-600 text-white rounded-md"
              >Ver Pasantías</a
            >
          </div>
        </div>
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
    <!--IMFOMACION DEL LA EMPRESAS-->

    <div class="container mt-12">
      <div class="grid md:grid-cols-12 grid-cols-1 gap-[30px]">
        <div class="lg:col-span-8 md:col-span-7">
          <h5 class="text-xl font-semibold">
            Acerca de {{ data?.nombre ? data?.nombre : "Skype" }}
          </h5>
          <p class="text-slate-400 mt-2">
            {{ data?.descripcion ? data?.descripcion : "Skype is a telecommunications application that specializes in providing video chat and voice calls between computers, tablets, mobile devices, the Xbox One console, and smartwatches over the Internet." }}
          </p>

          <div class="grid grid-cols-12 gap-6 mt-6">
            <div class="col-span-12">
              <img
                :src="data?.fotoInstitucion ? data?.fotoInstitucion : fotoInstitucion"
                class="rounded-md shadow dark:shadow-gray-700"
                alt=""
              />
            </div>
            <div id="pasantias" class="col-span-6" v-for="imagen in data?.fotos" v-bind:key="imagen">
              <img
                :src="imagen"
                class="rounded-md shadow dark:shadow-gray-700"
                alt=""
              />
            </div>
          </div>

          <h5  class="text-xl font-semibold mt-6">Pasantias disponibles aprobadas:</h5>

          <div class="grid lg:grid-cols-2 grid-cols-1 gap-6 mt-6">
            <div  
              class="group relative overflow-hidden rounded-md shadow dark:shadow-gray-800"
              v-for="item in data?.pasantias" :key="item"
              >
              <div class="p-6">
                <router-link
                                  :to="{ name: 'PasantiaDetalleAdministrador', params: { id: item.idPasantias } }"
                  class="title h5 text-lg font-semibold hover:text-cyan-600"
                  >
                  {{ item.titulo ? item.titulo : "Digital Marketing Solutions for Tomorrow" }}
                  </router-link
                >
                <p class="text-slate-400 mt-2">
                  <i class="uil uil-clock text-cyan-600"></i> Cierre:
                  {{ item.fechaCierre ? item.fechaCierre : "2 days ago" }}
                </p>

                <div class="flex flex-wrap justify-between items-center mt-4">
                  <span v-for="area in item.areas" :key="area"
                    class="bg-cyan-600/5 text-cyan-600 text-xs font-bold px-2.5 py-0.5 rounded h-5">
                    {{ area }}
                    </span
                  >
                </div>
              </div>

              <div
                class="flex items-center p-6 border-t border-gray-100 dark:border-gray-700"
              >
                <img
                  :src="data?.logoEmpresa ? data?.logoEmpresa : logoEmpresa"
                  class="size-12 shadow-md dark:shadow-gray-800 rounded-md p-1.5 bg-white dark:bg-slate-900"
                  alt=""
                />

                <div class="ms-3">
                  <h6 class="mb-0 font-semibold text-base">
                    {{ data?.nombre ? data?.nombre : "Circle CI Ltd." }}
                  </h6>
                  <span class="text-slate-400 text-sm">
                    <i class="uil uil-location-point"></i> {{ data?.direccion ? data.direccion : "Australia" }}
                  </span>
                </div>
              </div>
            </div>
          </div>

        </div>
        <!--end col-->

        <div class="lg:col-span-4 md:col-span-5">
          <div
            class="bg-slate-50 dark:bg-slate-800 rounded-md shadow dark:shadow-gray-700 p-6 sticky top-20"
          >
            <div class="w-full leading-[0] border-0">
              <iframe
                :src="mapSrc"
                style="border: 0"
                class="w-full h-[350px] rounded-md shadow dark:shadow-gray-700"
                allowfullscreen
              ></iframe>
            </div>

            <ul class="list-none mt-4">
              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Dirección:</span>
                <span class="font-medium">
                  {{ data?.direccion ? data.direccion : "Canberra, Australia" }}
                </span>
              </li>

              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Correo:</span>
                <span class="font-medium">
                  {{ data?.correo ? data.correo : "" }}
                </span>
              </li>

              <li class="flex justify-between mt-2 ">
                <span class="text-slate-400 font-medium">Sectores:</span>
                <div class="flex flex-col mb-2">
                  <a v-for="sector in this.data.sectores" v-bind:key="sector">
                <span 
                      class="bg-purple-600/5 hover:bg-purple-600/20 dark:bg-purple-600/10 hover:dark:bg-purple-600/30 inline-block text-purple-600 text-[10px] font-medium rounded-md mt-2 me-1 transition-all duration-500 p-1">{{ sector }}</span>
                    </a> 
                </div>
            
                  
                  
                  </li>
              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Social:</span>

                <ul class="list-none text-end space-x-0.5">
                  <li class="inline" v-for="(url, name) in data.redesSociales" :key="name">
                    <a
                      :href="url"
                      target="_blank"
                      class="btn btn-icon btn-sm border-2 border-gray-200 dark:border-gray-700 rounded-md hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600 hover:text-white dark:text-white text-slate-400"
                    >
                      <i :class="`uil uil-${name}`" :title="name" class="align-middle"></i>
                    </a>
                  </li>
                </ul>
                <!--end icon-->
              </li>
            </ul>
          </div>
        </div>
        <!--end col-->
      </div>

      <!--- MAS EMPREASA RELACIONADA-->

      <!--end container-->

      <!--end container-->
      <!-- FIN DE EMPRESAS RELACINADAS-->
      <!--end grid-->
    </div>
    <div class="grid grid-cols-1 pb-8 py-10 text-center">
      <h3
        class="mb-4 md:text-[26px] md:leading-normal text-2xl leading-normal font-semibold"
      >
        Usuarios de la empresa
      </h3>

      <p class="text-slate-400 dark:text-slate-300 max-w-xl mx-auto">
        Todos los usuarios que tienen acceso a la empresa
      </p>
    </div>
    <div class="container">
      <div
        class="grid lg:grid-cols-5 md:grid-cols-3 sm:grid-cols-2 grid-cols-1 gap-[30px]"
      >
        <div
          v-for="item in datas"
          :key="item"
          class="group px-3 py-5 rounded-md shadow dark:shadow-gray-700 hover:shadow-cyan-600/10 dark:hover:shadow-cyan-600/10 text-center bg-white dark:bg-slate-900 hover:bg-cyan-600/5 dark:hover:bg-cyan-600/5 transition duration-500"
        >
          <router-link>
            <i
              @click="deleteRelacion(item)"
              class="uil uil-trash-alt size-8 bg-red-600/5 hover:bg-red-600 text-red-600 hover:text-white rounded-md text-2xl flex align-middle justify-center items-center shadow-sm dark:shadow-gray-700 transition duration-500"
            ></i>
          </router-link>
          <div
            class="size-16 bg-cyan-600/5 group-hover:bg-cyan-600 text-cyan-600 group-hover:text-white rounded-md text-2xl flex align-middle justify-center items-center shadow-sm dark:shadow-gray-700 transition duration-500 mx-auto"
          >
            <img
              :src="item.fotoPerfil"
              class="size-16"
              alt=""
            />
          </div>

          <div class="content mt-1">
            <a
              href=""
              class="title text-lg font-semibold hover:text-cyan-600"
              >{{ item.nombre }} {{ item.apellidoPaterno }}</a
            >
            <p class="text-slate-400 mt-1">
              {{ item.correo }}
            </p>
          </div>
        </div>

        <!--end content-->
      </div>
      <!--end grid-->
    </div>
    <!--FIN DE LA INFROMACION DE LA EMPRESA-->
  </section>
  <footers />
  <switcher />
</template>

<script>
import navbar from "@/components/Administrador/navbarAdministrador.vue";

import footers from "@/components/footer/footer.vue";

import switcher from "@/components/General/switcher.vue";
import { useEmpresasAdminStore } from "@/stores/Administradores/empresasAdminStore"; 
import Swal from "sweetalert2";
export default {
  methods: {
    async getEmpresaById(id) {
      const loader = this.$loading.show();
      try {
        const empresa = await this.empresasStore.getEmpresaById(id, this.$keycloak.idTokenParsed.sub)
        if(empresa){
          this.data = empresa;
          return;
        }
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "No se pudo obtener la empresa",
        });
      } catch (error) {
        console.log(error);
      }finally{
        loader.hide();
      }
    },
    async getSolicitudesByIdEmpresa(id) {
      const loader = this.$loading.show();
      try {
        const empresa = await this.empresasStore.getSolicitudesByIdEmpresa(id, this.$keycloak.idTokenParsed.sub)
        if(empresa){
          this.datas = empresa;
          console.log(this.data); 
          return;
        }
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "No se pudo obtener la empresa",
        });
      } catch (error) {
        console.log(error);
      }finally{
        loader.hide();
      }
    },
    async deleteRelacion(item) {
      const confirmResult = await Swal.fire({
        title: '¿Estás seguro?',
        text: "No podrás revertir esto!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar!',
        cancelButtonText: 'Cancelar'
      });
      const idInstitucion=item.idInstituciones;
      const idSolicitud= item.idUsuariosInstitucion;
      const idUsuario= item.idUsuarios;
      if (confirmResult.isConfirmed) {
        const loader = this.$loading.show();
        try {
          const response = await this.empresasStore.eliminarSolitud(idInstitucion,idSolicitud,idUsuario,this.$keycloak.idTokenParsed.sub);
          if (response) {
            this.getSolicitudesByIdEmpresa(this.id);
            return;
          }
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "No se pudo eliminar la relación",
          });
        } catch (error) {
          console.log(error);
        } finally {
          loader.hide();
        }
      }
    },
  },
  props: {
    id: {
      type: String,
      required: true,
    }
  },
  data() {
    return {
      empresasStore: useEmpresasAdminStore(),
      data: "",
      id: "",
      image:
        "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",

      datas: [],
    };
  },
  components: {
    navbar,
    footers,
    switcher,
  },
  mounted() {
    this.id = this.$route.params.id;
    this.getEmpresaById(this.id);
    this.getSolicitudesByIdEmpresa(this.id);
  },
  computed: {
    mapSrc() {
      const direccionEncoded = encodeURIComponent(this.data.direccion);
      return `https://www.google.com/maps/embed/v1/place?q=${direccionEncoded}&key=AIzaSyCoOVExrC3ADb7HXkXLyBZB3zyVqClHZ7w`;
    },
    isDataLoaded() {
      return Object.keys(this.data).length > 0;
    },
  }
};
</script>

<style lang="scss" scoped></style>
