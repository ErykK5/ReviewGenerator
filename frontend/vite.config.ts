import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// The build output goes straight into backend/src/main/resources/static, so
// Spring Boot can serve the frontend as part of a single jar (see README - option 2).
export default defineConfig({
  plugins: [react()],
  build: {
    outDir: '../backend/src/main/resources/static',
    emptyOutDir: true,
  },
  server: {
    port: 5173,
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
})
