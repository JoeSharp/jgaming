import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  preview: {
    port: 9080,
    strictPort: true,
  },
  server: {
    port: 9080,
    strictPort: true,
    host: true,
    origin: "http://0.0.0.0:9080",
  }
})
