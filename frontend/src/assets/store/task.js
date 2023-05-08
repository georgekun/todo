import { createStore } from 'vuex'


const store = createStore({

    state: {
        monday: [],
        tuesday: [],
        wednesday: [],
        thursday: [],
        friday: [],
        saturday: [],
        sunday: []
    },
    getters: {

    },
    mutations: {
        in_moday(state, response) { state.monday = response },
        in_thuesday(state, response) { state.tuesday = response },
        in_wednesday(state, response) { state.wednesday = response },
        in_thursday(state, response) { state.thursday = response },
        in_friday(state, response) { state.friday = response },
        in_saturday(state, response) { state.saturday = response },
        in_sunday(state, response) { state.sunday = response },
    },
    actions: {

    }
})

export default store;