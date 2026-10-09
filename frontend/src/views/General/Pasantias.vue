<template>
  <navbar :container="'container'" :lightNav="'nav-light justify-end'" :lightLogo="true" />
  <!-- Start Hero -->
  <section class="relative table w-full py-36 bg-[url('../../assets/images/hero/bg.webp')] bg-top bg-no-repeat bg-cover">
    <div class="absolute inset-0 bg-cyan-900/90"></div>
    <div class="container">
      <div class="grid grid-cols-1 text-center mt-10">
        <h3 class="md:text-3xl text-2xl md:leading-snug tracking-wide leading-snug font-medium text-white">
          Pasantías
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
  <section class="relative py-8">
    <div class="container">
      <div class="grid lg:grid-cols-12 md:grid-cols-4 grid-cols-1 gap-[30px]">
        <div class="lg:col-span-4 md:col-span-6">
          <div class="shadow dark:shadow-gray-700 p-6 rounded-md bg-white dark:bg-slate-900 sticky top-20">
            <form>
              <div class="grid grid-cols-1 gap-3">
                <div>
                  <label for="searchname" class="font-semibold">Buscar pasantia</label>
                  <div class="relative mt-2">
                    <i class="uil uil-search text-lg absolute top-[5px] start-3"></i>
                    <input name="search" id="searchname" type="text"
                      class="form-input border border-slate-100 dark:border-slate-800 ps-10"
                      placeholder="Nombre de pasantia" v-model="searchValue" />

                  </div>
                </div>

                <!-- <div>
                  <label class="font-semibold">Area</label>
                  <select
                    class="form-select form-input border border-slate-100 dark:border-slate-800 block w-full mt-1"
                    v-model="selectedArea"
                    >

                   <option v-for="area in areas" :value="area">{{ area }}</option>

                  </select>
                </div> -->

                <div>
                  <label class="font-semibold">Carrera</label>
                  <select class="form-select form-input border border-slate-100 dark:border-slate-800 block w-full mt-1"
                    v-model="selectedCarrera" >
                    <option value="" selected disabled>Seleccione una carrera</option>

                    <option v-for="carrera in carreras" :value="carrera.idCarreras">{{ carrera.nombre }}</option>

                    <option value="">
                      Deseleccionar
                    </option>
                  </select>
                </div>

                <div>
                  <label class="font-semibold">Areas</label>
                  <div class="block mt-2" style="overflow: auto; max-height: 40rem;">
                    <div class="flex justify-between" v-for="area in areas">
                      <div class="inline-flex items-center mb-0">
                        <input
                          class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50 me-2"
                          type="checkbox" :value="area" :id="area" v-model="selectedAreas" />
                        <label class="form-checkbox-label text-slate-400" :for="area">{{ area }}</label>
                      </div>

                      <!-- <span
                        class="bg-cyan-600/10 text-cyan-600 text-xs px-2.5 py-0.5 font-semibold rounded-full h-5">3</span> -->
                    </div>
                    <!-- <div class="flex justify-between">
                      <div class="inline-flex items-center mb-0">
                        <input
                          class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50 me-2"
                          type="checkbox" value="" id="parttime" />
                        <label class="form-checkbox-label text-slate-400" for="parttime">Part Time</label>
                      </div>

                      <span
                        class="bg-cyan-600/10 text-cyan-600 text-xs px-2.5 py-0.5 font-semibold rounded-full h-5">7</span>
                    </div> -->
                    <!-- <div class="flex justify-between">
                      <div class="inline-flex items-center mb-0">
                        <input
                          class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50 me-2"
                          type="checkbox" value="" id="Freelancing" />
                        <label class="form-checkbox-label text-slate-400" for="Freelancing">Freelancing</label>
                      </div>

                      <span
                        class="bg-cyan-600/10 text-cyan-600 text-xs px-2.5 py-0.5 font-semibold rounded-full h-5">4</span>
                    </div> -->
                    <!-- <div class="flex justify-between">
                      <div class="inline-flex items-center mb-0">
                        <input
                          class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50 me-2"
                          type="checkbox" value="" id="fixedprice" />
                        <label class="form-checkbox-label text-slate-400" for="fixedprice">Fixed Price</label>
                      </div>

                      <span
                        class="bg-cyan-600/10 text-cyan-600 text-xs px-2.5 py-0.5 font-semibold rounded-full h-5">6</span>
                    </div> -->
                    <!-- <div class="flex justify-between">
                      <div class="inline-flex items-center mb-0">
                        <input
                          class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50 me-2"
                          type="checkbox" value="" id="Remote" />
                        <label class="form-checkbox-label text-slate-400" for="Remote">Remote</label>
                      </div>

                      <span
                        class="bg-cyan-600/10 text-cyan-600 text-xs px-2.5 py-0.5 font-semibold rounded-full h-5">7</span>
                    </div> -->
                    <!-- <div class="flex justify-between">
                      <div class="inline-flex items-center mb-0">
                        <input
                          class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50 me-2"
                          type="checkbox" value="" id="hourlybasis" />
                        <label class="form-checkbox-label text-slate-400" for="hourlybasis">Hourly Basis</label>
                      </div>

                      <span
                        class="bg-cyan-600/10 text-cyan-600 text-xs px-2.5 py-0.5 font-semibold rounded-full h-5">44</span>
                    </div> -->
                  </div>
                </div>

                <!-- <div>
                  <label class="font-semibold">Salary</label>
                  <div class="block mt-2">
                    <div>
                      <label class="inline-flex items-center">
                        <input type="radio"
                          class="form-radio border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50 me-2"
                          name="radio-colors" value="1" checked />
                        <span class="text-slate-400">10k - 15k</span>
                      </label>
                    </div>
                    <div>
                      <label class="inline-flex items-center">
                        <input type="radio"
                          class="form-radio border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50 me-2"
                          name="radio-colors" value="1" />
                        <span class="text-slate-400">15k - 25k</span>
                      </label>
                    </div>
                    <div>
                      <label class="inline-flex items-center">
                        <input type="radio"
                          class="form-radio border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50 me-2"
                          name="radio-colors" value="1" />
                        <span class="text-slate-400">more than 25K</span>
                      </label>
                    </div>
                  </div>
                </div> -->

                <div>
                  <button type="button" @click="fetchPasantias"
                    class="btn bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white rounded-md w-full">
                    Buscar
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
        <!--end col-->

        <!--TARTJETAZOOO-->
        <div class="lg:col-span-8 md:col-span-6">
          <div class="grid lg:grid-cols-2 md:grid-cols-2 gap-[30px]">
            <div v-if="data.length === 0" class="text-center">
              <h1 class="text-2xl font-semibold text-slate-400">No se encontraron pasantias</h1>
            </div>
            <div v-for="pasantia in data" :key="pasantia.idPasantias"
              class="group p-6 rounded-lg border border-cyan-600/20 dark:border-cyan-600/40 bg-white dark:bg-slate-900 hover:bg-cyan-600/[0.02] hover:dark:bg-cyan-600/5 hover:shadow-md hover:shadow-cyan-600/5 transition-all duration-500">
              <div class="flex justify-between items-start">
                <div>
                  <div
                    class="size-14 flex items-center justify-center bg-white dark:bg-slate-900 shadow dark:shadow-gray-700 rounded-full mb-2">
                    <img :src="pasantia.institucion.logoEmpresa" class="size-8" alt="" />
                  </div>
                  <a class="text-lg hover:text-cyan-600 font-semibold transition-all duration-500">{{
    concatList(pasantia.areas)
  }}</a>
                </div>

                <div class="flex items-center">
                  <router-link :to="{ name: 'pasantias-detalle', params: { id: pasantia.idPasantias } }"
                    class="btn btn-icon rounded-full bg-cyan-600/5 group-hover:bg-cyan-600 border-cyan-600/10 text-cyan-600 group-hover:text-white ms-1"><i
                      class="uil uil-arrow-up-right"></i></router-link>
                </div>
              </div>

              <div class="mt-3">
                <router-link :to="{ name: 'pasantias-detalle', params: { id: pasantia.idPasantias } }"
                  class="text-xl hover:text-cyan-600 font-semibold transition-all duration-500">
                  {{ pasantia.titulo }}
                </router-link>
                <!-- <p class="text-slate-400 mt-2">
                  {{ pasantia.descripcion }}
                </p> -->

                <div class="mt-3">
                  <a v-for="area in pasantia.areas">
                    <span
                      class="bg-rose-500/5 hover:bg-rose-500/20 dark:bg-rose-500/10 hover:dark:bg-rose-500/30 inline-block text-rose-500 px-4 text-[14px] font-medium rounded-full mt-2 me-1 transition-all duration-500">{{
    area
  }}</span>
                  </a>
                  <a v-for="(beneficio, index) in pasantia.beneficios">
                    <span v-if="index < 2"
                      class="bg-purple-600/5 hover:bg-purple-600/20 dark:bg-purple-600/10 hover:dark:bg-purple-600/30 inline-block text-purple-600 px-4 text-[14px] font-medium rounded-full mt-2 me-1 transition-all duration-500">{{
    beneficio }}</span>
                  </a>
                  <span v-if="pasantia.beneficios.length > 2" @mouseenter="pasantia.beneficiosIsHovered = true"
                    @mouseleave="pasantia.beneficiosIsHovered = false"
                    class="bg-purple-600/5 hover:bg-purple-600/20 dark:bg-purple-600/10 hover:dark:bg-purple-600/30 inline-block text-purple-600 px-4 text-[14px] font-medium rounded-full mt-2 me-1 transition-all duration-500">
                    ...
                  </span>
                  <a v-for="(beneficio, index) in pasantia.beneficios.slice(2)" :key="'extra-' + index"
                    v-show="pasantia.beneficiosIsHovered">
                    <span
                      class="bg-purple-600/5 hover:bg-purple-600/20 dark:bg-purple-600/10 hover:dark:bg-purple-600/30 inline-block text-purple-600 px-4 text-[14px] font-medium rounded-full mt-2 me-1 transition-all duration-500">
                      {{ beneficio }}
                    </span>
                  </a>
                  <a v-for="(carrera, index) in pasantia.carreras">
                    <span v-if="index < 2"
                      class="bg-cyan-600/5 hover:bg-cyan-600/20 dark:bg-cyan-600/10 hover:dark:bg-cyan-600/30 inline-block text-cyan-600 px-4 text-[14px] font-medium rounded-full mt-2 transition-all duration-500">
                      {{ carrera.nombre }}</span>
                  </a>

                  <span v-if="pasantia.carreras.length > 2" @mouseenter="pasantia.carrerasIsHovered = true"
                    @mouseleave="pasantia.carrerasIsHovered = false"
                    class="bg-cyan-600/5 hover:bg-cyan-600/20 dark:bg-cyan-600/10 hover:dark:bg-cyan-600/30 inline-block text-cyan-600 px-4 text-[14px] font-medium rounded-full mt-2 transition-all duration-500">
                    ...
                  </span>
                  <a v-for="(carrera, index) in pasantia.carreras.slice(2)" :key="'extra-' + index"
                    v-show="pasantia.carrerasIsHovered">
                    <span
                      class="bg-cyan-600/5 hover:bg-cyan-600/20 dark:bg-cyan-600/10 hover:dark:bg-cyan-600/30 inline-block text-cyan-600 px-4 text-[14px] font-medium rounded-full mt-2 transition-all duration-500">
                      {{ carrera.nombre }}
                    </span>
                  </a>
                </div>
              </div>
            </div>
            <!--end content-->
          </div>
          <!--end grid-->
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
                    <button @click="goToPage(page -1)" :class="[
    'size-[40px] inline-flex justify-center items-center text-slate-400 hover:text-white bg-cyan dark:bg-slate-900 border border-gray-100 dark:border-gray-800 hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600',
    { 'z-10 bg-cyan-600 text-white border-cyan-600': page - 1 === currentPage }
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
      </div>
    </div>
  </section>
  <footers />
  <switcher />
</template>
<script>
import navbar from "@/components/General/navbarGeneral.vue";

import explore from "@/components/General/Home/explore-job.vue";
import footers from "@/components/footer/footer.vue";
import switcher from "@/components/General/switcher.vue";
import { usePasantiasStore } from '@/stores/Pasantias/pasantiasStore';
import { fromJSON } from "postcss";

export default {
  setup() {
    const pasantiasStore = usePasantiasStore();
    return { pasantiasStore };
  },
  async mounted() {
    this.setInitialFilters();
    await this.fetchPasantias();
    await this.fetchAreas();
    await this.fetchCarreras();
  },
  methods: {
    concatList(area) {
      return area.join(", ");
    },
    attachIsHovered() {
      this.data = this.data.map((pasantia) => {
        return {
          ...pasantia,
          carrerasIsHovered: false,
          beneficiosIsHovered: false,
        };
      });
    },
    async fetchAreas() {
      let loader = this.$loading.show();
      const response = await this.pasantiasStore.getAreas();
      this.areas = response;
      loader.hide();
      //
      this.areas.sort((a, b) => {
        if (a < b) {
          return -1;
        }
        if (a > b) {
          return 1;
        }
        return 0;
      });
    },
    async fetchCarreras() {
      let loader = this.$loading.show();
      const response = await this.pasantiasStore.getCarreras();
      this.carreras = response;
      loader.hide();
      this.carreras.sort((a, b) => {
        if (a.nombre < b.nombre) {
          return -1;
        }
        if (a.nombre > b.nombre) {
          return 1;
        }
        return 0;
      });
    },
    async fetchPasantias() {
      let loader = this.$loading.show();
      console.log(this.selectedCarrera);
      const response = await this.pasantiasStore.getPasantias(
        this.currentPage,
        this.pageSize,
        this.searchValue,
        this.selectedAreas,
        this.selectedCarrera
      );
      this.data = response.content;
      this.totalPages = response.totalPages;
      loader.hide();
      this.attachIsHovered();
    },
    setInitialFilters() {
      const { searchValue, selectedCarreraId } = this.$route.query;
      if (searchValue) {
        this.searchValue = searchValue;
      }
      if (selectedCarreraId) {
        this.selectedCarrera = selectedCarreraId;
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages - 1) {
        this.currentPage++;
        this.fetchPasantias();
      }
    },
    prevPage() {
      if (this.currentPage > 0) {
        this.currentPage--;
        this.fetchPasantias();
      }
    },
    goToPage(page) {
      this.currentPage = page;
      this.fetchPasantias();
    }
  },

  data() {
    return {
      pageSize: 12,
      currentPage: 0,
      searchValue: null,
      totalPages: 0,
      data: [],
      areas: [],
      carreras: [],
      selectedAreas: [],
      selectedCarrera: "",
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
