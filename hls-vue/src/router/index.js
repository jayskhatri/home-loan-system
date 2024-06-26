
/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createRouter, createWebHistory } from 'vue-router/auto'
import Login from '../pages/Login.vue'
import Home from '@/components/Home.vue'
import Reset from '@/pages/Reset.vue'

const routes = [
  {
    path: '/',
    component: Home
  },
  { 
    path: '/login', 
    component: Login
  },
  {
    path: '/reset',
    component: Reset    
  }
]
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router
