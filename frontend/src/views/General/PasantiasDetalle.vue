<template>
  <navbar :container="'container'" :lightNav="'justify-end'" />

  <!-- Start -->
  <section class="bg-slate-50 dark:bg-slate-800 md:py-24 py-16">
    <div class="container mt-10">
      <div class="grid md:grid-cols-12 grid-cols-1 gap-[30px]">
        <div class="lg:col-span-8 md:col-span-6">
          <div
            class="md:flex items-center p-6 shadow dark:shadow-gray-700 rounded-md bg-white dark:bg-slate-900 mb-6"
          >
            <img
              :src="data.institucion.logoEmpresa"
              class="rounded-full size-28 p-4 bg-white dark:bg-slate-900 shadow dark:shadow-gray-700"
              alt="logo de la empresa"
            />

            <div class="md:ms-4 md:mt-0 mt-6">
              <h5 class="text-xl font-semibold">
                {{ data.titulo}}
              </h5>
              
              <div class="mt-2">
                <span class="text-slate-400 font-medium me-2 inline-block"
                  ><i
                    class="uil uil-building text-[18px] text-cyan-600 me-1"
                  ></i>
                  {{ data.institucion.nombre}}</span
                >
                <span class="text-slate-400 font-medium me-2 inline-block"
                  ><i
                    class="uil uil-map-marker text-[18px] text-cyan-600 me-1"
                  ></i>
                  {{
                    data.institucion.direccion
                  }}</span
                >
              </div>
            </div>
          </div>
          <!--detallesssss-->
          <h5 class="text-lg font-semibold">Detalles de la pasantia:</h5>

          <p class="text-slate-400 mt-4" v-for="paragraph in parsedData">
            {{ paragraph }}
          </p>
          <ul class="list-none">
            <li v-for="funcion in data.funciones" class="text-slate-400 mt-2">
              <i class="uil uil-arrow-right text-cyan-600 me-1"></i>
              {{ funcion }}
            </li>
          </ul>

          <h5 class="text-lg font-semibold mt-6">
            Requisistos necesarios y calificaciones:
          </h5>
          <!-- <p class="text-slate-400 mt-4">
            It sometimes makes sense to select texts containing the various
            letters and symbols specific to the output language.
          </p> -->
          <ul class="list-none">
            <li v-for="requisito in data.requisitos" :key="item" class="text-slate-400 mt-2">
              <i class="uil uil-arrow-right text-cyan-600 me-1"></i>
              {{ requisito }}
            </li>
          </ul>

          <div class="mt-5" v-if="this.$keycloak.authenticated">
            <a
              @click="aplicarPasantia()"
              class="btn rounded-md bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white md:ms-2 w-full md:w-auto"
              >Postula ahora</a
            >
          </div>
        </div>
        <!--end col-->

        <div class="lg:col-span-4 md:col-span-6">
          <div
            class="shadow dark:shadow-gray-700 rounded-md bg-white dark:bg-slate-900 sticky top-20"
          >
            <div class="p-6">
              <h5 class="text-lg font-semibold">Más información</h5>
            </div>
            <div class="p-6 border-t border-slate-100 dark:border-t-gray-700">
              <ul class="list-none">
                <li class="flex items-center">
                  <i data-feather="user-check" class="size-5"></i>

                  <div class="ms-4">
                    <p class="font-medium">Descripcion de la empresa:</p>
                    <span class="text-purple-600 font-medium text-sm">{{
                      data.institucion.descripcion.slice(0, 120)
                    }}...</span>
                  </div>
                </li>

                <li class="flex items-center mt-3">
                  <i data-feather="map-pin" class="size-5"></i>

                  <div class="ms-4">
                    <p class="font-medium">Ubicacion:</p>
                    <span class="text-purple-600 font-medium text-sm">{{
                      data.institucion.direccion
                    }}</span>
                  </div>
                </li>

                <li class="flex items-center mt-3">
                  <i data-feather="monitor" class="size-5"></i>

                  <div class="ms-4">
                    <p class="font-medium">Correo Electronico:</p>
                    <span class="text-purple-600 font-medium text-sm">{{
                      data.institucion.correo
                    }}</span>
                  </div>
                </li>

                <li class="flex items-center mt-3">
                  <i data-feather="briefcase" class="size-5"></i>

                  <div class="ms-4">
                    <p class="font-medium">Sectores:</p>
                    <p v-for="sector in data.institucion.sectores">
                      <span class="text-purple-600 font-medium text-sm"
                      >{{ sector }}</span
                    >
                    </p>
                  </div>
                </li>

              <li class="flex justify-between mt-6" v-if="this.data.institucion.redesSociales.web">
                <span class="text-slate-400 font-medium">Website:</span>
                <span class="font-medium">{{ this.data.institucion.redesSociales.web }}</span>
              </li>

              <li class="flex justify-between mt-6">
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

                <!-- <li class="flex items-center mt-3">
                  <i data-feather="book" class="size-5"></i>

                  <div class="ms-4">
                    <p class="font-medium">Qualifications:</p>
                    <span class="text-purple-600 font-medium text-sm">MCA</span>
                  </div>
                </li>

                <li class="flex items-center mt-3">
                  <i data-feather="dollar-sign" class="size-5"></i>

                  <div class="ms-4">
                    <p class="font-medium">Salary:</p>
                    <span class="text-purple-600 font-medium text-sm"
                      >$4000 - $4500</span
                    >
                  </div>
                </li>

                <li class="flex items-center mt-3">
                  <i data-feather="clock" class="size-5"></i>

                  <div class="ms-4">
                    <p class="font-medium">Date posted:</p>
                    <span class="text-purple-600 font-medium text-sm"
                      >28th Feb, 2023</span
                    >
                  </div>
                </li> -->
              </ul>
            </div>
          </div>
        </div>
        <!--end col-->
      </div>
      <!--end grid-->
    </div>
    <!--end container-->

    <div class="container lg:mt-24 mt-16">
      <!--end grid-->

      <div class="grid grid-cols-1 pb-8 text-center">
        <h3
          class="mb-4 md:text-[26px] md:leading-normal text-2xl leading-normal font-semibold"
        >
          Pasantias similares
        </h3>

        <p class="text-slate-400 max-w-xl mx-auto">
          Conoce mas pasantias con areas similares a tu busqueda actual
        </p>
      </div>
      <!--end grid-->

      <div
        v-if="related"
        class="grid lg:grid-cols-3 md:grid-cols-2 mt-8 gap-[30px]"
      >
        <div
          v-for="pasantia in related"
          :key="pasantia.idPasantias"
          class="group shadow dark:shadow-gray-700 p-6 rounded-md bg-white dark:bg-slate-900"
        >
          <div class="flex items-center justify-between">
            <div class="flex items-center">
              <div
                class="size-14 flex items-center justify-center bg-white dark:bg-slate-900 shadow dark:shadow-gray-700 rounded-md"
              >
                <img :src="pasantia.institucion.logoEmpresa" class="size-8" alt="logo empresa" />
              </div>

              <div class="ms-3">
                <router-link 
                  class="block text-[16px] font-semibold hover:text-cyan-600 transition-all duration-500"
                  >{{ pasantia.institucion.nombre}}</router-link
                >
              </div>
            </div>
          </div>

          <div class="mt-6">
            <a
              @click="this.$router.push({name: 'pasantias-detalle', params: {id: pasantia.idPasantias}})"
              class="text-lg hover:text-cyan-600 font-semibold transition-all duration-500"
              >{{ pasantia.titulo }}</a
            >
            <h6 class="text-base font-medium">
              <i class="uil uil-map-marker"></i> {{ pasantia.institucion.direccion }}
            </h6>
          </div>

          <div class="mt-6">
            <!-- <div
              class="w-full bg-gray-100 dark:bg-gray-800 rounded-full h-[6px]"
            >
              <div
                class="bg-cyan-600 h-[6px] rounded-full"
                style="width: 55%"
              ></div>
            </div> -->
            <div class="mt-2">
              <p class="text-slate-400 dark:text-white font-semibold inline-block">Carreras:</p>
              <p v-for="carrera in pasantia.carreras" class="text-slate-400"
                ><span
                  class="text-slate-900 dark:text-white font-semibold inline-block"
                  >{{carrera.nombre }}</span
                >
               </p
              >
            </div>
          </div>
        </div>
        <!--end content-->
      </div>
      <!--end grid-->

      <div v-if="seemore" class="grid md:grid-cols-12 grid-cols-1 mt-8">
        <div class="md:col-span-12 text-center">
          <router-link
            to="job-grid-two"
            class="btn btn-link text-slate-400 hover:text-cyan-600 after:bg-cyan-600 duration-500 ease-in-out"
            >See More Jobs <i class="uil uil-arrow-right align-middle"></i
          ></router-link>
        </div>
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
  </section>
  <postular-pasantia-modal 
    
      v-if="showAplicarPasantiaModal==true" 
      @cancel="cerrarModalPasantia()" 
      :pasantiaId="this.id"
      
      />

  <!--end section-->
  <!-- End -->
  <footers />
  <switcher />
</template>

<script>
import navbar from "@/components/General/navbarGeneral.vue";

import footers from "@/components/footer/footer.vue";

import switcher from "@/components/General/switcher.vue";
import { usePasantiasStore } from "@/stores/Pasantias/pasantiasStore";
import Swal from "sweetalert2";
import PostularPasantiaModal from "@/components/General/PostularPasantiaModal.vue";
import { useAuthStore } from "@/stores/authStore";

export default {
  setup(){
    const pasantiasStore = usePasantiasStore();
    const authStore = useAuthStore();
    return {pasantiasStore, authStore};
  },
  data() {
    return {
      parsedData: [],
      showAplicarPasantiaModal: false,
      data: {
        titulo: "",
        funciones: [],
        requisitos: [],
        institucion: {
          nombre: "",
          logoEmpresa: "",
          descripcion: "",
          direccion: "",
          correo: "",
          redesSociales: {
            web: "",
            facebook: "",
            twitter: "",
            linkedin: "",
            instagram: "",
          },
        }
      },
      related: [
        {
          idPasantias: "",
          titulo: "",
          descripcion: "",
          institucion: {
            nombre: "",
            logoEmpresa: "",
            direccion: "",
            sectores: [],
          }
        }
      ],
      id: "",
      image:
        "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
    };
  },
  components: {
    navbar,
    footers,
    switcher,
    PostularPasantiaModal
  },
  watch: {
    '$route.params.id': {
      handler(newId, oldId) {
        if (newId !== oldId) {
          this.asyncPasantias(newId);
          window.scrollTo(0, 0);
        }
      },
      immediate: true
    }
  },
  methods: {
    aplicarPasantia() {
      this.showAplicarPasantiaModal = true;
      //block scroll
      document.body.style.overflow = "hidden";
    },

    cerrarModalPasantia() {
      this.showAplicarPasantiaModal = false;
      //unlock scroll
      document.body.style.overflow = "auto";
    },




    async fetchPasantia() {
      let loader = this.$loading.show();
      try {
        const ref = this.$route.query.ref;
        const response = await this.pasantiasStore.getPasantiaById(this.id, ref);
        this.data = response;
        this.parsedData = this.data.descripcion.split("\n");
        console.log(this.parsedData);
        console.log("this.data"+this.data);
        if(this.data == null){
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "No se encontro la pasantia",
          });
          this.$router.push("/pasantias");
          return;
        }
      } catch (error) {

        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "ERROR: " + error,
        });

      } finally {
        loader.hide();
      }
      console.log("response "+this.response);
    },
    async fetchPasantiasRelacionadas() {
      let loader = this.$loading.show();
      try{
        const response = await this.pasantiasStore.getPasantiaRelacionada(this.id);
        this.related = response;
        if(this.related == null){
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "No se encontraron pasantias relacionadas",
          });
          return;
        }
      }catch(error){
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "ERROR: " + error,
        });
      }finally{
        loader.hide();
      }
    },
    async asyncPasantias(id){
      this.id = id;
      await this.fetchPasantia();
      await this.fetchPasantiasRelacionadas();
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    this.fetchPasantia();
    this.fetchPasantiasRelacionadas();
  },
  async beforeMount() {
    let existencia = false;
    if(this.$keycloak.authenticated){
      existencia = await this.authStore.checkExistencia(this.$keycloak.tokenParsed.sub);
      console.log(existencia);
      if(existencia==false){
        this.$router.push("/");
        return
      }
    }
  },
  props: {
    id: {
      type: String,
      required: true,
    }
  }
};
</script>

<style lang="scss" scoped></style>
