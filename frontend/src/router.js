import { createRouter, createWebHistory } from "vue-router" //роутер

import vMain from "./components/v-main.vue"
import vForm from "./components/v-form.vue"
import vProfile from "./components/v-profile.vue"
import vRegist from "./components/v-regist.vue"


const router = createRouter({
    routes: [

        {
            path: '/',
            component: vMain
        },
        {
            path: '/login',
            name: "login",
            component: vForm
        },
        {
            path: '/profile',
            name: "profile",
            component: vProfile,
        },
        {
            path: '/registration',
            name: "registration",
            component: vRegist,
        }

    ],
    history: createWebHistory()
})

export default router;