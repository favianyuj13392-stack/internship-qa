<template>
  <div>
    <div class="container md:pb-16">
      <div class="grid md:grid-cols-12 grid-cols-1 items-center gap-[30px]">
        <div class="lg:col-span-5 md:col-span-6 md:order-2 order-1">
          <div class="relative">
            <div class="relative flex justify-end">
              <img
                src="@/assets/images/about/ab03.jpg"
                class="lg:w-[400px] w-[280px] rounded-md shadow dark:shadow-gray-700"
                alt=""
              />
              <div class="absolute top-0 translate-y-2/4 start-0 text-center">
                <a
                  @click="toggle"
                  data-type="youtube"
                  :data-id="tutorialVideo.key"
                  class="lightbox size-20 rounded-full shadow-lg dark:shadow-gray-700 inline-flex items-center justify-center bg-white dark:bg-slate-900 text-cyan-600 dark:text-white cursor-pointer"
                >
                  <i
                    class="mdi mdi-play inline-flex items-center justify-center text-2xl"
                  ></i>
                </a>
              </div>
            </div>
            <div class="absolute md:-start-5 start-0 -bottom-16">
              <img
                src="@/assets/images/about/ab04.jpg"
                class="lg:w-[280px] w-[200px] border-8 border-white dark:border-slate-900 rounded-md shadow dark:shadow-gray-700"
                alt=""
              />
            </div>
          </div>
        </div>

        <div
          ref="formContainer"
          class="lg:col-span-7 md:col-span-6 mt-14 md:mt-0 md:order-1 order-2"
        >
          <div class="lg:me-5">
            <h3
              class="mb-6 md:text-[26px] text-2xl md:leading-normal leading-normal font-semibold"
            >
              Encuentra pasantías en las mejores empresas
            </h3>

            <p class="text-slate-400 max-w-xl">
              Conoce las empresas que se encuentran en este programa y con todas
              las pasantías que cuenta cada una en un solo CLICK
            </p>

            <div class="grid md:grid-cols-2 grid-cols-1 gap-6 mt-8">
              <div
                v-for="item in this.empresasDestacdas.slice(0, 6)"
                :key="item"
                class="p-3 rounded shadow dark:shadow-gray-700 bg-slate-50 dark:bg-slate-800"
              >
                <div class="flex items-center">
                  <div
                    class="size-12 flex items-center justify-center bg-white dark:bg-slate-900 shadow dark:shadow-gray-700 rounded-md"
                  >
                    <img
                      :src="item.logoEmpresa"
                      class="size-8"
                      :alt="item.nombre"
                    />
                  </div>

                  <div class="ms-3">
                    <router-link
                      :to="{
                        name: 'empresas-detalle',
                        params: { id: item.idInstituciones },
                      }"
                      class="block text-sm text-cyan-600"
                      >{{ item.nombre }}</router-link
                    >
                    {{ item.cantidadPasantias }} pasantías disponibles
                  </div>
                </div>
              </div>
            </div>

            <div class="grid md:grid-cols-12 grid-cols-1 mt-6">
              <div class="md:col-span-12">
                <router-link
                  to="/empresas"
                  class="btn btn-link text-slate-400 hover:text-cyan-600 after:bg-cyan-600 duration-500 ease-in-out"
                  >Ver más empresas
                  <i class="uil uil-arrow-right align-middle"></i
                ></router-link>
              </div>
            </div>
            <!--end grid-->
          </div>
        </div>
      </div>
    </div>
    <!--end container-->

    <!-- iframe start  -->
    <div
      :class="isActive ? 'fixed' : 'hidden'"
      class="bg-black/[0.9] top-0 left-0 bottom-0 w-[100%] h-[100%] z-999"
    >
      <div class="h-[100%] flex items-center justify-center">
        <iframe
          v-if="isActive"
          width="560"
          height="315"
          :src="videoUrl"
          title="YouTube video player"
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
          referrerpolicy="strict-origin-when-cross-origin"
          allowfullscreen
          loading="lazy"
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
    <!-- iframe end  -->
  </div>
</template>

<script>
import router from "@/router";
import { usePaginaPrincipalStore } from "@/stores/paginaPrincipal";
import { getTutorialVideo } from "@/config/tutorialVideos";

export default {
  setup() {
    const store = usePaginaPrincipalStore();
    return { store };
  },
  data() {
    return {
      tutorialVideo: getTutorialVideo("HOME_BEST_COMPANIES"),
      isActive: false,
      videoUrl: "",
      empresasDestacdas: "",
      fullPage: false,
      datas: [
        {
          id: 1,
          image:
            "https://editorial.aristeguinoticias.com/wp-content/uploads/2023/11/garfield-fuera-de-casa-3-14112023.jpeg",
          name: "Facebook",
          vacancy: "Garfield ",
        },
        {
          id: 2,
          image:
            "https://play-lh.googleusercontent.com/xmVUpHMpkci0tY46MtQkyfpz-HTnYp8E8SSjLulr5t97wI7Q-7RBpa46mWr_Zw4VNtrz",
          name: "Google",
          vacancy: "Mercantil Santa Cruz ",
        },
        {
          id: 3,
          image:
            "https://yt3.googleusercontent.com/uXmPvIfLu_mkH1K2eyyATnP9uS0k7JoUep9JKIgxWP6Hg6FtlayBq-emdHp3MlogRkuA4M-6=s900-c-k-c0x00ffffff-no-rj",
          name: "Android",
          vacancy: "Bisa",
        },
        {
          id: 4,
          image:
            "https://play-lh.googleusercontent.com/a_SdjKAz3foUzgPTMd0vYZdyR9eBfsO1yFWhkgIYztdqq3zLsQAQaszkovEiY5KOxg0",
          name: "Fie",
          vacancy: "5 Vacancy",
        },
        {
          id: 5,
          image:
            "https://www.diariomotor.com/imagenes/2012/10/Toyota-logo-1989-2560x1440-720x394.webp",
          name: "Toyota",
          vacancy: "Toyota",
        },
        {
          id: 6,
          image:
            "https://editorial.aristeguinoticias.com/wp-content/uploads/2023/11/garfield-fuera-de-casa-3-14112023.jpeg",
          name: "Facebook",
          vacancy: "Garfield ",
        },
      ],
    };
  },
  async mounted() {
    await this.fetchRecuento();
  },
  methods: {
    toggle() {
      this.isActive = !this.isActive;
      if (this.isActive) {
        this.videoUrl = this.tutorialVideo.embedUrl;
      } else {
        this.videoUrl = "";
      }
    },
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

<style lang="scss" scoped></style>
