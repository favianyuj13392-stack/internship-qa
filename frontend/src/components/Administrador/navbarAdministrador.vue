<template>
  <!-- Start Navbar -->
  <nav id="topnav" class="defaultscroll is-sticky">
    <div class="" :class="container">
      <router-link v-if="lightLogo" class="logo" to="/">
        <div class="block sm:hidden">
          <img
            src="../../assets/images/logo-icon-40.png"
            class="h-10 inline-block dark:hidden"
            alt=""
          />
          <img
            src="../../assets/images/logo-icon-40-white.png"
            class="h-10 hidden dark:inline-block"
            alt=""
          />
        </div>
        <div class="sm:block hidden">
          <span class="inline-block dark:hidden">
            <img
              src="../../assets/images/logo-dark.png"
              class="h-[70px] l-dark"
              alt=""
            />
            <img
              src="../../assets/images/logo-light.png"
              class="h-[70px] l-light"
              alt=""
            />
          </span>
          <img
            src="../../assets/images/logo-white.png"
            class="h-[24px] hidden dark:inline-block"
            alt=""
          />
        </div>
      </router-link>
      <!-- End Logo container-->

      <!-- Logo container-->
      <router-link v-else class="logo" to="/">
        <div class="block sm:hidden">
          <img
            src="../../assets/images/logo-icon-40.png"
            class="h-10 inline-block dark:hidden"
            alt=""
          />
          <img
            src="../../assets/images/logo-icon-40-white.png"
            class="h-10 hidden dark:inline-block"
            alt=""
          />
        </div>
        <div class="sm:block hidden">
          <img
            src="../../assets/images/logo-dark.png"
            class="h-[24px] inline-block dark:hidden"
            alt=""
          />
          <img
            src="../../assets/images/logo-white.png"
            class="h-[24px] hidden dark:inline-block"
            alt=""
          />
        </div>
      </router-link>
      <!-- End Logo container-->

      <!-- Logo container-->

      <!-- Start Mobile Toggle -->
      <div class="menu-extras" @click="handler">
        <div class="menu-item">
          <a
            class="navbar-toggle"
            id="isToggle"
            :class="toggle === false ? '' : 'open'"
          >
            <div class="lines">
              <span></span>
              <span></span>
              <span></span>
            </div>
          </a>
        </div>
      </div>
      <!-- End Mobile Toggle -->
   
          
            <ul v-if="$keycloak.authenticated" class="buy-button list-none mb-0">
              <li class="dropdown inline-block relative ps-1">
                <button
                  data-dropdown-toggle="dropdown"
                  class="dropdown-toggle items-center"
                  type="button"
                  ref="dropdownToggle"
                  @click="dropdownOpen = !dropdownOpen"
                >
                  <span
                    class="btn btn-icon rounded-full bg-cyan-600 hover:bg-cyan-700 border-cyan-600 hover:border-cyan-700 text-white"
                  >
                    <img
                      v-if="$keycloak.tokenParsed.picture"
                      :src="$keycloak.tokenParsed.picture"
                      class="rounded-full"
                      alt=""
                    />
                    <img
                      v-else
                      src="@/assets/images/user-empty.png"
                      class="rounded-full"
                      alt=""
                    />
                  </span>
                </button>
                <!-- Dropdown menu -->
                <div
                  class="dropdown-menu absolute end-0 m-0 mt-4 z-10 w-44 rounded-md overflow-hidden bg-white dark:bg-slate-900 shadow dark:shadow-gray-700"
                  v-show="dropdownOpen"
                >
                  <ul class="py-2 text-start">
                 
                   
                    <li>
                      <a
                        @click="block()"
                        class="flex items-center font-medium py-2 px-4 dark:text-white/70 hover:text-cyan-600 dark:hover:text-white"
                        ><i data-feather="lock" class="size-4 me-2"></i
                        >Bloquear</a
                      >
                    </li>
                    <li>
                      <a
                        @click="$keycloak.logout"
                        class="flex items-center font-medium py-2 px-4 dark:text-white/70 hover:text-cyan-600 dark:hover:text-white"
                        ><i data-feather="log-out" class="size-4 me-2"></i
                        >Salir</a
                      >
                    </li>

                
                  </ul>
                </div>
              </li>
              <!--end dropdown-->
              
            </ul>
        
      <!--Login button End-->

      <div id="navigation" :class="toggle === false ? 'none' : 'block'">
        <!-- Navigation Menu-->
        <ul class="navigation-menu" :class="lightNav">
          <li
            :class="activeMenu === '/administrador/dashboard' ? 'active' : ''"
          >
            <router-link to="/administrador/dashboard" class="sub-menu-item"
              >Inicio</router-link
            >
          </li>

      
          <!-- Inicio de menu de empresas-->
      
         
<!-- ARREGLANDO MENU
         
                   -->
                   <li :class="['/administrador/empresa', '/administrador/dashboard', '/administrador/solicitud/empresa',  ].includes(activeMenu) ? 'active' : ''"
                        class="has-submenu parent-menu-item"><span class="menu-arrow"></span>
                        <router-link to="" @click="submenu(menuOpen === '/administrador/empresa' ? '' : '/administrador/empresa')">Empresas</router-link>
                        <ul class="submenu"
                            :class="['/administrador/empresa', '/', '/administrador/solicitud/empresa',  ].includes(menuOpen) ? 'open' : ''">
                            <li :class="activeMenu === '/administrador/empresa' ? 'active' : ''"><router-link to="/administrador/empresa"
                                    class="sub-menu-item ">Empresas</router-link></li>
                            <li :class="activeMenu === '/administrador/solicitud/empresa' ? 'active' : ''"><router-link to="/administrador/solicitud/empresa"
                                    class="sub-menu-item">Solicitudes de usuarios</router-link></li>
                         
                        </ul>
                    </li>
                      <!-- fin de menu de empresas-->
         
                    <li :class="['/administrador/pasantia', '/administrador/dashboard', '/administrador/solicitud/pasantia',  ].includes(activeMenu) ? 'active' : ''"
                        class="has-submenu parent-menu-item"><span class="menu-arrow"></span>
                        <router-link to="" @click="submenu(menuOpen === '/administrador/pasantia' ? '' : '/administrador/pasantia')">Pasantias</router-link>
                        <ul class="submenu"
                            :class="['/administrador/pasantia', '/', '/administrador/solicitud/pasantia',  ].includes(menuOpen) ? 'open' : ''">
                            <li :class="activeMenu === '/administrador/pasantia' ? 'active' : ''"><router-link to="/administrador/pasantia"
                                    class="sub-menu-item">Pasantias</router-link></li>
                            <li :class="activeMenu === '/administrador/solicitud/pasantia' ? 'active' : ''"><router-link to="/administrador/solicitud/pasantia"
                                    class="sub-menu-item">Solicitudes de pasantias</router-link></li>
                            <li :class="activeMenu === '/administrador/pasantia/sinaplicantes' ? 'active' : ''"><router-link to="/administrador/pasantia/sinaplicantes"
                                    class="sub-menu-item">Pasantias sin aplicantes</router-link></li>
                              
                         
                        </ul>
                    </li>

                    <li :class="activeMenu === '/administrador/estudiantes' ? 'active' : ''">
                        <router-link to="/administrador/estudiantes" class="sub-menu-item">Estudiantes</router-link>
                    </li>


          <div
            v-if="!$keycloak.authenticated && !isLoading"
            @click="$keycloak.login"
            class="jhessika"
          >
            <li :class="activeMenu === '/contact' ? 'active' : ''">
              <router-link class="sub-menu-item">Iniciar Sesión</router-link>
            </li>
          </div>
        
        </ul>
        

        <!--end navigation menu-->
      </div>
      <!--end navigation-->
    </div>
    <!--end container-->
  </nav>
  <!--end header-->
  <!-- End Navbar -->
</template>

<script>
import vClickOutside from "v-click-outside";
import feather from "feather-icons";
import loader from "sass-loader";
export default {
  directives: {
    clickOutside: vClickOutside.directive,
  },
  props: {
    lightLogo: {
      type: Boolean,
      required: true,
    },
    lightNav: {
      type: String,
      required: true,
    },
    container: {
      type: String,
      required: true,
    },
  },
  computed: {
    isLoading() {
      return this.$keycloak.ready == false;
    },
  },
  data() {
    return {
      toggle: false,
      activeMenu: "",
      menu: true,
      menuOpen: "",
      dropdownOpen: false,
      prueba: false,
    };
  },
  created() {
    this.activeMenu = window.location.pathname;
    window.addEventListener("scroll", this.handleScroll);
  },
  mounted() {
    document.addEventListener("click", this.handleClickOutside);
    feather.replace();
    this.scrollToTop();

    /*
      let loader = this.$loading.show();
                  // simulate AJAX
                  setTimeout(() => {
                      loader.hide()
                  }, 5000)*/
  },
  unmounted() {
    window.removeEventListener("scroll", this.handleScroll);
    document.removeEventListener("click", this.handleClickOutside);
  },

  methods: {
    block() {
      let loader = this.$loading.show({
        container: this.fullPage ? null : this.$refs.formContainer,
        canCancel: true,
        onCancel: this.unlock(),
        width: 2,
        height: 2,
        backgroundColor: "#000000",
        opacity: 0.7,
      });
    },
    unlock() {
      let loader = this.$loading.show({
        // Optional parameters
        container: this.fullPage ? null : this.$refs.formContainer,
      });

      // simulate AJAX
      loader.hide();
      console.log("unlock");
    },

    handleLogout() {
      this.$keycloak.logout({
        redirectUri: window.location.origin,
      });
    },
    handler() {
      this.toggle = !this.toggle;
    },
    submenu(item) {
      this.menu = !this.menu;
      this.menuOpen = item;
    },
    handleScroll() {
      const navbar = document.getElementById("topnav");
      if (
        document.body.scrollTop >= 50 ||
        document.documentElement.scrollTop >= 50
      ) {
        navbar.classList.add("nav-sticky");
      } else {
        navbar.classList.remove("nav-sticky");
      }
    },
    handleClickOutside(event) {
      if (this.$refs.dropdownToggle && !this.$refs.dropdownToggle.contains(event.target)) {
        this.dropdownOpen = false;
      }
    },

    scrollToTop() {
      window.scrollTo({ top: 0, behavior: "smooth" });
    },
  },
};
</script>

<style lang="scss" scoped>
.jhessika {
  margin-top: 1rem;
  padding: 0.5rem;
  background-color: rgb(0, 148, 188);
  color: rgb(255, 255, 255);
  border-radius: 0.385rem;
  height: 3rem;
  font-family: "Plus Jakarta Sans", sans-serif;
  font-weight: 500;
}

.jhessika:hover {
  background-color: rgb(22, 102, 148);
}
.btn-icon {
  display: inline-block;
  transition: transform 0.3s ease-in-out;
}
.btn-icon:hover {
  transform: scale(1.2);
}
@media (max-width: 991px) {
  .jhessika {
    border-radius: 0;
    margin: 0;
    padding: 0.5rem 0rem 0rem 1.9rem;
  }
}
</style>
