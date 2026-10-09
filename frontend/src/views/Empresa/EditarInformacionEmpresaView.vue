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
              <h5 class="text-xl font-semibold text-red-500" v-if="data.activo==false">
                Sin Aprobar
              </h5>
              <h5 class="text-xl font-semibold text-cyan-600 " v-if="data.activo==true">
                Aprobado
              </h5>
            </div>
             
  
            <div class="md:mt-0 mt-4">
              
              <router-link
                to="/empresa/administrador/pasantias"
                class="btn btn-sm bg-cyan-600/5 hover:bg-cyan-600 border-cyan-600/10 hover:border-cyan-600 text-cyan-600 hover:text-white rounded-md ms-1"
                >Ver Pasantías</router-link
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
            <h5 class="text-xl font-semibold">A cerca de la compañia</h5>
            <p class="text-slate-400 mt-4">
              {{ data?.descripcion
                ? data?.descripcion
                : "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage." }}
            </p>
            
  
            <div class="grid grid-cols-12 gap-6 mt-6">
              <div class="col-span-12" v-if="data.fotos.length<3">
                <img
                  :src="data.fotoInstitucion"
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
  
            <h5 class="text-xl font-semibold mt-6">Cantidad de pasantias totales como empresa: <span class="text-cyan-600 font-large">{{ data.cantidadPasantias  }}</span></h5>
  
           
  
           
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
                  
                  <a v-for="sector in this.data.sectores">
                <span 
                      class="bg-cyan-600/5 hover:bg-cyan-600/20 dark:bg-cyan-600/10 hover:dark:bg-cyan-600/30 inline-block text-cyan-600 text-[12px] font-medium rounded-md mt-2 me-1 transition-all duration-500 p-1">{{ sector }}</span>
                    </a> </div>
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
       
        <!-- FIN DE EMPRESAS RELACINADAS-->
        <!--end grid-->
      </div>
      <!--FIN DE LA INFROMACION DE LA EMPRESA-->
    </section>
    <footers />
    <switcher />
  </template>
  
  <script>
  import navbar from "@/components/Empresa/NavBarEmpresa.vue";
  import footers from "@/components/footer/footer.vue";

import switcher from "@/components/General/switcher.vue";
import {useInstitucionesAdministracionStore} from "@/stores/Instituciones/InstitucionesAdministracionStore.js";
import Swal from "sweetalert2";
import {h} from 'vue';

  export default {
    props: {
      jobs: {
        type: Boolean,
        required: true,
      },
    },
    setup() {
      const institucionesStore = useInstitucionesAdministracionStore();
      return {institucionesStore};
    },

    


    methods: {
      onCancel() {
        this.$router.push("/");

        this.$keycloak.logout();
        document.body.style.overflow = "auto";
        return;

      },
      async fetchInstituciones() {
        let loader = this.$loading.show();
       const response =  await this.institucionesStore.fetchInstitucionByUUID(this.$keycloak.idTokenParsed.sub);
       const response2 = await this.institucionesStore.fetchAprobadosInstitucionByUUID(this.$keycloak.idTokenParsed.sub);
        loader.hide();
       console.log(response);
       
       if(response==null){
        Swal.fire({
          title: "Error",
          text: "No se pudo cargar la informacion de la empresa",
          icon: "error",
          confirmButtonText: "Ok",
        });
        this.$keycloak.logout();
        }
        this.data = response;
        this.activo = response2;

        if(this.activo==false){
          let loader2 = this.$loading.show(
            {
              container: null,
              canCancel: true,
              onCancel: this.onCancel,
              opacity: 1,
            },
            {
              /*
              default: `Tu usuario aún no ha sido aprobado. Debes contactarte con la U.S.E.I. de la UCB para que aprueben tu usuario. 
              \n Si haces click se cerrará la sesión`,*/
              default: h('div', {class: 'text-center'}, [
                h('p', 'Tu usuario aún no ha sido aprobado. Debes contactarte con la U.S.E.I. de la UCB para que aprueben tu usuario.'),
                h('p', 'Si haces click se cerrará la sesión')
              ])
            }
          );
          //block scroll
          document.body.style.overflow = "hidden";

          setTimeout(() => {
            loader2.hide();
            this.$router.push("/");
            document.body.style.overflow = "auto";

            this.$keycloak.logout();
          }, 100000);


         
    }
      },

    
      
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
        activo: false,
        data: {
          nombre: "",
          descripcion: "",
          direccion: "",
          fotoInstitucion: "",
          fotos: [
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          
          ],
          redesSociales: {
            web: "",
            facebook: "",
            twitter: "",
            linkedin: "",
            instagram: "",
          },
          sectores: [],
          cantidadPasantias: 0,
          activo: false,


        },
        id: "",
        image:
          "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
  
        datas: [
          {
            id: 1,
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Facebook",
            day: "2 days ago",
            type: "Full Time",
            job: "Web Designer / Developer",
            country: "Australia",
            vacancy: "21 applied",
            vacancy2: "of 40 vacancy",
            job: "6 Jobs",
            location: "Rush",
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 2,
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Google",
            day: "2 days ago",
            type: "Part Time",
            job: "Marketing Director",
            country: "USA",
            vacancy: "21 applied",
            vacancy2: "of 40 vacancy",
            job: "6 Jobs",
            location: "Rush",
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 3,
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Android",
            day: "2 days ago",
            type: "Remote",
            job: "Application Developer",
            country: "China",
            vacancy: "21 applied",
            vacancy2: "of 40 vacancy",
            job: "6 Jobs",
            location: "Rush",
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 4,
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Lenovo",
            day: "2 days ago",
            type: "WFH",
            job: "Senior Product Designer",
            country: "Dubai",
            vacancy: "21 applied",
            vacancy2: "of 40 vacancy",
            job: "6 Jobs",
            location: "Rush",
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 5,
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Spotify",
            day: "2 days ago",
            type: "Full Time",
            job: "C++ Developer",
            country: "India",
            vacancy: "21 applied",
            vacancy2: "of 40 vacancy",
            job: "6 Jobs",
            location: "Rush",
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 6,
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Linkedin",
            day: "2 days ago",
            type: "Remote",
            job: "Php Developer",
            country: "Pakistan",
            vacancy: "21 applied",
            vacancy2: "of 40 vacancy",
            job: "6 Jobs",
            location: "Rush",
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 7,
            job: "Software Engineering",
            day: "Posted 3 Days ago",
            type: "Full Time",
            salary: "$950 - $1100/mo",
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Circle CI Ltd.",
            location: "Australia",
            job: "6 Jobs",
  
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 8,
            job: "Web Developer",
            day: "Posted 3 Days ago",
            type: "Remote",
            salary: "$2500 - $2600/mo",
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Skype Ltd.",
            location: "America",
            job: "6 Jobs",
  
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 9,
            job: "UX/UI Designer",
            day: "Posted 3 Days ago",
            type: "Freelance",
            salary: "$3500 - $3600/mo",
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Snapchat Ltd.",
            location: "Canada",
            job: "6 Jobs",
  
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 10,
            job: "Human Resource(HR)",
            day: "Posted 3 Days ago",
            type: "Part Time",
            salary: "$2000 - $2500/mo",
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Shreethemes Ltd.",
            location: "UK",
            job: "6 Jobs",
  
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 11,
            job: "Web Designer",
            day: "Posted 3 Days ago",
            type: "Full Time",
            salary: "$1500 - $1600/mo",
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Telegram Ltd.",
            location: "China",
            job: "6 Jobs",
  
            title: "Digital Marketing Solutions for Tomorrow",
          },
          {
            id: 12,
            job: "Graphic Designer",
            day: "Posted 3 Days ago",
            type: "Part time",
            salary: "$500 - $600/mo",
            image:
              "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
            name: "Whatsapp Ltd.",
            location: "India",
            job: "6 Jobs",
  
            title: "Digital Marketing Solutions for Tomorrow",
          },
        ],
      };
    },
    components: {
      navbar,
      footers,
      switcher,
    },
    mounted() {
      this.fetchInstituciones();


      this.id = this.$route.params.id;
      this.data = this.datas.find((item) => item.id === parseInt(this.id));
    },
  };
  </script>
  
  <style lang="scss" scoped></style>
  