<template>
  <navbar :container="'container'" :lightNav="'justify-end'" />
  <section class="relative lg:mt-24 mt-[74px] pb-16">
    <div class="lg:container container-fluid">
      <div class="profile-banner relative text-transparent">
        <input
          id="pro-banner"
          name="profile-banner"
          type="file"
          class="hidden"
          @change="handleFileUploadBannerPerfil"
        />
        <div class="relative shrink-0">
          <img
            :src="this.estudianteDto.persona.bannerPerfil"
            class="h-64 w-full object-cover lg:rounded-xl shadow dark:shadow-gray-700"
            id="profile-banner"
            alt=""
          />
          <label
            class="absolute inset-0 cursor-pointer"
            for="pro-banner"
          ></label>
        </div>
      </div>

      <div class="md:flex mx-4 -mt-12">
        <div class="md:w-full">
          <div class="relative flex items-end">
            <div class="profile-pic text-center">
              <input
                id="pro-img"
                name="profile-image"
                type="file"
                class="hidden"
                @change="handleFileUploadFotoPerfil"
              />
              <div>
                <div
                  class="relative size-28 max-w-[112px] max-h-[112px] mx-auto"
                >
                  <img
                    :src="this.estudianteDto.persona.fotoPerfil"
                    class="rounded-full shadow dark:shadow-gray-800 ring-4 ring-slate-50 dark:ring-slate-800"
                    id="profile-image"
                    alt=""
                  />
                  <label
                    class="absolute inset-0 cursor-pointer"
                    for="pro-img"
                  ></label>
                </div>
              </div>
            </div>
            <div class="ms-4">
              <h5 class="text-lg font-semibold">
                {{
                  this.estudianteDto.persona.nombre +
                  " " +
                  this.estudianteDto.persona.apellidoPaterno +
                  " " +
                  this.estudianteDto.persona.apellidoMaterno
                }}
              </h5>
              <p class="text-slate-400">Estudiante</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--end -->
    <!--inicio de profie-->
    <div class="container mt-16">
      <div class="grid lg:grid-cols-12 grid-cols-1 gap-[30px]">
        <div class="lg:col-span-12">
          <div
            class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
          >
            <h5 class="text-lg font-semibold mb-4">Personal Detail :</h5>
            <div>
              <div
                class="grid lg:grid-cols-12 md:grid-cols-2 grid-cols-1 gap-4"
              >
                <div class="lg:col-span-6">
                  <label class="form-label font-medium"
                    >Nombres : <span class="text-red-600">*</span></label
                  >
                  <input
                    type="text"
                    class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                    :value="estudianteDto.persona.nombre.toUpperCase()"
                    @input="updateField('nombre', $event.target.value)"
                    placeholder="Nombres:"
                    id="firstname"
                    name="name"
                    required
                    disabled
                  />
                </div>
                <div class="lg:col-span-4">
                  <label class="form-label font-medium"
                    >Ci :<span class="text-red-600">*</span></label
                  >
                  <input
                    type="text"
                    class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                    v-model="estudianteDto.persona.ci"
                    placeholder="Ci"
                    name="address"
                    required=""
                  />
                </div>

                <div class="lg:col-span-6">
                  <label class="form-label font-medium"
                    >Apellido Paterno :
                    <span class="text-red-600">*</span></label
                  >
                  <input
                    type="text"
                    class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                    placeholder="Apellido Paterno:"
                    id="lastname"
                    :value="estudianteDto.persona.apellidoPaterno.toUpperCase()"
                    @input="updateField('apellidoPaterno', $event.target.value)"
                    name="name"
                    required=""
                    disabled
                  />
                </div>

                <div class="lg:col-span-6">
                  <label class="form-label font-medium"
                    >Apellido Materno :
                    <span class="text-red-600">*</span></label
                  >
                  <input
                    type="text"
                    class="form-input border border-slate-100 dark:border-slate-800 mt-2"

                      :value="estudianteDto.persona.apellidoMaterno.toUpperCase()"
                    @input="updateField('apellidoMaterno', $event.target.value)"
                    placeholder="Apellido Paterno:"
                    id="lastname"
                    name="name"
                    required=""
                    disabled
                  />
                </div>

                <div class="lg:col-span-6">
                  <label class="form-label font-medium"
                    >Email : <span class="text-red-600">*</span></label
                  >
                  <input
                    type="email"
                    class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                    v-model="estudianteDto.correo"
                    placeholder="Email"
                    name="email"
                    required=""
                    disabled
                  />
                </div>

                <div class="lg:col-span-6">
                  <label class="form-label font-medium" for="birthday"
                    >Fecha de Nacimiento :<span class="text-red-600"
                      >*</span
                    ></label
                  >
                  <input
                    type="date"
                    id="birthday"
                    name="birthday"
                    v-model="estudianteDto.persona.fechaDeNacimiento"
                    class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                  />
                </div>

                <div class="lg:col-span-4">
                  <label class="form-label font-medium"
                    >Número Celular :<span class="text-red-600">*</span></label
                  >
                  <input
                    type="number"
                    class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                    v-model="estudianteDto.persona.telefono"
                    placeholder="Número Celular"
                    name="address"
                    required=""
                  />
                </div>

                <div class="lg:col-span-3">
                  <label class="form-label font-medium">Carrera :</label>
                  <select
                    v-model="estudianteDto.idCarreras"
                    class="form-select form-input border border-slate-100 dark:border-slate-800 block w-full mt-2"
                  >
                    <option
                      v-for="carrera in carreras"
                      :value="carrera.idCarreras"
                    >
                      {{ carrera.nombre }}
                    </option>
                  </select>
                </div>


                <div class="lg:col-span-6">
                  <label class="form-label font-medium" for="birthday"
                    >Año de Ingreso a la Universidad :<span class="text-red-600"
                      >*</span
                    ></label
                  >
                  <input
                    type="number"
                    id="birthday"
                    name="birthday"
                    placeholder="Año de Ingreso a la Universidad'"

                    v-model="estudianteDto.persona.anioIngresoUniversidad"
                    @input="validateInputYear"
                    class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                  />
                </div>

                <!--

                <div class="lg:col-span-6">
                  <label class="form-label font-medium" for="multiple_files">Subir Curriculum:</label>
                  <input
                    class="relative form-input border border-slate-100 dark:border-slate-800 file:h-10 file:-mx-3 file:-my-2 file:cursor-pointer file:rounded-none file:border-0 file:px-3 file:text-neutral-700 bg-clip-padding px-3 py-1.5 file:me-3 mt-2"
                    id="multiple_files" type="file" multiple />
                </div>
-->
              </div>
              <!--end grid-->

              <div class="grid grid-cols-1">
                <div class="mt-5">
                  <label class="form-label font-medium"
                    >Descripción : <span class="text-red-600">*</span>
                  </label>
                  <textarea
                    name="comments"
                    id="comments"
                    class="form-input border border-slate-100 dark:border-slate-800 mt-2 textarea"
                    v-model="estudianteDto.persona.descripcion"
                    placeholder="Descripción :"
                  ></textarea>
                </div>
              </div>
              <!--end row-->
            </div>
            <!--end form-->
          </div>
        </div>

        <div class="lg:col-span-12">
          <div class="lg:col-span-6">
            <div
              class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
            >
              <div class="grid grid-cols-1 gap-4">
                <div>
                  <h5 class="text-lg font-semibold mb-4">Habilidades :</h5>
                  <div>
                    <div class="grid grid-cols-1 gap-4">
                      <div
                        class="grid sm:grid-cols-3 gap-4 mt-5 pt-3"
                        v-for="habilidad in estudianteDto.persona.habilidades
                          .habilidades"
                        id="contenedor-habilidad"
                      >
                        <label class="form-label font-medium" for="WordPress">{{
                          habilidad.habilidad
                        }}</label>

                        <StarRatingComponent
                          name="rating"
                          v-model="habilidad.nivel"
                          :disabled="false"
                        >
                        </StarRatingComponent>
                        <div class="grid grid-cols-2 gap-4 mt-0">
                          <button
                            id="submit"
                            name="send"
                            v-if="habilidad.principal == false"
                            @click="habilidad.principal = true"
                            class="btn border-zinc-600 bg-zinc-600 hover:bg-zinc-700 text-white text-sm p-0 rounded-md cursor-pointer"
                          >
                            Secundario
                          </button>

                          <button
                            id="submit"
                            name="send"
                            v-if="habilidad.principal == true"
                            @click="habilidad.principal = false"
                            class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white text-sm p-0 rounded-md cursor-pointer"
                          >
                            Principal
                          </button>

                          <button
                            id="submit"
                            name="send"
                            @click="eliminarHabilidad(habilidad)"
                            class="btn border-red-600 bg-red-600 hover:bg-red-700 text-white rounded-md cursor-pointer"
                          >
                            Eliminar
                          </button>
                        </div>
                      </div>
                    </div>

                    <div class="grid grid-cols-1 gap-4 mt-5">
                      <button
                        id="submit"
                        name="send"
                        @click="showNuevaHabilidadModal = true"
                        class="btn border-emerald-600 bg-emerald-600 hover:bg-emerald-700 text-white rounded-md cursor-pointer"
                      >
                        Agregar Nueva Habilidad
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="lg:col-span-6">
            <div
              class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
            >
              <div class="grid grid-cols-1 gap-4">
                <div>
                  <h5 class="text-lg font-semibold mb-4">Experiencia :</h5>

                  <div>
                    <div>
                      <div
                        class="grid sm:grid-cols-3 gap-4 mt-5 pt-3"
                        v-for="(experiencia,index) in this.estudianteDto.persona
                          .experiencia.experiencia"
                          :id="experiencia.titulo"
                          :key="experiencia.titulo"
                      >
                        <div
                          class="text-slate-400 font-semibold min-w-[80px] text-center"
                        >
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
                          <h5 class="text-lg font-medium mb-0">
                            {{ experiencia.titulo }}
                          </h5>
                          <span class="text-slate-400 company-university">{{
                            experiencia.empresa
                          }}</span>
                          <p class="text-slate-400 mt-2 mb-0">
                            {{ experiencia.descripcion }}
                          </p>
                        </div>

                        <button
                          id="submit"
                          name="send"
                          @click="eliminarExperiencia(index)"
                          class="btn border-red-600 bg-red-600 hover:bg-red-700 text-white rounded-md cursor-pointer"
                          style="max-height: 3rem"
                        >
                          Eliminar
                        </button>
                      </div>

                      <div class="grid grid-cols-1 gap-4 mt-5">
                        <button
                          id="submit"
                          name="send"
                          @click="showNuevaExperienciaModal = true"
                          class="btn border-emerald-600 bg-emerald-600 hover:bg-emerald-700 text-white rounded-md cursor-pointer"
                        >
                          Agregar Nueva Experiencia
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!--
        <div v-if="paginaFormulario == 4" class="lg:col-span-12">
          <div class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900">
            <div class="grid lg:grid-cols-2 grid-cols-1 gap-4">
              <div>
                <h5 class="text-lg font-semibold mb-4">Contact Info :</h5>

                <div>
                  <div class="grid grid-cols-1 gap-4">
                    <div>
                      <label class="form-label font-medium">Phone No. :</label>
                      <input name="number" id="number" type="number"
                        class="form-input border border-slate-100 dark:border-slate-800 mt-2" placeholder="Phone :" />
                    </div>

                    <div>
                      <label class="form-label font-medium">Website :</label>
                      <input name="url" id="url" type="url"
                        class="form-input border border-slate-100 dark:border-slate-800 mt-2" placeholder="Url :" />
                    </div>
                  </div>

                  <button
                    class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md mt-5 cursor-pointer">
                    Add
                  </button>
                </div>
              </div>

              <div>
                <h5 class="text-lg font-semibold mb-4">Change password :</h5>
                <div>
                  <div class="grid grid-cols-1 gap-4">
                    <div>
                      <label class="form-label font-medium">Old password :</label>
                      <input type="password" class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                        placeholder="Old password" required="" />
                    </div>

                    <div>
                      <label class="form-label font-medium">New password :</label>
                      <input type="password" class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                        placeholder="New password" required="" />
                    </div>

                    <div>
                      <label class="form-label font-medium">Re-type New password :</label>
                      <input type="password" class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                        placeholder="Re-type New password" required="" />
                    </div>
                  </div>


                </div>
              </div>
            </div>
            <div class="grid grid-cols-2 gap-4 mt-5">
              <button id="submit" name="send" @click="paginaFormulario = 3"
                class="btn border-red-600 bg-red-600 hover:bg-red-700 text-white rounded-md cursor-pointer">Atrás</button>

              <button id="submit" name="send" @click="paginaFormulario = 5"
                class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md cursor-pointer">Siguiente</button>
            </div>
          </div>
        </div>


      -->
        <div class="lg:col-span-12">
          <div
            class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
          >
            <h5 class="text-lg font-semibold mb-4">Redes Sociales :</h5>

            <div class="md:flex">
              <div class="md:w-1/3">
                <span class="font-medium">Linkedin</span>
              </div>

              <div class="md:w-2/3 mt-4 md:mt-0">
                <form>
                  <div class="form-icon relative">
                    <i
                      data-feather="linkedin"
                      class="size-4 absolute top-5 start-4"
                    ></i>
                    <input
                      type="text"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2 ps-12"
                      v-model="estudianteDto.persona.redesSociales.linkedin.url"
                      placeholder="Linkedin Url"
                      id="linkedin_name"
                      name="name"
                      required=""
                    />
                  </div>
                </form>

                <p class="text-slate-400 mt-1">
                  Agrega el link a tu perfil de Linkedin.
                </p>
              </div>
            </div>

            <div class="md:flex mt-8">
              <div class="md:w-1/3">
                <span class="font-medium">Facebook</span>
              </div>

              <div class="md:w-2/3 mt-4 md:mt-0">
                <form>
                  <div class="form-icon relative">
                    <i
                      data-feather="facebook"
                      class="size-4 absolute top-5 start-4"
                    ></i>
                    <input
                      type="text"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2 ps-12"
                      v-model="estudianteDto.persona.redesSociales.facebook.url"
                      placeholder="Facebook Profile Name"
                      id="facebook_name"
                      name="name"
                      required=""
                    />
                  </div>
                </form>

                <p class="text-slate-400 mt-1">
                  Agrega el link a tu perfil de Facebook.
                </p>
              </div>
            </div>

            <div class="md:flex mt-8">
              <div class="md:w-1/3">
                <span class="font-medium">X (Twitter)</span>
              </div>

              <div class="md:w-2/3 mt-4 md:mt-0">
                <form>
                  <div class="form-icon relative">
                    <i
                      data-feather="twitter"
                      class="size-4 absolute top-5 start-4"
                    ></i>
                    <input
                      type="text"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2 ps-12"
                      v-model="estudianteDto.persona.redesSociales.twitter.url"
                      placeholder="X (Twitter) Url "
                      id="twitter_name"
                      name="name"
                      required=""
                    />
                  </div>
                </form>

                <p class="text-slate-400 mt-1">
                  Agrega el link a tu perfil de X (Twitter).
                </p>
              </div>
            </div>

            <div class="md:flex mt-8 mb-8">
              <div class="md:w-1/3">
                <span class="font-medium">Instagram</span>
              </div>

              <div class="md:w-2/3 mt-4 md:mt-0">
                <form>
                  <div class="form-icon relative">
                    <i
                      data-feather="instagram"
                      class="size-4 absolute top-5 start-4"
                    ></i>
                    <input
                      type="text"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2 ps-12"
                      v-model="
                        estudianteDto.persona.redesSociales.instagram.url
                      "
                      placeholder="Instagram Url"
                      id="insta_name"
                      name="name"
                      required=""
                    />
                  </div>
                </form>

                <p class="text-slate-400 mt-1">
                  Agrega el link a tu perfil de Instagram.
                </p>
              </div>
            </div>

            <div class="grid grid-cols-1 gap-4 mt-5">
              <button
                id="submit"
                name="send"
                @click="registrarEstudiante()"
                class="btn border-emerald-600 bg-emerald-600 hover:bg-emerald-700 text-white rounded-md cursor-pointer"
              >
                Guardar
              </button>
            </div>
          </div>
        </div>

        <!--
          <div class="lg:col-span-12">
            <div
              class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
            >
              <h5 class="text-lg font-semibold mb-5">Account Notifications :</h5>
  
              <div class="flex justify-between pb-4">
                <h6 class="mb-0 font-medium">When someone mentions me</h6>
                <div class="">
                  <input
                    class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50"
                    type="checkbox"
                    value=""
                    id="noti1"
                  />
                  <label class="form-check-label" for="noti1"></label>
                </div>
              </div>
              <div
                class="flex justify-between py-4 border-t border-gray-100 dark:border-gray-700"
              >
                <h6 class="mb-0 font-medium">When someone follows me</h6>
                <div class="">
                  <input
                    class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50"
                    type="checkbox"
                    value=""
                    checked
                    id="noti2"
                  />
                  <label class="form-check-label" for="noti2"></label>
                </div>
              </div>
              <div
                class="flex justify-between py-4 border-t border-gray-100 dark:border-gray-700"
              >
                <h6 class="mb-0 font-medium">When shares my activity</h6>
                <div class="">
                  <input
                    class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50"
                    type="checkbox"
                    value=""
                    id="noti3"
                  />
                  <label class="form-check-label" for="noti3"></label>
                </div>
              </div>
              <div
                class="flex justify-between py-4 border-t border-gray-100 dark:border-gray-700"
              >
                <h6 class="mb-0 font-medium">When someone messages me</h6>
                <div class="">
                  <input
                    class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50"
                    type="checkbox"
                    value=""
                    id="noti4"
                  />
                  <label class="form-check-label" for="noti4"></label>
                </div>
              </div>
            </div>
          </div>
  
          <div class="lg:col-span-12">
            <div
              class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
            >
              <h5 class="text-lg font-semibold mb-5">
                Marketing Notifications :
              </h5>
  
              <div class="flex justify-between pb-4">
                <h6 class="mb-0 font-medium">There is a sale or promotion</h6>
                <div class="">
                  <input
                    class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50"
                    type="checkbox"
                    value=""
                    id="noti5"
                  />
                  <label class="form-check-label" for="noti5"></label>
                </div>
              </div>
              <div
                class="flex justify-between py-4 border-t border-gray-100 dark:border-gray-700"
              >
                <h6 class="mb-0 font-medium">Company news</h6>
                <div class="">
                  <input
                    class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50"
                    type="checkbox"
                    value=""
                    id="noti6"
                  />
                  <label class="form-check-label" for="noti6"></label>
                </div>
              </div>
              <div
                class="flex justify-between py-4 border-t border-gray-100 dark:border-gray-700"
              >
                <h6 class="mb-0 font-medium">Weekly jobs</h6>
                <div class="">
                  <input
                    class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50"
                    type="checkbox"
                    value=""
                    checked
                    id="noti7"
                  />
                  <label class="form-check-label" for="noti7"></label>
                </div>
              </div>
              <div
                class="flex justify-between py-4 border-t border-gray-100 dark:border-gray-700"
              >
                <h6 class="mb-0 font-medium">Unsubscribe News</h6>
                <div class="">
                  <input
                    class="form-checkbox rounded border-gray-200 dark:border-gray-800 text-cyan-600 focus:border-cyan-300 focus:ring focus:ring-offset-0 focus:ring-cyan-200 focus:ring-opacity-50"
                    type="checkbox"
                    value=""
                    checked
                    id="noti8"
                  />
                  <label class="form-check-label" for="noti8"></label>
                </div>
              </div>
            </div>
          </div>


          -->

        <!--
        <div class="lg:col-span-12">
          <div class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900">
            <h5 class="text-lg font-semibold mb-5 text-red-600">
              Delete Account :
            </h5>

            <p class="text-slate-400 mb-4">
              Do you want to delete the account? Please press below "Delete"
              button
            </p>

            <a @click="showAlert"
              class="btn border-red-600 bg-red-600 hover:bg-red-700 text-white rounded-md cursor-pointer">Delete</a>
          </div>
        </div>



-->
      </div>
    </div>
    <!--fin de edicion de profie-->
  </section>
  <div id="myModalExperiencia" class="modal" v-if="showNuevaExperienciaModal">
    <div
      class="modal-content p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
    >
      <span class="close" @click="showNuevaExperienciaModal = false"
        >&times;</span
      >

      <div class="grid grid-cols-1 gap-4">
        <div>
          <h5 class="text-lg font-semibold mb-4">Experiencia :</h5>

          <div>
            <div>
              <div class="grid grid-cols-12 mt-6 gap-4">
                <div class="col-span-12">
                  <label class="form-label font-medium"
                    >Título <span class="text-red-600">*</span></label
                  >
                  <input
                    name="name"
                    id="JobTitle"
                    type="text"
                    v-model="nuevaExperiencia.titulo"
                    class="form-input border border-slate-100 dark:border-slate-800"
                    placeholder="Título :"
                  />
                </div>
                <!--end col-->

                <div class="col-span-12">
                  <label class="form-label font-medium"
                    >Nombre de la compañia
                    <span class="text-red-600">*</span></label
                  >
                  <input
                    name="name"
                    id="CompanyName"
                    type="text"
                    v-model="nuevaExperiencia.empresa"
                    class="form-input border border-slate-100 dark:border-slate-800"
                    placeholder="Compañia :"
                  />
                </div>
                <!--end col-->

                <div class="col-span-12">
                  <label class="form-label font-medium"
                    >Duración "(2020-2024)"
                    <span class="text-red-600">*</span></label
                  >
                  <input
                    name="number"
                    id="Year"
                    type="text"
                    v-model="nuevaExperiencia.duracion"
                    class="form-input border border-slate-100 dark:border-slate-800"
                    placeholder="Duración :"
                  />
                </div>
                <!--end col-->

                <div class="col-span-12">
                  <label class="form-label font-medium"> Descripción : </label>
                  <textarea
                    name="comments"
                    id="Description"
                    v-model="nuevaExperiencia.descripcion"
                    class="form-input border border-slate-100 dark:border-slate-800 textarea"
                    placeholder="Descripción :"
                  ></textarea>
                </div>
                <!--end col-->
              </div>
            </div>

            <div class="grid grid-cols-1 gap-4 mt-5">
              <button
                id="submit"
                name="send"
                @click="agregarNuevaExperiencia(nuevaExperiencia)"
                class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md cursor-pointer"
              >
                Agregar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div id="myModal" class="modal" v-if="showNuevaHabilidadModal">
    <div
      class="modal-content p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
    >
      <span class="close" @click="showNuevaHabilidadModal = false"
        >&times;</span
      >
      <div class="grid grid-cols-1 gap-4">
        <div>
          <h5 class="text-lg font-semibold mb-4">Nueva Habilidad :</h5>
          <div>
            <div class="grid grid-cols-1 gap-4">
              <div class="">
                <input
                  type="text"
                  class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                  placeholder="Habilidad:"
                  id="WordPress"
                  name="number"
                  required=""
                  v-model="nuevaHabilidad"
                />
              </div>
            </div>
            <div class="grid grid-cols-1 gap-4 mt-5">
              <button
                id="submit"
                name="send"
                @click="agregarHabilidad(nuevaHabilidad)"
                class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md cursor-pointer"
              >
                Agregar
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- End Hero -->
  <footers />
  <switcher />
</template>

<script>
import navbar from "@/components/General/navbarGeneral.vue";
import footers from "@/components/footer/footer.vue";
import switcher from "@/components/General/switcher.vue";
import { useEstudiantesStore } from "@/stores/Estudiantes/estudiantesStore";
import Swal from "sweetalert2";
import { useFilesStore } from "@/stores/fileStore.js";
import Compressor from "compressorjs";
import StarRatingComponent from "@/components/General/Extras/StartRatingComponent.vue";
import { useCarrerasStore } from "@/stores/carrerasStore.js";

export default {
  setup() {
    const estudiantesStore = useEstudiantesStore();
    const filesStore = useFilesStore();
    const carrerasStore = useCarrerasStore();
    return {
      estudiantesStore,
      filesStore,
      carrerasStore,
    };
  },
  data() {
    return {
      imageSrc: "../assets/images/team/01.jpg",
      image: "",
      imageSrc2: "../assets/images/team/01.jpg",
      image2: "",
      nuevaHabilidad: "",
      showNuevaHabilidadModal: false,
      showNuevaExperienciaModal: false,
      nuevaExperiencia: {
        titulo: "",
        empresa: "",
        duracion: "",
        descripcion: "",
      },
      estudianteDto: {
        idUsuarios: null,
        kc_UUID: "asdkasdja-sdajfsdbkfasd-32",
        correo: "daniel.aldazosa@ucb.edu.bo",
        fechaRegistro: "2024-06-28",
        horaRegistro: "11:50:07",
        idRoles: 1,
        idPersonas: null,
        idCarreras: 1,

        institucion: null,
        cargo: null,

        persona: {
          idPersona: null,
          nombre: "",
          apellidoPaterno: "Aldazosa",
          apellidoMaterno: "Miranda",
          telefono: 60176430,
          ci: "6959943",
          fotoPerfil:
            "https://backend-sistemas.serverbb.online/api/v1/public/files/download/blob_20240520-221153808",
          anioIngresoUniversidad: 2020,
          descripcion: "slkdf sldklfjsald flskdfs dlfksdfjskdf sdfskdfjsldjk",
          fechaDeNacimiento: "2002-12-17",

          habilidades: {
            habilidades: [
              {
                habilidad: "Java",
                nivel: 5,
                principal: true,
              },
              {
                habilidad: "Python",
                nivel: 4,
                principal: true,
              },
              {
                habilidad: "C++",
                nivel: 3,
                principal: true,
              },
              {
                habilidad: "C#",
                nivel: 2,
                principal: false,
              },
              {
                habilidad: "JavaScript",
                nivel: 1,
                principal: false,
              },
            ],
          },

          habilidadesSeleccionada: {
            habilidades_seleccionadas: [],
          },

          experiencia: {
            experiencia: [
              {
                idExperiencia: 1,
                titulo: "Desarrollador de Software",
                empresa: "Empresa 1",
                duracion: "2020-2021",
                descripcion: "Desarrollador de Software en la empresa 1",
              },
              {
                idExperiencia: 1,
                titulo: "Desarrollador de Software",
                empresa: "Empresa 1",
                duracion: "2020-2021",
                descripcion: "Desarrollador de Software en la empresa 1",
              },
              {
                idExperiencia: 1,
                titulo: "Desarrollador de Software",
                empresa: "Empresa 1",
                duracion: "2020-2021",
                descripcion: "Desarrollador de Software en la empresa 1",
              },
            ],
          },
          redesSociales: {
            linkedin: {
              url: "https://www.linkedin.com/in/daniel-aldazosa-miranda-0b0b3b1b4/",
            },
            facebook: {
              url: "https://www.facebook.com/daniel.aldazosa",
            },
            twitter: {
              url: "https://twitter.com/daniel_aldazosa",
            },
            instagram: {
              url: "https://www.instagram.com/daniel_aldazosa/",
            },
          },
        },
        carrera: {
          idCarreras: 1,
          nombre: "Ing. Sistemas",
          descripcion: "INGENIERIA EN SISTEMAS",
        },
      },
      habilidadesGenericas: {
            habilidades: [
              {
                habilidad: "Administracion de tiempo",
                nivel: 1,
                principal: false,
              },
              {
                habilidad: "Comunicacion",
                nivel: 1,
                principal: false,
              },
              {
                habilidad: "Adaptabilidad",
                nivel: 1,
                principal: false,
              },
              {
                habilidad: "Resolucion de problemas",
                nivel: 1,
                principal: false,
              },
              {
                habilidad: "Trabajo en equipo",
                nivel: 1,
                principal: false,
              },
              {
                habilidad: "Creatividad",
                nivel: 1,
                principal: false,
              },
              {
                habilidad: "Liderazgo",
                nivel: 1,
                principal: false,
              },
              {
                habilidad: "Habilidades interpersonales",
                nivel: 1,
                principal: false,
              },
              {
                habilidad: "Etica de trabajo",
                nivel: 1,
                principal: false,
              },
              {
                habilidad: "Atencion al detalle",
                nivel: 1,
                principal: false,
              }
            ],
          },
    };
  },
  components: {
    navbar,
    footers,
    switcher,
    StarRatingComponent,
  },
  mounted() {
    this.fecthEstudiante();
  },
  watch: {
    "estudianteDto.idCarreras"(newValue) {
      this.estudianteDto.carrera.idCarreras = newValue;
    },
  },

  beforeMount() {
    if (!this.$keycloak.authenticated) {
      this.$router.push("/");
    }
    this.estudianteDto.persona.anioIngresoUniversidad = 2000;
    this.estudianteDto.kc_UUID = this.$keycloak.idTokenParsed.sub;
    this.fetchCarreras();
  },
  methods: {
    async fetchCarreras() {
      let loader = this.$loading.show();
      const response = await this.carrerasStore.getCarreras();
      loader.hide();
      if (response === null) {
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "Error al cargar las carreras, porfavor intenta de nuevo",
        });
      }

      console.log(response);

      this.carreras = response;

      //ordenar
      this.carreras.sort((a, b) => {
        if (a.nombre > b.nombre) {
          return 1;
        }
        if (a.nombre < b.nombre) {
          return -1;
        }
        return 0;
      });
      this.estudianteDto.idCarreras = this.carreras[0].idCarreras;
    },


    updateField(field, value) {
      this.estudianteDto.persona[field] = value.toUpperCase();
    },
    async registrarEstudiante() {
      let loader = this.$loading.show();
      try {
        this.estudianteDto.persona.habilidadesSeleccionada.habilidades_seleccionadas =
          JSON.stringify({
            habilidades_seleccionadas:
              this.estudianteDto.persona.habilidades.habilidades
                .filter((habilidad) => habilidad.principal)
                .map((habilidad) => habilidad.habilidad),
          });
        this.estudianteDto.persona.habilidades = JSON.stringify(
          this.estudianteDto.persona.habilidades
        );
        this.estudianteDto.persona.experiencia = JSON.stringify(
          this.estudianteDto.persona.experiencia
        );
        this.estudianteDto.persona.redesSociales = JSON.stringify(
          this.estudianteDto.persona.redesSociales
        );
        this.estudianteDto.persona.habilidadesSeleccionada = JSON.stringify(
          this.estudianteDto.persona.habilidadesSeleccionada
        );

        const response = await this.estudiantesStore.updateEstudiante(
          this.estudianteDto,
          this.$keycloak.idTokenParsed.sub
        );
        if (response == null) {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Ocurrio un error al actualizar el estudiante!",
          });

          this.$router.push("/");
        } else {
          await Swal.fire({
            icon: "success",
            title: "Estudiante actualizado con exito!",
            showConfirmButton: false,
            timer: 1500,
          });
          this.$router.push("/perfil/estudiante");
        }
      } catch (error) {
        console.log(error);
      } finally {
        loader.hide();
      }
    },

    async fecthEstudiante() {
      let loader = this.$loading.show();
      const response = await this.estudiantesStore.fetchUserByUUID(
        this.$keycloak.idTokenParsed.sub
      );
      if (response == null) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "No se pudo cargar la información del estudiante!",
        });
        this.$router.push("/");
        loader.hide();
        return;
      } else {
        this.estudianteDto = response;
      }
      console.log(this.estudianteDto);
      loader.hide();
    },

    async handleFileUploadFotoPerfil(event) {
      const file = event.target.files[0];
      let auxLink = "";

      if (file) {
        //comprimir imagen si es mayor a 4mb

        console.log("tamaño imagen anterior " + file.size);
        if (file.size > 4000000) {
          auxLink = await this.comprimirYSubirImagenFotoPerfil(file, 0.3);
          console.log("tamaño imagen comprimida " + auxLink);
          this.estudianteDto.persona.fotoPerfil = auxLink;
        } else if (file.size > 1000000) {
          auxLink = await this.comprimirYSubirImagenFotoPerfil(file, 0.5);
          console.log("tamaño imagen comprimida " + auxLink);
          this.estudianteDto.persona.fotoPerfil = auxLink;
        } else {
          let loader = this.$loading.show();
          const response = await this.filesStore.uploadFile(file);
          loader.hide();

          if (response === false) {
            Swal.fire({
              icon: "error",
              title: "Error",
              text: "Error al subir la imagen, porfavor intenta de nuevo",
            });
          } else {
            // Corrected code: use push() to add the new link to the array
            this.estudianteDto.persona.fotoPerfil = this.filesStore.link;

            this.imageSrc = this.filesStore.link;
          }
        }
      }
    },
    async comprimirYSubirImagenFotoPerfil(file, cantidadCompresion) {
      new Compressor(file, {
        quality: cantidadCompresion,
        success: async (compressedResult) => {
          console.log(
            "tamaño imagen comprimida " +
              compressedResult.size +
              " con una compreison de " +
              cantidadCompresion
          );
          let loader = this.$loading.show();
          const response = await this.filesStore.uploadFile(compressedResult);
          loader.hide();

          if (response === false) {
            Swal.fire({
              icon: "error",
              title: "Error",
              text: "Error al subir la imagen, porfavor intenta de nuevo",
            });
          } else {
            this.estudianteDto.persona.fotoPerfil = this.filesStore.link;

            this.imageSrc = this.filesStore.link;
          }
        },
        error(err) {
          console.log(err.message);
        },
      });
    },

    async handleFileUploadBannerPerfil(event) {
      const file = event.target.files[0];
      let auxLink = "";

      if (file) {
        //comprimir imagen si es mayor a 4mb

        console.log("tamaño imagen anterior " + file.size);
        if (file.size > 4000000) {
          auxLink = await this.comprimirYSubirImagenBannerPerfil(file, 0.3);
          console.log("tamaño imagen comprimida " + auxLink);
          this.estudianteDto.persona.bannerPerfil = auxLink;
        } else if (file.size > 1000000) {
          auxLink = await this.comprimirYSubirImagenBannerPerfil(file, 0.5);
          console.log("tamaño imagen comprimida " + auxLink);
          this.estudianteDto.persona.bannerPerfil = auxLink;
        } else {
          let loader = this.$loading.show();
          const response = await this.filesStore.uploadFile(file);
          loader.hide();

          if (response === false) {
            Swal.fire({
              icon: "error",
              title: "Error",
              text: "Error al subir la imagen, porfavor intenta de nuevo",
            });
          } else {
            // Corrected code: use push() to add the new link to the array
            this.estudianteDto.persona.bannerPerfil = this.filesStore.link;

            this.imageSrc2 = this.filesStore.link;
          }
        }
      }
    },
    async comprimirYSubirImagenBannerPerfil(file, cantidadCompresion) {
      new Compressor(file, {
        quality: cantidadCompresion,
        success: async (compressedResult) => {
          console.log(
            "tamaño imagen comprimida " +
              compressedResult.size +
              " con una compreison de " +
              cantidadCompresion
          );
          let loader = this.$loading.show();
          const response = await this.filesStore.uploadFile(compressedResult);
          const aux = this.filesStore.link;
          loader.hide();

          if (response === false) {
            Swal.fire({
              icon: "error",
              title: "Error",
              text: "Error al subir la imagen, porfavor intenta de nuevo",
            });
          } else {
            this.estudianteDto.persona.bannerPerfil = this.filesStore.link;

            this.imageSrc2 = this.filesStore.link;
          }
        },
        error(err) {
          console.log(err.message);
        },
      });
    },

    agregarNuevaExperiencia(experiencia) {
      if (
        experiencia.titulo == "" ||
        experiencia.empresa == "" ||
        experiencia.duracion == "" ||
        experiencia.descripcion == ""
      ) {
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "Porfavor llene todos los campos",
        });
        return;
      }
      console.log("duracion: " + experiencia.duracion);
      this.estudianteDto.persona.experiencia.experiencia.push({
        titulo: experiencia.titulo,
        empresa: experiencia.empresa,
        duracion: experiencia.duracion,
        descripcion: experiencia.descripcion,
      });
      this.nuevaExperiencia = {
        titulo: "",
        empresa: "",
        duracion: "",
        descripcion: "",
      };
      this.showNuevaExperienciaModal = false;
    },

    eliminarExperiencia(index) {
      this.estudianteDto.persona.experiencia.experiencia.splice(index, 1);
    },
    agregarHabilidad(habilidad) {
      if (habilidad == "") {
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "Porfavor ingrese una habilidad",
        });
        return;
      }
      this.estudianteDto.persona.habilidades.habilidades.push({
        habilidad: habilidad,
        nivel: 0,
        principal: false,
      });
      this.nuevaHabilidad = "";
      this.showNuevaHabilidadModal = false;
    },

    eliminarHabilidad(habilidad) {
      if (
        this.habilidadesGenericas.habilidades.find(
          (habilidadGenerica) => habilidadGenerica.habilidad == habilidad.habilidad
        )
      ) {
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "No se puede eliminar esta habilidad",
        });
        return;
      }
      const index =
        this.estudianteDto.persona.habilidades.habilidades.indexOf(habilidad);
      this.estudianteDto.persona.habilidades.habilidades.splice(index, 1);
    },

    validateInputYear(event) {
      const value = event.target.value;
      // Limita la entrada a 4 dígitos numéricos
      if (/^\d{0,4}$/.test(value)) {
        this.estudianteDto.persona.anioIngresoUniversidad = value;
      } else {
        event.target.value = this.estudianteDto.persona.anioIngresoUniversida;
      }
    },
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
  top: 30%;
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
