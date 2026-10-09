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
          Empresas Solicitantes
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
                    class="grid lg:grid-cols-2 md:grid-cols-2 grid-cols-1 lg:gap-0 gap-6"
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

  <div class="container mt-2">
  <div class="grid grid-cols-1 gap-[30px]">
    <div
      v-for="item in datas"
      :key="item"
      class="group relative overflow-hidden rounded shadow hover:shadow-md dark:shadow-gray-700 transition-all duration-500 p-5"
    >
      <!-- Nombre en la parte superior -->
      <div class="w-full text-lg hover:text-cyan-600 font-semibold transition-all duration-500 mb-4">
        {{ item.nombreInstitucion }}
      </div>
      
      <!-- Contenedor de columnas -->
      <div class="grid grid-cols-4 gap-4">
        <!-- Foto -->
        <div class="flex items-center justify-center bg-white dark:bg-slate-900 shadow dark:shadow-gray-700 rounded-md">
          <img :src="item.logoEmpresa" class="size-14" alt="" />
        </div>

        <!-- Datos de la empresa -->
        <div>
          <span class="block">
            <span class="bg-cyan-600/10 inline-block text-cyan-600 text-xs px-2.5 py-0.5 font-semibold rounded-full">
              Empresa:
            </span>
          </span>
          <span class="text-slate-400 block mt-1">
            <i class="uil uil-map-marker"></i> {{ item.direccion }}
          </span>
          <span class="block font-semibold mt-1">{{ item.correoInstitucion }}</span>
        </div>

        <!-- Datos del usuario -->
        <div>
          <span class="block">
            <span class="bg-cyan-600/10 inline-block text-cyan-600 text-xs px-2.5 py-0.5 font-semibold rounded-full">
              Solicitante:
            </span>
          </span>
          <span class="block text-slate-400 text-sm mt-1">
            <i class="uil uil-user"></i> {{ item.nombre }} {{ item.apellidoPaterno }}
          </span>
          <span class="block text-slate-400 text-sm mt-1">
            <i class="uil uil-fast-mail"></i> {{ item.correo }}
          </span>
        </div>

        <!-- Botones -->
        <div class="flex flex-col items-start">
          <button
            @click="sendEmail(item.correo)"
            class="btn btn-icon rounded-full bg-yellow-600/5 hover:bg-yellow-600 border-yellow-600/10 hover:border-yellow-600 text-yellow-600 hover:text-white"
          >
            <i class="uil-fast-mail"></i>
          </button>
          <router-link
            v-if="item.idUsuariosInstitucion"
            :to="{
              name: 'EmpresaSolicitudUsuarioAdministrador',
              params: {
                idEmpresa: item.idInstituciones,
                idSolicitud: item.idUsuariosInstitucion,
                idUsuario: item.idUsuarios,
              },
            }"
            class="btn rounded-md bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white mt-2 w-full"
          >
            Más información
          </router-link>
        </div>
      </div>
    </div>
    <!--end content-->
  </div>
</div>

  <!-- iframe end  -->
  <div class="md:my-16 my-16">
    <counter />
  </div>
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
  mounted() {
    this.fetchEmpresas();
  },
  setup() {
    const empresaStore = useEmpresasAdminStore();
    return { empresaStore };
  },
  data() {
    return {
      isActive: false,
      tutorialVideo: getTutorialVideo("ADMIN_SOLICITUD_EMPRESAS"),
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
      datas: [],
      formData: {
        name: "",
        email: "",
        subject: "INTERNSHIP",
        comments: "",
        numero: "",
      },
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
    toggle() {
      this.isActive = !this.isActive;
    },
    async fetchEmpresas() {
      const loader = this.$loading.show();
      try {
        const response = await this.empresaStore.getSolicitudes(
          this.searchValue,
          this.$keycloak.idTokenParsed.sub
        );
        console.log(response);
        if (response == null) {
          this.datas = [];
          return;
        }
        console.log(response);
        this.datas = response;
      } catch (error) {
        console.log(error);
      } finally {
        loader.hide();
      }
    },
    searchEmpresas() {
      this.currentPage = 0;
      this.fetchEmpresas();
    },
    async sendEmail(Correo) {
      this.formData.subject = "INTERNSHIP";
      const remitente = "usei.lpz@ucb.edu.bo";
      this.formData.email = remitente;
      const { name, email, subject, comments, numero } = this.formData;

      const mailtoLink = `mailto:${Correo}?subject=${encodeURIComponent(
        subject
      )}&body=${encodeURIComponent(`Mensaje de la Internship`)}`;
      window.location.href = mailtoLink;
      this.formData.numero = "";
    },
  },
};
</script>

<style lang="scss" scoped>
.map-marker-icon {
  color: #00ff00;
}
</style>
