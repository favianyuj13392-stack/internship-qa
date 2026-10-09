<template>
  <navbar :container="'container'" :lightNav="'justify-end'" />
  <section class="relative lg:mt-24 mt-[74px]">
    <div class="lg:container container-fluid">
      <div class="relative shrink-0">
        <img
          :src="
            data?.persona.bannerPerfil
              ? data?.persona.bannerPerfil
              : '@/assets/images/hero/bg5.jpg'
          "
          class="h-64 w-full object-cover lg:rounded-xl shadow dark:shadow-gray-700"
          alt=""
        />
      </div>

      <div class="md:flex mx-4 -mt-12">
        <div class="md:w-full">
          <div class="relative flex items-end justify-between">
            <div class="relative flex items-end">
              <img
                :src="
                  data?.persona.fotoPerfil ? data?.persona.fotoPerfil : image
                "
                class="size-28 rounded-full shadow dark:shadow-gray-800 ring-4 ring-slate-50 dark:ring-slate-800"
                alt=""
              />
              <div class="ms-4">
                <h5 class="text-lg font-semibold">
                  {{
                    data.persona.nombre +
                    " " +
                    data.persona.apellidoPaterno +
                    " " +
                    data.persona.apellidoMaterno
                  }}
                </h5>
                <p class="text-slate-400">
                  {{ data?.position ? data?.position : "Estudiante" }}
                </p>
              </div>
              <router-link
                to="/perfil/estudiante/editar"
                class="btn ml-10 rounded-full w-30 bg-green-600/5 hover:bg-green-600 border-green-600/10 hover:border-green-600 text-green-600 hover:text-white"
                >Aprobar<i data-feather="settings" class="size-4"></i
              ></router-link>
            </div>

        
          </div>
        </div>
      </div>
    </div>
    <!--end -->
  </section>
  <!-- End Hero -->

  <section v-if="this.ventana==0" class="relative mt-12 md:pb-24 pb-16">
    <div class="container">
      <div class="grid md:grid-cols-12 grid-cols-1 gap-[30px]">
        <div class="lg:col-span-8 md:col-span-7">
          <h5 class="text-xl font-semibold">
            {{
              data.persona.nombre +
              " " +
              data.persona.apellidoPaterno +
              " " +
              data.persona.apellidoMaterno
            }}
          </h5>
          <p class="text-slate-400 mt-4">
            {{
              data?.persona.descripcion
                ? data?.persona.descripcion
                : "Estudiante de la Universidad Católica Boliviana"
            }}
          </p>

          <h4 class="mt-6 text-xl font-semibold">Skills :</h4>
          <div class="grid grid-cols-1 gap-4" v-if="data.persona.habilidades">
            <div
              class="grid sm:grid-cols-3 gap-4 mt-5 pt-3"
              v-for="habilidad in this.data.persona.habilidades.habilidades"
              id="contenedor-habilidad"
            >
              <label class="form-label font-medium" for="WordPress">{{
                habilidad.habilidad
              }}</label>

              <StarRatingComponent
                name="rating"
                v-model="habilidad.nivel"
                :disabled="true"
              >
              </StarRatingComponent>
              <div class="grid grid-cols-1 gap-4 mt-0">
                <button
                  disabled
                  id="submit"
                  name="send"
                  v-if="habilidad.principal == false"
                  class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white text-sm p-0 rounded-md cursor-pointer"
                >
                  Secundario
                </button>

                <button
                  disabled
                  id="submit"
                  name="send"
                  v-if="habilidad.principal == true"
                  class="btn border-yellow-600 bg-yellow-600 hover:bg-yellow-700 text-white text-sm p-0 rounded-md cursor-pointer"
                >
                  Principal
                </button>
              </div>
            </div>
          </div>

          <h4 class="mt-6 text-xl font-semibold">Experiencia :</h4>

          <div
            class="grid sm:grid-cols-2 gap-4 mt-5 pt-3"
            v-for="experiencia in this.data.persona.experiencia.experiencia"
            :id="experiencia.titulo"
          >
            <div class="text-slate-400 font-semibold min-w-[80px] text-center">
              <!-- Icono de Tailwind CSS -->
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="h-16 w-16 mx-auto mb-2 block"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <path
                  fill-rule="evenodd"
                  clip-rule="evenodd"
                  d="M12.052 1.25H11.948C11.0495 1.24997 10.3003 1.24995 9.70552 1.32991C9.07773 1.41432 8.51093 1.59999 8.05546 2.05546C7.59999 2.51093 7.41432 3.07773 7.32991 3.70552C7.24995 4.3003 7.24997 5.04951 7.25 5.94799V6.02572C5.22882 6.09185 4.01511 6.32803 3.17157 7.17157C2 8.34315 2 10.2288 2 14C2 17.7712 2 19.6569 3.17157 20.8284C4.34315 22 6.22876 22 10 22H14C17.7712 22 19.6569 22 20.8284 20.8284C22 19.6569 22 17.7712 22 14C22 10.2288 22 8.34315 20.8284 7.17157C19.9849 6.32803 18.7712 6.09185 16.75 6.02572V5.94801C16.75 5.04954 16.7501 4.3003 16.6701 3.70552C16.5857 3.07773 16.4 2.51093 15.9445 2.05546C15.4891 1.59999 14.9223 1.41432 14.2945 1.32991C13.6997 1.24995 12.9505 1.24997 12.052 1.25ZM15.25 6.00189V6C15.25 5.03599 15.2484 4.38843 15.1835 3.9054C15.1214 3.44393 15.0142 3.24644 14.8839 3.11612C14.7536 2.9858 14.5561 2.87858 14.0946 2.81654C13.6116 2.7516 12.964 2.75 12 2.75C11.036 2.75 10.3884 2.7516 9.90539 2.81654C9.44393 2.87858 9.24644 2.9858 9.11612 3.11612C8.9858 3.24644 8.87858 3.44393 8.81654 3.9054C8.7516 4.38843 8.75 5.03599 8.75 6V6.00189C9.14203 6 9.55807 6 10 6H14C14.4419 6 14.858 6 15.25 6.00189ZM17 9C17 9.55229 16.5523 10 16 10C15.4477 10 15 9.55229 15 9C15 8.44772 15.4477 8 16 8C16.5523 8 17 8.44772 17 9ZM8 10C8.55228 10 9 9.55229 9 9C9 8.44772 8.55228 8 8 8C7.44772 8 7 8.44772 7 9C7 9.55229 7.44772 10 8 10Z"
                  fill="#0891b2"
                ></path>
              </svg>

              {{ experiencia.duracion }}
            </div>

            <div class="ms-4">
              <h5 class="text-lg font-medium mb-0">{{ experiencia.titulo }}</h5>
              <span class="text-slate-400 company-university">{{
                experiencia.empresa
              }}</span>
              <p class="text-slate-400 mt-2 mb-0">
                {{ experiencia.descripcion }}
              </p>
            </div>
          </div>
        </div>
        <!--end col-->

        <!--Inicio de profieee-->
        <div class="lg:col-span-4 md:col-span-5">
          <div
            class="bg-slate-50 dark:bg-slate-800 rounded-md shadow dark:shadow-gray-700 p-6 sticky top-20"
          >
            <h5 class="text-lg font-semibold">Detalles Personales:</h5>
            <ul class="list-none mt-4">
              <li class="flex justify-between mt-3 items-center font-medium">
                <span
                  ><i
                    data-feather="mail"
                    class="size-4 text-slate-400 me-3 inline"
                  ></i
                  ><span class="text-slate-400 me-3">Email :</span></span
                >

                <span style="font-size: x-small">{{ data.correo }}</span>
              </li>
              <li class="flex justify-between mt-3 items-center font-medium">
                <span
                  ><i
                    data-feather="gift"
                    class="size-4 text-slate-400 me-3 inline"
                  ></i
                  ><span class="text-slate-400 me-3"
                    >Ingreso a la Universidad :</span
                  ></span
                >

                <span>{{ data.persona.anioIngresoUniversidad }}</span>
              </li>
              <li class="flex justify-between mt-3 items-center font-medium">
                <span
                  ><i
                    data-feather="home"
                    class="size-4 text-slate-400 me-3 inline"
                  ></i
                  ><span class="text-slate-400 me-3">Teléfono :</span></span
                >

                <span>{{ data.persona.telefono }}</span>
              </li>
              <li class="flex justify-between mt-3 items-center font-medium">
                <span
                  ><i
                    data-feather="map-pin"
                    class="size-4 text-slate-400 me-3 inline"
                  ></i
                  ><span class="text-slate-400 me-3">Ci :</span></span
                >

                <span>{{ data.persona.ci }}</span>
              </li>
              <li class="flex justify-between mt-3 items-center font-medium">
                <span
                  ><i
                    data-feather="globe"
                    class="size-4 text-slate-400 me-3 inline"
                  ></i
                  ><span class="text-slate-400 me-3"
                    >Fecha Nacimiento :</span
                  ></span
                >

                <span style="font-size: small">{{
                  data.persona.fechaDeNacimiento
                }}</span>
              </li>
              <li
                class="flex justify-between mt-2"
                v-if="this.data.persona.redesSociales.web"
              >
                <span class="text-slate-400 font-medium">Website:</span>
                <span class="font-medium">{{
                  this.data.persona.redesSociales.web.url
                }}</span>
              </li>

              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Social:</span>

                <ul class="list-none text-end space-x-0.5">
                  <li
                    class="inline"
                    v-for="(url, name) in data.persona.redesSociales"
                    :key="name"
                  >
                    <a
                      :href="url.url"
                      target="_blank"
                      class="btn btn-icon btn-sm border-2 border-gray-200 dark:border-gray-700 rounded-md hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600 hover:text-white dark:text-white text-slate-400"
                    >
                      <i
                        :class="`uil uil-${name}`"
                        :title="name"
                        class="align-middle"
                      ></i>
                    </a>
                  </li>
                </ul>
                <!--end icon-->
              </li>
              <div >
                <!-- Detalles de la solicitud -->
              <h5 class="text-lg font-semibold">Detalles Solicitud:</h5>
                 <!--
                  Fecha de la solicitud y estado de la solicitud
                 -->
                <li class="flex justify-between mt-3 items-center font-medium">
                  <span
                    ><i
                      data-feather="calendar"
                      class="size-4 text-slate-400 me-3 inline"
                    ></i
                    ><span class="text-slate-400 me-3">Fecha:</span
                    ></span
                  >

                  <span>{{ cv.fechaAplicacion }}</span>
                </li>
                <li class="flex justify-between mt-3 items-center font-medium">
                  <span
                    ><i
                      data-feather="check-circle"
                      class="size-4 text-slate-400 me-3 inline"
                    ></i
                    ><span class="text-slate-400 me-3">Estado:</span
                    ></span
                  >

                  <span>
                    {{
                      cv.activo == true
                        ? "Aceptado"
                        : "Pendiente"
                    }}
                  </span>
                </li>


                <!-- Botón para descargar el CV -->
                <li
                  class="mt-3 w-full bg-white p-3 rounded-md shadow dark:shadow-gray-700 dark:bg-slate-900"
                >
                  <div class="relative">
                    <div class="flex items-center mb-3">
                      <i data-feather="file" class="size-8 text-slate-400"></i>
                      <span class="font-medium ms-2">
                        CV de aplicación
                      </span>

                    </div>
                  </div>
                  <a
                    @click="downloadPDF(cv.urlCurriculum)"
                    class="btn bg-cyan-600 hover:bg-cyan-700 border-cyan-600 dark:border-cyan-600 text-white rounded-md w-full"
                    download
                  >
                    <i class="uil uil-file-download-alt"></i> Descargar CV
                  </a>
                  <a
                    @click="this.ventana = 1"
                    class=" mt-3 btn bg-cyan-600 hover:bg-cyan-700 border-cyan-600 dark:border-cyan-600 text-white rounded-md w-full"
                    download
                  >
                    <i class="uil uil-file-download-alt"></i> Ver CV
                  </a>
                </li>
              </div>
            </ul>
          </div>
        </div>
        <!--fin de profieee-->
      </div>
    </div>
  </section>

  <section v-if="this.ventana==1" class="relative mt-12 md:pb-24 pb-16">
    <div class="container">
      <div class="grid md:grid-cols-12 grid-cols-1 gap-[30px]">
        <span class="close" @click="this.ventana = 0">&times;</span>

        <div class="md:col-span-8 col-span-full">
        <embed :src="cv.urlCurriculum" type="application/pdf" width="100%" height="600px" />
      </div>
      </div>
    </div>
  </section>
  <!-- Segundo moda empresa -->
  <div id="myModalCurriculum" class="modal" v-if="showFormularioCV">
    <div
      class="modal-content p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
    >
      <span class="close" @click="showFormularioCV = false">&times;</span>

      <div class="grid grid-cols-1 gap-4">
        <div>
          <h5 class="text-lg font-semibold mb-4">Selecciona un archivo PDF:</h5>
          <div class="mt-4">
            <input
              type="file"
              accept="application/pdf"
              @change="handleFileUploadPDF"
              class="form-input border border-slate-100 dark:border-slate-800 w-full"
            />
          </div>
        </div>
      </div>
    </div>

    <!-- Modal para ingresar título del PDF -->
    <div class="modal" v-if="showTitleModal">
      <div
        class="modal-content p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
      >
        <span class="close" @click="showTitleModal = false">&times;</span>
        <h5 class="text-lg font-semibold mb-4">Título del PDF</h5>
        <input
          type="text"
          v-model="pdfTitle"
          placeholder="Título del PDF"
          disabled
          class="form-input border border-slate-100 dark:border-slate-800 w-full"
        />
        <div class="mt-4">
          <button
            @click="savePDF"
            class="bg-blue-600 text-white p-2 rounded-md"
          >
            Guardar
          </button>
        </div>
      </div>
    </div>
  </div>
  <footers />
  <switcher />
</template>

<script>
import navbar from "@/components/Empresa/NavBarEmpresa.vue";
import footers from "@/components/footer/footer.vue";
import switcher from "@/components/General/switcher.vue";
import image from "@/assets/images/team/01.jpg";
import Swal from "sweetalert2";
import StarRatingComponent from "@/components/General/Extras/StartRatingComponent.vue";
import { useEstudiantesAdminStore } from "@/stores/Administradores/estudiantesAdminStore";
export default {
  setup() {
    const estudiantesStore = useEstudiantesAdminStore();
    return {
      estudiantesStore,
    };
  },
  data() {
    return {
      ventana: 0,
      idEstudiante: "",
      idSolicitud: "",
      cv: "",
      data: {
        persona: {
          nombre: "",
          apellidoPaterno: "",
          apellidoMaterno: "",
          descripcion: "",
          redesSociales: {
            web: {
              url: "",
            },
          },
          experiencia: {
            experiencia: [
              {
                titulo: "",
                empresa: "",
                duracion: "",
                descripcion: "",
              },
            ],
            habilidades: {
              habilidades: [
                {
                  habilidad: "",
                  nivel: 0,
                  principal: false,
                },
              ],
            },
          },
        },
      },
      datas: [
        {
          id: 1,
          name: "Calvin Carlo",
          position: "UI/UX Designer",
          image: image,
          banner: image,
          email: "",
        },
      ],
    };
  },

  methods: {
    downloadPDF(url){
      window.open(url, '_blank');
    },
    async fetchUserByUUID() {
      let loader = this.$loading.show();
      const response = await this.estudiantesStore.fetchUserByUUID(
        this.$keycloak.idTokenParsed.sub,
        this.idEstudiante
      );
      if (response == null) {
        loader.hide();
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "No se pudo cargar la información del estudiante",
        });
        return;
      }
      this.data = response;
      console.log(this.data);
      //Obtener la solicitud del estudiante
      const response1 = await this.estudiantesStore.getSolicitudByIdSolitud(
        this.$keycloak.idTokenParsed.sub,
        this.idSolicitud
      );
      this.cv = response1;
      console.log(this.cv);
      loader.hide();
    }
  },
  components: {
    navbar,
    switcher,
    footers,
    StarRatingComponent,
  },
  mounted() {
    this.idEstudiante = this.$route.params.idEstudiante;
    this.idSolicitud = this.$route.params.idSolicitud;
    this.fetchUserByUUID();
  },
};
</script>

<style scoped>
.modal {
  /* Hidden by default */
  position: fixed;
  /* Stay in place */
  z-index: 1;
  /* Sit on top */
  left: 0;
  top: 0;
  width: 100%;
  /* Full width */
  height: 100%;
  /* Full height */
  overflow: auto;
  /* Enable scroll if needed */
  background-color: rgb(0, 0, 0);
  /* Fallback color */
  background-color: rgba(0, 0, 0, 0.4);
  /* Black w/ opacity */
}

.modal-content {
  position: relative;
  top: 20%;
  margin: auto;
  padding: 0;
  border: 1px solid #888;
  width: 80%;
  /* Could be more or less, depending on screen size */
  max-width: 600px;
  /* Max width */
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  animation-name: animatetop;
  animation-duration: 0.4s;
}

@keyframes animatetop {
  from {
    top: -300px;
    opacity: 0;
  }

  to {
    top: 300px;
    opacity: 1;
  }
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>
