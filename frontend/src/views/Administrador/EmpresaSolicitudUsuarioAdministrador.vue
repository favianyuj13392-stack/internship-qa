<template>
  <navbar
    :container="'container'"
    :lightLogo="true"
    :lightNav="'nav-light justify-end'"
  />
  <!-- Start Hero -->
  <section v-if="isDataLoaded"
    class="relative table w-full py-40 bg bg-center bg-no-repeat bg-cover"
    :style="{ backgroundImage: `url(${data?.institucion.fotoInstitucion ? data.institucion.fotoInstitucion : image})` }"
    
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

  <section class="relative mb:pb-14 pb-16 -mt-3 z-1" v-if="isDataLoaded">
    <div class="container mt-10">
      <div class="grid md:grid-cols-12 grid-cols-1 gap-[30px]">
        <div class="lg:col-span-4 md:col-span-6">
          <div
            class="p-6 shadow dark:shadow-gray-700 rounded-md bg-white dark:bg-slate-900 sticky top-20"
          >
       
            <div class="flex justify-center">
              <div
                class="size-20 bg-cyan-600/5 group-hover:bg-cyan-600 text-cyan-600 group-hover:text-white rounded-md text-2xl flex align-middle justify-center items-center shadow-sm dark:shadow-gray-700 transition duration-500 mx-auto"
              >
                <img
                  :src="data.persona.fotoPerfil"
                  class="w-[100px] h-[100px] rounded-md shadow dark:shadow-gray-700"
                  alt=""
                />
              </div>
            </div>

            <div class="md:ms-4 mt-4">
              <h5 class="text-xl font-semibold">
                {{ data?.persona.nombre ? data?.persona.nombre : "" }} {{ data?.persona.apellidoPaterno ? data?.persona.apellidoPaterno : "" }} {{ data?.persona.apellidoMaterno ? data?.persona.apellidoMaterno : "" }}
              </h5>
              <div class="mt-1">
                <span class="text-slate-400 font-medium me-2 inline-block"
                  ><i
                    class="uil uil-building text-[18px] text-cyan-600 me-1"
                  ></i>
                  {{ data?.usuarioInstitucion.cargo ? data?.usuarioInstitucion.cargo : "Directora" }}</span
                >
                <span class="text-slate-400 font-medium me-2 inline-block"
                  ><i
                    class="uil uil-envelope text-[18px] text-cyan-600 me-1"
                  ></i>
                  {{ data?.usuario.correo ? data?.usuario.correo : " @ucb.edu.bo" }}</span
                >
                <span class="text-slate-400 font-medium me-2 inline-block"
                  ><i class="uil uil-phone text-[18px] text-cyan-600 me-1"></i>
                  {{ data?.persona.telefono ? data?.persona.telefono : "7777761" }}</span
                >
                <span class="text-slate-400 font-medium me-2 inline-block"
                  ><i class="uil uil-calendar-alt text-[18px] text-cyan-600 me-1"></i>
                  {{ data?.persona.fechaDeNacimiento ? data?.persona.fechaDeNacimiento : "" }}</span
                >
              </div>
            </div>
            <div class="relative flex justify-center items-center mb-4 mt-4">
              <div
                @click="aceptarSolicitud"
                class="btn rounded-md bg-green-600 hover:bg-green-700 border-green-600 hover:border-green-700 text-white ms-0 w-auto cursor-pointer "
                >Aprobar</div
              >
              <div
                @click="rechazarSolicitud"
                class="btn rounded-md bg-red-600 hover:bg-red-700 border-red-600 hover:border-red-700 text-white ms-2 w-auto cursor-pointer"
                >Rechazar</div
              >
            </div>
            <div
              class="bg-slate-50 dark:bg-slate-800 rounded-md shadow dark:shadow-gray-700 p-2 sticky top-20 mt-5"
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
                    {{ data?.institucion.direccion ? data?.institucion.direccion : "Calle 1" }}
                  </span>
                </li>

                <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Sectores:</span>
                <div class="flex flex-wrap ml-6">
                  <a v-for="sector in this.data.institucion.sectores" v-bind:key="sector">
                <span 
                      class="bg-purple-600/5 hover:bg-purple-600/20 dark:bg-purple-600/10 hover:dark:bg-purple-600/30 inline-block text-purple-600 text-[10px] font-medium rounded-md mt-2 me-1 transition-all duration-500 p-1">{{ sector }}</span>
                    </a> </div>
              </li>

                <li class="flex justify-between mt-2">
                  <span class="text-slate-400 font-medium">Correo:</span>
                  <span class="font-medium">
                    {{ data?.institucion.correo ? data?.institucion.correo : "" }}
                  </span>
                </li>

                <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Social:</span>

                <ul class="list-none text-end space-x-0.5">
                  <li class="inline" v-for="(url, name) in data.institucion.redesSociales" :key="name">
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
        </div>
        <!--end col-->

        <div class="lg:col-span-8 md:col-span-6">
          <div class="lg:col-span-8 md:col-span-7">
            <h5 class="text-xl font-semibold flex items-center">
              {{ data?.institucion.nombre ? data?.institucion.nombre : "" }}
              <span 
                v-if="this.data.institucion.activo" 
                class="text-green-600 flex items-center ml-2">
                <i class="uil uil-check-circle text-xl"></i>
              </span>
              <span 
                v-else 
                class="text-red-600 flex items-center ml-2">
                <i class="uil uil-times-circle text-xl"></i>
              </span>
            </h5>
            <p class="text-slate-400 mt-4" 
              v-if="this.data.institucion.activo">
              <span class="text-green-600"><i class="uil uil-check"></i> 
                Empresa Activa
              </span>
            </p>
            <p v-else class="text-slate-400 mt-4">
              <span class="text-red-600"><i class="uil uil-times"></i> 
                Empresa Nueva (Al aceptar al usuario se aceptará también la empresa)
              </span>
            </p>
            <p class="text-slate-400 mt-2">
              {{ data?.institucion.descripcion ? data?.institucion.descripcion : "" }}
            </p>

            <div class="grid grid-cols-12 gap-6 mt-6">
              <div class="col-span-12">
                <img
                  :src="data.institucion.logoEmpresa"
                  class="rounded-md shadow dark:shadow-gray-700"
                  alt=""
                />
              </div>
              <div class="col-span-6" v-for="fotos in data.institucion.fotos" :key="fotos">
                <img
                  :src="fotos"
                  class="rounded-md shadow dark:shadow-gray-700"
                  alt=""
                />
              </div>
            </div>
          </div>
        </div>
        <!--end col-->
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
    <!--IMFOMACION DEL LA EMPRESAS-->

    <!--FIN DE LA INFROMACION DE LA EMPRESA-->
  </section>
  <!-- Start -->

  <!--end section-->
  <footers />
  <switcher />
</template>

<script>
import navbar from "@/components/Administrador/navbarAdministrador.vue";
import footers from "@/components/footer/footer.vue";
import { useEmpresasAdminStore } from "@/stores/Administradores/empresasAdminStore"; 
import switcher from "@/components/General/switcher.vue";
import Swal from "sweetalert2";
export default {
  props: {
    idEmpresa: {
      type: String,
      required: true,
    },
    idSolicitud: {
      type: String,
      required: true,
    },
    idUsuario: {
      type: String,
      required: true,
    },
  },
  setup() {
    const empresaStore = useEmpresasAdminStore();
    return { empresaStore };
  },
  data() {
    return {
      data: {},
      idEmp: null,
      idSol: null,
      idUser: null,
      token: "",
      image:"https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
    };
  },
  components: {
    navbar,
    footers,
    switcher,
  },
  created() {
    this.idEmp = this.$route.params.idEmpresa;
    this.idSol = this.$route.params.idSolicitud;
    this.idUser = this.$route.params.idUsuario;
    this.token = this.$keycloak.idTokenParsed.sub;
  },
  mounted() {
    this.getAllInformation(this.idEmp,this.idSol,this.idUser,this.token);
  },
  methods: {
    //Obtener toda la informacion de la solicitud
    async getAllInformation(idInstitucion,idSolicitud,idUsuario,uuid){
      const loader = this.$loading.show();
      try{
        const response = await this.empresaStore.getAllInformation(idInstitucion,idSolicitud,idUsuario,uuid);
        if(response==null){
          this.data = {};
          return;
        }
        this.data = response;
        console.log(this.data);
      }catch(e){
        console.log(e);
      }finally{
        loader.hide();
      }
    },
    async handleSolicitud(active){
      const loader = this.$loading.show();
      try{
        let response = null;
        if(active){
          response = await this.empresaStore.aceptarSolicitud(this.idEmp,this.idSol,this.idUser,this.token);
        }else{
          response = await this.empresaStore.eliminarSolitud(this.idEmp,this.idSol,this.idUser,this.token);
        }
        if(response==null){
          this.data = {};
          Swal.fire({
            icon: "error",
            title: "Error",
            text: "No se pudo realizar la operación",
          });
          return;
        }
        Swal.fire({
          icon: "success",
          title: "Operación exitosa",
          text: "Se realizó la operación con éxito",
        });
        this.data = response;
        console.log(this.data);
        //Redireccionar a la pagina de solicitudes
        this.$router.push({ name: "SolicitudEmpresaAdministrador" });
      }catch(e){
        console.log(e);
      }finally{
        loader.hide();
      }
    },
    async aceptarSolicitud(){
      this.handleSolicitud(true);
    },
    async rechazarSolicitud(){
      this.handleSolicitud(false);
    },
  },
  computed: {
    mapSrc() {
      const direccionEncoded = encodeURIComponent(this.data.institucion.direccion);
      return `https://www.google.com/maps/embed/v1/place?q=${direccionEncoded}&key=AIzaSyCoOVExrC3ADb7HXkXLyBZB3zyVqClHZ7w`;
    },
    isDataLoaded() {
      return Object.keys(this.data).length > 0;
    },
  }
};
</script>

<style lang="scss" scoped></style>
