/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from '@/plugins'

// Components
import App from './App.vue'

// Composables
import { createApp } from 'vue';
import store from './store';
import router from './router';

import setupInterceptors from './services/setupInterceptors';

setupInterceptors(store);

const app = createApp(App).use(store).use(router)

registerPlugins(app)

app.mount('#app')
