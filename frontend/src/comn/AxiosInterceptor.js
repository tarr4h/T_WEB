import axios from "axios";


const instance = axios.create({
    // baseURL: 'http://localhost:8080',
    baseURL: 'https://tarr4h.com',
    timeout: 1000
});


instance.interceptors.request.use(
    (config) => {
        console.log('interceptor ---');
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
)


export default instance;