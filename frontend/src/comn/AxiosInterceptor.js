import axios from "axios";


const instance = axios.create({
    baseURL: 'http://localhost:8080',
    // baseURL: 'https://tarr4h.com',
    timeout: 300000
});


instance.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
)

// instance.interceptors.response.use(
//     async (res) => {
//         return res;
//     },
//     (error) =>{
//         console.log('axios error : ', error);
//     }
// )




export default instance;