
/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createRouter, createWebHistory } from 'vue-router/auto'
import Homepage from '../components/Homepage.vue'
import Login from '../pages/Login.vue'

const routes = [
  { path: '/login', component: Login},
  { path: '/', component: Homepage},
]
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
