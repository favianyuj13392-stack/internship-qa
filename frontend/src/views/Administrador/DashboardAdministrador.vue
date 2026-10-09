<template>
  <navbar :container="'container'" :lightNav="'justify-end'" />
  <!-- Google Map -->
  <section class="relative py-20 w-full">
    <div class="absolute inset-0 bg-cyan-600/5 dark:bg-cyan-600/10"></div>
    <div class="container z-1">
      <div class="grid grid-cols-1 text-center relative">
        <h4
          class="lg:leading-normal leading-normal text-4xl lg:text-5xl mb-5 font-bold"
        >
          Dashboard <span class="text-cyan-600 font-bold">de la</span>
          <br />
          <span class="text-cyan-600 font-bold">INTERNSHIP</span> by UCB
        </h4>

        <div class="d-flex">
          <span class="relative inline-block">
            <input
              type="checkbox"
              class="checkbox opacity-0 absolute"
              id="cual"
              @change="cambioContendor($event)"
            />
            <label
              class="label bg-slate-900 dark:bg-white shadow dark:shadow-gray-800 cursor-pointer rounded-full flex justify-between items-center p-1 w-14 h-8"
              for="cual"
            >
              <i class="uil uil-chart-line text-[20px] text-cyan-500"></i>
              <i class="uil uil-chart-pie-alt text-[20px] text-cyan-500"></i>
              <span
                class="ball bg-white dark:bg-slate-900 rounded-full absolute top-[2px] left-[2px] size-7"
              ></span>
            </label>
          </span>
        </div>

        <!--end grid-->
<div class="responsive-container">
        <div class="absolute -top-10 start-1/2 -translate-x-1/2">
          <div
            class="size-10 animate-[bounce_2s_infinite] bg-white dark:bg-slate-900 flex items-center justify-center shadow dark:shadow-gray-700 rounded-md"
          >
            <img
              src="https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png"
              class="size-9"
              alt=""
            />
          </div>
        </div>

        <div class="absolute top-[20%] start-10">
          <div
            class="size-10 animate-[spin_5s_linear_infinite] bg-white dark:bg-slate-900 flex items-center justify-center shadow dark:shadow-gray-700 rounded-md"
          >
            <img
              src="https://lpz.ucb.edu.bo/wp-content/uploads/2022/04/ICO.png"
              class="size-9 w-19"
              alt=""
            />
          </div>
        </div>

        <div class="absolute top-[20%] end-1">
          <div
            class="size-10 bg-white dark:bg-slate-900 flex items-center justify-center shadow dark:shadow-gray-700 rounded-md"
          >
            <img
              src="https://lpz.ucb.edu.bo/wp-content/uploads/2022/04/COM.png"
              class="size-9 w-19"
              alt=""
            />
          </div>
        </div>

        <div class="absolute top-3/4 start-1">
          <div
            class="size-10 bg-white dark:bg-slate-900 flex items-center justify-center shadow dark:shadow-gray-700 rounded-md"
          >
            <img
              src="https://lpz.ucb.edu.bo/wp-content/uploads/2022/04/SIS.png"
              class="size-9 w-15"
              alt=""
            />
          </div>
        </div>

        <div class="absolute top-3/4 end-10">
          <div
            class="size-10 animate-[spin_5s_linear_infinite] bg-white dark:bg-slate-900 flex items-center justify-center shadow dark:shadow-gray-700 rounded-md"
          >
            <img
              src="https://lpz.ucb.edu.bo/wp-content/uploads/2022/04/CPO.png"
              class="size-9 w-19"
              alt=""
            />
          </div>
        </div>

        <div class="absolute -bottom-16 start-1/2 -translate-x-1/2">
          <div
            class="size-10 animate-pulse bg-white dark:bg-slate-900 flex items-center justify-center shadow dark:shadow-gray-700 rounded-md"
          >
            <img
              src="https://lpz.ucb.edu.bo/wp-content/uploads/2023/01/web-Acreditada-IAC-CINDA.jpg"
              class="size-9"
              alt=""
            />
          </div>
        </div>
      </div>
      </div>
      <!--end grid-->
    </div>
    <section class="relative md:py-24 py-16">
      <!-- Start -->
      <div v-if="contenedor" class="container">
        <!--KPIS SIN PARAMETROS-->
        <h2 class="text-3xl font-bold text-center text-slate-900 dark:text-white mb-10">
          KPIs
        </h2>
        <div class="grid lg:grid-cols-5 md:grid-cols-3 sm:grid-cols-2 grid-cols-1 gap-[30px]">
          <div v-for="kpi in KpiSinParametros" :key="kpi" class="group px-3 py-10 rounded-md shadow dark:shadow-gray-700 hover:shadow-cyan-600/10 dark:hover:shadow-cyan-600/10 text-center bg-white dark:bg-slate-900 hover:bg-cyan-600/5 dark:hover:bg-cyan-600/5 transition duration-500">
            <div class="size-16 bg-cyan-600/5 group-hover:bg-cyan-600 text-cyan-600 group-hover:text-white rounded-md text-2xl flex align-middle justify-center items-center shadow-sm dark:shadow-gray-700 transition duration-500 mx-auto">
              <div class="flex items-center justify-center">
                {{ kpi.value }}
              </div>
            </div>
            <div class="content mt-6">
              <p class="text-slate-400 mt-3">{{ kpi.title }}</p>
            </div>
          </div>
        </div>
        <!--KPIS POR CARRERA-->
        <div class="mt-10">
          <h2 class="text-3xl font-bold text-center text-slate-900 dark:text-white">
            KPIs por Carrera
          </h2>
          <v-select
            :options="carreras"
            v-model="selectedIdCarrera"
            placeholder="Selecciona una carrera..."
            label="nombre"
            :reduce="(carrera) => carrera.idCarreras"
            class="w-full mt-5 mb-5"
          ></v-select>
          <div v-if="selectedIdCarrera" class="grid lg:grid-cols-3 md:grid-cols-3 sm:grid-cols-2 grid-cols-1 gap-[30px]">
            <div v-for="kpi in KpiCarrera" :key="kpi" class="group px-3 py-10 rounded-md shadow dark:shadow-gray-700 hover:shadow-cyan-600/10 dark:hover:shadow-cyan-600/10 text-center bg-white dark:bg-slate-900 hover:bg-cyan-600/5 dark:hover:bg-cyan-600/5 transition duration-500">
              <div class="size-16 bg-cyan-600/5 group-hover:bg-cyan-600 text-cyan-600 group-hover:text-white rounded-md text-2xl flex align-middle justify-center items-center shadow-sm dark:shadow-gray-700 transition duration-500 mx-auto">
                <div class="flex items-center justify-center">
                  {{ kpi.value }}
                </div>
              </div>
              <div class="content mt-6">
                <p class="text-slate-400 mt-3">{{ kpi.title }}</p>
              </div>
            </div>
          </div>
        </div>
        <!--KPIS POR FECHA-->
        <div class="mt-10">
          <h2 class="text-3xl font-bold text-center text-slate-900 dark:text-white mb-5">
            KPIs por Fecha
          </h2>
          <div class="grid lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-2 grid-cols-1 gap-4 mb-5">
            <div>
              <h6 class="text-slate-400 dark:text-white mb-1">Fecha de Inicio</h6>
              <input
                type="date"
                v-model="selectedFechaInicio"
                class="w-full p-2 rounded-md border border-slate-300 dark:border-slate-800"
                placeholder="Selecciona la fecha de inicio"
              />
            </div>
            <div>
              <h6 class="text-slate-400 dark:text-white mb-1">Fecha de Fin</h6>
              <input
                type="date"
                v-model="selectedFechaFin"
                class="w-full p-2 rounded-md border border-slate-300 dark:border-slate-800"
                placeholder="Selecciona la fecha de fin"
              />
            </div>
            <div class="flex items-end">
              <button
                class="w-full bg-cyan-600 text-white rounded-md p-2 hover:bg-cyan-700 transition duration-500"
                @click="getKPIWithDate()"
              >
                Buscar
              </button>
            </div>
          </div>
         <div v-if="selectedFechaInicio && selectedFechaFin" class="grid lg:grid-cols-2 md:grid-cols-3 sm:grid-cols-2 grid-cols-1 gap-[30px]">
            <div v-for="kpi in KpiFecha" :key="kpi" class="group px-3 py-10 rounded-md shadow dark:shadow-gray-700 hover:shadow-cyan-600/10 dark:hover:shadow-cyan-600/10 text-center bg-white dark:bg-slate-900 hover:bg-cyan-600/5 dark:hover:bg-cyan-600/5 transition duration-500">
              <div class="size-16 bg-cyan-600/5 group-hover:bg-cyan-600 text-cyan-600 group-hover:text-white rounded-md text-2xl flex align-middle justify-center items-center shadow-sm dark:shadow-gray-700 transition duration-500 mx-auto">
                <div class="flex items-center justify-center">
                  {{ kpi.value }}
                </div>
              </div>
              <div class="content mt-6">
                <p class="text-slate-400 mt-3">{{ kpi.title }}</p>
              </div>
            </div>
          </div>
        </div>
        <!--KPIS POR EMPRESA-->
        <div class="mt-10">
          <h2 class="text-3xl font-bold text-center text-slate-900 dark:text-white">
            KPIs por Empresa
          </h2>
          <v-select
            :options="empresas"
            v-model="selectedIdEmpresa"
            placeholder="Selecciona una empresa..."
            label="nombre"
            :reduce="(empresa) => empresa.idInstituciones"
            class="w-full mt-5 mb-5"
          ></v-select>
          <div v-if="selectedIdEmpresa" class="grid lg:grid-cols-3 md:grid-cols-3 sm:grid-cols-2 grid-cols-1 gap-[30px]">
            <div v-for="kpi in KpiEmpresa" :key="kpi" class="group px-3 py-10 rounded-md shadow dark:shadow-gray-700 hover:shadow-cyan-600/10 dark:hover:shadow-cyan-600/10 text-center bg-white dark:bg-slate-900 hover:bg-cyan-600/5 dark:hover:bg-cyan-600/5 transition duration-500">
              <div class="size-16 bg-cyan-600/5 group-hover:bg-cyan-600 text-cyan-600 group-hover:text-white rounded-md text-2xl flex align-middle justify-center items-center shadow-sm dark:shadow-gray-700 transition duration-500 mx-auto">
                <div class="flex items-center justify-center">
                  {{ kpi.value }}
                </div>
              </div>
              <div class="content mt-6">
                <p class="text-slate-400 mt-3">{{ kpi.title }}</p>
              </div>
            </div>
          </div>
        </div>
        <!--KPIS POR SECTOR-->
        <div class="mt-10">
          <h2 class="text-3xl font-bold text-center text-slate-900 dark:text-white">
            KPIs por Sector
          </h2>
          <v-select
            :options="sectores"
            v-model="selectedSector"
            placeholder="Selecciona un sector..."
            label="nombre"
            :reduce="(sector) => sector"
            class="w-full mt-5 mb-5"
          ></v-select>
          <div v-if="selectedSector" class="grid lg:grid-cols-3 md:grid-cols-3 sm:grid-cols-2 grid-cols-1 gap-[30px]">
            <div v-for="kpi in KpiSector" :key="kpi" class="group px-3 py-10 rounded-md shadow dark:shadow-gray-700 hover:shadow-cyan-600/10 dark:hover:shadow-cyan-600/10 text-center bg-white dark:bg-slate-900 hover:bg-cyan-600/5 dark:hover:bg-cyan-600/5 transition duration-500">
              <div class="size-16 bg-cyan-600/5 group-hover:bg-cyan-600 text-cyan-600 group-hover:text-white rounded-md text-2xl flex align-middle justify-center items-center shadow-sm dark:shadow-gray-700 transition duration-500 mx-auto">
                <div class="flex items-center justify-center">
                  {{ kpi.value }}
                </div>
              </div>
              <div class="content mt-6">
                <p class="text-slate-400 mt-3">{{ kpi.title }}</p>
              </div>
            </div>
          </div>
        </div>
        <!--KPIS POR AREA-->
        <div class="mt-10">
          <h2 class="text-3xl font-bold text-center text-slate-900 dark:text-white">
            KPIs por Area
          </h2>
          <v-select
            :options="areas"
            v-model="selectedArea"
            placeholder="Selecciona un area..."
            label="nombre"
            :reduce="(area) => area"
            class="w-full mt-5 mb-5"
          ></v-select>
          <div v-if="selectedArea" class="grid lg:grid-cols-3 md:grid-cols-3 sm:grid-cols-2 grid-cols-1 gap-[30px]">
            <div v-for="kpi in KpiArea" :key="kpi" class="group px-3 py-10 rounded-md shadow dark:shadow-gray-700 hover:shadow-cyan-600/10 dark:hover:shadow-cyan-600/10 text-center bg-white dark:bg-slate-900 hover:bg-cyan-600/5 dark:hover:bg-cyan-600/5 transition duration-500">
              <div class="size-16 bg-cyan-600/5 group-hover:bg-cyan-600 text-cyan-600 group-hover:text-white rounded-md text-2xl flex align-middle justify-center items-center shadow-sm dark:shadow-gray-700 transition duration-500 mx-auto">
                <div class="flex items-center justify-center">
                  {{ kpi.value }}
                </div>
              </div>
              <div class="content mt-6">
                <p class="text-slate-400 mt-3">{{ kpi.title }}</p>
              </div>
            </div>
          </div>
        </div>

      </div>
      <!--end container-->
      <!-- Start -->
      <div v-else class="container center-content" style="height: auto; padding: 1rem; margin: 0; width: 100vw; max-width: 100vw;">
    <!--end grid-->
    <div
        class="bg-white flex justify-center dark:bg-cyan-700 rounded-md shadow dark:shadow-gray-700 transition duration-500"
        style="height: 80vh; width: 90%;">
        <!-- Matomo dashboard disabled until analytics service is provisioned -->
        <div class="flex items-center justify-center h-full text-gray-500">
            <p class="text-lg">Métricas analíticas no disponibles temporalmente.</p>
        </div>
    </div>
</div>


      <!--end container-->
    </section>
    <!--end container-->
  </section>
  <!--end section-->

  <footers />
  <switcher />
</template>

<script>
import vSelect from "vue-select";
import navbar from "@/components/Administrador/navbarAdministrador.vue";
import footers from "@/components/footer/footer.vue";
import { useDashboardAdminStore } from "@/stores/Administradores/dashboardAdminStore";
import { useCarrerasStore } from "@/stores/carrerasStore";
import { useEmpresasStore } from "@/stores/Estudiantes/empresasStore";
import { usePasantiasStore } from "@/stores/Pasantias/pasantiasStore";
import switcher from "@/components/General/switcher.vue";
import Swal from "sweetalert2";
export default {
  setup() {
    const dashboardStore = useDashboardAdminStore();
    const carrerasStore = useCarrerasStore();
    const empresasStore = useEmpresasStore();
    const pasantiasStore = usePasantiasStore();
    return { dashboardStore, carrerasStore, empresasStore, pasantiasStore };
  },
  mounted() {
    this.uuid = this.$keycloak.idTokenParsed.sub;
    this.getKPIWithoutParams();
    this.getCarreras();
    this.getEmpresas();
    this.getSectores();
    this.getAreas();
  },
  data() {
    return {
      uuid: "",
      contenedor: false,
      KpiSinParametros: [],
      selectedIdCarrera: "",
      KpiCarrera: [],
      selectedFechaInicio: "",
      selectedFechaFin: "",
      KpiFecha: [],
      selectedIdEmpresa: "",
      KpiEmpresa: [],
      selectedSector: "",
      KpiSector: [],
      selectedArea: "",
      KpiArea: [],
      carreras: [],
      empresas: [],
      sectores: [],
      areas: [],
    };
  },
  methods: {
    cambioContendor() {
      if (this.contenedor) {
        this.contenedor = false;
      } else {
        this.contenedor = true;
      }
    },
    async getKPIWithoutParams() {
      this.KpiSinParametros = await this.dashboardStore.getKPIWithoutParams(
        this.uuid
      );
    },
    async getCarreras() {
      this.carreras = await this.carrerasStore.getCarreras();
    },
    async getEmpresas() {
      this.empresas = await this.empresasStore.getEmpresasNombre();
    },
    async getSectores() {
      this.sectores = await this.empresasStore.getSectores();
    },
    async getAreas() {
      this.areas = await this.pasantiasStore.getAreas();
    },
    async getKPICarrera() {
      this.KpiCarrera = await this.dashboardStore.getKPIWithCareerParams(
        this.uuid,
        this.selectedIdCarrera
      );
    },
    async getKPIWithDate() {
      if (this.selectedFechaInicio > this.selectedFechaFin) {
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "La fecha de inicio no puede ser mayor a la fecha de fin",
        });
        return;
      }
      this.KpiFecha = await this.dashboardStore.getKPIWithDateParams(
        this.uuid,
        this.selectedFechaInicio,
        this.selectedFechaFin
      );
    },
    async getKPIEmpresa() {
      this.KpiEmpresa = await this.dashboardStore.getKPIWithEmpresaParams(
        this.uuid,
        this.selectedIdEmpresa
      );
    },
    async getKPISector() {
      this.KpiSector = await this.dashboardStore.getKPIWithSectorParams(
        this.uuid,
        this.selectedSector
      );
    },
    async getKPIArea() {
      this.KpiArea = await this.dashboardStore.getKPIWithAreaParams(
        this.uuid,
        this.selectedArea
      );
    },
  },
  components: {
    navbar,
    footers,
    switcher,
    vSelect,
  },
  watch: {
    selectedIdCarrera() {
      this.getKPICarrera();
    },
    selectedIdEmpresa() {
      this.getKPIEmpresa();
    },
    selectedSector() {
      this.getKPISector();
    },
    selectedArea() {
      this.getKPIArea();
    },
  },
};
</script>

<style lang="scss" scoped>
img {
  filter: drop-shadow(0 0 0.75rem rgba(0, 0, 0, 0.1));
}
.center-content {
    display: flex;
    justify-content: center;
    align-items: center;
}

@media (max-width: 768px) {
  .responsive-container {
    display: none;
  }
}
</style>
