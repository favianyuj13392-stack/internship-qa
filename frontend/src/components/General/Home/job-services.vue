<template>
  <div ref="formContainer">
    <div class="grid md:grid-cols-12 grid-cols-1 pb-8 items-end">
      <div class="lg:col-span-8 md:col-span-6">
        <h3
          class="mb-4 md:text-[26px] md:leading-normal text-2xl leading-normal font-semibold"
        >
       
          Empresas  <a  href="https://www.instagram.com/jhessika_zarate/"
          >j</a>unto a nosotros
        </h3>
        <p class="text-slate-400 max-w-xl">
          Contamos con conexión de más de 10 empresas reconocidas a nivel
          nacional
        </p>
      </div>
    </div>
    <!--end grid-->

    <div class="grid grid-cols-1 mt-7 relative">
      <div class="tiny-five-item">
        <div
          v-for="item in empresasDestacdas.slice(0, 10)"
          :key="item"
          class="tiny-slide"
        >
          <div
            class="group relative overflow-hidden rounded-md shadow dark:shadow-gray-700 transition duration-500 m-1"
          >
            <img
              :src="item.logoEmpresa"
              :alt="item.nombre"
              class="object-cover w-full h-80"
            />
            <div class="absolute inset-0 bg-slate-900/50"></div>
            <div class="absolute bottom-0 p-4">
              <router-link
                :to="{
                  name: 'empresas-detalle',
                  params: { id: item.idInstituciones },
                }"
                class="text-lg font-semibold text-white hover:text-white-600 transition-all duration-500 hover:shadow-effect"
                >{{ item.nombre }}</router-link
              >
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--grid-->
</template>

<script>
import { usePaginaPrincipalStore } from "@/stores/paginaPrincipal";
import { tns } from "tiny-slider/src/tiny-slider";
import image01 from "@/assets/images/work/01.jpg";
import image02 from "@/assets/images/work/02.jpg";
import router from "@/router";

export default {
  setup() {
    const store = usePaginaPrincipalStore();
    return { store };
  },
  data() {
    return {
      empresasDestacdas: "",
      fullPage: false,
      datas: [
        {
          image: image01,
          name: "Product & Branding Design",
        },
        {
          image: image02,
          name: "Product & Branding Design",
        },
        {
          image: image01,
          name: "Product & Branding Design",
        },
        {
          image: image02,
          name: "Product & Branding Design",
        },
        {
          image: image01,
          name: "Product & Branding Design",
        },
        {
          image: image02,
          name: "Product & Branding Design",
        },
      ],
    };
  },
  async mounted() {
    await this.fetchRecuento();
    tns({
      container: ".tiny-five-item",
      controls: true,
      mouseDrag: true,
      loop: true,
      rewind: true,
      autoplay: true,
      autoplayButtonOutput: false,
      autoplayTimeout: 3000,
      navPosition: "bottom",
      controlsText: [
        '<i class="mdi mdi-chevron-left "></i>',
        '<i class="mdi mdi-chevron-right"></i>',
      ],
      nav: false,
      speed: 400,
      gutter: 0,
      responsive: {
        1025: {
          items: 5,
        },
        992: {
          items: 4,
        },
        767: {
          items: 3,
        },
        425: {
          items: 1,
        },
      },
    });
  },
  methods: {
    async fetchRecuento() {
      try {
        let loader = this.$loading.show({
          container: this.$refs.formContainer, // Limitar el loader al contenedor del formulario
          canCancel: true,
          onCancel: this.onCancel,
        });
        const response = await this.store.getInstitucionesDestacadas();
        if (response) {
          this.empresasDestacdas = response.data.response.content;
          console.log("Recuento", this.empresasDestacdas);
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

<style lang="scss" scoped>
.hover\:shadow-effect:hover {
  text-shadow: 0 2px 5px rgb(0, 0, 0);
}
</style>
