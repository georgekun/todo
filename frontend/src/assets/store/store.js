import { createStore } from 'vuex'
import axios from "axios"


const store = createStore({

    state: {
        //авторизация
        accessToken: "",
        refreshToken: "",
        auth: false,
        tasks: [],
    },
    getters: {
        get_all_task(state) { return state.tasks },
    },
    mutations: {
        set_accessToken(state, token) { state.accessToken = token },
        set_refreshToken(state, token) { state.refreshToken = token },
        set_all_task(state, data) { state.tasks = data },


        // DELETE_TASK(state, id) { state. }
    },
    actions: {
        // async get_task_from_api({ commit }) {
        //     let response = await axios.get("/task/get");
        //     let all = response.data;
        //     commit('set_all_task', all);
        // },
        get_task_from_api({ commit }) {
            axios.get("/task/get").then(response => commit('set_all_task', response.data)).catch(error => console.log(error))

        }
    }
})

export default store;