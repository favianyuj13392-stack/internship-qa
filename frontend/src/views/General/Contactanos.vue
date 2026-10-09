<template>
  <navbar :container="'container'" :lightNav="'justify-end'" />
  <!-- Google Map -->
  <div class="container-fluid relative mt-20">
    <div class="grid grid-cols-1">
      <div class="w-full leading-[0] border-0">
        <iframe
          src="https://www.google.com/maps/embed?pb=!1m14!1m12!1m3!1d1237.2603610040005!2d-68.1126686331831!3d-16.523058166077593!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e0!3m2!1ses-419!2sbo!4v1719299088191!5m2!1ses-419!2sbo"
          style="border: 0"
          class="w-full h-[500px]"
          allowfullscreen
        ></iframe>
      </div>
    </div>
    <!--end grid-->
  </div>
  <!--end container-->
  <!-- Google Map -->

  <!-- Start Section-->
  <section class="relative lg:py-24 py-16">
    <div class="container">
      <div class="grid md:grid-cols-12 grid-cols-1 items-center gap-[30px]">
        <div class="lg:col-span-7 md:col-span-6">
          <img src="@/assets/images/svg/contact.svg" alt="" />
        </div>

        <div class="lg:col-span-5 md:col-span-6">
          <div class="lg:ms-5">
            <div
              class="bg-white dark:bg-slate-900 rounded-md shadow dark:shadow-gray-700 p-6"
            >
              <h3 class="mb-6 text-2xl leading-normal font-semibold">
                ¡Contáctanos!
              </h3>

              <form @submit.prevent="sendEmail">
                <div class="grid lg:grid-cols-12 lg:gap-6">
                  <div class="lg:col-span-6 mb-5">
                    <label for="name" class="font-semibold"
                      >Nombre completo</label
                    >
                    <input
                      name="name"
                      id="name"
                      type="text"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      placeholder="Nombre..."
                      v-model="formData.name"
                    />
                  </div>

                  <div class="lg:col-span-6 mb-5">
                    <label for="email" class="font-semibold">Email</label>
                    <input
                      name="email"
                      id="email"
                      type="email"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      placeholder="Email... "
                      v-model="formData.email"
                    />
                  </div>
                </div>

                <div class="grid grid-cols-1">
                  <div class="mb-5">
                    <label for="subject" class="font-semibold"
                      >Número de celular</label
                    >
                    <input
                      name="subject"
                      id="subject"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2"
                      placeholder=" (Opcional)"
                      v-model="formData.numero"
                    />
                  </div>

                  <div class="mb-5">
                    <label for="comments" class="font-semibold"
                      >Tu comentario:</label
                    >
                    <textarea
                      name="comments"
                      id="comments"
                      class="form-input border border-slate-100 dark:border-slate-800 mt-2 textarea"
                      placeholder="Mensaje :"
                      v-model="formData.comments"
                    ></textarea>
                  </div>
                </div>
                <button
                  type="submit"
                  id="submit"
                  name="send"
                  class="btn border-cyan-600 bg-cyan-600 hover:bg-cyan-700 text-white rounded-md"
                >
                  Enviar correo
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--end container-->

    <div class="container lg:mt-24 mt-16">
      <div class="grid grid-cols-1 lg:grid-cols-3 md:grid-cols-2 gap-[30px]">
        <div v-for="item in datas" :key="item" class="text-center px-6">
          <div class="relative text-transparent">
            <div
              class="size-14 bg-cyan-600/5 text-cyan-600 rounded-xl text-2xl flex align-middle justify-center items-center mx-auto shadow-sm dark:shadow-gray-800"
            >
              <i :class="item.icon"></i>
            </div>
          </div>

          <div class="content mt-7">
            <h5 class="title h5 text-lg font-semibold">{{ item.name }}</h5>
            <p class="text-slate-400 mt-3">{{ item.desc }}</p>

            <div class="mt-5">
              <a
                :href="item.href"
                class="btn btn-link text-cyan-600 hover:text-cyan-600 after:bg-cyan-600 transition duration-500"
                >{{ item.title }}</a
              >
            </div>
          </div>
        </div>
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
  </section>
  <!--end section-->
  <!-- End Section-->
  <footers />
  <switcher />
</template>

<script>
import Swal from "sweetalert2";
import navbar from "@/components/General/navbarGeneral.vue";
import footers from "@/components/footer/footer.vue";
import switcher from "@/components/General/switcher.vue";
import { toast } from "vue3-toastify";
import "vue3-toastify/dist/index.css";
export default {
  data() {
    return {
      formData: {
        name: "",
        email: "",
        subject: "",
        comments: "",
        numero: "",
      },
      datas: [
        {
          icon: "uil uil-phone",
          name: "Teléfono",
          desc: "Lunes a jueves de 08:30 a las 16:00",
          href: "tel:+59176521445",
          title: "+591 76521445",
        },
        {
          icon: "uil uil-envelope",
          name: "Email",
          desc: "Comunicate con nosotros a través de correo electrónico",
          href: "mailto: usei.lpz@ucb.edu.bo",
          title: " usei.lpz@ucb.edu.bo",
        },
        {
          icon: "uil uil-map-marker",
          name: "Ubicación",
          desc: "Av. 14 de Septiembre Nº 4807 esq. calle 2 de Obrajes",
          href: "https://maps.app.goo.gl/kYtnX1WQKetvAHqf7",
          title: "Ver en Google Maps",
        },
      ],
    };
  },
  methods: {
    validateEmail(email) {
      const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return regex.test(email);
    },

    async sendEmail() {
      if (this.formData.name === "") {
        toast.error("Te falta poner tu nombre");
        return;
      }
      if (this.formData.email === "") {
        toast.error("Te falta poner tu correo");
        return;
      }
      if (!this.validateEmail(this.formData.email)) {
        toast.error("Por favor ingresa un correo electrónico válido.");
        return;
      }

      if (this.formData.comments === "") {
        toast.error("Te falta poner el mensaje");
        return;
      }
      /** 
      
      Swal.fire({
  title: "Informacion completa",
  text: "Se te dirrecionara a tu correo electronico ${this.formData.email}",
  icon: "success",
});*/
      this.formData.subject = "CONSULTA DE USUARIO SOBRE LA INTERNSHIP";
if(this.formData.numero){
  this.formData.numero = `Número para contactarse con ${this.formData.name}:\n${this.formData.numero}`;
      
}
      const { name, email, subject, comments, numero } = this.formData;

      const remitente = "jhessikazarate@gmail.com";
      const mailtoLink = `mailto:${remitente}?subject=${encodeURIComponent(
        subject
      )}&body=${encodeURIComponent(
        `Nombre de la persona interesada: ${name}\n\n\n${comments}\n\n \n${numero}`
      )}`;
      window.location.href = mailtoLink;
      this.formData.numero="";
    },
    
  },
  components: {
    navbar,
    footers,
    switcher,
  },
};
</script>

<style lang="scss" scoped></style>
