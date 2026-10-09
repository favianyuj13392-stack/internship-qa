<template>
  <div class="container md:mt-5 mt-16">
    <div class="grid grid-cols-1 pb-8 text-center">
      <h3
        class="mb-4 md:text-[26px] md:leading-normal text-2xl leading-normal font-semibold"
      >
        INTERNSHIP BY CATO
      </h3>

      <p class="text-slate-400 max-w-xl mx-auto">
        ¡Bienvenido a la página de Internship de la UCB La Paz! En la
        Universidad Católica Boliviana "San Pablo".
      </p>
    </div>
    <!--end grid-->

    <div class="grid md:grid-cols-2 grid-cols-1 mt-8 gap-[30px]">
      <div v-for="item in datas" :key="item" class="flex">
        <i data-feather="help-circle" class="h-8 text-cyan-600 me-3"></i>
        <div class="flex-1">
          <h5 class="mb-2 text-lg font-semibold">
            {{ item.name }}
            <span class="text-cyan-600">{{ item.name2 }}</span>
            {{ item.name3 }}
          </h5>
          <p class="text-slate-400">{{ item.desc }}</p>
        </div>
      </div>
    </div>
    <!--end grid-->
  </div>
  <!--end container-->
</template>

<script>
export default {
  data() {
    return {
      datas: [
        {
          name: "Qué es la ",
          name2: "INTERNSHIP",
          name3: "by Cato ?",
          desc: "La Internship es un programa diseñado para conectar a los estudiantes con oportunidades de pasantías en empresas asociadas, brindándoles experiencias prácticas que complementan su formación académica.",
        },
        {
          name: " Cuál es nuestra visión ?",
          desc: "La visión de Internship en la UCB La Paz es ser el puente efectivo entre nuestros estudiantes y el mundo profesional, facilitando experiencias enriquecedoras que preparen a nuestros alumnos para enfrentar los desafíos del mercado laboral global con confianza y competencia.",
        },
        {
          name: " Cuál es nuestra misión  ?",
          desc: "La misión de Internship en la UCB La Paz es conectar a nuestros estudiantes con experiencias prácticas en empresas, preparando profesionales listos para el mercado laboral.",
        },
        {
          name: " Cómo puedo",
          name2: "UNIRME",
          name3: " en el programa Internship ?",
          desc: "Para postular a las pasantías a través del programa Internship de la UCB La Paz, primero crea tu cuenta en nuestro sistema con tus datos personales y académicos.",
        },
      ],
    };
  },
};
</script>

<style lang="scss" scoped></style>
