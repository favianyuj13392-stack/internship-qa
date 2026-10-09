<template>
  <navbar
    :lightLogo="true"
    :lightNav="'nav-light justify-end'"
    :container="'container'"
  />
  <!-- Start -->
  <section
    class="py-20 w-full table relative bg-[url('../../assets/images/hero/bg2.webp')] bg-top bg-no-repeat bg-cover"
  >
    <div class="absolute inset-0 bg-slate-900/70"></div>
    <div class="container relative">
      <div class="grid grid-cols-1 text-center">
        <h3 class="mb-4 md:text-[26px] text-2xl text-white font-medium">
          Empresas
        </h3>

        <p class="text-white/80 max-w-xl mx-auto">
          Lugar donde podras visualizar las empresas que solicitan pasantes.
        </p>

        <a
          @click="toggle"
          data-type="youtube"
          :data-id="tutorialVideo.key"
          class="lightbox size-20 rounded-full shadow-lg dark:shadow-gray-800 inline-flex items-center justify-center bg-white dark:bg-slate-900 text-cyan-600 mx-auto mt-10 cursor-pointer"
        >
          <i
            class="mdi mdi-play inline-flex items-center justify-center text-2xl"
          ></i>
        </a>
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
  </section>
  <!--end section-->
  <!-- End -->

  <!-- iframe start  -->
  <div
    :class="isActive ? 'fixed' : 'hidden'"
    class="bg-black/[0.9] top-0 left-0 bottom-0 w-[100%] h-[100%] z-999"
  >
    <div class="h-[100%] flex items-center justify-center">
      <iframe
        v-if="isActive"
        :src="tutorialVideo.embedUrl"
        width="700"
        height="500"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        allowfullscreen
      ></iframe>
    </div>
    <button class="text-slate-400 absolute top-[20px] right-[20px]">
      <svg
        stroke="currentColor"
        fill="none"
        stroke-width="2"
        viewBox="0 0 24 24"
        stroke-linecap="round"
        stroke-linejoin="round"
        class="size-5"
        height="1em"
        width="1em"
        xmlns="http://www.w3.org/2000/svg"
        @click="toggle"
      >
        <line x1="18" y1="6" x2="6" y2="18"></line>
        <line x1="6" y1="6" x2="18" y2="18"></line>
      </svg>
    </button>
  </div>
  <section class="relative -mt-[42px]">
    <div class="container z-1">
      <div class="d-flex" id="reserve-form">
        <div class="md:w-5/6 mx-auto">
          <div class="lg:col-span-10">
            <div
              class="bg-white dark:bg-slate-900 border-0 shadow rounded-md p-3"
            >
              <form action="#">
                <div class="registration-form text-dark text-start">
                  <div
                    class="grid lg:grid-cols-3 md:grid-cols-2 grid-cols-1 lg:gap-0 gap-6"
                  >
                    <div class="filter-search-form relative filter-border">
                      <i class="uil uil-briefcase-alt icons"></i>
                      <input
                        name="name"
                        type="text"
                        id="job-keyword"
                        class="form-input filter-input-box bg-gray-50 dark:bg-slate-800 border-0"
                        placeholder="Buscar empresa..."
                        v-model="searchValue"
                        @keyup.enter="searchEmpresas"

                      />
                    </div>
                    <div class="filter-search-form relative filter-border bg-gray-50 dark:bg-slate-800">
                      <i class="uil uil-briefcase-alt icons"></i>
                      <v-select  placeholder="Buscar sectores..." :options="sectores" v-model="selectedSector" class="ms-10"></v-select>
                    </div>
                    <input
                      type="submit"
                      id="search"
                      name="search"
                      style="height: 60px"
                      class="btn bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white searchbtn submit-btn w-100"
                      value="Buscar"
                      @click.prevent="searchEmpresas"
                    />
                  </div>
                  <!--end grid-->
                </div>
                <!--end container-->
              </form>
            </div>
          </div>
          <!--ed col-->
        </div>
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
    <div class="container mt-10">
      <div class="grid grid-cols-1 gap-[30px]">
        <listone />
      </div>
    </div>
  </section>

  <div class="container">
    <div class="grid lg:grid-cols-3 md:grid-cols-2 grid-cols-1 gap-[30px]">
      <div
        v-for="item in datas"
        :key="item"
        class="group relative overflow-hidden bg-white dark:bg-slate-900 rounded-md shadow dark:shadow-gray-700"
      >
        <div class="relative overflow-hidden h-40">
          <img
            :src="item.logoEmpresa"
            class="absolute inset-0 w-full h-full object-cover object-center transition-all duration-500"
            alt=""
          />
        </div>

        <div class="relative p-6">
          <div class="absolute start-6 -top-4">
            <span
              class="bg-cyan-600 text-white text-[12px] px-2.5 py-1 font-semibold rounded-full h-5"
              >{{ item.correo }}</span
            >
          </div>

          <div class="">
            <div class="flex mb-4">
              <span class="text-slate-400 text-sm"
                ><i
                  class="uil uil-location-point text-slate-900 dark:text-white me-2"
                ></i
                >{{ item.direccion }}</span
              >
            </div>

            <router-link
              class="title text-lg font-semibold hover:text-cyan-600 duration-500 ease-in-out"
              :to="{ name: 'EmpresaDetalleAdministrador', params: { id: item.idInstituciones } }"
              >{{ item.nombre }}</router-link
            >

            <div class="flex justify-between items-center mt-3">
              <router-link
                :to="{ name: 'EmpresaDetalleAdministrador', params: { id: item.idInstituciones } }"
                class="btn btn-link hover:text-cyan-600 after:bg-cyan-600 duration-500 ease-in-out"
                >Ver empresa <i class="uil uil-arrow-right"></i
              ></router-link>
            </div>
          </div>
        </div>
      </div>
      <!--end content-->
    </div>
    <!--end grid-->
  </div>
  <!--end container-->
  <!--PAGINACIONNN-->
  <div class="grid md:grid-cols-12 grid-cols-1 mt-8">
    <div class="md:col-span-12 text-center">
      <nav aria-label="Page navigation example">
        <ul class="inline-flex items-center -space-x-px">
          <li>
            <button
              @click="prevPage"
              :disabled="currentPage === 0"
              class="size-[40px] inline-flex justify-center items-center text-slate-400 bg-white dark:bg-slate-900 rounded-s-3xl hover:text-white border border-gray-100 dark:border-gray-800 hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600"
            >
              <i class="uil uil-angle-left text-[20px] rtl:rotate-180 rtl:-mt-1"></i>
            </button>
          </li>
          <li v-for="page in totalPages" :key="page">
            <button
              @click="goToPage(page-1)"
              :class="[
                'size-[40px] inline-flex justify-center items-center text-slate-400 hover:text-white bg-cyan dark:bg-slate-900 border border-gray-100 dark:border-gray-800 hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600',
                { 'z-10 bg-cyan-600 text-white border-cyan-600': page-1 === currentPage }
              ]"
            >
              {{ page }}
            </button>
          </li>
          <li>
            <button
              @click="nextPage"
              :disabled="currentPage === totalPages - 1"
              class="size-[40px] inline-flex justify-center items-center text-slate-400 bg-white dark:bg-slate-900 rounded-e-3xl hover:text-white border border-gray-100 dark:border-gray-800 hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600"
            >
              <i class="uil uil-angle-right text-[20px] rtl:rotate-180 rtl:-mt-1"></i>
            </button>
          </li>
        </ul>
      </nav>
    </div>
    <!--end col-->
  </div>
  <!--end grid-->
  <!--FIN DE PAGINACION-->
  <!-- iframe end  -->
  <div class="md:my-16 my-16">
    <counter />
  </div>
  <!--end section-->
  <!--end section-->
  <!-- Hero End
    
        <h1>hola</h1>
        
    
    
        <company />
    
    
    
      <div class="container md:py-10 py-10">
          <services />
        </div>
    
        <popularjob />
        <company />
     -->

  <switcher />
  <footers />
</template>

<script>
import vSelect from "vue-select";
import "vue-select/dist/vue-select.css";
import navbar from "@/components/Administrador/navbarAdministrador.vue";
import counter from "@/components/General/Home/counter.vue";
import services from "@/components/General/Home/job-services.vue";
import popularjob from "@/components/General/Home/popular-job.vue";
import company from "@/components/General/Home/best-companies.vue";
import cta from "@/components/General/Home/two-job-cta.vue";
import question from "@/components/General/Home/job-questions.vue";
import explore from "@/components/General/Home/explore-job.vue";
import switcher from "@/components/General/switcher.vue";
import footers from "@/components/footer/footer.vue";
import { useEmpresasAdminStore } from "@/stores/Administradores/empresasAdminStore"; 
import { getTutorialVideo } from "@/config/tutorialVideos";

export default {
  setup() {
    const empresaStore = useEmpresasAdminStore();
    return { empresaStore };
  },
  async mounted() {
    await this.fetchEmpresas();
    await this.fetchSectores();
  },
  data() {
    return {
      tutorialVideo: getTutorialVideo("ADMIN_EMPRESAS"),
      sectores: [],
      selectedSector: null,
      isActive: false,
      options: [
        "Ingenieria de Sistemas",
        "Comunicación",
        "Psicopedagogia",
        "Administracion",
      ],
      selected: "Ingenieria de Sistemas",
      pageSize: 12,
      currentPage: 0,
      searchValue: "",
      totalPages: 0,
      datas:[]
    };
  },
  components: {
    navbar,
    switcher,
    footers,
    vSelect,
    services,
    popularjob,
    cta,
    company,
    question,
    explore,
    counter,
  },
  methods: {
    async fetchSectores(){
      let loader = this.$loading.show();
      const response = await this.empresaStore.getSectores();
      if (response == null) {
        loader.hide();
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "No se pudo cargar los sectores",
        });
        return;
      }
      this.sectores = response;
      loader.hide();
    },
    toggle() {
      this.isActive = !this.isActive;
    },
    async fetchEmpresas() {
      const loader = this.$loading.show();
      try{
        const response = await this.empresaStore.getEmpresas(
          this.currentPage,
          this.pageSize,
          this.searchValue,
          "true",
          this.$keycloak.idTokenParsed.sub,
          this.selectedSector
        );
        console.log(response);
        if(response==null){
          this.totalPages = 0;
          this.datas = [];
          return;
        }
        this.totalPages = response.totalPages;
        this.datas = response.content;

      }catch(error){
        console.log(error);
      }finally{
        loader.hide();
      }
    },
    searchEmpresas() {
      this.currentPage = 0;
      this.fetchEmpresas();
    },
    nextPage() {
      if (this.currentPage < this.totalPages - 1) {
        this.currentPage++;
        this.fetchEmpresas();
      }
    },
    prevPage() {
      if (this.currentPage > 0) {
        this.currentPage--;
        this.fetchEmpresas();
      }
    },
    goToPage(page) {
      this.currentPage = page;
      this.fetchEmpresas();
    },
  },
};
</script>

<style lang="scss" scoped>
.map-marker-icon {
  color: #00ff00;
}
</style>
