<template>
  <link rel="preload" fetchpriority="high" as="image"
    :href="data?.persona.fotoPerfil ? data?.persona.fotoPerfil : '@/assets/images/team/01.jpg'">
  <link rel="preload" fetchpriority="high" as="image"
    :href="data?.persona.bannerPerfil ? data?.persona.bannerPerfil : '@/assets/images/hero/bg5.jpg'">

  <navbar :container="'container'" :lightNav="'justify-end'" />
  <section class="relative lg:mt-24 mt-[74px]">
    <div class="lg:container container-fluid">
      <div class="relative shrink-0">
        <img :src="data?.persona.bannerPerfil
            ? data?.persona.bannerPerfil
            : '@/assets/images/hero/bg5.jpg'
          " class="h-64 w-full object-cover lg:rounded-xl shadow dark:shadow-gray-700" alt="" />
      </div>

      <div class="md:flex mx-4 -mt-12">
        <div class="md:w-full">
          <div class="relative flex items-end justify-between">
            <div class="relative flex items-end">
              <img :src="data?.persona.fotoPerfil ? data?.persona.fotoPerfil : '@/assets/images/team/01.jpg'
                " class="size-28 rounded-full shadow dark:shadow-gray-800 ring-4 ring-slate-50 dark:ring-slate-800"
                alt="" />
              <div class="ms-4" id="contenedor-nombre">
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
            </div>

            <div class="">
              <router-link to="/perfil/estudiante/editar"
              id="editarPerfil"
                class="btn btn-icon rounded-full bg-cyan-600/5 hover:bg-cyan-600 border-cyan-600/10 hover:border-cyan-600 text-cyan-600 hover:text-white"><i
                  data-feather="settings" class="size-4"></i></router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--end -->
  </section>
  <!-- End Hero -->

  <section class="relative mt-12 md:pb-24 pb-16">
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
          <p class="text-slate-400 mt-4" id="valueDescripcionEstudiante">
            {{
              data?.persona.descripcion
                ? data?.persona.descripcion
                : "Estudiante de la Universidad Católica Boliviana"
            }}
          </p>

          <h4 class="mt-6 text-xl font-semibold">Skills :</h4>
          <div class="grid grid-cols-1 gap-4" v-if="data.persona.habilidades">
            <div class="grid sm:grid-cols-3 gap-4 mt-5 pt-3"
              v-for="habilidad in this.data.persona.habilidades.habilidades" id="contenedor-habilidad">
              <label class="form-label font-medium" for="WordPress">{{
                habilidad.habilidad
                }}</label>

              <StarRatingComponent name="rating" v-model="habilidad.nivel" :disabled="true">
              </StarRatingComponent>
              <div class="grid grid-cols-1 gap-4 mt-0">
                <button disabled id="submit" name="send" v-if="habilidad.principal == false"
                  class="btn border-zinc-600 bg-zinc-600 hover:bg-zinc-700 text-white text-sm p-0 rounded-md cursor-pointer">
                  Secundario
                </button>

                <button disabled id="submit" name="send" v-if="habilidad.principal == true"
                  class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white text-sm p-0 rounded-md cursor-pointer">
                  Principal
                </button>
              </div>
            </div>
          </div>

          <h4 class="mt-6 text-xl font-semibold">Experiencia :</h4>

          <div class="grid sm:grid-cols-2 gap-4 mt-5 pt-3"
            v-for="experiencia in this.data.persona.experiencia.experiencia" :id="experiencia.titulo">
            <div class="text-slate-400 font-semibold min-w-[80px] text-center">
              <!-- Icono de Tailwind CSS -->
              <svg xmlns="http://www.w3.org/2000/svg" class="h-16 w-16 mx-auto mb-2 block" viewBox="0 0 20 20"
                fill="currentColor">
                <path fill-rule="evenodd" clip-rule="evenodd"
                  d="M12.052 1.25H11.948C11.0495 1.24997 10.3003 1.24995 9.70552 1.32991C9.07773 1.41432 8.51093 1.59999 8.05546 2.05546C7.59999 2.51093 7.41432 3.07773 7.32991 3.70552C7.24995 4.3003 7.24997 5.04951 7.25 5.94799V6.02572C5.22882 6.09185 4.01511 6.32803 3.17157 7.17157C2 8.34315 2 10.2288 2 14C2 17.7712 2 19.6569 3.17157 20.8284C4.34315 22 6.22876 22 10 22H14C17.7712 22 19.6569 22 20.8284 20.8284C22 19.6569 22 17.7712 22 14C22 10.2288 22 8.34315 20.8284 7.17157C19.9849 6.32803 18.7712 6.09185 16.75 6.02572V5.94801C16.75 5.04954 16.7501 4.3003 16.6701 3.70552C16.5857 3.07773 16.4 2.51093 15.9445 2.05546C15.4891 1.59999 14.9223 1.41432 14.2945 1.32991C13.6997 1.24995 12.9505 1.24997 12.052 1.25ZM15.25 6.00189V6C15.25 5.03599 15.2484 4.38843 15.1835 3.9054C15.1214 3.44393 15.0142 3.24644 14.8839 3.11612C14.7536 2.9858 14.5561 2.87858 14.0946 2.81654C13.6116 2.7516 12.964 2.75 12 2.75C11.036 2.75 10.3884 2.7516 9.90539 2.81654C9.44393 2.87858 9.24644 2.9858 9.11612 3.11612C8.9858 3.24644 8.87858 3.44393 8.81654 3.9054C8.7516 4.38843 8.75 5.03599 8.75 6V6.00189C9.14203 6 9.55807 6 10 6H14C14.4419 6 14.858 6 15.25 6.00189ZM17 9C17 9.55229 16.5523 10 16 10C15.4477 10 15 9.55229 15 9C15 8.44772 15.4477 8 16 8C16.5523 8 17 8.44772 17 9ZM8 10C8.55228 10 9 9.55229 9 9C9 8.44772 8.55228 8 8 8C7.44772 8 7 8.44772 7 9C7 9.55229 7.44772 10 8 10Z"
                  fill="#0891b2"></path>
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
          <div class="bg-slate-50 dark:bg-slate-800 rounded-md shadow dark:shadow-gray-700 p-6 sticky top-20">
            <h5 class="text-lg font-semibold">Detalles Personales:</h5>
            <ul class="list-none mt-4">
              <li class="flex justify-between mt-3 items-center font-medium">
                <span><i data-feather="mail" class="size-4 text-slate-400 me-3 inline"></i><span
                    class="text-slate-400 me-3">Email :</span></span>

                <span style="font-size: x-small" id="valueCorreoEstudiante">{{ data.correo }}</span>
              </li>
              <li class="flex justify-between mt-3 items-center font-medium">
                <span><i data-feather="gift" class="size-4 text-slate-400 me-3 inline"></i><span
                    class="text-slate-400 me-3">Ingreso a la Universidad :</span></span>

                <span id="valueAnioIngresoEstudiante">{{ data.persona.anioIngresoUniversidad }}</span>
              </li>
              <li class="flex justify-between mt-3 items-center font-medium">
                <span><i data-feather="home" class="size-4 text-slate-400 me-3 inline"></i><span
                    class="text-slate-400 me-3">Teléfono :</span></span>

                <span id="valueTelefonoEstudiante">{{ data.persona.telefono }}</span>
              </li>
              <li class="flex justify-between mt-3 items-center font-medium">
                <span><i data-feather="map-pin" class="size-4 text-slate-400 me-3 inline"></i><span
                    class="text-slate-400 me-3">Ci :</span></span>

                <span>{{ data.persona.ci }}</span>
              </li>
              <li class="flex justify-between mt-3 items-center font-medium">
                <span><i data-feather="globe" class="size-4 text-slate-400 me-3 inline"></i><span
                    class="text-slate-400 me-3">Fecha Nacimiento :</span></span>

                <span style="font-size: small">{{
                  data.persona.fechaDeNacimiento
                  }}</span>
              </li>
              <li class="flex justify-between mt-2" v-if="this.data.persona.redesSociales.web">
                <span class="text-slate-400 font-medium">Website:</span>
                <span class="font-medium">{{
                  this.data.persona.redesSociales.web.url
                  }}</span>
              </li>

              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Social:</span>

                <ul class="list-none text-end space-x-0.5">
                  <li class="inline" v-for="(url, name) in data.persona.redesSociales" :key="name">
                    <a v-if="url.url" :href="url.url" target="_blank"
                      class="btn btn-icon btn-sm border-2 border-gray-200 dark:border-gray-700 rounded-md hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600 hover:text-white dark:text-white text-slate-400">
                      <i :class="`uil uil-${name}`" :title="name" class="align-middle"></i>
                    </a>
                  </li>
                </ul>
                <!--end icon-->
              </li>
              <li class="mt-3">
                <a @click="FormularioCV()"
                  class="btn bg-cyan-600 hover:bg-cyan-700 border-cyan-600 dark:border-cyan-600 text-white rounded-md w-full"><i
                    class="uil uil-file-download-alt"></i> Agregar
                  curriculums</a>
              </li>
              <div v-for="(curri, index) in cv" :key="index">
                <li class="mt-3 w-full bg-white p-3 rounded-md shadow dark:shadow-gray-700 dark:bg-slate-900">
                  <div class="relative">
                    <!-- Botón de eliminación -->
                    <button @click="removePDF(curri.idCurriculums)"
                      class="absolute top-0 right-0 px-3 py-1 bg-red-600 text-white rounded-md">
                      x
                    </button>

                    <!-- Contenedor del icono y texto -->
                    <div class="flex items-center mb-3">
                      <i data-feather="file" class="size-8 text-slate-400"></i>
                      <!-- Ajuste del texto -->
                      <span class="font-medium truncate" :title="splitLongWords(curri.titulo)">
                        {{ splitLongWords(curri.titulo) }}
                      </span>
                    </div>
                  </div>
                  <!-- Botón de descarga -->
                  <a @click="downloadPDF(curri.pdfCurriculum)"
                    class="btn bg-cyan-600 hover:bg-cyan-700 border-cyan-600 dark:border-cyan-600 text-white rounded-md w-full flex items-center justify-center gap-2"
                    download>
                    <i class="uil uil-file-download-alt"></i>
                    Ver currículum
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
  <!-- Segundo moda empresa -->
  <div id="myModalCurriculum" class="modal" v-if="showFormularioCV">
    <div class="modal-content p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900">
      <span class="close" @click="showFormularioCV = false">&times;</span>

      <div class="grid grid-cols-1 gap-4">
        <div>
          <h5 class="text-lg font-semibold mb-4">Selecciona un archivo PDF:</h5>
          <div class="mt-4">
            <input type="file" accept="application/pdf" @change="handleFileUploadPDF"
              class="form-input border border-slate-100 dark:border-slate-800 w-full" />
          </div>
        </div>
      </div>
    </div>

    <!-- Modal para ingresar título del PDF -->
    <div class="modal" v-if="showTitleModal">
      <div class="modal-content p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900">
        <span class="close" @click="showTitleModal = false">&times;</span>
        <h5 class="text-lg font-semibold mb-4">Título del PDF</h5>
        <input type="text" v-model="pdfTitle" placeholder="Título del PDF" disabled
          class="form-input border border-slate-100 dark:border-slate-800 w-full" />
        <div class="mt-4">
          <button @click="savePDF" class="bg-blue-600 text-white p-2 rounded-md">
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
import navbar from "@/components/General/navbarGeneral.vue";
import footers from "@/components/footer/footer.vue";
import switcher from "@/components/General/switcher.vue";
import image from "@/assets/images/team/01.jpg";
import { useEstudiantesStore } from "@/stores/Estudiantes/estudiantesStore";
import Swal from "sweetalert2";
import StarRatingComponent from "@/components/General/Extras/StartRatingComponent.vue";
import { useCurriculumsStore } from "@/stores/Estudiantes/curriculumsStore";
export default {
  setup() {
    const estudiantesStore = useEstudiantesStore();
    const curriculumsStore = useCurriculumsStore();
    return {
      estudiantesStore,
      curriculumsStore,
    };
  },
  data() {
    return {
      id: "",
      cv: "",
      showTitleModal: false,
      pdfTitle: "",
      pdfFile: null,
      nuevaEmpresa: {
        pdfs: [],
      },
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
      image,
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
      showFormularioCV: false,
    };
  },

  methods: {
    splitLongWords(text, maxLength = 10) {
      /*
      return text.split(' ').map(word => {
        if (word.length > maxLength) {
          return word.match(new RegExp(`.{1,${maxLength}}`, 'g')).join(' ');
        }
        return word;
      }).join(' ');*/
      return text;
    },
    FormularioCV() {
      this.showFormularioCV = true;
      console.log("hola");
      console.log(this.nuevaEmpresa.pdfs);
    },
    async fetchUserByUUID() {
      if (!this.$keycloak.authenticated) {
        this.$router.push("/");
        return;
      }

      let loader = this.$loading.show();
      const response = await this.estudiantesStore.fetchUserByUUID(
        this.$keycloak.idTokenParsed.sub
      );
      if (response == null) {
        loader.hide();
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "No se pudo cargar la información del usuario",
        });
        this.$keycloak.logout();
        this.$router.push("/");
        return;
      }
      let habilidades = response.persona.habilidades.habilidades;
      habilidades.sort((a, b) => b.principal - a.principal);
      response.persona.habilidades.habilidades = habilidades;

      this.data = response;
      console.log(this.data);

      const response1 = await this.curriculumsStore.getCurriculum(
        this.$keycloak.idTokenParsed.sub
      );

      this.cv = response1;
      console.log(this.cv);
      loader.hide();
    },
    async getCurriculum() {
      let loader = this.$loading.show();
      const response = await this.curriculumsStore.getCurriculum(
        this.$keycloak.idTokenParsed.sub
      );
      if (response == null) {
        loader.hide();
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "No se pudo cargar la información del usuario",
        });
        this.$keycloak.logout();
        this.$router.push("/");
        return;
      }

      this.cv = response;
      console.log(this.cv);
      loader.hide();
    },
    async handleFileUploadPDF(event) {
      const loader = this.$loading.show();
      try {
        const file = event.target.files[0];
        if (file) {
          this.pdfFile = file;
          this.showTitleModal = true;
          //Ponemos el nombre del archivo en el input
          this.pdfTitle = file.name;
        }
      } catch (error) {
        console.log(error);
      } finally {
        loader.hide();
      }
    },
    async savePDF() {
      const loader = this.$loading.show();
      console.log(this.pdfTitle);
      console.log("yhio pedfFile" + this.pdfFile);
      try {
        if (this.pdfFile && this.pdfTitle) {
          const response = await this.curriculumsStore.postCurriculum(
            this.pdfFile,
            this.$keycloak.idTokenParsed.sub
          );
          console.log(response);
          if (response == null) {
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "No se pudo cargar el archivo PDF",
            });
            return;
          }

          Swal.fire({
            icon: "success",
            title: "¡Éxito!",
            text: "El archivo PDF se ha subido correctamente",
            timer: 2000,
          });
        }
      } catch (error) {
        console.log(error);
      } finally {
        loader.hide();
      }
      this.showTitleModal = false;
      this.showFormularioCV = false;
      this.getCurriculum();
    },
    downloadPDF(url) {
      window.open(url, '_blank');
    },
    async removePDF(index) {
      console.log("numero de index" + index);
      let loader = this.$loading.show();
      try {
        const result = await Swal.fire({
          title: "¿Estás seguro de eliminar este  Curriculum?",
          text: "¡Se eliminará de manera permanente!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "¡Sí, elimínala!",
        });

        if (result.isConfirmed) {
          try {
            const response = await this.curriculumsStore.deleteCurriculum(
              this.$keycloak.idTokenParsed.sub,
              index
            );
            if (response == null) {
              const errorMessage = this.curriculumsStore.errorMessageDeleteCurriculum;
              console.error("Error eliminando el archivo PDF:", errorMessage);
              Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "ERROR: " + errorMessage,
              });
              this.showFormularioCV = false;
              this.showTitleModal = false;
              return;
            }
            Swal.fire({
              title: "Eliminada!",
              text: "Tu archivo ha sido eliminado.",
              icon: "success",
            });
          } catch (error) {
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "ERROR: " + error.message,
            });
          }
        }
      } catch (error) {
        console.error("Error mostrando el diálogo de confirmación:", error);
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "ERROR: " + error.message,
        });
      } finally {
        loader.hide();
      }
      this.getCurriculum();
    },
  },
  components: {
    navbar,
    switcher,
    footers,
    StarRatingComponent,
  },
  mounted() {
    this.fetchUserByUUID();

    //this.id = this.$route.params.id;
    //this.data = this.datas.find((item) => item.id === parseInt(this.id));
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

@media (max-width: 480px) {
  #contenedor-nombre {
    margin-top: 70px;
  }
}
</style>
