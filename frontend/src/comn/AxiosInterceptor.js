import axios from "axios";

const instance = axios.create({
    baseURL : process.env.REACT_APP_API_BASE_URL,
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

instance.interceptors.response.use(
    async (res) => {
        return res;
    },
    (error) =>{
        console.log('axios error : ', error);
        console.log('response : ', error.response);

        return error.response;
    }
)




export default instance;