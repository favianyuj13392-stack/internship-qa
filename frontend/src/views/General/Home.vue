<template>
  <link rel="preload" fetchpriority="high" as="image" href="@/assets/images/hero/bg.webp" />

  <navbar
    :lightLogo="true"
    :lightNav="'nav-light justify-end'"
    :container="'container'"
  />
  <!-- Hero Start -->
  <section
    id="hero-image"
    class="relative h-screen flex justify-center items-center bg-[url('../../assets/images/hero/bg.webp')] bg-cover"
  >
    <div class="absolute inset-0 bg-slate-900/30"></div>
    <div class="container z-1" id="hero-container">
      <div class="grid grid-cols-1 text-center mt-10 relative" id="hero">
        <h4
          class="lg:leading-normal leading-normal text-4xl lg:text-6xl mb-5 font-bold text-white"
          id="hero-title"
          >
          INTERNSHIP <br />
          by Universidad Católica Boliviana
        </h4>
        <p class="text-white/70 text-lg max-w-xl mx-auto" id="hero-description">
          Únete a nosotros en esta emocionante etapa de tu educación, donde el
          aprendizaje se transforma en experiencia y las conexiones se
          convierten en   <a  href="https://www.instagram.com/jhessika_zarate/"
          >oportunidades</a> concretas para el futuro.
        </p>
        <div class="d-flex" id="reserve-form">
          <div class="md:w-5/6 mx-auto">
            <div class="lg:col-span-10 mt-8">
              <div
                class="bg-white dark:bg-slate-900 border-0 shadow rounded-md p-3"
              >
                <form @submit.prevent="redirectToPasantias">
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
                          placeholder="Busca tu interes..."
                           v-model="searchValue"
                        />
                      </div>

                      <div
                        class="filter-search-form relative filter-border bg-gray-50 dark:bg-slate-800"
                      >
                        <i class="uil uil-graduation-cap icons"></i>
                        <v-select
                          :options="carrerasOptions"
                          v-model="selectedCarreraId"
                          placeholder="Selecciona tu carrera..."
                          label="nombre"
                          :reduce="(carrera) => carrera.idCarreras"
                          class="ms-10"
                        ></v-select>
                      </div>

                      <input
                        type="submit"
                        id="search"
                        name="search"
                        style="height: 60px"
                        class="btn bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white searchbtn submit-btn w-100"
                        value="Buscar"
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

        <div class="mt-4">
  <span class="text-yellow-400" id="hero-footer">
    <span class="text-white">Tu primer paso en el mundo laboral por :</span>
    <a href="https://lpz.ucb.edu.bo" class="text-yellow-400" target="_blank">
      Universidad Católica Boliviana "San Pablo"
    </a>
  </span>
</div>
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
  </section>
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
  <section class="relative md:py-16 py-16">
    <div class="container md:py-10 py-10">
      <services />
    </div>

    <question />
    <cta />
    <company />
    <explore />
  </section>

  <switcher />
  <footers />
</template>

<script>
import vSelect from "vue-select";
import "vue-select/dist/vue-select.css";
import navbar from "@/components/General/navbarGeneral.vue";
import counter from "@/components/General/Home/counter.vue";
import services from "@/components/General/Home/job-services.vue";
import popularjob from "@/components/General/Home/popular-job.vue";
import company from "@/components/General/Home/best-companies.vue";
import cta from "@/components/General/Home/two-job-cta.vue";
import question from "@/components/General/Home/job-questions.vue";
import explore from "@/components/General/Home/explore-job.vue";
import switcher from "@/components/General/switcher.vue";
import footers from "@/components/footer/footer.vue";
import { useCarrerasStore } from "@/stores/carrerasStore.js";
import { useAuthStore } from "@/stores/authStore.js";

export default {
  setup() {
    const authStore = useAuthStore();
    const carrerasStore = useCarrerasStore();
    return {
      authStore,
      carrerasStore,
    };
  },

  async beforeMount() {
    if (!this.$keycloak.authenticated) {
      return;
    }
    let loader = this.$loading.show();
    try {
      const existencia = await this.authStore.checkExistencia(
        this.$keycloak.tokenParsed.sub
      );
      console.log(this.$keycloak.tokenParsed);
      console.log(existencia);
      if (
        this.$keycloak.tokenParsed.resource_access["internship-cliente"] ==
          undefined &&
        existencia == false
      ) {
        this.$router.push("/finish/register-empresa");
        loader.hide();
        return;
      }

      try {
        if (
          existencia == true &&
          this.$keycloak.tokenParsed.resource_access[
            "internship-cliente"
          ].roles.includes("EMPRESA")
        ) {
          this.$router.push("/empresa/administrador/informacion");
          loader.hide();
          return;
        }
      } catch (error) {
        console.log(error);
        //reload page
        window.location.reload();
        this.$keycloak;
      }

      if (
        existencia == false &&
        this.$keycloak.tokenParsed.resource_access[
          "internship-cliente"
        ].roles.includes("ESTUDIANTE")
      ) {
        this.$router.push("/finish/register-estudiante");
        loader.hide();
        return;
      } else if (
        existencia == false &&
        this.$keycloak.tokenParsed.resource_access[
          "internship-cliente"
        ].roles.includes("ADMIN")
      ) {
        this.$router.push("/administrador/dashboard");
        loader.hide();
        return;
      } else if (
        existencia == true &&
        this.$keycloak.tokenParsed.resource_access[
          "internship-cliente"
        ].roles.includes("EMPRESA")
      ) {
        this.$router.push("/empresa/administrador/informacion");
        loader.hide();
        return;
      }
      loader.hide();
    } catch (error) {
      loader.hide();
      console.log(error);
    }
  },
  async mounted() {
    await this.fetchRecuento();
  },
  methods: {
    async fetchRecuento() {
      try {
        const response = await this.carrerasStore.getCarreras();
        if (response) {
          this.carreras = response;
          console.log("carrera", this.carreras);
        } else {
          console.error("Error al obtener los datos de recuento");
        }
      } catch (error) {
        console.error(error);
      }
    },
    redirectToPasantias() {
      this.$router.push({
        name: "Pasantias",
        query: {
          searchValue: this.searchValue,
          selectedCarreraId: this.selectedCarreraId,
        },
      });
    },
  },

  data() {
    return {
      searchValue: "",
      selectedCarreraId: "",
      carreras: [],
      options: [
        "Ingenieria de Sistemas",
        "Comunicación",
        "Psicopedagogia",
        "Administracion",
      ],
      selected: "",
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
  computed: {
    carrerasOptions() {
      return this.carreras.map((carrera) => ({
        nombre: carrera.nombre,
        idCarreras: carrera.idCarreras,
      }));
    },
  },
};
</script>

<style lang="scss" scoped>
.map-marker-icon {
  color: #00ff00;
}
@media (max-width:400px){
  #hero-image{
    margin-top: 3rem;
  }
  #hero-container{
    padding: 0 1rem;
  }
  #hero-title{
    font-size: 1.5rem!important;
  }
}
@media (max-width: 388px) {
  #hero-title {
    font-size: 1.5rem !important;
  }
  #hero-description {
    font-size: 0.8rem !important;
  }
  #hero-footer {
    display: none;
  }
}
</style>
