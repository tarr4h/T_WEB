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
            if(data.errorType === 'JWT_ERROR'){
                const onClose = () => window.location.reload();
                openModal(data.msg, onClose, data.title);
            } else {
                openModal(data.msg, null, data.title);
            }
            return error.response;
        }
    )
}

export const AxiosInterceptorSetup = () => {
    const {openSmallModalCenterCallback} = useModal();

    React.useEffect(() => {
        setupAxiosInterceptors(openSmallModalCenterCallback);
    }, [openSmallModalCenterCallback]);

    return null;
}


export default instance;