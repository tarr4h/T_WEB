import React, {useState, createContext, useContext, useEffect} from 'react';
import ReactDOM from 'react-dom';
import Modal from "./Modal";

const modalContext = createContext({});

export function ModalProvider({children}) {
    const [isModalOpen, setModalOpen] = useState(false);
    const [modalTitle, setModalTitle] = useState('');
    const [modalContent, setModalContent] = useState();
    const [onClose, setOnClose] = useState(() => {});

    const [centerText, setCenterText] = useState(false);
    const [modalSize, setModalSize] = useState('');

    function openModal(content, title) {
        setModalOpen(true);
        if(title && title !== ''){
            setModalTitle(title);
        } else {
            setModalTitle('알림');
        }
        setModalSize('per_100');
        setCenterText(false);
        setModalContent(content);
    }

    function openSmallModal(content, title){
        openModal(content, title);
        setModalSize('per_30');
    }

    function openModalCallback(content, callback, title) {
        setModalOpen(true);
        if(title && title !== ''){
            setModalTitle(title);
        } else {
            setModalTitle('알림');
        }
        setModalSize('per_100');
        setCenterText(false);
        setModalContent(content);
        if(callback){
            setOnClose(() => callback);
        }
    }

    function openSmallModalCallback(content, callback, title){
        openModalCallback(content, callback, title);
        setModalSize('per_30');
    }

    function openModalCenter(content, title) {
        setModalOpen(true);
        if(title && title !== ''){
            setModalTitle(title);
        } else {
            setModalTitle('알림');
        }
        setModalSize('per_100');
        setModalContent(content);
        setCenterText(true);
    }

    function openSmallModalCenter(content, title){
        openModalCenter(content, title);
        setModalSize('per_30');
    }

    function openModalCenterCallback(content, callback, title) {
        setModalOpen(true);
        if(title && title !== ''){
            setModalTitle(title);
        } else {
            setModalTitle('알림');
        }
        setModalSize('per_100');
        setModalContent(content);
        setCenterText(true);
        if(callback) setOnClose(() => callback);
    }

    function openSmallModalCenterCallback(content, callback, title){
        openModalCenterCallback(content, callback, title);
        setModalSize('per_30');
    }

    function closeModal() {
        setModalOpen(false);
        if(onClose) {
            onClose();
            setOnClose(null);
        }
    }

    return (
        <modalContext.Provider value={{
            openModal,
            openSmallModal,
            openModalCallback,
            openSmallModalCallback,
            openModalCenter,
            openSmallModalCenter,
            openModalCenterCallback,
            openSmallModalCenterCallback,
            closeModal
        }}>
            {children}
            {isModalOpen && ReactDOM.createPortal(
                <Modal title={modalTitle}
                       content={modalContent}
                       isOpen={isModalOpen}
                       centerText={centerText}
                       size={modalSize}
                       closeModal={() => closeModal()}
                />
                ,
                document.body
            )}
        </modalContext.Provider>
    );
}

export function useModal() {
    return useContext(modalContext);
}