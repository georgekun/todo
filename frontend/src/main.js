import { createApp } from 'vue'
import App from './App.vue'
import router from "./router"
import store from "./assets/store/store"
import "./interceptors";


createApp(App).use(store).use(router).mount('#app')