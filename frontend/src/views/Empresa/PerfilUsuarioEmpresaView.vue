<template>
    <navbar :container="'container'" :lightNav="'justify-end'" />
    <section class="relative lg:mt-24 mt-[74px] pb-16">
      <div class="lg:container container-fluid">
        <div class="profile-banner relative text-transparent">
          
          <div class="relative shrink-0">
            <div
              class="h-64 w-full object-cover lg:rounded-xl shadow dark:shadow-gray-700 bg-[url('../../assets/images/hero/bg.webp')] bg-cover "
              id="profile-banner"
              alt=""
            ></div>
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
                    :src="this.usuarioEmpresa.persona.fotoPerfil"
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
                <h5 class="text-lg font-semibold">{{ this.usuarioEmpresa.persona.nombre+" "+this.usuarioEmpresa.persona.apellidoPaterno+" "+this.usuarioEmpresa.persona.apellidoMaterno }}</h5>
                <p class="text-slate-400">Usuario de Empresa</p>
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
            <div class="p-6 rounded-md shadow dark:shadow-gray-800 bg-white dark:bg-slate-900">
              <h5 class="text-lg font-semibold mb-4">Personal Detail :</h5>
              <div>
                <div class="grid lg:grid-cols-12 md:grid-cols-2 grid-cols-1 gap-4">
                  <div class="lg:col-span-6">
                    <label class="form-label font-medium">Nombres : <span class="text-red-600">*</span></label>
                    <input type="text" class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioEmpresa.persona.nombre" placeholder="Nombres:" id="firstname" name="name"
                      required="" @input="handleInputChange"
                      disabled
                      />
                  </div>
                  <div class="lg:col-span-4">
                    <label class="form-label font-medium">Ci :<span class="text-red-600">*</span></label>
                    <input type="text" class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioEmpresa.persona.ci" placeholder="Ci" name="address" required="" @input="handleInputChange"/>
                  </div>
  
  
                  <div class="lg:col-span-6">
                    <label class="form-label font-medium">Apellido Paterno : <span class="text-red-600">*</span></label>
                    <input type="text" class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioEmpresa.persona.apellidoPaterno" placeholder="Apellido Paterno:" id="lastname"
                      name="name" required="" @input="handleInputChange"
                      disabled
                      />
                  </div>
  
                  <div class="lg:col-span-6">
                    <label class="form-label font-medium">Apellido Materno : <span class="text-red-600">*</span></label>
                    <input type="text" class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioEmpresa.persona.apellidoMaterno" placeholder="Apellido Paterno:" id="lastname"
                      name="name" required="" @input="handleInputChange"
                      disabled
                      />
                  </div>
  
  
                  <div class="lg:col-span-6">
                    <label class="form-label font-medium">Email : <span class="text-red-600">*</span></label>
                    <input type="email" class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioEmpresa.correo" placeholder="Email" name="email" required="" @input="handleInputChange"
                      disabled
                      />
                  </div>
  
                  <div class="lg:col-span-6">
                    <label class="form-label font-medium" for="birthday">Fecha de Nacimiento :<span
                        class="text-red-600">*</span></label>
                    <input type="date" id="birthday" name="birthday" v-model="usuarioEmpresa.persona.fechaDeNacimiento"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2" @input="handleInputChange"/>
                  </div>
  
                  <div class="lg:col-span-4">
                    <label class="form-label font-medium">Número Celular :<span class="text-red-600">*</span></label>
                    <input type="number" class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioEmpresa.persona.telefono" placeholder="Número Celular" name="address" required="" @input="handleInputChange"/>
                  </div>

                  <div class="lg:col-span-4">
                    <label class="form-label font-medium">Cargo :<span class="text-red-600">*</span></label>
                    <input type="text" class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      v-model="usuarioEmpresa.usuarioInstitucion.cargo" placeholder="Cargo" name="cargo" required="" @input="handleInputChange"/>
                  </div>

                </div>
                <div class="lg:col-span-4 my-8">
                  <button name="send" @click="registrarUsuarioEmpresa()" :disabled="!changes"
                  class="btn border-emerald-600 bg-emerald-600 hover:bg-emerald-700 text-white rounded-md cursor-pointer w-full">Guardar</button>
                </div>


              </div>
              <!--end form-->
            </div>
          </div>
        </div>
      </div>
      <!--fin de edicion de profie-->
    </section>

    <!-- End Hero -->
    <footers />
    <switcher />
  </template>
  
  <script>
  import navbar from "@/components/Empresa/NavBarEmpresa.vue";
  import footers from "@/components/footer/footer.vue";
  import switcher from "@/components/General/switcher.vue";
  import { useEmpresasAdminStore } from "@/stores/Administradores/empresasAdminStore";
  import Swal from "sweetalert2";
  import {useFilesStore} from "@/stores/fileStore.js";
  import Compressor from 'compressorjs';
  import StarRatingComponent from "@/components/General/Extras/StartRatingComponent.vue";
  
  export default {
    setup(){
      const empresasAdminStore = useEmpresasAdminStore();
      const filesStore = useFilesStore();
      return{
        empresasAdminStore, filesStore
      }
    },
    data() {
      return {
        changes:true,
        imageSrc: "../assets/images/team/01.jpg",
        image: "",
        imageSrc2: "../assets/images/team/01.jpg",
        image2: "",
        usuarioEmpresa: {
          "idUsuarios": null,
          "kc_UUID": "asdkasdja-sdajfsdbkfasd-32",
          "correo": "daniel.aldazosa@ucb.edu.bo",
          "fechaRegistro": "2024-06-28",
          "horaRegistro": "11:50:07",
          "idRoles": 1,
          "idPersonas": null,
          "idCarreras": 1,
  
          institucion:null,
          cargo:null,
  
          
          "persona": {
            "idPersona": null,
            "nombre": "",
            "apellidoPaterno": "Aldazosa",
            "apellidoMaterno": "Miranda",
            "telefono": 60176430,
            "ci": "6959943",
            "fotoPerfil": "https://backend-sistemas.serverbb.online/api/v1/public/files/download/blob_20240520-221153808",
            "anioIngresoUniversidad": 2020,
            "descripcion": "slkdf sldklfjsald flskdfs dlfksdfjskdf sdfskdfjsldjk",
            "fechaDeNacimiento": "2002-12-17",  
          },
          "usuarioInstitucion": {
            "idUsuariosInstituciones": 0,
            "idUsuarios": 0,
            "idInstituciones": 0,
            "cargo": ""
        }
        },
      };
    },
    components: {
      navbar,
      footers,
      switcher,
      StarRatingComponent
    },
    mounted() {
      this.fecthUsuarioEmpresa();
    },
    beforeMount() {
      if (!this.$keycloak.authenticated) {
        this.$router.push('/')
      }
      this.usuarioEmpresa.persona.anioIngresoUniversidad = 2000;
      this.usuarioEmpresa.kc_UUID = this.$keycloak.idTokenParsed.sub;
    },
    methods: {
      async registrarUsuarioEmpresa() {
        let loader= this.$loading.show();
        try {
          const response = await this.empresasAdminStore.updateUsuarioEmpresa(this.usuarioEmpresa, this.$keycloak.idTokenParsed.sub);
          if(response == null){
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Ocurrio un error al actualizar la información del usuario!',
            })
  
            this.$router.push('/');
          }else{
            await Swal.fire({
              icon: 'success',
              title: 'Usuario actualizado con exito!',
              showConfirmButton: false,
              timer: 1500
            })  
            this.$router.push('/empresa/administrador/perfil');
          }
        } catch (error) {
          console.log(error);
        }finally{
          loader.hide();
        }
      },

      async fecthUsuarioEmpresa() {
        let loader = this.$loading.show();

        const response = await  this.empresasAdminStore.fetchUserByUUID(this.$keycloak.idTokenParsed.sub);
        console.log(response);
        if(response==null){
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'No se pudo cargar la información del usuario!',
          })
          this.$router.push('/');
          loader.hide();
          return
        }
        else{
          this.usuarioEmpresa = response;
        }
        console.log(this.usuarioEmpresa);
        loader.hide();
      },

      async handleInputChange(){
        this.changes=true;
      },
  
      async handleFileUploadFotoPerfil(event) {
          const file = event.target.files[0];
          let auxLink="";
  
          if (file) {
            //comprimir imagen si es mayor a 4mb 
  
            console.log("tamaño imagen anterior "+file.size);
            if (file.size > 4000000) {
              auxLink= await this.comprimirYSubirImagenFotoPerfil(file, 0.3);
              console.log("tamaño imagen comprimida "+auxLink);
              this.usuarioEmpresa.persona.fotoPerfil=auxLink;
            } else if( file.size > 1000000){ 
              auxLink = await this.comprimirYSubirImagenFotoPerfil(file,0.5 );
              console.log("tamaño imagen comprimida "+auxLink);
              this.usuarioEmpresa.persona.fotoPerfil=auxLink;
  
            } 
            else {
                let loader= this.$loading.show();
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
                  this.usuarioEmpresa.persona.fotoPerfil=this.filesStore.link;
                  
                  this.imageSrc = this.filesStore.link;
                  
                }
            }
          }
        },
        async comprimirYSubirImagenFotoPerfil(file, cantidadCompresion){
        new Compressor(file, {
            quality: cantidadCompresion,
            success: async  (compressedResult) => {
              console.log("tamaño imagen comprimida "+compressedResult.size+ " con una compreison de "+cantidadCompresion);
              let loader= this.$loading.show();
              const response = await this.filesStore.uploadFile(compressedResult);
              loader.hide();
  
              if (response === false) {
                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: "Error al subir la imagen, porfavor intenta de nuevo",
                  });
                
              } else {
                this.usuarioEmpresa.persona.fotoPerfil=this.filesStore.link;
                  
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
          let auxLink="";
  
          if (file) {
            //comprimir imagen si es mayor a 4mb 
  
            console.log("tamaño imagen anterior "+file.size);
            if (file.size > 4000000) {
              auxLink= await this.comprimirYSubirImagenBannerPerfil(file, 0.3);
              console.log("tamaño imagen comprimida "+auxLink);
              this.usuarioEmpresa.persona.bannerPerfil=auxLink;
            } else if( file.size > 1000000){ 
              auxLink = await this.comprimirYSubirImagenBannerPerfil(file,0.5 );
              console.log("tamaño imagen comprimida "+auxLink);
              this.usuarioEmpresa.persona.bannerPerfil=auxLink;
  
            } 
            else {
                let loader= this.$loading.show();
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
                  this.usuarioEmpresa.persona.bannerPerfil=this.filesStore.link;
                  
                  this.imageSrc2 = this.filesStore.link;
                  
                }
            }
          }
        },
        async comprimirYSubirImagenBannerPerfil(file, cantidadCompresion){
        new Compressor(file, {
            quality: cantidadCompresion,
            success: async  (compressedResult) => {
              console.log("tamaño imagen comprimida "+compressedResult.size+ " con una compreison de "+cantidadCompresion);
              let loader= this.$loading.show();
              const response = await this.filesStore.uploadFile(compressedResult);
              const aux= this.filesStore.link;
              loader.hide();
  
              if (response === false) {
                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: "Error al subir la imagen, porfavor intenta de nuevo",
                  });
                
              } else {
                this.usuarioEmpresa.persona.bannerPerfil=this.filesStore.link;
                  
                  this.imageSrc2 = this.filesStore.link;
              }
            },
            error(err) {
              console.log(err.message);
            },
          });
      },
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
      agregarNuevaExperiencia(experiencia) {
        console.log("duracion: "+experiencia.duracion)
        this.usuarioEmpresa.persona.experiencia.experiencia.push({
          titulo: experiencia.titulo,
          empresa: experiencia.empresa,
          duracion: experiencia.duracion,
          descripcion: experiencia.descripcion
        });
        this.nuevaExperiencia= {
          titulo: "",
          empresa: "",
          duracion: "",
          descripcion: ""
        }
        this.showNuevaExperienciaModal = false;
  
      },
  
      eliminarExperiencia(experiencia) {
        const index = this.usuarioEmpresa.persona.experiencia.indexOf(experiencia);
        this.usuarioEmpresa.persona.experiencia.experiencia.splice(index, 1);
      },
  
  
  
  
      agregarHabilidad(habilidad) {
        this.usuarioEmpresa.persona.habilidades.habilidades.push({
          habilidad: habilidad,
          nivel: 0,
          principal: false
        });
        this.nuevaHabilidad = "";
        this.showNuevaHabilidadModal = false;
      },
  
      eliminarHabilidad(habilidad) {
        const index = this.usuarioEmpresa.persona.habilidades.habilidades.indexOf(habilidad);
        this.usuarioEmpresa.persona.habilidades.habilidades.splice(index, 1);
      },
  
  
      validateInputYear(event) {
        const value = event.target.value;
        // Limita la entrada a 4 dígitos numéricos
        if (/^\d{0,4}$/.test(value)) {
          this.usuarioEmpresa.persona.anioIngresoUniversidad = value;
        } else {
          event.target.value = this.usuarioEmpresa.persona.anioIngresoUniversida;
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
  