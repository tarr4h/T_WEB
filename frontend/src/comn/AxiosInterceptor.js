import React from 'react';
import axios from "axios";
import {ModalProvider, useModal} from "../page/modal/ModalContext";

const instance = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL,
    timeout: 300000,
    withCredentials: true
});


export const setupAxiosInterceptors = (openModal) => {
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
            const data = error.response.data;
            openModal(data.msg, data.title);
            return error.response;
        }
    )
}

export const AxiosInterceptorSetup = () => {
    const {openSmallModalCenter} = useModal();

    React.useEffect(() => {
        setupAxiosInterceptors(openSmallModalCenter);
    }, [openSmallModalCenter]);

    return null;
}


export default instance;