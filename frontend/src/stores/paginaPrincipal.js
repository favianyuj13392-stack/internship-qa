import { defineStore } from "pinia";
import axios from "axios";
import RutaApi from "@/assets/rutaApi.js";

export const usePaginaPrincipalStore = defineStore({
  id: "paginaPrincipal",
  state: () => ({}),

  actions: {
    async getRecuento() {
      try {
        const response = await axios.get(RutaApi + "/inicio/recuento");
        if (response.data.code === "200") {
          console.log("se mando" + response);
          return response;
        } else {
          console.log("se mando null");
          return null;
        }
      } catch (error) {
        console.log("salio del catch");
        console.log(error);
        return null;
      }
    },

    async getInstitucionesDestacadas() {
      try {
        const response = await axios.get(RutaApi + "/institucion/destacadas");
        if (response.data.code === "200") {
          console.log("se mando" + response);
          return response;
        } else {
          console.log("se mando null");
          return null;
        }
      } catch (error) {
        console.log("salio del catch");
        console.log(error);
        return null;
      }
    },
  },
});
