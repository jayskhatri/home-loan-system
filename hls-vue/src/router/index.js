
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
  { path: '/', component: Homepage},
  { path: '/login', component: Login},
]
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
