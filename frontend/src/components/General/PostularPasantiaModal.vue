<template>
  <div id="myModalCurriculum" class="modal">
    <div
      class="modal-content p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
      ref="myModalCurriculum"
    >
      <span class="close" @click="$emit('cancel')">&times;</span>

      <div class="grid grid-cols-1 gap-4">
        <div>
          <h5 class="text-lg font-semibold mt-4">
            Selecciona un curriculum para postular:
          </h5>
          <div class="mt-4 max-h-40vh overflow-y-auto">
            <table class="min-w-full leading-normal">
              <thead>
                <tr>
                  <th
                    class="px-5 py-3 border-b-2 border-purple-200 bg-purple-100 text-left text-xs font-semibold text-purple-600 uppercase tracking-wider"
                  ></th>
                  <th
                    class="px-5 py-3 border-b-2 border-purple-200 bg-purple-100 text-left text-xs font-semibold text-purple-600 uppercase tracking-wider"
                  >
                    CV
                  </th>
                  <th
                    class="px-5 py-3 border-b-2 border-purple-200 bg-purple-100 text-left text-xs font-semibold text-purple-600 uppercase tracking-wider"
                    id="dateCV"
                  >
                    Fecha de carga
                  </th>
                  <th
                    class="px-5 py-3 border-b-2 border-purple-200 bg-purple-100"
                  ></th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(curri, index) in paginatedCv"
                  :key="index"
                  :class="{
                    'bg-cyan-50 text-white': selectedCv === index,
                    'bg-white cursor-pointer': selectedCv !== index,
                  }"
                  @click="selectCv(index)"
                  class=""
                >
                  <td class="px-5 py-5 border-b border-gray-200 text-sm">
                    <input
                      type="radio"
                      :id="curri.idCurriculums"
                      :value="index"
                      v-model="selectedCv"
                      @change="selectCv(index)"
                    />
                  </td>
                  <td class="px-5 py-5 border-b border-purple-200 text-sm">
                    <div class="flex items-center">
                      <div class="flex-shrink-0">
                        <i
                          data-feather="file"
                          class="size-8 text-slate-400"
                        ></i>
                      </div>
                      <!-- Título para pantallas grandes -->
                   
                      <!-- Título para pantallas pequeñas -->
                      <div class="ml-3 ">
                        <p class="text-gray-900 whitespace-no-wrap">
                          {{ splitLongWords(curri.titulo) }}
                        </p>
                      </div>
                    </div>
                  </td>
                  <td
                    class="px-5 py-5 border-b border-gray-200 text-sm"
                    id="dateCVData"
                  >
                    <p class="text-gray-900 whitespace-no-wrap">
                      {{ curri.fechaCreacion }}
                    </p>
                  </td>
                  <td class="px-5 py-5 border-b border-gray-200 text-sm">
                    <button
                      @click="downloadPDF(curri.pdfCurriculum)"
                      :class="{
                        'btn bg-cyan-600 border-cyan-600 hover:bg-cyan-700  hover:border-cyan-700 text-white rounded-md':
                          selectedCv === index,
                        'btn bg-purple-500 border-purple-500 hover:bg-purple-400 hover:border-purple-400 text-white rounded-md':
                          selectedCv !== index,
                      }"
                    >
                      <!-- Texto para pantallas grandes -->
                      <span class="hidden lg:inline">Ver currículum</span>
                      <!-- Texto para pantallas pequeñas -->
                      <span class="inline lg:hidden">Ver</span>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div
            class="flex justify-between items-center mt-4"
            id="pagination"
            v-if="cv.length > itemsPerPage"
          >
            <button
              @click="previousPage"
              :disabled="currentPage === 1"
              class="btn bg-slate-400 border-slate-400 hover:bg-slate-500 text-white rounded-md cursor-pointer"
            >
              Anterior
            </button>
            <span>Página {{ currentPage }} de {{ totalPages }}</span>
            <button
              @click="nextPage"
              :disabled="currentPage === totalPages"
              class="btn bg-cyan-600 hover:bg-cyan-700 text-white rounded-md cursor-pointer"
            >
              Siguiente
            </button>
          </div>

          <h5 class="text-lg font-semibold mt-4 mb-4">
            ¿No encuentras tu curriculum? Sube uno nuevo
            <i
              @click="goToModificar()"
              class="uil uil-file-download-alt cursor-pointer text-cyan-600 hover:text-cyan-700"
            ></i>
          </h5>

          <div class="mt-5">
            <a
              @click="aplicarPasantia()"
              class="btn bg-emerald-600 hover:bg-emerald-700 border-emerald-600 dark:border-emerald-600 text-white rounded-md w-full"
              >Aplicar a pasantia</a
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import { useCurriculumsStore } from "@/stores/Estudiantes/curriculumsStore";
import { useAplicacionesStore } from "@/stores/Estudiantes/aplicacionesStore";
export default {
  setup() {
    const curriculumsStore = useCurriculumsStore();
    const aplicacionesStore = useAplicacionesStore();
    return { curriculumsStore, aplicacionesStore };
  },
  props: {
    pasantiaId: {
      type: Number,
      required: true,
    },
  },
  mounted() {
    this.getCurriculum();
  },
  data() {
    return {
      showTitleModal: false,
      pdfTitle: "",
      pdfFile: null,
      selectedCv: null,
      cv: [],
      currentPage: 1,
      itemsPerPage: 3,
    };
  },
  computed: {
    paginatedCv() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.cv.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.cv.length / this.itemsPerPage);
    },
  },
  methods: {
    splitLongWords(text, maxLength = 15) {
      return text
        .split(" ")
        .map((word) => {
          if (word.length > maxLength) {
            return word.match(new RegExp(`.{1,${maxLength}}`, "g")).join(" ");
          }
          return word;
        })
        .join(" ");
    },
    nextPage() {
      this.selectCv(null);
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    previousPage() {
      this.selectCv(null);
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },

    downloadPDF(url) {
      window.open(url, "_blank");
    },

    goToModificar() {
      document.body.style.overflow = "auto";

      this.$router.push("/perfil/estudiante");
    },

    async aplicarPasantia() {
      if (this.selectedCv === null) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Debes seleccionar un CV para aplicar a la pasantía",
        });
        return;
      }
      let confirm = false;
      await Swal.fire({
        title: "¿Estás seguro de aplicar a esta pasantía?",
        text: "Una vez aplicada no podrás deshacer esta acción",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sí, aplicar",
        cancelButtonText: "Cancelar",
      }).then((result) => {
        console.log(result);
        if (!result.isConfirmed) {
          confirm = false;
          return;
        }
        confirm = true;
      });
      if (!confirm) {
        return;
      }
      this.selectedCv =
        this.selectedCv + (this.currentPage - 1) * this.itemsPerPage;
      console.log(this.cv[this.selectedCv]);

      let loader = this.$loading.show();
      const response = await this.aplicacionesStore.postAplicacion(
        this.$keycloak.idTokenParsed.sub,
        this.pasantiaId,
        this.cv[this.selectedCv].idCurriculums
      );
      console.log(response);
      loader.hide();
      if (response.response == null) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: response.errorMessage,
        });
        window.scrollTo(0, 0);
        this.$emit("cancel");
        return;
      }

      Swal.fire({
        icon: "success",
        title: "¡Aplicación exitosa!",
        text: "Tu aplicación ha sido enviada con éxito",
      });
      window.scrollTo(0, 0);

      this.$emit("cancel");
      return;
    },

    async getCurriculum() {
      let loader = this.$loading.show({
        isFullPage: false,
        container: this.$refs.myModalCurriculum,
      });
      if (!this.$keycloak.authenticated) {
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

      if (response.length == 0) {
        loader.hide();
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "No se encontraron curriculums, por favor sube un curriculum desde tu perfil para poder aplicar a la pasantía",
        });
        window.scrollTo(0, 0);

        this.$emit("cancel");
        return;
      }
      this.cv = response;
      console.log(this.cv);
      loader.hide();
    },
    selectCv(index) {
      this.selectedCv = index;
      console.log(this.selectedCv);
    },
  },
};
</script>

<style scoped>
.modal {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  position: relative;
  top: 10%;
  margin: auto;
  padding: 0;
  border: 1px solid #888;
  width: 80%;
  max-width: 1000px;
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

@media (max-width: 640px) {
  table {
    border-collapse: collapse;
    border-spacing: 0;
    width: 100%;
    border: 1px solid #ddd;
  }
  th,
  td {
    text-align: left;
    padding: 8px !important;
  }
  tr:nth-child(even) {
    background-color: #f2f2f2;
  }
  #dateCVData,
  #dateCV {
    display: none;
  }
  /*CSS para la paginación*/
  #pagination {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}
</style>
