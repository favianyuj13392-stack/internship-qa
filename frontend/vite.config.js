import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => ({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  esbuild: {
    sourcemap: false,
    drop: mode === 'production' || process.env.NODE_ENV === 'production' ? ['console', 'debugger'] : [],
  },
  css: {
    devSourcemap: false,
  },
}));

