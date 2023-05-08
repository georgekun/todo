import axios from "axios";
axios.defaults.baseURL = 'http://localhost:8080/api/';


console.log(1)
    // function get_new_access_token() {
    //     console.log("Обновляю")
    //     axios.post('refresh', {}, { withCredentials: true })

//     .then((response) => {

//             if (response.status === 200) {
//                 const now = response.data.new_access;
//                 axios.defaults.headers.common['Authorization'] = `Bearer ${now}`;
//             }


//         })
//         .catch(() => (alert("обновите страницу, проблемы с сервером")))


// }

// setInterval(get_new_access_token(), 2000)



axios.interceptors.response.use(resp => resp, async(err) => {

    if (err.response.status === 401) {

        const response = await axios.post('refresh', {}, { withCredentials: true });

        if (response.status === 200) {
            const now = response.data.new_access;
            axios.defaults.headers.common['Authorization'] = `Bearer ${now}`;
            return axios(err.config);

        }
    }

    return err;
})