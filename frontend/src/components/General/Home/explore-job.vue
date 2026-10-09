<template>
  <div class="container-fluid md:mt-16 mt-16">
    <div class="container">
      <div class="grid grid-cols-1">
        <div
          class="relative overflow-hidden lg:px-8 px-6 py-10 rounded-xl shadow-lg dark:shadow-gray-700"
        >
          <div class="grid md:grid-cols-12 grid-cols-1 items-center gap-[30px]">
            <div class="lg:col-span-8 md:col-span-7">
              <div class="md:text-start text-center relative z-1">
                <h3
                  class="text-2xl font-semibold text-black dark:text-white mb-4"
                >
                  Inscribe a tu empresa!
                </h3>
                <p class="text-slate-400 max-w-xl">
                  Se parte del programa y podras poner tus postulaciones de
                  pasantias, para que alumnos de la Universidad Catolica
                  Boliviana, puedan aplicar
                </p>
              </div>
            </div>
            <!--end col-->

            <div class="lg:col-span-4 md:col-span-5">
              <div class="text-end relative z-1">
         
                <div
                 @click="$keycloak.login"
                  class="btn bg-cyan-600 hover:bg-cyan-700 border-cyan-600 dark:border-cyan-600 text-white rounded-md cursor-pointer "
                  >Inscribir</div
                >
                
              </div>
            </div>
            <!--end col-->
          </div>
          <!--end grid-->

          <div class="absolute -top-5 -start-5">
            <div
              class="uil uil-envelope lg:text-[150px] text-7xl text-black/5 dark:text-white/5 ltr:-rotate-45 rtl:rotate-45"
            ></div>
          </div>

          <div class="absolute -bottom-5 -end-5">
            <div
              class="uil uil-pen lg:text-[150px] text-7xl text-black/5 dark:text-white/5 rtl:-rotate-90"
            ></div>
          </div>
        </div>
      </div>
      <!--end grid-->
    </div>
    <!--end container-->
  </div>
  <!--end container-->
</template>

<script>
export default {};
</script>

<style lang="scss" scoped></style>
