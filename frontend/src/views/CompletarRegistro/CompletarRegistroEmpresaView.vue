<template>
  <section
    v-if="paginaFormulario == 1"
    class="py-20 w-full table relative bg-[url('../../assets/images/hero/bg2.webp')] bg-top bg-no-repeat bg-cover"
  >
    <div class="absolute inset-0 bg-slate-900/70"></div>
    <div class="container relative">
      <div class="grid grid-cols-1 text-center">
        <h3 class="mb-4 md:text-[26px] text-2xl text-white font-medium">
          Completa tus datos
        </h3>

        <p class="text-white/80 max-w-xl mx-auto">
          Porfavor completa tus datos para incrementar tus posibilidades de
          obtener tu pasantía sońada
        </p>

        <a
          @click="toggle"
          data-type="youtube"
          :data-id="tutorialVideo.key"
          class="lightbox size-20 rounded-full shadow-lg dark:shadow-gray-800 inline-flex items-center justify-center bg-white dark:bg-slate-900 text-cyan-600 mx-auto mt-10 cursor-pointer"
        >
          <i
            class="mdi mdi-play inline-flex items-center justify-center text-2xl"
          ></i>
        </a>
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
  </section>
  <!--end section-->
  <section
    v-if="paginaFormulario == 2"
    :style="{ backgroundImage: `url(${nuevaEmpresa.fotoInstitucion})` }"
    class="relative table w-full py-40 bg-center bg-no-repeat bg-cover"
  >
    <div class="absolute inset-0 bg-cyan-900/60"></div>
  </section>
  <!--end section-->
  <div class="relative">
    <div
      class="shape absolute start-0 end-0 sm:-bottom-px -bottom-[2px] overflow-hidden z-1 text-white dark:text-slate-900"
    >
      <svg
        class="w-full h-auto"
        viewBox="0 0 2880 48"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M0 48H1437.5H2880V0H2160C1442.5 52 720 0 720 0H0V48Z"
          fill="currentColor"
        ></path>
      </svg>
    </div>
  </div>
  <!-- End Hero -->

  <section
    v-if="paginaFormulario == 2"
    class="relative pb-16 mb:pb-6 sm:pb-10 -mt-16 z-1"
  >
    <div class="container">
      <div class="grid grid-cols-1">
        <div
          class="md:flex justify-between items-center shadow dark:shadow-gray-700 rounded-md p-6 bg-white dark:bg-slate-900"
        >
          <div class="flex items-center">
            <img
              :src="this.nuevaEmpresa.logoEmpresa"
              class="size-20 shadow dark:shadow-gray-700 rounded-md bg-slate-50 dark:bg-slate-800"
              alt=""
            />

            <div class="ms-4">
              <h5 class="text-xl font-bold">
                {{ this.empresaSeleccionada.nombre }}
              </h5>
              <h6 class="text-base text-slate-400">
                <i class="uil uil-map-marker"></i>
                {{ splitLongWords(
                  
                  this.empresaSeleccionada.direccion)  }}
             
              </h6>
              <h6 class="text-base text-slate-400">
                <i class="uil uil-envelope align-middle" title="email"></i>
                {{ splitLongWords(this.empresaSeleccionada.correo) }}
              </h6>
            </div>
          </div>
          <div class="ms-4">
            <h5 class="text-base">Redes sociales de la empresa:</h5>
            <div class="mt-0">
              <a
                v-for="sector in empresaSeleccionada.redesSociales"
                :key="sector.id"
                href=""
              >
                <span
                  class="bg-purple-600/5 hover:bg-purple-600/20 dark:bg-purple-600/10 hover:dark:bg-purple-600/30 inline-block text-purple-600 px-4 text-[14px] font-medium rounded-full mt-2 me-1 transition-all duration-500"
                >
                  {{ splitLongWords(
                  
                  sector)  }}
                </span>
              </a>
            </div>
          </div>
        </div>
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
  </section>
  <!-- End -->

  <!-- iframe start  -->
  <div
    :class="isActive ? 'fixed' : 'hidden'"
    class="bg-black/[0.9] top-0 left-0 bottom-0 w-[100%] h-[100%] z-999"
  >
    <div class="h-[100%] flex items-center justify-center">
      <iframe
        v-if="isActive"
        :src="tutorialVideo.embedUrl"
        width="700"
        height="500"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        allowfullscreen
      ></iframe>
    </div>
    <button class="text-slate-400 absolute top-[20px] right-[20px]" @click="toggle">
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
      >
        <line x1="18" y1="6" x2="6" y2="18"></line>
        <line x1="6" y1="6" x2="18" y2="18"></line>
      </svg>
    </button>
  </div>
  <section class="relative lg:mt-10 mt-[10px] pb-16">
    <div v-if="paginaFormulario == 2" class="lg:container container-fluid">
      <div class="md:flex mx-4 -mt-12">
        <div class="md:w-full">
          <div class="relative flex items-end">
            <div class="profile-pic text-center">
              <input
                id="pro-img"
                name="profile-image"
                type="file"
                class="hidden"
                accept="image/*"
                @change="handleFileUploadFotoPerfil"
              />
              <div>
                <div
                  class="relative size-28 max-w-[112px] max-h-[112px] mx-auto"
                >
                  <img
                    :src="this.imageSrc3"
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
                  usuarioInstitucion.persona.nombre +
                  " " +
                  usuarioInstitucion.persona.apellidoPaterno +
                  " " +
                  usuarioInstitucion.persona.apellidoMaterno
                }}
              </h5>
              <p class="text-slate-400">Usuario de la empresa</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--end -->
    <!--inicio de profie-->
    <div class="container mt-16">
      <div class="grid lg:grid-cols-12 grid-cols-1 gap-[30px]">
        <div v-if="paginaFormulario == 1" class="lg:col-span-12">
          <div
            class="p-4 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
          >
            <div class="grid grid-cols-4 gap-4 mb-2">
              <h5 class="col-span-3">Selecciona tu empresa:</h5>

              <button
                id="submit"
                name="send"
                @click="
                  (showNuevaEmpresaModal = true),
                    (showNuevaEmpresaModalForms = 10),
                    (nuevaEmpresa = {
                      fotos: [],
                      sectores: [],
                      selectedAreas: [],
                      redesSociales: {},
                      activo: true,
                    }),
                    (this.imageSrc =
                      'https://cdn-icons-png.flaticon.com/512/84/84099.png'),
                    (this.imageSrc2 =
                      'https://cdn-icons-png.flaticon.com/512/84/84099.png'),
                    (this.imageSrc3 =
                      'https://cdn-icons-png.flaticon.com/512/84/84099.png')
                "
                class="col-span-1 btn border-green-600 bg-green-600 hover:bg-green-700 text-white rounded-md cursor-pointer p-1"
              >
                <!-- Texto para pantallas grandes -->
                <span class="hidden lg:inline">Agrega tu empresa</span>
                <!-- Texto para pantallas pequeñas -->
                <span class="inline lg:hidden">Agregar</span>
              </button>
            </div>

            <div class="grid grid-cols-5 gap-4">
              <h5 class="col-span-1 mt-2">Buscar:</h5>
              <input
                type="text"
                v-model="Busqueda"
                class="col-span-4 p-2 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-cyan-600 text-l font-semibold p-2 form-input border border-slate-100 dark:border-slate-800"
              />
            </div>
            <div
              class="grid lg:grid-cols-5 md:grid-cols-3 sm:grid-cols-2 grid-cols-1 mt-8 gap-[30px]"
            >
              <div
                @click="seleccionarEmpresa(item)"
                v-for="item in filteredDatas"
                :key="item"
                class="group px-3 py-10 rounded-full shadow dark:shadow-gray-700 hover:shadow-cyan-600/10 dark:hover:shadow-cyan-600/10 text-center bg-white dark:bg-slate-900 hover:bg-cyan-600/5 dark:hover:bg-cyan-600/5 transition duration-500"
              >
                <div
                  class="size-16 bg-cyan-600/5 group-hover:bg-cyan-600 text-cyan-600 group-hover:text-white rounded-full text-2xl flex align-middle justify-center items-center shadow-sm dark:shadow-gray-700 transition duration-500 mx-auto"
                >
                  <img :src="item.logoEmpresa" class="rounded-full" alt="" />
                </div>

                <div class="content mt-6">
                  <a class="title text-lg font-semibold hover:text-cyan-600"
                    >{{ item.nombre }}
                  </a>
                  <p class="text-slate-400 mt-3">{{ item.direccion }}</p>
                </div>
              </div>
              <!--end row-->
            </div>
            <div class="grid grid-cols-1 gap-4 mt-5">
              <button
                id="submit"
                name="send"
                @click="this.$keycloak.logout()"
                class="btn border-red-600 bg-red-600 hover:bg-red-700 text-white rounded-md cursor-pointer"
              >
                Cancelar
              </button>
            </div>
            <!--end form-->
          </div>
        </div>

        <div class="lg:col-span-12">
          <div v-if="paginaFormulario == 2" class="lg:col-span-6">
            <div
              class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
            >
              <h5 class="text-lg font-semibold mb-4">Detalles Personales :</h5>
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
                      v-model="usuarioInstitucion.persona.nombre"
                      disabled
                      placeholder="Nombres:"
                      id="firstname"
                      name="name"
                      required=""
                      @input="toUpperCase($event)"
                    />
                  </div>
                  <div class="lg:col-span-4">
                    <label class="form-label font-medium"
                      >Ci :<span class="text-red-600">*</span></label
                    >
                    <input
                      type="text"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioInstitucion.persona.ci"
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
                      v-model="usuarioInstitucion.persona.apellidoPaterno"
                      placeholder="Apellido Paterno:"
                      id="lastname"
                      name="name"
                      required=""
                      disabled
                      @input="toUpperCase($event)"
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
                      v-model="usuarioInstitucion.persona.apellidoMaterno"
                      placeholder="Apellido Materno:"
                      id="lastname"
                      name="name"
                      required=""
                      :disabled="
                        this.$keycloak.idTokenParsed.family_name.split(' ')
                          .length > 2
                      "
                      @input="toUpperCase($event)"
                    />
                  </div>

                  <div class="lg:col-span-6">
                    <label class="form-label font-medium"
                      >Email : <span class="text-red-600">*</span></label
                    >
                    <input
                      type="email"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioInstitucion.correo"
                      placeholder="Email"
                      name="email"
                      required=""
                      disabled
                      @input="toLowerCaseMail($event)"
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
                      v-model="usuarioInstitucion.persona.fechaDeNacimiento"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                    />
                  </div>

                  <div class="lg:col-span-6">
                    <label class="form-label font-medium"
                      >Número Celular :<span class="text-red-600"
                        >*</span
                      ></label
                    >
                    <input
                      type="number"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioInstitucion.persona.telefono"
                      placeholder="Número Celular"
                      name="address"
                      required=""
                      maxlength="8"
                      @input="limitInputLength"
                    />
                  </div>
                  <div class="lg:col-span-6">
                    <label class="form-label font-medium"
                      >Cargo en la empresa :
                      <span class="text-red-600">*</span></label
                    >
                    <input
                      type="text"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioInstitucion.cargo"
                      placeholder="Cargo en la empresa :"
                      id="lastname"
                      name="name"
                      required=""
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

                <!--end row-->
              </div>
              <div class="grid grid-cols-2 gap-4 mt-5">
                <button
                  id="submit"
                  name="send"
                  @click="paginaFormulario = 1"
                  class="btn border-red-600 bg-red-600 hover:bg-red-700 text-white rounded-md cursor-pointer"
                >
                  Atrás
                </button>

                <button
                  id="submit"
                  name="send"
                  @click="registrarUsuarioInstitucion()"
                  class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md cursor-pointer"
                >
                  Registrar
                </button>
              </div>
              <!--end form-->
            </div>
          </div>

          <div v-if="paginaFormulario == 3" class="lg:col-span-6">
            <div
              class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
            >
              <div class="grid grid-cols-1 gap-4">
                <div>
                  <h5 class="text-lg font-semibold mb-4">Experiencia :</h5>

                  <div>
                    <div>
                      <div
                        class="grid grid-cols-3 gap-4 mt-5 pt-3"
                        v-for="experiencia in this.usuarioInstitucion.persona
                          .experiencia"
                        :id="experiencia.titulo"
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
                          @click="eliminarExperiencia(experiencia)"
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
                          class="btn border-green-600 bg-green-600 hover:bg-green-700 text-white rounded-md cursor-pointer"
                        >
                          Agregar Nueva Habilidad
                        </button>
                      </div>
                    </div>

                    <div class="grid grid-cols-2 gap-4 mt-5">
                      <button
                        id="submit"
                        name="send"
                        @click="paginaFormulario = 2"
                        class="btn border-red-600 bg-red-600 hover:bg-red-700 text-white rounded-md cursor-pointer"
                      >
                        Atrás
                      </button>

                      <button
                        id="submit"
                        name="send"
                        @click="paginaFormulario = 4"
                        class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md cursor-pointer"
                      >
                        Siguiente
                      </button>
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
        <div v-if="paginaFormulario == 4" class="lg:col-span-12">
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
                      v-model="
                        usuarioInstitucion.persona.redesSociales.linkedin.url
                      "
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
                <span class="font-medium">Web</span>
              </div>

              <div class="md:w-2/3 mt-4 md:mt-0">
                <form>
                  <div class="form-icon relative">
                    <i
                      data-feather="web"
                      class="size-4 absolute top-5 start-4"
                    ></i>
                    <input
                      type="text"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2 ps-12"
                      v-model="
                        usuarioInstitucion.persona.redesSociales.web.url
                      "
                      placeholder="Web Profile Name"
                      id="webb_name"
                      name="name"
                      required=""
                    />
                  </div>
                </form>

                <p class="text-slate-400 mt-1">
                  Agrega el link de su sitio web.
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
                      v-model="
                        usuarioInstitucion.persona.redesSociales.facebook.url
                      "
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
                      v-model="
                        usuarioInstitucion.persona.redesSociales.twitter.url
                      "
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
                        usuarioInstitucion.persona.redesSociales.instagram.url
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

            <div class="grid grid-cols-2 gap-4 mt-5">
              <button
                id="submit"
                name="send"
                @click="paginaFormulario = 3"
                class="btn border-red-600 bg-red-600 hover:bg-red-700 text-white rounded-md cursor-pointer"
              >
                Atrás
              </button>

              <button
                id="submit"
                name="send"
                @click="paginaFormulario = 5"
                class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md cursor-pointer"
              >
                Siguiente
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
  <!-- Segundo moda empresa -->
  <div id="myModalCompany" class="modal" v-if="showNuevaEmpresaModal">
    <div
      class="modal-content p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900"
    >
      <span class="close" @click="showNuevaEmpresaModal = false">&times;</span>
      <div
        v-if="showNuevaEmpresaModalForms == 10"
        class="grid grid-cols-1 gap-4"
      >
        <div>
          <h5 class="text-lg font-semibold mb-4">
            ¿Tu empresa aún no está registrada?
          </h5>

          <div>
            <div>
              <div class="mt-6">
                <h5 class="text-lg font-semibold mb-4">
                  ¿Qué necesitas para registrar tu empresa?
                </h5>
                <div class="flex flex-wrap gap-2">
                  <span
                    >Para dar de alta tu empresa, solo necesitas completar la información básica:</span
                  >
                </div>

                <div class="mt-4">
                  <ul class="list-disc list-inside space-y-1">
                    <li><strong>Nombre de la empresa</strong></li>
                    <li><strong>Dirección de la empresa</strong></li>
                    <li><strong>Correo electrónico</strong></li>
                    <li><strong>Logo de la empresa</strong></li>
                    <li><strong>Descripción de la empresa</strong></li>
                  </ul>
                </div>

                <div class="mt-4 p-3 bg-cyan-50 dark:bg-cyan-900/30 border border-cyan-200 dark:border-cyan-800 rounded-md text-sm text-cyan-800 dark:text-cyan-200">
                  <i class="uil uil-info-circle me-1 font-bold"></i>
                  <strong>Nota:</strong> Las áreas de la empresa, fotos de instalaciones y redes sociales son opcionales y podrás completarlas o actualizarlas más adelante desde el perfil de la empresa.
                </div>
              </div>
              <!--end col-->
            </div>

            <div class="flex gap-4 mt-5 justify-end">
              <button
                id="submit"
                name="send"
                @click="showNuevaEmpresaModalForms = 1"
                class="btn border-green-600 bg-green-600 hover:bg-green-700 text-white rounded-md cursor-pointer"
              >
                ¡Empezar ahora!
              </button>
            </div>
          </div>
        </div>
      </div>
      <div
        v-if="showNuevaEmpresaModalForms == 1"
        class="grid grid-cols-1 gap-4"
      >
        <div>
          <h5 class="text-lg font-semibold mb-4">Empresa :</h5>

          <div>
            <div>
              <div class="grid grid-cols-12 mt-6 gap-4">
                <div class="col-span-12">
                  <label class="form-label font-medium"
                    >Nombre: <span class="text-red-600">*</span></label
                  >
                  <input
                    name="name"
                    id="JobTitle"
                    type="text"
                    v-model="nuevaEmpresa.nombre"
                    class="form-input border border-slate-100 dark:border-slate-800"
                    placeholder="Nombre :"
                  />
                </div>
                <!--end col-->

                <div class="col-span-12">
                  <label class="form-label font-medium"
                    >Dirección de la empresa
                    <span class="text-red-600">*</span></label
                  >
                  <input
                    name="name"
                    id="CompanyName"
                    type="text"
                    v-model="nuevaEmpresa.direccion"
                    class="form-input border border-slate-100 dark:border-slate-800"
                    placeholder="Dirección :"
                  />
                </div>
                <!--end col-->

                <div class="col-span-12">
                  <label class="form-label font-medium"
                    >correo <span class="text-red-600">*</span></label
                  >
                  <input
                    name="email"
                    type="text"
                    v-model="nuevaEmpresa.correo"
                    class="form-input border border-slate-100 dark:border-slate-800"
                    placeholder="Correo :"
                  />
                  <label class="form-label font-medium"
                    >Logo de la empresa:
                    <span class="text-red-600">*</span></label
                  >
                </div>

                <div class="justify-center">
                  <input
                    id="pro-img"
                    name="profile-image"
                    type="file"
                    accept="image/*"

                    class="hidden"
                    @change="handleFileUploadLogoEmpresa"
                  />
                  <div>
                    <div
                      class="relative size-28 max-w-[112px] max-h-[112px] mx-auto"
                    >
                      <img
                        :src="this.imageSrc"
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
                <!--end col-->
                <div class="col-span-12 mt-4">
                  <label class="form-label font-medium">
                    Descripción :
                    <span v-if="nuevaEmpresa.descripcion"
                      >{{ nuevaEmpresa.descripcion.length }}/1000</span
                    ></label
                  >
                  <textarea
                    name="comments"
                    id="Description"
                    v-model="nuevaEmpresa.descripcion"
                    class="form-input border border-slate-100 dark:border-slate-800 textarea"
                    placeholder="Descripción :"
                    maxlength="1000"
                  ></textarea>
                </div>
                <!--end col-->
              </div>
            </div>

            <div class="flex flex-col sm:flex-row justify-between items-center gap-3 mt-5">
              <button
                @click="showNuevaEmpresaModal = false"
                class="btn border-red-600 bg-red-600 hover:bg-red-700 text-white rounded-md cursor-pointer w-full sm:w-auto"
              >
                Cancelar
              </button>

              <div class="flex gap-2 w-full sm:w-auto justify-end">
                <button
                  @click="guardarEmpresaDirectamente"
                  class="btn border-emerald-600 bg-emerald-600 hover:bg-emerald-700 text-white rounded-md cursor-pointer"
                  title="Guardar empresa de inmediato"
                >
                  Guardar empresa
                </button>
                <button
                  @click="formEmpresaPaso2"
                  class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md cursor-pointer"
                  title="Continuar para agregar redes sociales opcionales"
                >
                  Siguiente (Redes)
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div
        v-if="showNuevaEmpresaModalForms == 2"
        class="grid grid-cols-1 gap-4"
      >
        <div>
          <h5 class="text-lg font-semibold mb-2">
            Redes sociales de la empresa (Opcional):
          </h5>
          <p class="text-sm text-slate-400 mb-4">
            Puedes agregar las redes sociales oficiales de la empresa o continuar directamente.
          </p>

          <div>
            <!-- Redes sociales seleccionadas -->
            <div class="mt-4" v-if="nuevaEmpresa.redesSociales && Object.keys(nuevaEmpresa.redesSociales).length > 0">
              <h5 class="text-md font-semibold mb-2">
                Redes sociales agregadas:
              </h5>
              <div class="flex flex-wrap gap-2">
                <div
                  v-for="(url, social) in nuevaEmpresa.redesSociales"
                  :key="social"
                  class="bg-cyan-600 text-white px-3 py-1 rounded-md flex items-center text-sm"
                >
                  <strong class="uppercase me-1">{{ social }}:</strong> {{ splitLongWords(url) }}
                  <button
                    @click="removeRedSocial(social)"
                    class="ml-2 bg-red-600 hover:bg-red-700 text-white px-2 py-0.5 rounded-md font-bold"
                    title="Eliminar red social"
                  >
                    ×
                  </button>
                </div>
              </div>
            </div>

            <div class="mt-4 grid grid-cols-12 gap-2">
              <div class="col-span-12 sm:col-span-4">
                <select
                  v-model="selectedSocial"
                  class="form-select border border-slate-100 dark:border-slate-800 dark:bg-slate-800 w-full"
                >
                  <option value="" disabled selected>
                    Seleccione una red social
                  </option>
                  <option
                    v-for="social in availableSocials"
                    :key="social"
                    :value="social"
                  >
                    {{ social }}
                  </option>
                </select>
              </div>

              <div class="col-span-10 sm:col-span-6">
                <input
                  type="text"
                  v-model="socialLink"
                  class="form-input border border-slate-100 dark:border-slate-800 w-full"
                  placeholder="https://... o enlace de perfil"
                  @keyup.enter="addRedSocial"
                />
              </div>

              <div class="col-span-2 sm:col-span-2">
                <button
                  @click="addRedSocial"
                  class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md cursor-pointer w-full"
                  title="Agregar red social"
                >
                  +
                </button>
              </div>
            </div>

            <div class="flex justify-between items-center gap-4 mt-6">
              <button
                @click="showNuevaEmpresaModalForms = 1"
                class="btn border-slate-400 bg-slate-200 dark:bg-slate-700 text-slate-800 dark:text-white rounded-md cursor-pointer"
              >
                Atrás
              </button>

              <button
                @click="guardarNuevaEmpresa"
                class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md cursor-pointer"
              >
                Confirmar y guardar empresa
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- fin Segundo moda empresa -->
  <switcher />
</template>

<script>
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";

import switcher from "@/components/General/switcher.vue";
import StarRatingComponent from "@/components/General/Extras/StartRatingComponent.vue";
import { useInstitucionesStore } from "@/stores/Instituciones/InstitucionesPublicStore.js";
import { useFilesStore } from "@/stores/fileStore.js";
import Swal from "sweetalert2";
import Compressor from "compressorjs";
import { getTutorialVideo } from "@/config/tutorialVideos";

export default {
  components: {
    switcher,
    StarRatingComponent,
  },

  data() {
    return {
      tutorialVideo: getTutorialVideo("REGISTRO_EMPRESA"),
      institucionesPublicStore: useInstitucionesStore(),
      isActive: false,
      imageSrc: "https://cdn-icons-png.flaticon.com/512/84/84099.png",
      image: "https://cdn-icons-png.flaticon.com/512/84/84099.png",
      imageSrc2: "https://cdn-icons-png.flaticon.com/512/84/84099.png",
      imageSrc3: "https://cdn-icons-png.flaticon.com/512/84/84099.png",
      image2: "https://cdn-icons-png.flaticon.com/512/84/84099.png",
      searchQuery: "",
      selectedAreas: [],
      allAreas: [
        "BANCA",
        "PRODUCTIVO",
        "SERVICIOS CONSULTORÍA",
        "SERVICIOS LEGALES",
        "FARMACÉUTICO",
        "ONG",
        "TRANSPORTE",
        "SEGUROS",
        "TECNOLOGÍA",
        "TELECOMUNICACIÓN",
        "GOBIERNO",
        "FUNDACIÓN",
        "CONSTRUCCIÓN",
        "DISTRIBUCIÓN",
        "EDUCACIÓN",
        "SERVICIOS EDUCATIVOS",
        "TURISMO Y HOSPITALIDAD",
        "MEDIOS DE COMUNICACIÓN",
        "MARKETING Y PUBLICIDAD",
        "ENERGÍA Y MINERÍA",
        "SALUD",
        "RECURSOS HUMANOS",
        "INVESTIGACIÓN Y DESARROLLO",
        "INDUSTRIA TEXTIL Y MODA",
        "SOCIAL MEDIA",
        "AUTOMOTRIZ",
        "DESARROLLO INMOBILIARIO",
        "ALIMENTACIÓN",
        "ARTES Y ENTRETENIMIENTO",
        "AGRICULTURA",
        "BIOTECNOLOGÍA",
        "COMERCIO ELECTRÓNICO",
      ], // Lista de todas las áreas disponibles
      selectedSocial: "",
      socialLink: "",
      availableSocials: [
        "Facebook",
        "Twitter",
        "Instagram",
        "LinkedIn",
        "YouTube",
        "TikTok",
        "Web"
      ],
      allSocials: [
        "Facebook",
        "Twitter",
        "Instagram",
        "LinkedIn",
        "YouTube",
        "TikTok",
        "Web"
      ],
      empresaSeleccionada: {
        idInstituciones: "",
        nombre: "",
        descripcion: "",
        direccion: "",
        fotoInstitucion: "https://cdn-icons-png.flaticon.com/512/84/84099.pn",
        correo: "",
        sectores: ["a", "b"],
        logoEmpresa: "",
        fotos: ["", ""],
        redesSociales: {
          facebook: "fa",
          twitter: "tw",
          instagram: "instagram",
          linkedin: "linkedin",
          web: "web",
        },
        activo: true,
      },
      nuevaEmpresa: {
        idInstituciones: "",
        nombre: "",
        descripcion: "",
        direccion: "",
        fotoInstitucion: "",
        correo: "",
        sectores: [],
        logoEmpresa: "",
        fotos: [],
        redesSociales: {},
        activo: true,
      },
      paginaFormulario: 1,
      Busqueda: "",
      usuarioInstitucion: {
        idUsuarios: 10001,
        kc_UUID: "asdkasdja-sdajfsdbkfasd-32",
        correo: "daniel.aldazosa@ucb.edu.bo",
        fechaRegistro: "2024-06-28",
        horaRegistro: "11:50:07",
        idRoles: 1,
        idPersonas: 100001,
        idCarreras: 1,
        persona: {
          idPersona: 100001,
          nombre: "Daniel Ignacio",
          apellidoPaterno: "Aldazosa",
          apellidoMaterno: "Miranda",
          telefono: 60176430,
          ci: "6959943",
          fotoPerfil:
            "https://backend-sistemas.serverbb.online/api/v1/public/files/download/blob_20240520-221153808",
          anioIngresoUniversidad: 2020,
          descripcion: "slkdf sldklfjsald flskdfs dlfksdfjskdf sdfskdfjsldjk",
          fechaDeNacimiento: "2002-12-17",

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

          habilidadesSeleccionada: '{"habilidades_seleccionadas": "todas"}',

          experiencia: [
            {
              idExperiencia: 1,
              titulo: "Desarrollador de Software",
              empresa: "Empresa 1",
              duracion: "2020-2021",
              descripcion: "Desarrollador de Software en la empresa 1",
            },
            {
              idExperiencia: 2,
              titulo: "Desarrollador de Software",
              empresa: "Empresa 2",
              duracion: "2021-2022",
              descripcion: "Desarrollador de Software en la empresa 2",
            },
            {
              idExperiencia: 3,
              titulo: "Desarrollador de Software",
              empresa: "Empresa 3",
              duracion: "2022-2023",
              descripcion: "Desarrollador de Software en la empresa 3",
            },
          ],

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

      rating: 0,
      showNuevaHabilidadModal: false,
      showNuevaExperienciaModal: false,
      showNuevaEmpresaModal: false,
      showNuevaEmpresaModal2: false,
      showNuevaEmpresaModal3: false,
      showNuevaEmpresaModalForms: 0,
      nuevaHabilidad: "",
      nuevaExperiencia: {
        titulo: "",
        empresa: "",
        duracion: "",
        descripcion: "",
      },
      datas: [
        {
          image:
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          icon: "uil uil-gitlab",
          title: "TIGO",
          title2: "Development",
          job: "74 Jobs",
        },
        {
          image:
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          icon: "uil uil-book-open",
          title: "BISA",
          title2: "Communication",
          job: "20 Jobs",
        },
        {
          image:
            "https://img.freepik.com/fotos-premium/adorable-bebe-leon-sonrisa-estilo-pixar-ojos-grandes_804788-4863.jpg",
          icon: "uil uil-chart-pie-alt",
          title: "Project",
          title2: "Management",
          job: "35 Jobs",
        },
        {
          image:
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          icon: "uil uil-feedback",
          title: "Customer",
          title2: "Service",
          job: "46 Jobs",
        },
        {
          image:
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          icon: "uil uil-presentation-line",
          title: "Software",
          title2: "Engineering",
          job: "60 Jobs",
        },
      ],
      instituciones: [
        {
          idInstituciones: 1,
          nombre: "Universidad Católica Boliviana",
          descripcion: "Institución educativa de prestigio en Bolivia.",
          direccion: "Av. 6 de Agosto, La Paz",
          fotoInstitucion:
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          correo: "@ucb.edu.bo",
          sectores: ["Educación", "Investigación"],
          logoEmpresa:
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          fotos: [
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          ],
          redesSociales: {
            facebook: "https://facebook.com/ucb",
            twitter: "https://twitter.com/ucb",
            instagram: "https://instagram.com/ucb",
          },
          activo: true,
        },
        {
          idInstituciones: 2,
          nombre: "Universidad Mayor de San Andrés",
          descripcion: "Institución educativa de prestigio en Bolivia.",
          direccion: "Av. 6 de Agosto, La Paz",
          fotoInstitucion:
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          correo: "@ucb.edu.bo",
          sectores: ["Educación", "Investigación"],
          logoEmpresa:
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          fotos: [
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          ],
          redesSociales: {
            facebook: "https://facebook.com/ucb",
            twitter: "https://twitter.com/ucb",
            instagram: "https://instagram.com/ucb",
          },
          activo: true,
        },
        {
          idInstituciones: 3,
          nombre: "Universidad Mayor de San Andrés",
          descripcion: "Institución educativa de prestigio en Bolivia.",
          direccion: "Av. 6 de Agosto, La Paz",
          fotoInstitucion:
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          correo: "@ucb.edu.bo",
          sectores: ["Educación", "Investigación"],
          logoEmpresa:
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          fotos: [
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
            "https://seeklogo.com/images/T/Tigo-logo-BDF99BD6CC-seeklogo.com.png",
          ],
          redesSociales: {
            facebook: "https://facebook.com/ucb",
            twitter: "https://twitter.com/ucb",
            instagram: "https://instagram.com/ucb",
          },
          activo: true,
        },
      ],
      institucionesWithOnlyName: [],
      filesStore: useFilesStore(),
      usuarioInstitucion: {
        idUsuarios: null,
        kc_UUID: "",
        correo: "",
        fechaRegistro: "",
        horaRegistro: "",
        idRoles: null,
        idPersonas: null,
        idCarreras: null,
        institucion: {
          idInstituciones: null,
          nombre: "",
          descripcion: "",
          direccion: "",
          fotoInstitucion: "",
          correo: "",
          sectores: [],
          logoEmpresa: "",
          fotos: [],
          redesSociales: {
            facebook: "",
            twitter: "",
            instagram: "",
          },
          activo: null,
        },
        cargo: "",
        persona: {
          idPersona: null,
          nombre: "",
          apellidoPaterno: "",
          apellidoMaterno: "",
          telefono: null,
          ci: "",
          fotoPerfil: "",
          anioIngresoUniversidad: null,
          descripcion: "",
          fechaDeNacimiento: "",
          habilidades: [],
          habilidadesSeleccionada: [],
          experiencia: [],
          redesSociales: {},
        },
        carrera: null,
      },
    };
  },
  watch: {
    "usuarioInstitucion.idCarreras"(newValue) {
      this.usuarioInstitucion.carrera.idCarreras = newValue;
    },
  },

  beforeMount() {
    if (!this.$keycloak.authenticated) {
      this.$router.push("/");
    }
    this.usuarioInstitucion.persona.anioIngresoUniversidad = 2000;
    this.usuarioInstitucion.kc_UUID = this.$keycloak.idTokenParsed.sub;

    this.usuarioInstitucion.persona.nombre =
      this.$keycloak.idTokenParsed.given_name.toUpperCase();

    //first word of the family name
    this.usuarioInstitucion.persona.apellidoPaterno =
      this.$keycloak.idTokenParsed.family_name.split(" ")[0].toUpperCase();
    //second word of the family name
    if (this.$keycloak.idTokenParsed.family_name.split(" ").length > 1) {
      this.usuarioInstitucion.persona.apellidoMaterno =
        this.$keycloak.idTokenParsed.family_name.split(" ")[1].toUpperCase();
    } else {
      this.usuarioInstitucion.persona.apellidoMaterno = "";
    }
    this.usuarioInstitucion.correo = this.$keycloak.tokenParsed.email;
  },
  mounted() {
    this.getInstitucionesWithName();
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
    formEmpresaPaso2() {
      if (
        this.nuevaEmpresa.nombre == "" ||
        this.nuevaEmpresa.nombre == undefined ||
        this.nuevaEmpresa.direccion == "" ||
        this.nuevaEmpresa.direccion == undefined ||
        this.nuevaEmpresa.correo == "" ||
        this.nuevaEmpresa.correo == undefined ||
        this.nuevaEmpresa.logoEmpresa == "" ||
        this.nuevaEmpresa.logoEmpresa == undefined
      ) {
        Swal.fire({
          title: "Complete los campos obligatorios",
          text: "Nombre, dirección, correo y logo de la empresa son requeridos.",
          icon: "error",
          showConfirmButton: false,
          timer: 2000,
        });
        return false;
      }
      //check that the mail is valid
      if (!this.validateEmail(this.nuevaEmpresa.correo)) {
        Swal.fire({
          title: "Correo no válido",
          text: "Por favor ingrese un correo electrónico con formato correcto.",
          icon: "error",
          showConfirmButton: false,
          timer: 2000,
        });
        return false;
      }

      this.showNuevaEmpresaModalForms = 2;
      return true;
    },

    guardarEmpresaDirectamente() {
      const valid = this.formEmpresaPaso2();
      if (!valid) return;
      this.guardarNuevaEmpresa();
    },

    formEmpresaPaso3() {
      this.showNuevaEmpresaModalForms = 2;
    },

    formEmpresaPaso4() {
      this.showNuevaEmpresaModalForms = 2;
    },

    toUpperCase(event) {
      event.target.value = event.target.value.toUpperCase();
      this.usuarioInstitucion.persona.apellidoMaterno =
        this.usuarioInstitucion.persona.apellidoMaterno.toUpperCase();
    },
    toLowerCaseMail(event) {
      event.target.value = event.target.value.toLowerCase();
      if (event.target.value.includes(" ")) {
        event.target.value = event.target.value.replace(" ", "");
      }
    },
    limitInputLength() {
      const telefono = this.usuarioInstitucion.persona.telefono;
      if (telefono.length > 8) {
        this.usuarioInstitucion.persona.telefono = telefono.slice(0, 8);
      }
    },

    validateEmail(email) {
      const re = /\S+@\S+\.\S+/;
      return re.test(email);
    },

    async registrarUsuarioInstitucion() {
      console.log("usuarioInstitucion", this.usuarioInstitucion);
      this.usuarioInstitucion.kc_UUID = this.$keycloak.idTokenParsed.sub;
      this.usuarioInstitucion.idUsuarios = null;
      this.usuarioInstitucion.horaRegistro = "12:12:12";
      this.usuarioInstitucion.institucion = this.empresaSeleccionada;
      //converir nombre , apellido paterno y apellido materno a mayusculas
      this.usuarioInstitucion.persona.nombre =
        this.usuarioInstitucion.persona.nombre.toUpperCase();
      this.usuarioInstitucion.persona.apellidoPaterno =
        this.usuarioInstitucion.persona.apellidoPaterno.toUpperCase();
      this.usuarioInstitucion.persona.apellidoMaterno =
        this.usuarioInstitucion.persona.apellidoMaterno.toUpperCase();

      //check the data before send
      if (
        this.usuarioInstitucion.persona.nombre == "" ||
        this.usuarioInstitucion.persona.apellidoPaterno == "" ||
        this.usuarioInstitucion.persona.apellidoMaterno == "" ||
        this.usuarioInstitucion.persona.ci == "" ||
        this.usuarioInstitucion.persona.telefono == "" ||
        this.usuarioInstitucion.persona.fechaDeNacimiento == "" ||
        this.usuarioInstitucion.cargo == ""
      ) {
        Swal.fire({
          title: "Complete todos los campos, incluida la foto de perfil",
          icon: "error",
          showConfirmButton: false,
          timer: 1500,
        });

        return 0;
      }

      //check that the user has uploaded a profile photo
      if (this.usuarioInstitucion.persona.fotoPerfil == "") {
        Swal.fire({
          title: "Suba una foto de perfil",
          icon: "error",
          showConfirmButton: false,
          timer: 1500,
        });
        return;
      }

      //check that the mail is valid
      if (!this.validateEmail(this.usuarioInstitucion.correo)) {
        Swal.fire({
          title: "Correo no válido",
          icon: "error",
          showConfirmButton: false,
          timer: 1500,
        });
        return;
      }

      let loader = this.$loading.show();
      try {
        const response =
          await this.institucionesPublicStore.registrarUsuarioInstitucion(
            this.usuarioInstitucion
          );
        if (response) {
          Swal.fire({
            title: "Usuario registrado correctamente",
            icon: "success",
            showConfirmButton: false,
            timer: 1500,
          });
        } else {
          Swal.fire({
            title: "Error al registrar usuario",
            icon: "error",
            showConfirmButton: false,
            timer: 1500,
          });
        }
      } catch (error) {
        console.log("error", error);
      } finally {
        this.$router.push("/");
        loader.hide();
      }
    },
    async getInstitucionesWithName() {
      this.institucionesWithOnlyName =
        await this.institucionesPublicStore.getAllInstitucionesWithOnlyName();
      this.instituciones = this.institucionesWithOnlyName;
      console.log("institucionesWithOnlyName", this.institucionesWithOnlyName);
    },
    selectArea(area) {
      if (!this.selectedAreas.includes(area)) {
        this.selectedAreas.push(area);
        this.nuevaEmpresa.sectores.push(area);
      }
    },
    removeArea(areaName) {
      console.log("area", areaName);
      const index = this.selectedAreas.indexOf(areaName);
      if (index !== -1) {
        this.selectedAreas.splice(index, 1);
      } else {
        console.log("Area not found");
      }
    },
    validateSocialUrl(social, url) {
      if (!url || typeof url !== "string") {
        return { valid: false, message: "Ingrese un enlace válido." };
      }
      let trimmed = url.trim();
      if (!trimmed) {
        return { valid: false, message: "El enlace no puede estar vacío." };
      }

      // Normalizar esquema https:// si el usuario no incluyó protocolo
      if (!/^https?:\/\//i.test(trimmed)) {
        trimmed = "https://" + trimmed;
      }

      let parsedUrl;
      try {
        parsedUrl = new URL(trimmed);
        if (parsedUrl.protocol !== "http:" && parsedUrl.protocol !== "https:") {
          return { valid: false, message: "El protocolo debe ser http:// o https://." };
        }
      } catch (e) {
        return {
          valid: false,
          message: `El enlace "${url}" no tiene un formato de URL válido (ej: https://ejemplo.com).`,
        };
      }

      const host = parsedUrl.hostname.toLowerCase();
      const socialLower = (social || "").toLowerCase();

      switch (socialLower) {
        case "facebook":
          if (!host.includes("facebook.com") && !host.includes("fb.com") && !host.includes("fb.me")) {
            return {
              valid: false,
              message: "El enlace debe pertenecer a Facebook (ej: https://facebook.com/tuempresa).",
            };
          }
          break;
        case "twitter":
          if (!host.includes("twitter.com") && !host.includes("x.com")) {
            return {
              valid: false,
              message: "El enlace debe pertenecer a Twitter / X (ej: https://x.com/tuempresa).",
            };
          }
          break;
        case "instagram":
          if (!host.includes("instagram.com")) {
            return {
              valid: false,
              message: "El enlace debe pertenecer a Instagram (ej: https://instagram.com/tuempresa).",
            };
          }
          break;
        case "linkedin":
          if (!host.includes("linkedin.com")) {
            return {
              valid: false,
              message: "El enlace debe pertenecer a LinkedIn (ej: https://linkedin.com/company/tuempresa).",
            };
          }
          break;
        case "youtube":
          if (!host.includes("youtube.com") && !host.includes("youtu.be")) {
            return {
              valid: false,
              message: "El enlace debe pertenecer a YouTube (ej: https://youtube.com/@tuempresa).",
            };
          }
          break;
        case "tiktok":
          if (!host.includes("tiktok.com")) {
            return {
              valid: false,
              message: "El enlace debe pertenecer a TikTok (ej: https://tiktok.com/@tuempresa).",
            };
          }
          break;
        case "web":
          if (!host.includes(".") || host.endsWith(".")) {
            return {
              valid: false,
              message: "Ingrese una dirección web válida (ej: https://tuempresa.com).",
            };
          }
          break;
        default:
          if (!host.includes(".")) {
            return { valid: false, message: "El dominio ingresado no es válido." };
          }
      }

      return { valid: true, normalizedUrl: trimmed };
    },

    guardarNuevaEmpresa() {
      // Valores por defecto seguros para nutrir fotos y áreas después sin causar inconsistencias
      if (!this.nuevaEmpresa.fotoInstitucion) {
        this.nuevaEmpresa.fotoInstitucion = "https://cdn-icons-png.flaticon.com/512/84/84099.png";
      }
      if (!this.nuevaEmpresa.sectores) {
        this.nuevaEmpresa.sectores = [];
      }
      if (!this.nuevaEmpresa.fotos) {
        this.nuevaEmpresa.fotos = [];
      }
      if (!this.nuevaEmpresa.redesSociales) {
        this.nuevaEmpresa.redesSociales = {};
      }

      this.showNuevaEmpresaModal = false;
      this.paginaFormulario = 2;
      this.empresaSeleccionada = { ...this.nuevaEmpresa };

      Swal.fire({
        title: "¡Empresa registrada!",
        text: "La empresa se configuró correctamente. Podrás agregar fotos e información adicional más adelante.",
        icon: "success",
        showConfirmButton: false,
        timer: 1800,
      });
    },
    addFoto(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.nuevaEmpresa.fotos.push(e.target.result);
        };
        reader.readAsDataURL(file);
      }
    },
    removeFoto(index) {
      this.nuevaEmpresa.fotos.splice(index, 1);
    },
    addRedSocial() {
      if (!this.selectedSocial) {
        Swal.fire({
          title: "Seleccione una red social",
          text: "Elija qué red social desea agregar antes de ingresar el enlace.",
          icon: "warning",
          confirmButtonColor: "#0891b2",
        });
        return;
      }

      if (!this.socialLink || !this.socialLink.trim()) {
        Swal.fire({
          title: "Enlace requerido",
          text: `Ingrese el enlace para ${this.selectedSocial}.`,
          icon: "warning",
          confirmButtonColor: "#0891b2",
        });
        return;
      }

      const socialKey = this.selectedSocial.toLowerCase();
      if (this.nuevaEmpresa.redesSociales && socialKey in this.nuevaEmpresa.redesSociales) {
        Swal.fire({
          title: "Red social ya agregada",
          text: `Ya agregaste un enlace para ${this.selectedSocial}. Si deseas cambiarlo, elimínalo primero.`,
          icon: "info",
          confirmButtonColor: "#0891b2",
        });
        return;
      }

      const validation = this.validateSocialUrl(this.selectedSocial, this.socialLink);
      if (!validation.valid) {
        Swal.fire({
          title: "URL no válida",
          text: validation.message,
          icon: "error",
          confirmButtonColor: "#0891b2",
        });
        return;
      }

      this.nuevaEmpresa.redesSociales = {
        ...this.nuevaEmpresa.redesSociales,
        [socialKey]: validation.normalizedUrl,
      };
      this.selectedSocial = "";
      this.socialLink = "";
    },
    removeRedSocial(social) {
      const { [social]: _, ...rest } = this.nuevaEmpresa.redesSociales;
      this.nuevaEmpresa.redesSociales = rest;
    },
    agregarNuevaExperiencia(experiencia) {
      console.log("duracion: " + experiencia.duracion);
      this.usuarioInstitucion.persona.experiencia.push({
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

    eliminarExperiencia(experiencia) {
      const index =
        this.usuarioInstitucion.persona.experiencia.indexOf(experiencia);
      this.usuarioInstitucion.persona.experiencia.splice(index, 1);
    },

    agregarHabilidad(habilidad) {
      this.usuarioInstitucion.persona.habilidades.push({
        habilidad: habilidad,
        nivel: 0,
        principal: false,
      });
      this.nuevaHabilidad = "";
      this.showNuevaHabilidadModal = false;
    },

    eliminarHabilidad(habilidad) {
      const index =
        this.usuarioInstitucion.persona.habilidades.indexOf(habilidad);
      this.usuarioInstitucion.persona.habilidades.splice(index, 1);
    },

    validateInputYear(event) {
      const value = event.target.value;
      // Limita la entrada a 4 dígitos numéricos
      if (/^\d{0,4}$/.test(value)) {
        this.usuarioInstitucion.persona.anioIngresoUniversidad = value;
      } else {
        event.target.value =
          this.usuarioInstitucion.persona.anioIngresoUniversida;
      }
    },

    //detener video
    toggle() {
      this.isActive = !this.isActive;
      //parar video de youtube
      const iframe = document.querySelector("iframe");
      const video = document.querySelector("video");
      if (iframe) {
        const iframeSrc = iframe.src;
        iframe.src = iframeSrc;
      }
      if (video) {
        video.pause();
      }
    },
    loadFile(event) {
      this.image = document.getElementById(event.target.name);
      this.imageSrc = URL.createObjectURL(event.target.files[0]);
    },
    loadFile2(event) {
      this.nuevaEmpresa.fotoInstitucion = document.getElementById(
        event.target.name
      );
      this.nuevaEmpresa.fotoInstitucion = URL.createObjectURL(
        event.target.files[0]
      );
    },
    showAlert() {
      // Use sweetalert2
      this.$swal
        .fire({
          title: "Are you sure?",
          text: "You won't be able to revert this!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "red",
          cancelButtonColor: "blue",
          confirmButtonText: "Delete",
        })
        .then((result) => {
          if (result.isConfirmed) {
            toast.error("Deleted!", {
              autoClose: 1000,
            });
          }
        });
    },
    async seleccionarEmpresa(Empresa) {
      this.paginaFormulario = 2;
      const loader = this.$loading.show();
      try {
        //Obtener la empresa seleccionada por id
        Empresa = await this.institucionesPublicStore.obtenerEmpresaPorId(
          Empresa.idInstituciones
        );
        //Quitar las pasantías de la empresa
        Empresa.pasantias = [];
        //Asignar la empresa seleccionada a la variable nuevaEmpresa
        this.empresaSeleccionada = Empresa;
        this.nuevaEmpresa = Empresa;
      } catch (error) {
        console.log("error", error);
      } finally {
        loader.hide();
      }
    },
    async handleFileUploadFotosGeneral(event) {
      const file = event.target.files[0];
      let auxLink = "";

      if (file) {
        //comprimir imagen si es mayor a 4mb

        console.log("tamaño imagen anterior " + file.size);
        if (file.size > 4000000) {
          auxLink = await this.comprimirYSubirImagenFotosGeneral(file, 0.3);
          console.log("tamaño imagen comprimida " + auxLink);
          this.nuevaEmpresa.fotos.push(auxLink);
          return;
        } else if (file.size > 1000000) {
          auxLink = await this.comprimirYSubirImagenFotosGeneral(file, 0.5);
          console.log("tamaño imagen comprimida " + auxLink);
          this.nuevaEmpresa.fotos.push(auxLink);
          return;
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
            this.nuevaEmpresa.fotos.push(this.filesStore.link);
            console.log("fotos", this.nuevaEmpresa.fotos);
          }
        }
      }
    },
    async handleFileUploadFotoPerfil(event) {
      const file = event.target.files[0];
      let auxLink = "";

      if (file) {
        //comprimir imagen si es mayor a 4mb

        console.log("tamaño imagen anterior " + file.size);
        if (file.size > 4000000) {
          auxLink = await this.comprimirYSubirImagenBannerPerfil(file, 0.3);
        } else if (file.size > 1000000) {
          auxLink = await this.comprimirYSubirImagenBannerPerfil(file, 0.5);
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
            //this.nuevaEmpresa.logoEmpresa = this.filesStore.link;

            this.imageSrc3 = this.filesStore.link;
            this.usuarioInstitucion.persona.fotoPerfil = this.filesStore.link;
          }
        }
      }
    },
    async handleFileUploadFotoGrande(event) {
      const file = event.target.files[0];
      let auxLink = "";

      if (file) {
        //comprimir imagen si es mayor a 4mb

        console.log("tamaño imagen anterior " + file.size);
        if (file.size > 4000000) {
          auxLink = await this.comprimirYSubirImagenFotoGrande(file, 0.3);
          console.log("tamaño imagen comprimida " + auxLink);
          this.nuevaEmpresa.fotoInstitucion = auxLink;
        } else if (file.size > 1000000) {
          auxLink = await this.comprimirYSubirImagenFotoGrande(file, 0.5);
          console.log("tamaño imagen comprimida " + auxLink);
          this.nuevaEmpresa.fotoInstitucion = auxLink;
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
            this.nuevaEmpresa.fotoInstitucion = this.filesStore.link;
          }
        }
      }
    },
    async handleFileUploadLogoEmpresa(event) {
      const file = event.target.files[0];
      let auxLink = "";

      if (file) {
        //comprimir imagen si es mayor a 4mb

        console.log("tamaño imagen anterior " + file.size);
        if (file.size > 4000000) {
          auxLink = await this.comprimirYSubirImagenLogoEmpresa(file, 0.3);
          console.log("tamaño imagen comprimida " + auxLink);
          this.nuevaEmpresa.logoEmpresa = auxLink;
          this.imageSrc = auxLink;
        } else if (file.size > 1000000) {
          auxLink = await this.comprimirYSubirImagenLogoEmpresa(file, 0.5);
          console.log("tamaño imagen comprimida " + auxLink);
          this.nuevaEmpresa.logoEmpresa = auxLink;
          this.imageSrc = auxLink;
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
            this.nuevaEmpresa.logoEmpresa = this.filesStore.link;

            this.imageSrc = this.filesStore.link;
          }
        }
      }
    },
    async comprimirYSubirImagenLogoEmpresa(file, cantidadCompresion) {
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
            this.nuevaEmpresa.logoEmpresa = this.filesStore.link;
            this.imageSrc = this.filesStore.link;
          }
        },
        error(err) {
          console.log(err.message);
        },
      });
    },

    async comprimirYSubirImagenFotoGrande(file, cantidadCompresion) {
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
            this.nuevaEmpresa.fotoInstitucion = this.filesStore.link;
          }
        },
        error(err) {
          console.log(err.message);
        },
      });
    },

    async comprimirYSubirImagenFotosGeneral(file, cantidadCompresion) {
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
            this.nuevaEmpresa.fotos.push(this.filesStore.link);
            this.nuevaEmpresa.fotos = this.nuevaEmpresa.fotos.filter(
              (element) => element !== undefined
            );
          }
        },
        error(err) {
          console.log(err.message);
        },
      });
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
          const aux = this.filesStore.link;
          loader.hide();

          if (response === false) {
            Swal.fire({
              icon: "error",
              title: "Error",
              text: "Error al subir la imagen, porfavor intenta de nuevo",
            });
          } else {
            this.imageSrc3 = this.filesStore.link;
            this.usuarioInstitucion.persona.fotoPerfil = this.filesStore.link;
          }
        },
        error(err) {
          console.log(err.message);
        },
      });
    },
  },
  computed: {
    filteredDatas() {
      return this.instituciones.filter((item) =>
        item.nombre.toLowerCase().includes(this.Busqueda.toLowerCase())
      );
    },
    filteredAreas() {
      return this.allAreas.filter(
        (area) =>
          area.toLowerCase().includes(this.searchQuery.toLowerCase()) &&
          !this.selectedAreas.includes(area)
      );
    },
    availableSocials() {
      return this.allSocials.filter(
        (social) => !(social.toLowerCase() in this.nuevaEmpresa.redesSociales)
      );
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

@media (max-width: 451px) {
  .modal-content {
    top: 5rem;
    width: 90%;
  }
}
</style>
