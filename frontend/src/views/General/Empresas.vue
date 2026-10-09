<template>
  <navbar :container="'container'" :lightNav="'nav-light justify-end'" :lightLogo="true" />
  <!-- Start Hero -->
  <section class="relative table w-full py-36 bg-[url('../../assets/images/hero/bg.webp')] bg-top bg-no-repeat bg-cover">
    <div class="absolute inset-0 bg-cyan-900/90"></div>
    <div class="container">
      <div class="grid grid-cols-1 text-center mt-10">
        <h3 class="md:text-3xl text-2xl md:leading-snug tracking-wide leading-snug font-medium text-white">
          Empresas asociadas
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

  <section class="relative -mt-[42px] md:pb-24 pb-16">
    <div class="container z-1">
      <div class="d-flex" id="reserve-form">
        <div class="md:w-5/6 mx-auto">
          <div class="lg:col-span-10">
            <div class="bg-white dark:bg-slate-900 border-0 shadow rounded-md p-3">
              <form @submit.prevent="searchEmpresas">
                <div class="registration-form text-dark text-start">
                  <div class="grid lg:grid-cols-3 md:grid-cols-2 grid-cols-1 lg:gap-0 gap-6">
                    <div class="filter-search-form relative filter-border">
                      <i class="uil uil-briefcase-alt icons"></i>
                      <input name="name" type="text" id="job-keyword"
                        class="form-input filter-input-box bg-gray-50 dark:bg-slate-800 border-0"
                        placeholder="Buscar empresa..." v-model="searchValue" />
                    </div>

                    <div class="filter-search-form relative filter-border bg-gray-50 dark:bg-slate-800" >
                      <i class="uil uil-briefcase-alt icons"></i>
                      <v-select :options="sectores" v-model="selectedSector" class="ms-10"   placeholder="Selecciona la área..."></v-select>
                    </div>

                    <input type="submit" id="search" name="Buscar" style="height: 60px"
                  
                    class="btn bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white searchbtn submit-btn w-100"
                      value="Buscar" />
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
    <!-- JOBGRIDDONNN-->
    <div class="container mt-10">
      <div class="grid lg:grid-cols-3 md:grid-cols-2 gap-[30px]">
        <div v-for="item in empresas" :key="item.idInstituciones"
          class="group shadow dark:shadow-gray-700 p-6 rounded-md bg-white dark:bg-slate-900 relative flex flex-col justify-between">
          <div class=" flex items-center justify-between">
            <div class="flex items-center">
              <div
                class="size-14 flex items-center justify-center bg-white dark:bg-slate-900 shadow dark:shadow-gray-700 rounded-md">
                <img :src="item.logoEmpresa" class="size-15" alt="" />
              </div>

              <div class="ms-3">
                <router-link :to="{
    name: 'empresas-detalle',
    params: { id: item.idInstituciones },
  }"
                  class="block text-[18px] font-semibold hover:text-cyan-600 transition-all duration-500 mt-3 lg:text-lg md:text-[18px] xl:text-[18px] ">{{
    item.nombre }}</router-link>
                <span class="block text-sm text-slate-400 lg:text-[12px] md:text-sm  xl:text-sm   ">{{
    item.correo
  }}</span>
              </div>
            </div>

            <span
              class="bg-cyan-600/10 group-hover:bg-cyan-600 inline-block text-cyan-600 group-hover:text-white text-xs px-2.5 py-0.5 font-semibold rounded-full transition-all duration-500 absolute top-2 right-2"><i
                class="mdi mdi-check-decagram mdi-18px text-blue-500 me-1 group-hover:text-white"></i>
              Verificado</span>
          </div>

          <div class="mt-6 flex-grow">
            <p class="text-slate-400 text-md hover:text-cyan-600 transition-all duration-500">
              {{ item.descripcion }}
            </p>

          </div>

          <div class="mt-1">
            <div class="py-2">
              <span v-for="sector in item.sectores" :key="sector"
                class="bg-slate-100 dark:bg-slate-800 inline-block text-slate-900 dark:text-slate-300 text-xs px-2.5 py-0.5 font-semibold rounded-full me-1">{{
    sector }}</span>
            </div>
            <div class="mt-2 mb-2">
              <span class="text-slate-400 text-sm">
                <span class="text-slate-900 dark:text-white font-semibold inline-block">{{ item.cantidadPasantias }}
                  pasantias disponibles</span>
              </span>
            </div>
            <div class="w-full bg-gray-100 dark:bg-gray-800 rounded-full h-[6px]">
              <div class="bg-cyan-600 h-[6px] rounded-full" style="width: 100%"></div>
            </div>
            <div class="mt-2">
              <h6 class="text-base font-medium">
                <i class="uil uil-map-marker"></i> {{ item.direccion }}
              </h6>
            </div>
          </div>
        </div>
        <!--end content-->
      </div>

      <!--end grid-->

      <!--PAGINACIONNN-->
      <div class="grid md:grid-cols-12 grid-cols-1 mt-8">
        <div class="md:col-span-12 text-center">
          <nav aria-label="Page navigation example">
            <ul class="inline-flex items-center -space-x-px">
              <li>
                <button @click="prevPage" :disabled="currentPage === 0"
                  class="size-[40px] inline-flex justify-center items-center text-slate-400 bg-white dark:bg-slate-900 rounded-s-3xl hover:text-white border border-gray-100 dark:border-gray-800 hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600">
                  <i class="uil uil-angle-left text-[20px] rtl:rotate-180 rtl:-mt-1"></i>
                </button>
              </li>
              <li v-for="page in totalPages" :key="page">
                <button @click="goToPage(page - 1)" :class="[
    'size-[40px] inline-flex justify-center items-center text-slate-400 hover:text-white bg-cyan dark:bg-slate-900 border border-gray-100 dark:border-gray-800 hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600',
    {
      'z-10 bg-cyan-600 text-white border-cyan-600':
        page - 1 === currentPage,
    },
  ]">
                  {{ page }}
                </button>
              </li>
              <li>
                <button @click="nextPage" :disabled="currentPage === totalPages - 1"
                  class="size-[40px] inline-flex justify-center items-center text-slate-400 bg-white dark:bg-slate-900 rounded-e-3xl hover:text-white border border-gray-100 dark:border-gray-800 hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600">
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
    </div>

    <!--FIIINDE JOB-->
  </section>
  <footers />
  <switcher />
</template>

<script>
import navbar from "@/components/General/navbarGeneral.vue";
import vSelect from "vue-select";
import "vue-select/dist/vue-select.css";
import footers from "@/components/footer/footer.vue";
import switcher from "@/components/General/switcher.vue";
import { useEmpresasStore } from "@/stores/Estudiantes/empresasStore.js";
import Swal from "sweetalert2";

export default {
  setup() {
    const empresasStore = useEmpresasStore();
    return { empresasStore };
  },

  async mounted() {
    await this.fetchEmpresas();
    await this.fetchSectores();
  },

  data() {
    return {
      pageSize: 12,
      currentPage: 0,
      searchValue: "",
      empresas: [],
      totalPages: 0,
      sectores: [
      ],
      selectedArea: [],
      options: [
        "Miraflores",
        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Canada",
        "Cape Verde",
        "Denmark",
        "Djibouti",
        "Eritrea",
        "Estonia",
        "Gambia",
      ],
      selected: "Miraflores",
      options2: ["Tecnologia", "Freelancer", "Remote Work", "Office Work"],
      selected2: [],
      selectedSector: [],
    };
  },

  methods: {
    async fetchEmpresas() {
      let loader = this.$loading.show();
      const response = await this.empresasStore.getEmpresas(
        this.currentPage,
        this.pageSize,
        this.searchValue,
        this.selectedSector
      );
      if (response == null) {
        loader.hide();
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "No se pudo cargar las empresas",
        });
        return;
      }
      this.empresas = response.content;
      this.totalPages = response.totalPages;

      loader.hide();
    },
    async fetchSectores(){
      let loader = this.$loading.show();
      const response = await this.empresasStore.getSectores();
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

  components: {
    navbar,
    vSelect,
    footers,
    switcher,
  },
};
</script>

<style lang="scss" scoped></style>
