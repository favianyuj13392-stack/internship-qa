<template>
  <div class="container">
    <div ref="formContainer"
      class="relative grid md:grid-cols-3 grid-cols-1 items-center gap-[30px] z-1"
    >
      <div class="counter-box text-center">
        <h1
          class="lg:text-5xl text-4xl font-semibold mb-2 dark:text-white flex justify-center"
        >
          <count-up
            class="counter-value"
            :start-val="-1000"
            :end-val="this.cantidadEstudiantes"
            >1010</count-up
          >
        
        </h1>
        <h5 class="counter-head text-sm font-semibold text-slate-400 uppercase">
          Estudiantes
        </h5>
      </div>
      <!--end counter box-->

      <div class="counter-box text-center">
        <h1
          class="lg:text-5xl text-4xl font-semibold mb-2 dark:text-white flex justify-center"
        >
          <count-up
            class="counter-value"
            :start-val="-1000"
            :end-val="this.cantidadPasantias"
            >2</count-up
          >
        </h1>
        <h5 class="counter-head text-sm font-semibold text-slate-400 uppercase">
          Pasantias
        </h5>
      </div>
      <!--end counter box-->

      <div class="counter-box text-center">
        <h1
          class="lg:text-5xl text-4xl font-semibold mb-2 dark:text-white flex justify-center"
        >
          <count-up class="counter-value" :start-val="-1000" :end-val="this.cantidadInstituciones"
            >0</count-up
          >
        </h1>
        <h5 class="counter-head text-sm font-semibold text-slate-400 uppercase">
          Instituciones
        </h5>
      </div>
      <!--end counter box-->
    </div>
  </div>
  <!--end container-->
</template>

<script>
import { usePaginaPrincipalStore } from "@/stores/paginaPrincipal";
import CountUp from "vue-countup-v3";

export default {
  components: {
    CountUp,
  },
  setup() {
    const store = usePaginaPrincipalStore();
    return { store };
  },
  data() {
    return {
      fullPage: false,
      recuento: null,
      cantidadEstudiantes: 0,
      cantidadPasantias: 0,
      cantidadInstituciones: 0,
    };
  },
  async mounted() {
    await this.fetchRecuento();
  },
  methods: {
    async fetchRecuento() {
      try {
        let loader = this.$loading.show({
          container: this.$refs.formContainer, // Limitar el loader al contenedor del formulario
          canCancel: true,
          color: '#800080', // Color morado
          onCancel: this.onCancel,
        });
        const response = await this.store.getRecuento();
        if (response) {
          this.recuento = response.data.response;
          console.log("Recuento", this.recuento);
          this.cantidadEstudiantes = this.recuento.cantidadEstudiantes;
          this.cantidadPasantias = this.recuento.cantidadPasantias;
          this.cantidadInstituciones = this.recuento.cantidadInstituciones;
          loader.hide();
        } else {
          console.error("Error al obtener los datos de recuento");
        }
      } catch (error) {
        console.error(error);
      }
    },
    submit() {
      let loader = this.$loading.show({
        // Parámetros opcionales
        container: this.fullPage ? null : this.$refs.formContainer,
        canCancel: true,
        color: '#800080', // Color morado
        onCancel: this.onCancel,
        
      });
      // Simular AJAX
      setTimeout(() => {
        loader.hide();
      }, 5000);
    },
    onCancel() {
      console.log("User cancelled the loader.");
    },
  },
};
</script>

<style scoped></style>
