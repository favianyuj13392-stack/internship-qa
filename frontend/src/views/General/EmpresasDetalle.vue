<template>
  <navbar
    :container="'container'"
    :lightLogo="true"
    :lightNav="'nav-light justify-end'"
  />
  <!-- Start Hero -->
  <section
    class="relative table w-full py-40 bg-center bg-no-repeat bg-cover" 
    :style="{ backgroundImage: `url(${data?.fotoInstitucion ? data.fotoInstitucion : '@/assets/images/1.jpg'})` }"

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

  <section class="relative mb:pb-24 pb-16 -mt-16 z-1">
    <div class="container">
      <div class="grid grid-cols-1">
        <div
          class="md:flex justify-between items-center shadow dark:shadow-gray-700 rounded-md p-6 bg-white dark:bg-slate-900"
        >
          <div class="flex items-center">
            <img
              :src="data?.logoEmpresa ? data?.logoEmpresa : image"
              class="size-20 p-3 shadow dark:shadow-gray-700 rounded-md bg-slate-50 dark:bg-slate-800"
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
              :href="data.redesSociales.web"
              v-if="data.redesSociales.web"
              class="btn btn-sm bg-cyan-600 hover:bg-cyan-700 border-cyan-600 dark:border-cyan-600 text-white rounded-md"
              >Visitar</a
            >
            <a
              href=""
              class="btn btn-sm bg-cyan-600/5 hover:bg-cyan-600 border-cyan-600/10 hover:border-cyan-600 text-cyan-600 hover:text-white rounded-md ms-1"
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
          <h5 class="text-xl font-semibold">Sobre la empresa</h5>
          <p class="text-slate-400 mt-4">
            {{ data.descripcion ? data.descripcion : "Skype is a telecommunications application that specializes in providing video chat and voice calls between computers, tablets, mobile devices, the Xbox One console, and smartwatches over the Internet. Skype also provides instant messaging services. Users may transmit text, video, audio, and images. Skype allows video conference calls."}}
          </p>
       

          <div class="grid grid-cols-12 gap-6 mt-6">
            <div class="col-span-12" v-if="data.fotos.length<3">
              <img

                :src="data?.fotoInstitucion ? data.fotoInstitucion : image"
                class="rounded-md shadow dark:shadow-gray-700"
                alt=""
              />
            </div>
            <div class="col-span-6" v-for="foto in data.fotos">
              <img
                :src="foto"
                class="rounded-md shadow dark:shadow-gray-700"
                alt=""
              />
            </div>
           
          </div>

          <h5 v-if="data.pasantias.length>0" class="text-xl font-semibold mt-6">Pasantias disponibles:</h5>

          <div v-if="data.pasantias.length>0" class="grid lg:grid-cols-2 grid-cols-1 gap-6 mt-6" >
            <div
              class="group relative overflow-hidden rounded-md shadow dark:shadow-gray-800"
              v-for="item in data.pasantias"
              >
              <div class="p-6">
                <router-link :to="{ name: 'pasantias-detalle', params: { id: item.idPasantias} }"
                  class="title h5 text-lg font-semibold hover:text-cyan-600"
                  >{{item.titulo}}</router-link>
                <p class="text-slate-400 mt-2">
                   {{ item.descripcion.substring(0, 100)}}{{item.descripcion.length>100 ? "...":""}}
                </p>

                <div class="flex justify-between items-center mt-4">
                  <span
                    class="bg-cyan-600/5 text-cyan-600 text-xs font-bold px-2.5 py-0.5 rounded h-5"
                    >Acaba el: {{ item.fechaCierre }}</span
                  >

                  
                </div>
              </div>

              <div
                class="flex items-center p-6 border-t border-gray-100 dark:border-gray-700"
              >
                <img
                  :src="data.logoEmpresa"
                  class="size-12 shadow-md dark:shadow-gray-800 rounded-md p-2 bg-white dark:bg-slate-900"
                  alt=""
                />

                <div class="ms-3">
                  <h6 class="mb-0 font-semibold text-base">{{ data.nombre }}</h6>
                  <span class="text-slate-400 text-sm">{{ data.direccion }}</span>
                </div>
              </div>
            </div>
            <!--end content-->

            
            <!--end content-->
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
                <span class="font-medium">{{ data.direccion }}</span>
              </li>

             

              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Correo:</span>
                <span class="font-medium">{{ this.data.correo }}</span>
              </li>

              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Sectores:</span>
                <div class="flex flex-wrap ml-16">
                  <span class="font-medium" v-for="sector in this.data.sectores">{{ sector}}</span>
                </div>
              </li>

              

              <li class="flex justify-between mt-2" v-if="this.data.redesSociales.web">
                <span class="text-slate-400 font-medium">Website:</span>
                <span class="font-medium">{{ this.data.redesSociales.web }}</span>
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
      <div class="grid grid-cols-1 pb-8 py-10 text-center" v-if="empresasRelacionadas.length>0">
        <h3
          class="mb-4 md:text-[26px] md:leading-normal text-2xl leading-normal font-semibold"
        >
          Empresas Relacionadas
        </h3>

        <p class="text-slate-400 dark:text-slate-300 max-w-xl mx-auto">
          Explora empresas relacionadas con la empresa actual y descubre nuevas oportunidades.
        </p>
      </div>
      <div class="grid lg:grid-cols-4 md:grid-cols-2 grid-cols-1 gap-[30px]">
        <div
          v-for="item in empresasRelacionadas"
          :key="item"
          class="group relative p-6 rounded-md shadow dark:shadow-gray-700 mt-6"
        >
          <div
            class="size-14 flex items-center justify-center bg-white dark:bg-slate-900 shadow-md dark:shadow-gray-700 rounded-md relative -mt-12"
          >
            <img :src="item.logoEmpresa" class="size-8" alt="" />
          </div>

          <div class="mt-4">
            <a 
            @click="goToEmpresa(item.idInstituciones)"
            class="text-lg hover:text-cyan-600 font-semibold">{{
              item.nombre
            }}</a>
            <p class="text-slate-400 mt-2">{{ item.correo }}</p>
          </div>

          <div
            class="mt-4 pt-4 border-t border-gray-100 dark:border-gray-700 flex justify-between"
          >
            <span class="text-slate-400"
              ><i class="uil uil-map-marker"></i> {{ item.direccion }}</span
            >
            <span class="block font-semibold text-cyan-600">{{
              item.job
            }}</span>
          </div>
        </div>
        <!--end content-->
      </div>
      <!-- FIN DE EMPRESAS RELACINADAS-->
      <!--end grid-->
    </div>
    <!--FIN DE LA INFROMACION DE LA EMPRESA-->
  </section>
  <footers />
  <switcher />
</template>

<script>
import navbar from "@/components/General/navbarGeneral.vue";
import footers from "@/components/footer/footer.vue";
import { useEmpresasStore } from "@/stores/Estudiantes/empresasStore.js";
import switcher from "@/components/General/switcher.vue";
import Swal from "sweetalert2";
export default {
  setup() {
    const empresasStore = useEmpresasStore();
    return  {empresasStore} ;
  },
  computed: {
    mapSrc() {
      // Construir la URL del mapa de Google con la dirección
      const direccionEncoded = encodeURIComponent(this.data.direccion);
      return `https://www.google.com/maps/embed/v1/place?q=${direccionEncoded}&key=AIzaSyCoOVExrC3ADb7HXkXLyBZB3zyVqClHZ7w`;
      // Reemplaza TU_API_KEY con tu propia clave de API de Google Maps
    },
  },
  
  data() {
    return {
      data: {
        fotos:[],
        sectores: ["prueba","prueba2"],
        redesSociales: {
          web: "https://dribbble.com/shreethemes",
        },
        pasantias:[],

      },
      empresasRelacionadas: [],
      
      id: "",
      image:
        "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",

      imagenesRedes:{
        facebook:"https://www.facebook.com/shreethemes",
        twitter:"https://twitter.com/shreethemes",
        linkedin:"http://linkedin.com/company/shreethemes",
        instagram:"https://www.instagram.com/shreethemes",
        youtube:"https://www.youtube.com/shreethemes",
        tiktok:"https://www.tiktok.com/shreethemes",
        web:"https://dribbble.com/shreethemes",
      }
    };
  },
  components: {
    navbar,
    footers,
    switcher,
  },
  methods: {
     async fetchEmpresa() {
      let loader = this.$loading.show();
      const response = await this.empresasStore.getEmpresaById(this.id);
      if(response==null){
        loader.hide();
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "No se encontro la empresa",
        });
        this.$router.push("/empresas");
        return;
      }
      console.log(response);
      this.data = response;
      //if begins with https://example.com
      if(this.data.fotoInstitucion.startsWith("https://example.com")){
        this.data.fotoInstitucion = 'https://inascleaningservices.llc/wp-content/uploads/2024/03/white-corner-desk-scaled.jpg';
       
      }
      console.log(this.data);
    
      loader.hide();
    },
    async fetchEmpresasRelacionadas(){
      let loader = this.$loading.show();
      const response = await this.empresasStore.getEmpresasRelacionadas(this.id);
      console.log(response);
      this.empresasRelacionadas = response;
      console.log(this.empresasRelacionadas);
      loader.hide();
    },
    goToEmpresa(id){
        //href not using router
        window.location.href = `/empresas/${id}/detalle`;
      
    }
   
  },
  mounted() {
    this.id = this.$route.params.id;
    this.fetchEmpresa();
    this.fetchEmpresasRelacionadas();

    //this.data = this.datas.find((item) => item.id === parseInt(this.id));
  },
  props: {
    id: {
      type: String,
      required: true,
    },
  },
};
</script>

<style lang="scss" scoped></style>
