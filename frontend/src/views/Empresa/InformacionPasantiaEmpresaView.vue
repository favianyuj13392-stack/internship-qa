<template>
  <navbar :container="'container'" :lightNav="'justify-end'" />

  <!-- Start -->
  <section class="bg-slate-50 dark:bg-slate-800 md:py-24 py-16">
    <div class="container mt-10">
      <div class="grid md:grid-cols-12 grid-cols-1 gap-[30px]">
        <div class="lg:col-span-8 md:col-span-6">
          <div
            class="md:flex items-center p-6 shadow dark:shadow-gray-700 rounded-md bg-white dark:bg-slate-900 mb-6"
          >
            <img
              :src="
                data?.institucion.logoEmpresa
                  ? data?.institucion.logoEmpresa
                  : image
              "
              class="rounded-full size-28 p-4 bg-white dark:bg-slate-900 shadow dark:shadow-gray-700"
              alt=""
            />

            <div class="md:ms-4 md:mt-0 mt-6">
              <h5 class="text-xl font-semibold">
                {{
                  data?.pasantiasDto.titulo
                    ? data?.pasantiasDto.titulo
                    : "Back-End Developer"
                }}
              </h5>
              <div class="mt-2">
                <span class="text-slate-400 font-medium me-2 inline-block"
                  ><i
                    class="uil uil-building text-[18px] text-cyan-600 me-1"
                  ></i>
                  {{
                    data?.institucion.nombre
                      ? data?.institucion.nombre
                      : "Lenovo pvt. ltd."
                  }}</span
                >
                <span class="text-slate-400 font-medium me-2 inline-block"
                  ><i
                    class="uil uil-map-marker text-[18px] text-cyan-600 me-1"
                  ></i>
                  {{
                    data?.institucion.direccion
                      ? data?.institucion.direccion
                      : "Beijing,    China"
                  }}</span
                >
              </div>
            </div>
          </div>

          <div class="flex justify-center mb-4">
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
                <i class="uil uil-user text-[20px] text-cyan-500"></i>
                <i class="uil uil-file-alt text-[20px] text-cyan-500"></i>
                <span
                  class="ball bg-white dark:bg-slate-900 rounded-full absolute top-[2px] left-[2px] size-7"
                ></span>
              </label>
            </span>
          </div>
          <!--detallesssss div-->
          <div v-if="contenedor">
            <h5 class="text-lg font-semibold">Detalles de la pasantia:</h5>

            <p class="text-slate-400 mt-4">
              {{
                this.data.pasantiasDto.descripcion
                  ? this.data.pasantiasDto.descripcion
                  : "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
              }}
            </p>

            <h5 class="text-lg font-semibold mt-6">Áreas:</h5>
            <p class="text-slate-400 mt-4">
              Áreas a las cuales esta dirigida la pasantía
            </p>
            <ul class="list-none">
              <li
                v-for="item in this.data.pasantiasDto.areas"
                :key="item"
                class="text-slate-400 mt-2"
              >
                <i class="uil uil-arrow-right text-cyan-600 me-1"></i>{{ item }}
              </li>
            </ul>

            <h5 class="text-lg font-semibold mt-6">
              Requisistos necesarios y calificaciones:
            </h5>

            <ul class="list-none">
              <li
                v-for="item in this.data.pasantiasDto.requisitos"
                :key="item"
                class="text-slate-400 mt-2"
              >
                <i class="uil uil-arrow-right text-cyan-600 me-1"></i>{{ item }}
              </li>
            </ul>

            <h5 class="text-lg font-semibold mt-6">Funciones:</h5>

            <ul class="list-none">
              <li
                v-for="item in this.data.pasantiasDto.funciones"
                :key="item"
                class="text-slate-400 mt-2"
              >
                <i class="uil uil-arrow-right text-cyan-600 me-1"></i>{{ item }}
              </li>
            </ul>

            <h5 class="text-lg font-semibold mt-6">Beneficios:</h5>

            <ul class="list-none">
              <li
                v-for="item in this.data.pasantiasDto.beneficios"
                :key="item"
                class="text-slate-400 mt-2"
              >
                <i class="uil uil-arrow-right text-cyan-600 me-1"></i>{{ item }}
              </li>
            </ul>

            <div class="mt-5" v-if="!this.data.pasantiasDto.sinAplicantes">
              <router-link
                :to="'/empresa/administrador/pasantias/' + this.id + '/editar'"
                class="btn rounded-md bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white md:ms-2 w-full md:w-auto"
                >Editar Pasantia</router-link
              >
            </div>
          </div>
          <!--detallesssss usuario-->
          <div v-else>
            <div v-if="this.data.activoPasantia">
              <div
                v-if="this.data.postulantes.length <= 0"
                class="grid grid-cols-1 mt-10 pb-2 text-center"
              >
                <div v-if="data.pasantiasDto.sinAplicantes">
                  <h3
                    class="mb-2 md:text-[26px] md:leading-normal text-2xl leading-normal font-semibold text-red-400"
                  >
                    La pasantía fue terminada y catalogada como sin aplicantes
                  </h3>
                </div>
                <div v-else>
                  <h3
                    class="mb-2 md:text-[26px] md:leading-normal text-2xl leading-normal font-semibold text-red-400"
                  >
                    No hay postulantes que esperen una respuesta
                  </h3>
                  <div
                    @click="terminarPasantia"
                    class="btn rounded-md bg-red-600 hover:bg-red-700 border-red-600 hover:border-red-700 text-white md:ms-2 w-full md:w-auto mt-2"
                    >Terminar Pasantia</div
                  >
                </div>

              </div>

              <div v-else>
              
                <div class="grid grid-cols-1 text-center" v-if="this.isDataLoad">
                  <h2 
                  v-if="getPostulantesAprobados == 0"
                  class="text-lg font-semibold text-red-400">
                        *Nota: Para marcar a una pasantía como terminada, todos los postulantes deben haber sido rechazados. Si tiene postulantes pendientes, no podrá marcar la pasantía como terminada.
                  </h2>
                  <h3
                    class="mb-2 md:text-[26px] md:leading-normal text-2xl leading-normal font-semibold"
                  >
                    Postulantes a la pasantia
                  </h3>  
                  <span>Pasantes aprobados: 
                    {{ getPostulantesAprobados }}
                  </span>
                  <span>Pasantes pendientes: 
                    {{ getPostulantesPendientes }}
                  </span>
                </div>
                <div
                  v-if="this.isDataLoad"
                  class="grid lg:grid-cols-2 md:grid-cols-1 grid-cols-1 mt-8 gap-[30px]"
                >
                 
                  <!--end content-->


                  <div
                  v-for="(item,index) in this.listaPostulantes"
                  :key="item"
                  class="group bg-white dark:bg-slate-900 relative overflow-hidden rounded-md shadow dark:shadow-gray-700 text-center p-6">
                    <img
                      :src="item.persona.fotoPerfil"
                      class="size-20 rounded-full shadow dark:shadow-gray-700 mx-auto"
                  :alt="item.persona.nombre"
                    />

                    <div class="mt-2">
                      <div
                      class="hover:text-cyan-600 font-semibold text-lg"
                        >  {{ item.persona.nombre }}
                                      {{ item.persona.apellidoPaterno }}
                                      {{ item.persona.apellidoMaterno }}</div
                      >
                      <p class="text-sm text-slate-400">{{ item.position }}</p>
                    </div>

                    <ul class="mt-2 list-none">
                      <li v-for="type in item.type" :key="type" class="inline me-1">
                        <span
                          class="bg-cyan-600/10 inline-block text-cyan-600 text-xs px-2.5 py-0.5 font-semibold rounded-full"
                          >{{ type }}</span
                        >
                      </li>
                    </ul>
                    <div class="flex justify-between mt-2">
                      <div class="block">
                        <span class="text-slate-400">
                          <i class="fas fa-phone pr-1"></i>
                          {{ item.persona.telefono }}
                        </span> 
                      </div>
                      <div class="block">
                        <span class="block font-semibold text-cyan-600 text-sm" v-if="item.aplicacionPasantia.activo">
                          Aprobado
                        </span>
                        <span class="block font-semibold text-yellow-600 text-sm" v-else>
                          Pendiente
                        </span>
                      </div>
                    </div>
                    <div class="flex justify-between mt-2">
                      <div class="block">
                        <span class="text-slate-400">
                          Fecha de postulacion:
                          {{ item.aplicacionPasantia.fechaAplicacion }}
                        </span> 
                      </div>
                    </div>
                    <div class="flex justify-between mt-2" v-if="item.seleccionAplicante">
                      <div class="block">
                        <span class="text-slate-400">
                          Fecha de aprobacion:
                          {{ item.seleccionAplicante.fechaSeleccion }}
                        </span> 
                      </div>
                    </div>

                    <div class="mt-3">
                      <button
                        @click="mostrarInformacionPaante(index,1)"
                        class="btn btn-sm bg-cyan-600 hover:bg-cyan-700 border-cyan-600 dark:border-cyan-600 text-white rounded-md"
                      >
                        Perfil
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--end col-->

        <div class="lg:col-span-4 md:col-span-5">
          <div class="">
            <h5
              class="text-lg font-semibold text-cyan-600"
              v-if="this.data.activoPasantia == true"
            >
              Aprobado
            </h5>
            <h5
              class="text-lg font-semibold text-red-600"
              v-if="this.data.activoPasantia == false"
            >
              Sin Aprobar
            </h5>
          </div>
          <div
            class="bg-slate-50 dark:bg-slate-800 rounded-md shadow dark:shadow-gray-700 p-6 sticky top-20"
          >
            <div class="w-full leading-[0] border-0">
              <iframe
                :src="mapSrc"
                style="border: 0"
                class="w-full h-[350px] rounded-md shadow dark:shadow-gray-700"
                allowfullscreen
              ></iframe>
            </div>

            <ul class="list-none mt-4">
              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Dirección:</span>
                <span class="font-medium">{{
                  data.institucion.direccion
                }}</span>
              </li>

              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Correo:</span>
                <span class="font-medium">{{
                  this.data.institucion.correo
                }}</span>
              </li>

              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Sectores:</span>
                <div class="flex flex-wrap ml-16">
                  <span
                    class="font-medium bg-cyan-100 text-cyan-600 px-2 py-1 rounded-md mt-1"
                    v-for="sector in this.data.institucion.sectores"
                    :key="sector"
                    >{{ sector }}</span
                  >
                </div>
              </li>

              <li
                class="flex justify-between mt-2"
                v-if="this.data.institucion.redesSociales.web"
              >
                <span class="text-slate-400 font-medium">Website:</span>
                <span class="font-medium">{{
                  this.data.institucion.redesSociales.web
                }}</span>
              </li>

              <li class="flex justify-between mt-2">
                <span class="text-slate-400 font-medium">Social:</span>

                <ul class="list-none text-end space-x-0.5">
                  <li
                    class="inline"
                    v-for="(url, name) in data.institucion.redesSociales"
                    :key="name"
                  >
                    <a
                      :href="url"
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
            </ul>
          </div>
        </div>

        <!--end col-->
      </div>
      <!--end grid-->
    </div>
    <!--end container-->

    <!--end container-->
  </section>
  <!-- Segundo moda empresa -->
  <div id="myModalCurriculum" class="modal" v-if="showFormularioCV">
    <!-- Modal para preview de curriculum -->
    <!-- Modal -->
    <div class="modal" v-if="this.Modal == 1">
      <div class="modal-content p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900">
        <span class="close" @click="showFormularioCV = false">&times;</span>

        <!-- Encabezado del modal -->
        <div class="relative flex items-end justify-between mb-6">
          <div class="relative flex items-end">
            <img
              :src="personaInfo?.persona.fotoPerfil ? personaInfo?.persona.fotoPerfil : image"
              class="size-28 rounded-full shadow dark:shadow-gray-800 ring-4 ring-slate-50 dark:ring-slate-800"
              alt=""
            />
            <div class="ms-4">
              <h5 class="text-lg font-semibold">
                {{ personaInfo.persona.nombre + " " + personaInfo.persona.apellidoPaterno + " " + personaInfo.persona.apellidoMaterno }}
              </h5>
              <p class="text-slate-400">
                Estudiante
              </p>
            </div>
          </div>
        </div>
        <!-- Detalles personales -->
        <h5 class="text-lg font-semibold mb-4">Descripción:</h5>
        <p class="text-slate-400">
          {{ personaInfo.persona.descripcion }}
        </p>

        <h5 class="text-lg font-semibold mb-4 mt-4">Detalles Personales:</h5>
        <ul class="list-none mt-4">
          <li class="flex justify-between mt-3 items-center font-medium">
            <span><i data-feather="mail" class="size-4 text-slate-400 me-3 inline"></i><span class="text-slate-400 me-3">Email :</span></span>
            <span style="font-size: x-small">{{ personaInfo.usuario.correo }}</span>
          </li>
          <li class="flex justify-between mt-3 items-center font-medium">
            <span><i data-feather="gift" class="size-4 text-slate-400 me-3 inline"></i><span class="text-slate-400 me-3">Ingreso a la Universidad :</span></span>
            <span>{{ personaInfo.persona.anioIngresoUniversidad }}</span>
          </li>
          <li class="flex justify-between mt-3 items-center font-medium">
            <span><i data-feather="home" class="size-4 text-slate-400 me-3 inline"></i><span class="text-slate-400 me-3">Teléfono :</span></span>
            <span>{{ personaInfo.persona.telefono }}</span>
          </li>
          <li class="flex justify-between mt-3 items-center font-medium">
            <span><i data-feather="map-pin" class="size-4 text-slate-400 me-3 inline"></i><span class="text-slate-400 me-3">Ci :</span></span>
            <span>{{ personaInfo.persona.ci }}</span>
          </li>
          <li class="flex justify-between mt-3 items-center font-medium">
            <span><i data-feather="globe" class="size-4 text-slate-400 me-3 inline"></i><span class="text-slate-400 me-3">Fecha Nacimiento :</span></span>
            <span style="font-size: small">{{ personaInfo.persona.fechaDeNacimiento }}</span>
          </li>
          <li class="flex justify-between mt-2" v-if="this.personaInfo.persona.redesSociales.web">
            <span class="text-slate-400 font-medium">Website:</span>
            <span class="font-medium">{{ this.personaInfo.persona.redesSociales.web.url }}</span>
          </li>
          <li class="flex justify-between mt-2">
            <span class="text-slate-400 font-medium">Social:</span>
            <ul class="list-none text-end space-x-0.5">
              <li class="inline" v-for="(url, name) in personaInfo.persona.redesSociales" :key="name">
                <a :href="url.url" target="_blank"
                v-if="url.url.length > 0"
                  class="btn btn-icon btn-sm border-2 border-gray-200 dark:border-gray-700 rounded-md hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600 hover:text-white dark:text-white text-slate-400">
                  <i :class="`uil uil-${name}`" :title="name" class="align-middle"></i>
                </a>
              </li>
            </ul>
          </li>
        </ul>
        <!-- Habilidades-->
        <h5 class="text-lg font-semibold mb-4">Skills:</h5>
        <div class="grid grid-cols-1 gap-4" v-if="personaInfo.persona.habilidades">
          <div
            class="grid sm:grid-cols-3 gap-4 mt-5 pt-3"
            v-for="habilidad in this.personaInfo.persona.habilidades.habilidades"
            id="contenedor-habilidad"
            :key="habilidad.idHabilidad"
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
        <!--Experiencia-->
        <h5 class="text-lg font-semibold mb-4">Experiencia:</h5>
        <div
          class="grid sm:grid-cols-2 gap-4 mt-5 pt-3"
          v-for="experiencia in this.personaInfo.persona.experiencia.experiencia"
          :id="experiencia.titulo"
          :key="experiencia.idExperiencia"
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
        <!-- Detalles de la solicitud -->
        <div class="mt-4">
          <h5 class="text-lg font-semibold">Detalles Solicitud:</h5>
          <li class="flex justify-between mt-3 items-center font-medium">
            <span><i data-feather="calendar" class="size-4 text-slate-400 me-3 inline"></i><span class="text-slate-400 me-3">Fecha:</span></span>
            <span>{{ personaInfo.aplicacionPasantia.fechaAplicacion }}</span>
          </li>
          <li class="flex justify-between mt-3 items-center font-medium">
            <span><i data-feather="check-circle" class="size-4 text-slate-400 me-3 inline"></i><span class="text-slate-400 me-3">Estado:</span></span>
            <span>{{ personaInfo.aplicacionPasantia.activo == true ? "Aceptado" : "Pendiente" }}</span>
          </li>
          <li class="list-none mt-3 w-full bg-white p-3 rounded-md shadow dark:shadow-gray-700 dark:bg-slate-900">
            <div class="flex items-center mb-3">
              <span class="font-medium ms-2">CV de aplicación</span>
            </div>
            <a @click="downloadPDF(personaInfo.aplicacionPasantia.urlCurriculum)"
              class="btn bg-cyan-600 hover:bg-cyan-700 border-cyan-600 dark:border-cyan-600 text-white rounded-md w-full"
              download>
              <i class="uil uil-file-download-alt"></i> Descargar CV
            </a>
          </li>
        </div>

        <!-- Botones de aceptar y rechazar -->
        <div class="flex justify-end mt-6" v-if="personaInfo.aplicacionPasantia.activo == false">
          <button class="btn bg-emerald-600 hover:bg-emerald-700 text-white mr-2"
            @click="aceptarSolicitud">
            Aceptar
          </button>
          <button class="btn bg-red-600 hover:bg-red-700 text-white"
            @click="rechazarSolicitud">
            Rechazar
          </button>
        </div>
      </div>
    </div>
    <!-- Modal para perfil -->
    <div class="modal" v-if="this.Modal == 2">
      <div class="modal-content p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900">
        <span class="close" @click="showFormularioCV=false">&times;</span>
        <h5 class="text-lg font-semibold mb-4">
          CV de {{ personaInfo.persona.nombre + " " + personaInfo.persona.apellidoPaterno + " " + personaInfo.persona.apellidoMaterno }}
        </h5>
        <div class="w-full leading-[0] border-0">
          <iframe
            :src="webUrl"
            style="border: 0"
            class="w-full h-[500px] rounded-md shadow dark:shadow-gray-700"
            allowfullscreen
          ></iframe>
        </div>
      </div>
    </div>
  </div>
  <!--end section-->
  <!-- End -->
  <footers />
  <switcher />
</template>

<script>
import navbar from "@/components/Empresa/NavBarEmpresa.vue";

import footers from "@/components/footer/footer.vue";

import switcher from "@/components/General/switcher.vue";

import { usePasantiasAdministracionInstitucionStore } from "@/stores/Instituciones/PasantiasAdministracionInstitucionStore.js";
import Swal from "sweetalert2";
import StarRatingComponent from "@/components/General/Extras/StartRatingComponent.vue";

export default {
  
  setup() {
    const pasantiasAdministracionInstitucionStore =
      usePasantiasAdministracionInstitucionStore();
    return {
      pasantiasAdministracionInstitucionStore
    };
  },

  computed: {
    mapSrc() {
      const direccionEncoded = encodeURIComponent(
        this.data.institucion.direccion
      );
      return `https://www.google.com/maps/embed/v1/place?q=${direccionEncoded}&key=AIzaSyCoOVExrC3ADb7HXkXLyBZB3zyVqClHZ7w`;
    },
    getPostulantesAprobados() {
      return this.listaPostulantes.filter(
        (postulante) => postulante.aplicacionPasantia.activo
      ).length;
    },
    getPostulantesPendientes() {
      return this.listaPostulantes.filter(
        (postulante) => !postulante.aplicacionPasantia.activo
      ).length;
    }
  },

  methods: {
    cambioContendor() {
      if (this.contenedor) {
        this.contenedor = false;
      } else {
        this.contenedor = true;
      }
    },

    async fetchPasantiaActual() {
      let loader = this.$loading.show();
      const response =
        await this.pasantiasAdministracionInstitucionStore.fetchPasantiaInstitucionByUUID(
          this.$keycloak.tokenParsed.sub,
          this.id
        );
      if (response == null) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "No se pudo cargar la pasantia",
        });
        loader.hide();

        this.$router.push("/");
        return;
      }
      loader.hide();
      console.log("lo que me devuelve" + response);
      this.data = response;
      console.log(this.data);
      console.log(this.data.postulantes);
      this.listaPostulantes = this.data.postulantes;
      this.listaPostulantes = this.ordenarPostulantes();
      this.isDataLoad = true;
      this.personaInfo = {};
    },
    mostrarInformacionPaante(index, numero) {
      this.personaInfo = this.listaPostulantes[index];
      this.Modal = numero;
      this.showFormularioCV = true;
      this.webUrl = this.personaInfo.aplicacionPasantia.urlCurriculum;
    },
    //Metodo para ordernar la lista de postulantes para que primero se muestren los pendientes y luego los aprobados
    ordenarPostulantes(){
      let postulantesPendientes = this.listaPostulantes.filter(
        (postulante) => !postulante.aplicacionPasantia.activo
      );
      let postulantesAprobados = this.listaPostulantes.filter(
        (postulante) => postulante.aplicacionPasantia.activo
      );
      return postulantesPendientes.concat(postulantesAprobados);
    },
    downloadPDF(url){
      window.open(url, '_blank');
    },
    async aceptarSolicitud(){
      try{
        const comentarios = await Swal.fire({
          title: 'Comentarios',
          input: 'textarea',
          inputLabel: 'Comentarios',
          inputPlaceholder: 'Ingrese los comentarios',
          inputAttributes: {
            'aria-label': 'Ingrese los comentarios'
          },
          showCancelButton: true,
          confirmButtonText: 'Aceptar',
          cancelButtonText: 'Cancelar',
          showLoaderOnConfirm: true,
          preConfirm: (comentarios) => {
            if(!comentarios){
              Swal.showValidationMessage('Los comentarios son requeridos');
            }
          },
          allowOutsideClick: () => !Swal.isLoading()

        });
        if(comentarios.isConfirmed){
          
          let loader= this.$loading.show();
          
          const dataComment = {
            comentarios: comentarios.value
          };
          const response = await this.pasantiasAdministracionInstitucionStore.aceptarPasantia(
            this.$keycloak.tokenParsed.sub,
            this.id,
            this.personaInfo.aplicacionPasantia.idAplicacionPasantias,
            dataComment
          );
          loader.hide();
          if(response){
            Swal.fire({
              icon: 'success',
              title: 'Solicitud aceptada',
              showConfirmButton: false,
              timer: 1500
            });
            this.showFormularioCV = false;
            this.fetchPasantiaActual();
          }else{
            Swal.fire({
              icon: 'error',
              title: 'Error al aceptar la solicitud',
              showConfirmButton: false,
              timer: 1500
            });
          }
        }
      }catch(error){
        console.log(error);
      }

    },
    async rechazarSolicitud(){
      const loader = this.$loading.show();
      try{
        let confirmacion = await Swal.fire({
          title: '¿Está seguro de rechazar la solicitud?',
          text: "No podrá revertir esta acción",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#d33',
          cancelButtonColor: '#3085d6',
          confirmButtonText: 'Sí, rechazar',
          cancelButtonText: 'Cancelar'
        });
        if(confirmacion.isConfirmed){
          const response = await this.pasantiasAdministracionInstitucionStore.rechazarPasantia(
            this.$keycloak.tokenParsed.sub,
            this.id,
            this.personaInfo.aplicacionPasantia.idAplicacionPasantias
          );
          loader.hide();
          if(response){
            Swal.fire({
              icon: 'success',
              title: 'Solicitud rechazada',
              showConfirmButton: false,
              timer: 1500
            });
            
            this.showFormularioCV = false;
            this.fetchPasantiaActual();
          }else{
            Swal.fire({
              icon: 'error',
              title: 'Error al rechazar la solicitud',
              showConfirmButton: false,
              timer: 1500
            });
          }
        }
      }catch(error){
        console.log(error);
      }finally{
        loader.hide();
      }
    },
    async terminarPasantia(){
      const loader = this.$loading.show();
      try{
        let confirmacion = await Swal.fire({
          title: '¿Está seguro de terminar la pasantía?',
          text: "No podrá revertir esta acción y se catalogará como sin aplicantes",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#d33',
          cancelButtonColor: '#3085d6',
          confirmButtonText: 'Sí, terminar',
          cancelButtonText: 'Cancelar'
        });
        if(confirmacion.isConfirmed){
          const response = await this.pasantiasAdministracionInstitucionStore.terminarPasantia(
            this.$keycloak.tokenParsed.sub,
            this.id
          );
          loader.hide();
          if(response){
            Swal.fire({
              icon: 'success',
              title: 'Pasantía terminada',
              showConfirmButton: false,
              timer: 1500
            });
            this.$router.push("/empresa/administrador/pasantias");
          }else{
            Swal.fire({
              icon: 'error',
              title: 'Error al terminar la pasantía',
              showConfirmButton: false,
              timer: 1500
            });
          }
        }
      }catch(error){
        console.log(error);
      }finally{
        loader.hide();
      }
    }
  },

  props: {
    id: {
      type: String,
      default: "",
    },
  },

  data() {
    return {
      webUrl: "",
      listaPostulantes: "",
      pdfUrl: "",
      pdfTitle: "",
      Modal: 0,
      personaInfo:{},
      showFormularioCV:false,
      contenedor: false,
      isDataLoad: false,
      postulantesEstado: [
        {
          idUsuarios: 1,
          activo: true,
        },
        {
          idUsuarios: 2,
          activo: false,
        },
      ],
      data: {
        activoPasantia: true,

        postulantes: [
          {
            idPersona: 1,
            nombre: "Juan",
            apellidoPaterno: "Perez",
            apellidoMaterno: "Garcia",
            fotoPerfil: "https://randomuser.me/api/port",
            telefono: "123456789",
            descripcion: "Estudiante de la carrera de Ingenieria de Sistemas",
          },
        ],

        pasantiasDto: {
          titulo: "",
          descripcion: "",
          areas: [],
          requisitos: [],
          calificaciones: [],
        },
        institucion: {
          nombre: "",
          logoEmpresa: "",
          direccion: "",
          correo: "",
          redesSociales: {
            web: "",
            facebook: "",
            twitter: "",
            linkedin: "",
            instagram: "",
          },
          sectores: [],
        },
      },
      id: "",
      image:
        "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",

      datas: [
        {
          id: 1,
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Facebook",
          day: "2 days ago",
          type: "Full Time",
          job: "Web Designer / Developer",
          country: "Australia",
          vacancy: "21 applied",
          vacancy2: "of 40 vacancy",
        },
        {
          id: 2,
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Google",
          day: "2 days ago",
          type: "Part Time",
          job: "Marketing Director",
          country: "USA",
          vacancy: "21 applied",
          vacancy2: "of 40 vacancy",
        },
        {
          id: 3,
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Android",
          day: "2 days ago",
          type: "Remote",
          job: "Application Developer",
          country: "China",
          vacancy: "21 applied",
          vacancy2: "of 40 vacancy",
        },
        {
          id: 4,
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Lenovo",
          day: "2 days ago",
          type: "WFH",
          job: "Senior Product Designer",
          country: "Dubai",
          vacancy: "21 applied",
          vacancy2: "of 40 vacancy",
        },
        {
          id: 5,
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Spotify",
          day: "2 days ago",
          type: "Full Time",
          job: "C++ Developer",
          country: "India",
          vacancy: "21 applied",
          vacancy2: "of 40 vacancy",
        },
        {
          id: 6,
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Linkedin",
          day: "2 days ago",
          type: "Remote",
          job: "Php Developer",
          country: "Pakistan",
          vacancy: "21 applied",
          vacancy2: "of 40 vacancy",
        },
        {
          id: 7,
          job: "Software Engineering",
          day: "Posted 3 Days ago",
          type: "Full Time",
          salary: "$950 - $1100/mo",
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Circle CI Ltd.",
          location: "Australia",
        },
        {
          id: 8,
          job: "Web Developer",
          day: "Posted 3 Days ago",
          type: "Remote",
          salary: "$2500 - $2600/mo",
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Skype Ltd.",
          location: "America",
        },
        {
          id: 9,
          job: "UX/UI Designer",
          day: "Posted 3 Days ago",
          type: "Freelance",
          salary: "$3500 - $3600/mo",
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Snapchat Ltd.",
          location: "Canada",
        },
        {
          id: 10,
          job: "Human Resource(HR)",
          day: "Posted 3 Days ago",
          type: "Part Time",
          salary: "$2000 - $2500/mo",
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Shreethemes Ltd.",
          location: "UK",
        },
        {
          id: 11,
          job: "Web Designer",
          day: "Posted 3 Days ago",
          type: "Full Time",
          salary: "$1500 - $1600/mo",
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Telegram Ltd.",
          location: "China",
        },
        {
          id: 12,
          job: "Graphic Designer",
          day: "Posted 3 Days ago",
          type: "Part time",
          salary: "$500 - $600/mo",
          image:
            "https://tja.ucb.edu.bo/wp-content/uploads/2020/09/cropped-logo-UCB.png",
          name: "Whatsapp Ltd.",
          location: "India",
        },
      ],
      datas1: [
        "Participate in requirements analysis",
        "Write clean, scalable code using C# and .NET frameworks",
        "Test and deploy applications and systems",
        "Revise, update, refactor and debug code",
        "Improve existing software",
        "Develop documentation throughout the software development life cycle (SDLC",
        "Serve as an expert on applications and provide technical support",
      ],
      datas2: [
        "Proven experience as a .NET Developer or Application Developer",
        "good understanding of SQL and Relational Databases, specifically Microsoft SQL Server.",
        "Experience designing, developing and creating RESTful web services and APIs",
        "Basic know how of Agile process and practices",
        "Good understanding of object-oriented programming.",
        "Good understanding of concurrent programming.",
        "Sound knowledge of            application architecture and design.",
        "Excellent problem solving and analytical skills",
      ],
    };
  },
  components: {
    navbar,
    footers,
    switcher,
    StarRatingComponent,
  },
  

  mounted() {
    this.id = this.$route.params.id;

    this.fetchPasantiaActual();

    

    
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
  top: 15%;
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
