<template>
  <navbar
    :container="'container'"
    :lightNav="'nav-light justify-end'"
    :lightLogo="true"
  />
  <!-- Start Hero -->
  <section
    class="py-20 w-full table relative bg-[url('../../assets/images/hero/bg2.webp')] bg-top bg-no-repeat bg-cover"
  >
    <div class="absolute inset-0 bg-slate-900/70"></div>
    <div class="container relative">
      <div class="grid grid-cols-1 text-center">
        <h3 class="mb-4 md:text-[26px] text-2xl text-white font-medium">
          Solicitudes de pasantias
        </h3>

        <p class="text-white/80 max-w-xl mx-auto">
          Lugar donde podras las solicitudes de pasantias.
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
  <!-- End Hero -->

  <section class="relative -mt-[42px] md:pb-24 pb-16">
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
                    class="grid lg:grid-cols-2 md:grid-cols-2 grid-cols-1 lg:gap-0 gap-6"
                  >
                    <div class="filter-search-form relative filter-border">
                      <i class="uil uil-briefcase-alt icons"></i>
                      <input
                        name="name"
                        type="text"
                        id="job-keyword"
                        class="form-input filter-input-box bg-gray-50 dark:bg-slate-800 border-0"
                        placeholder="Buscar pasantia..."
                        v-model="searchValue"
                        @keyup.enter="searchEmpresas"
                      />
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
    <!-- JOBGRIDDONNN-->
    <div class="container md:mt-16 mt-16">
      <div
        class="grid lg:grid-cols-3 md:grid-cols-2 grid-cols-1 mt-8 gap-[30px]"
      >
        <div
          v-for="item in data"
          :key="item"
          class="group relative overflow-hidden rounded-md shadow dark:shadow-gray-500"
        >
          <div class="flex justify-end mt-2 mr-2">
            <router-link
            :to="{ name: 'PasantiaDetalleAdministrador', params: { id: item.idPasantias } }"
                   
              class="btn btn-icon rounded-full bg-cyan-600/5 hover:bg-cyan-600 border-cyan-600/10 text-cyan-600 hover:text-white ms-1 ml-3"
              ><i class="uil uil-arrow-up-right"></i
            ></router-link>
          </div>
          <div class="p-2">
            <router-link
            :to="{ name: 'PasantiaDetalleAdministrador', params: { id: item.idPasantias } }"

              class="title h5 text-lg font-semibold hover:text-cyan-600"
              >{{ item.titulo }}</router-link
            >

            <p class="text-slate-400 mt-2">
              <i class="uil uil-clock text-cyan-600"></i>
              {{ item.fechaIngreso }}
            </p>

            <div class="flex justify-between items-center mt-4">
              
              <span
                v-for="area in item.areas"
                class="bg-cyan-600/5 text-cyan-600 text-xs font-bold px-2.5 py-0.5 rounded h-5"
                >{{ area }}</span
              >

              
            </div>
          </div>

          <div
            class="flex items-center p-6 border-t border-gray-100 dark:border-gray-700"
          >
            <img
              :src="item.logoEmpresa"
              class="size-12 shadow-md dark:shadow-gray-800 rounded-md p-2 bg-white dark:bg-slate-900"
              alt=""
            />

            <div class="ms-3">
              <router-link
                :to="{
                  name: 'EmpresaDetalleAdministrador',
                  params: { id: item.idInstituciones },
                }"
              >
                <h6 class="mb-0 font-semibold text-base hover:text-cyan-700">
                  {{ item.nombre }}
                </h6></router-link
              >
              <span class="text-slate-400 text-sm">
                Hasta: {{ item.fechaCierre }}</span
              >
            </div>
          </div>
        </div>
      </div>
    </div>
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
                <i
                  class="uil uil-angle-left text-[20px] rtl:rotate-180 rtl:-mt-1"
                ></i>
              </button>
            </li>
            <li v-for="page in totalPages" :key="page">
              <button
                @click="goToPage(page - 1)"
                :class="[
                  'size-[40px] inline-flex justify-center items-center text-slate-400 hover:text-white bg-cyan dark:bg-slate-900 border border-gray-100 dark:border-gray-800 hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600',
                  {
                    'z-10 bg-cyan-600 text-white border-cyan-600':
                      page - 1 === currentPage,
                  },
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
                <i
                  class="uil uil-angle-right text-[20px] rtl:rotate-180 rtl:-mt-1"
                ></i>
              </button>
            </li>
          </ul>
        </nav>
      </div>
      <!--end col-->
    </div>
    <!--end grid-->
    <!--end container-->

    <!--FIIINDE JOB-->
  </section>
  <footers />
  <switcher />
</template>

<script>
import navbar from "@/components/Administrador/navbarAdministrador.vue";
import vSelect from "vue-select";
import "vue-select/dist/vue-select.css";
import footers from "@/components/footer/footer.vue";
import { usePasantiasAdminStore } from "@/stores/Administradores/pasantiasAdminStore";
import switcher from "@/components/General/switcher.vue";
import { getTutorialVideo } from "@/config/tutorialVideos";

export default {
  setup() {
    const pasantiasStore = usePasantiasAdminStore();
    return { pasantiasStore };
  },
  data() {
    return {
      isActive: false,
      tutorialVideo: getTutorialVideo("ADMIN_SOLICITUD_PASANTIA"),
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
      options2: ["Facebook", "Freelancer", "Remote Work", "Office Work"],
      selected2: [],

      pageSize: 12,
      currentPage: 0,
      searchValue: "",
      totalPages: 0,
      data: [],
      kkuid: "",
      active: false,
    };
  },
  mounted() {
    this.fetchPasantias();
  },
  methods: {
    toggle() {
      this.isActive = !this.isActive;
    },
    async fetchPasantias() {
      let loader = this.$loading.show();
      try {
        const response = await this.pasantiasStore.getPasantias(
          this.currentPage,
          this.pageSize,
          this.searchValue,
          false,
          this.$keycloak.idTokenParsed.sub
        );
        console.log(response);
        if (response == null) {
          this.totalPages = 0;
          this.data = [];
          return;
        }

        this.data = response.content;
        this.totalPages = response.totalPages;
      } catch (error) {
        console.log(error);
      } finally {
        loader.hide();
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
    },
    searchEmpresas() {
      this.currentPage = 0;
      this.fetchPasantias();
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

<style lang="scss" scoped>
.bg-cover {
  background-size: cover;
}

.bg-center {
  background-position: center;
}

.jhess {
  background-color: rgba(
    0,
    0,
    0,
    0.5
  ); /* Puedes ajustar la opacidad según tus necesidades */
}
</style>
