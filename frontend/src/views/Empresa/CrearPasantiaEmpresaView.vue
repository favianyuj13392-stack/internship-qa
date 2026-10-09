<template>
    <navbar :container="'container'" :lightNav="'justify-end'" />

    <!-- Start -->
    <section class="bg-slate-50 dark:bg-slate-800 md:py-24 py-16">
        <div class="container mt-10">
            <div class="grid md:grid-cols-12 grid-cols-1 gap-[30px]">
                <div class="lg:col-span-8 md:col-span-6">
                    <div
                        class="md:flex items-center p-6 shadow dark:shadow-gray-700 rounded-md bg-white dark:bg-slate-900 mb-6">
                        <img :src="data?.institucion.logoEmpresa ? data?.institucion.logoEmpresa : image"
                            class="rounded-full size-28 p-4 bg-white dark:bg-slate-900 shadow dark:shadow-gray-700"
                            alt="" />

                        <div class="md:ms-4 md:mt-0 mt-6">
                            <input type="text"
                                class="text-l font-semibold p-2 form-input border border-slate-100 dark:border-slate-800  "
                                v-model="data.pasantiasDto.titulo" placeholder="Título Pasantía" />
                            <div class="mt-2">
                                <span class="text-slate-400 font-medium me-2 inline-block"><i
                                        class="uil uil-building text-[18px] text-cyan-600 me-1"></i>
                                    {{ data?.institucion.nombre ? data?.institucion.nombre : "Lenovo pvt. ltd."
                                    }}</span>
                                <span class="text-slate-400 font-medium me-2 inline-block"><i
                                        class="uil uil-map-marker text-[18px] text-cyan-600 me-1"></i>
                                    {{
        data?.institucion.direccion ? data?.institucion.direccion : "Beijing, China"
    }}</span>
                            </div>
                        </div>
                    </div>
                    <!--detallesssss-->
                    <h5 class="text-lg font-semibold">Detalles de la pasantia: <span>{{
            data.pasantiasDto.descripcion.length }}/1000</span></h5>

                    <textarea
                        class="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-cyan-600 w-full p-2  form-input border border-slate-100 dark:border-slate-800 h-24 "
                        placeholder="Escribe aquí..." v-model="data.pasantiasDto.descripcion" maxlength="1000">
                    </textarea>

                    <div class="flex items
                    -center space-x-40 mt-6">
                        <h5 class="text-lg font-semibold">Fecha de Cierre:</h5>
                        <input type="date"
                            class="p-2 border rounded focus:outline-none focus:ring-2 focus:ring-cyan-600 w-full p-2 form-input border border-slate-100 dark:border-slate-800"
                            v-model="data.pasantiasDto.fechaCierre" />
                    </div>

                    <div>
                        <div class="flex grid items-center grid-cols-2 space-x-40 mt-6 mr-36">
                            <h5 class="text-lg font-semibold">Áreas:</h5>
                            <select v-model="areaSelected" @change="handleSelectChange"
                                class="btn rounded-md bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white w-full md:w-full">
                                <option value="" disabled selected>Seleccionar Área</option>
                                <option v-for="area in areas" :key="area" :value="area">{{ area }}</option>
                                <option value="nuevaArea">Agregar nueva área</option>
                            </select>
                        </div>

                        <ul class="list-none mt-4">
                            <li v-for="area in data.pasantiasDto.areas" :key="area" class="text-slate-400 mt-2">
                                <i class="uil uil-arrow-right text-cyan-600 me-1"></i>{{ area }}
                                <i class="uil uil-trash-alt text-red-600 cursor-pointer p-2"
                                    @click="removerArea(area)"></i>
                            </li>
                        </ul>
                    </div>

                    <div class="flex grid items-center grid-cols-2 space-x-40 mt-6 mr-36">
                        <h5 class="text-lg font-semibold">Requisistos:</h5>

                        <button @click="agregarRequisitos()"
                            class="btn rounded-md bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white w-full md:w-full">
                            Agregar
                        </button>
                    </div>



                    <ul class="list-none">
                        <li v-for="item in this.data.pasantiasDto.requisitos" :key="item" class="text-slate-400 mt-2">
                            <i class="uil uil-arrow-right text-cyan-600 me-1"></i>{{ item }}
                            <i class="uil uil-trash-alt text-red-600 cursor-pointer p-2"
                                @click="data.pasantiasDto.requisitos = data.pasantiasDto.requisitos.filter(e => e !== item)"></i>
                        </li>
                    </ul>



                    <div class="flex grid items-center grid-cols-2 space-x-40 mt-6 mr-36">
                        <h5 class="text-lg font-semibold">Funciones:</h5>
                        <button @click="agregarFunciones()"
                            class="btn rounded-md bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white w-full md:w-full">
                            Agregar
                        </button>
                    </div>

                    <ul class="list-none">
                        <li v-for="item in this.data.pasantiasDto.funciones" :key="item" class="text-slate-400 mt-2">
                            <i class="uil uil-arrow-right text-cyan-600 me-1"></i>{{ item }}
                            <i class="uil uil-trash-alt text-red-600 cursor-pointer p-2"
                                @click="data.pasantiasDto.funciones = data.pasantiasDto.funciones.filter(e => e !== item)"></i>
                        </li>
                    </ul>


                    <div class="flex grid items-center grid-cols-2 space-x-40 mt-6 mr-36">
                        <h5 class="text-lg font-semibold">Beneficios:</h5>
                        <button @click="agregarBeneficios()"
                            class="btn rounded-md bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white w-full md:w-full">
                            Agregar
                        </button>
                    </div>

                    <ul class="list-none">
                        <li v-for="item in this.data.pasantiasDto.beneficios" :key="item" class="text-slate-400 mt-2">
                            <i class="uil uil-arrow-right text-cyan-600 me-1"></i>{{ item }}
                            <i class="uil uil-trash-alt text-red-600 cursor-pointer p-2"
                                @click="data.pasantiasDto.beneficios = data.pasantiasDto.beneficios.filter(e => e !== item)"></i>
                        </li>
                    </ul>

                    <div>
                        <div class="flex grid items-center grid-cols-2 space-x-40 mt-6 mr-36">
                            <h5 class="text-lg font-semibold">Carreras:</h5>
                            <select v-model="carreraSelected" @change="agregarCarrera($event.target.value)"
                                class="btn rounded-md bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white w-full md:w-full">
                                <option value="" disabled selected>Seleccionar Carrera</option>
                                <option v-for="carrera in carreras" :key="carrera.idCarreras" :value="carrera">{{
        carrera.nombre }}
                                </option>
                            </select>
                        </div>

                        <ul class="list-none">
                            <li v-for="carrera in selectedCarreras" :key="carrera.idCarreras"
                                class="text-slate-400 mt-2">
                                <i class="uil uil-arrow-right text-cyan-600 me-1"></i>
                                <span>{{ carrera.nombre }}</span>
                                <i class="uil uil-trash-alt text-red-600 cursor-pointer p-2"
                                    @click="removerCarrera(carrera)"></i>
                            </li>
                        </ul>
                    </div>









                    <div class="mt-16">
                        <a @click="guardarPasantia()"
                            class="btn rounded-md bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white md:ms-2 w-full md:w-full">Guardar
                            Pasantía</a>
                    </div>
                </div>
                <!--end col-->

                <div class="lg:col-span-4 md:col-span-5">
                    <div class="bg-slate-50 dark:bg-slate-800 rounded-md shadow dark:shadow-gray-700 p-6 sticky top-20">
                        <div class="w-full leading-[0] border-0">
                            <iframe :src="mapSrc" style="border: 0"
                                class="w-full h-[350px] rounded-md shadow dark:shadow-gray-700"
                                allowfullscreen></iframe>
                        </div>

                        <ul class="list-none mt-4">
                            <li class="flex justify-between mt-2">
                                <span class="text-slate-400 font-medium">Dirección:</span>
                                <span class="font-medium">{{ data.institucion.direccion }}</span>
                            </li>



                            <li class="flex justify-between mt-2">
                                <span class="text-slate-400 font-medium">Correo:</span>
                                <span class="font-medium">{{ this.data.institucion.correo }}</span>
                            </li>

                            <li class="flex justify-between mt-2">
                                <span class="text-slate-400 font-medium">Sectores:</span>
                                <div class="flex flex-wrap ml-16">
                                    <a v-for="sector in this.data.institucion.sectores" v-bind:key="sector">
                                        <span
                                            class="bg-cyan-600/5 hover:bg-cyan-600/20 dark:bg-cyan-600/10 hover:dark:bg-cyan-600/30 inline-block text-cyan-600 text-[12px] font-medium rounded-md mt-2 me-1 transition-all duration-500 p-1">{{
        sector }}</span>
                                    </a>
                                </div>
                            </li>



                            <li class="flex justify-between mt-2" v-if="this.data.institucion.redesSociales.web">
                                <span class="text-slate-400 font-medium">Website:</span>
                                <span class="font-medium">{{ this.data.institucion.redesSociales.web }}</span>
                            </li>

                            <li class="flex justify-between mt-2">
                                <span class="text-slate-400 font-medium">Social:</span>

                                <ul class="list-none text-end space-x-0.5">
                                    <li class="inline" v-for="(url, name) in data.institucion.redesSociales"
                                        :key="name">
                                        <a :href="url" target="_blank"
                                            class="btn btn-icon btn-sm border-2 border-gray-200 dark:border-gray-700 rounded-md hover:border-cyan-600 dark:hover:border-cyan-600 hover:bg-cyan-600 dark:hover:bg-cyan-600 hover:text-white dark:text-white text-slate-400">
                                            <i :class="`uil uil-${name}`" :title="name" class="align-middle"></i>
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
import { useInstitucionesAdministracionStore } from "@/stores/Instituciones/InstitucionesAdministracionStore.js";
import { useCarrerasStore } from "@/stores/carrerasStore";
import { useAreasStore } from "@/stores/areasStore";
import Swal from "sweetalert2";
export default {

    setup() {
        const pasantiasAdministracionInstitucionStore = usePasantiasAdministracionInstitucionStore();
        const institucionesAdministracionStore = useInstitucionesAdministracionStore();
        const carrerasStore = useCarrerasStore();
        const areasStore = useAreasStore();
        return {
            pasantiasAdministracionInstitucionStore, institucionesAdministracionStore, carrerasStore, areasStore
        }
    },


    computed: {
        mapSrc() {
            // Construir la URL del mapa de Google con la dirección
            const direccionEncoded = encodeURIComponent(this.data.institucion.direccion);
            return `https://www.google.com/maps/embed/v1/place?q=${direccionEncoded}&key=AIzaSyCoOVExrC3ADb7HXkXLyBZB3zyVqClHZ7w`;
            // Reemplaza TU_API_KEY con tu propia clave de API de Google Maps
        },
    },


    methods: {
        async agregarNuevaArea() {
            //agregar un area con swal
            const { value: area } = await Swal.fire({
                title: 'Agregar Area',
                input: 'text',
                inputLabel: 'Area',
                inputPlaceholder: 'Area',
                showCancelButton: true,
                inputValidator: (value) => {
                    if (!value) {
                        return 'Debes ingresar un area'
                    }
                }
            })

            if (area) {
                this.data.pasantiasDto.areas.push(area);
            }

        },

        handleSelectChange(){
            if(this.areaSelected == "nuevaArea"){
                this.agregarNuevaArea();
                this.areaSelected = null;
            }else{
                this.agregarArea();
            }
        },

        agregarArea() {
            if (this.areaSelected && !this.data.pasantiasDto.areas.includes(this.areaSelected)) {
                this.data.pasantiasDto.areas.push(this.areaSelected);
            }
            console.log(this.data.pasantiasDto.areas)
            this.areaSelected = null;
        },

        removerArea(area) {
            this.data.pasantiasDto.areas = this.data.pasantiasDto.areas.filter(item => item !== area);
        },

        async agregarRequisitos() {
            //agregar un area con swal
            const { value: requisito } = await Swal.fire({
                title: 'Agregar Requisito',
                input: 'text',
                inputLabel: 'Requisito',
                inputPlaceholder: 'Requisito',
                showCancelButton: true,
                inputValidator: (value) => {
                    if (!value) {
                        return 'Debes ingresar un requisito'
                    }
                }
            })

            if (requisito) {
                this.data.pasantiasDto.requisitos.push(requisito);
            }


        },

        async agregarFunciones() {
            //agregar un area con swal
            const { value: funcion } = await Swal.fire({
                title: 'Agregar Funcion',
                input: 'text',
                inputLabel: 'Funcion',
                inputPlaceholder: 'Funcion',
                showCancelButton: true,
                inputValidator: (value) => {
                    if (!value) {
                        return 'Debes ingresar una funcion'
                    }
                }
            })

            if (funcion) {
                this.data.pasantiasDto.funciones.push(funcion);
                console.log(this.data.pasantiasDto.funciones)
            }



        },

        async agregarBeneficios() {
            //agregar un area con swal
            const { value: beneficio } = await Swal.fire({
                title: 'Agregar Beneficio',
                input: 'text',
                inputLabel: 'Beneficio',
                inputPlaceholder: 'Beneficio',
                showCancelButton: true,
                inputValidator: (value) => {
                    if (!value) {
                        return 'Debes ingresar un beneficio'
                    }
                }
            })

            if (beneficio) {
                this.data.pasantiasDto.beneficios.push(beneficio);
            }
        },

        agregarCarrera() {
            if (this.carreraSelected && !this.selectedCarreras.includes(this.carreraSelected)) {
                this.selectedCarreras.push(this.carreraSelected);
            }
            console.log(this.selectedCarreras)
            this.carreraSelected = null;
        },
        removerCarrera(carrera) {
            this.selectedCarreras = this.selectedCarreras.filter(item => item.idCarreras !== carrera.idCarreras);
        },


        async fetchPasantiaActual() {
            let loader = this.$loading.show();
            const response = await this.pasantiasAdministracionInstitucionStore.fetchPasantiaInstitucionByUUID(this.$keycloak.tokenParsed.sub, this.id);
            if (response == null) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'No se pudo cargar la pasantia',
                })
                loader.hide();

                this.$router.push('/');
                return;

            }
            loader.hide();
            console.log(response)
            this.data = response;

        },

        async fetchInstitucion() {
            let loader = this.$loading.show();
            const response = await this.institucionesAdministracionStore.fetchInstitucionByUUID(this.$keycloak.tokenParsed.sub);
            loader.hide();
            if (response == null) {
                Swal.fire({
                    title: "Error",
                    text: "No se pudo cargar la informacion de la empresa",
                    icon: "error",
                    confirmButtonText: "Ok",
                });
                //this.$keycloak.logout();
            }
            this.data.institucion = response;
        },

        async fetchCarreras() {
            let loader = this.$loading.show();
            const response = await this.carrerasStore.getCarreras();
            loader.hide();
            if (response == null) {
                Swal.fire({
                    title: "Error",
                    text: "No se pudo cargar las carreras",
                    icon: "error",
                    confirmButtonText: "Ok",
                });
                return;
            }
            this.carreras = response;
            //ordenar las carreras
            this.carreras.sort((a, b) => a.nombre.localeCompare(b.nombre));
            this.carreraSelected = this.carreras[0];

        },

        async fetchAreas() {
            let loader = this.$loading.show();
            const response = await this.areasStore.getAreas();
            loader.hide();
            if (response == null) {
                Swal.fire({
                    title: "Error",
                    text: "No se pudo cargar las areas",
                    icon: "error",
                    confirmButtonText: "Ok",
                });
                return;
            }
            this.areas = response;
            //ordenar las areas en orden alfabetico
            this.areas.sort((a, b) => a.localeCompare(b));
            this.areaSelected = this.areas[0];
        },

        async guardarPasantia() {
            //comprobar los campos
            if (this.data.pasantiasDto.titulo == "" || this.data.pasantiasDto.descripcion == "" || this.data.pasantiasDto.areas.length == 0 || this.data.pasantiasDto.requisitos.length == 0 || this.data.pasantiasDto.funciones.length == 0 || this.data.pasantiasDto.beneficios.length == 0 || this.data.pasantiasDto.fechaCierre == "" || this.selectedCarreras.length == 0) {
                Swal.fire({
                    title: "Error",
                    text: "Debes llenar todos los campos",
                    icon: "error",
                    confirmButtonText: "Ok",
                });
                return;
            }

            if (this.data.pasantiasDto.descripcion.length > 1000) {
                Swal.fire({
                    title: "Error",
                    text: "La descripción no puede tener más de 1000 caracteres",
                    icon: "error",
                    confirmButtonText: "Ok",
                });
                return;
            }

            //fecha de cierre no puede ser menor a la fecha actual
            const fechaCierre = new Date(this.data.pasantiasDto.fechaCierre);
            const fechaActual = new Date();
            if (fechaCierre < fechaActual) {
                Swal.fire({
                    title: "Error",
                    text: "La fecha de cierre no puede ser menor a la fecha actual",
                    icon: "error",
                    confirmButtonText: "Ok",
                });
                return;
            }




            let loader = this.$loading.show();
            const idInstituciones = this.data.institucion.idInstituciones;
            this.data.pasantiasDto.idCarreras = this.selectedCarreras.map(carrera => carrera.idCarreras);
            const pasantiaDto = this.data.pasantiasDto;
            console.log(pasantiaDto)
            const response = await this.pasantiasAdministracionInstitucionStore.postPasantiaInstitucion(this.$keycloak.tokenParsed.sub, idInstituciones, pasantiaDto);
            loader.hide();
            if (response == null) {
                Swal.fire({
                    title: "Error",
                    text: "No se pudo guardar la pasantia",
                    icon: "error",
                    confirmButtonText: "Ok",
                });
                return;
            }
            Swal.fire({
                title: "Guardado",
                text: "Se guardo la pasantia",
                icon: "success",
                confirmButtonText: "Ok",
            });
            this.$router.push('/empresa/administrador/pasantias');
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
            data: {
                carreraSelected: "",
                areaSelected: "",
                pasantiasDto: {
                    titulo: "",
                    descripcion: "",
                    areas: [],
                    requisitos: [],
                    calificaciones: [],
                    funciones: [],
                    beneficios: [],
                    fechaCierre: "",
                    idCarreras: [],

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
            carreras: [],
            areas: [],
            selectedCarreras: [
                {
                    idCarreras: "",
                    nombre: "",
                },
            ],
        };
    },
    components: {
        navbar,
        footers,
        switcher,
    },
    mounted() {
        //this.id = this.$route.params.id;

        //this.fetchPasantiaActual();
        this.fetchCarreras();
        this.fetchAreas();
        this.fetchInstitucion();
        this.selectedCarreras = [];

    },
};
</script>

<style lang="scss" scoped></style>