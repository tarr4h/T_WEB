import React, {useState, createContext, useContext} from 'react';
import ReactDOM from 'react-dom';

const modalContext = createContext({});

export function ModalProvider({children}) {
    const [isModalOpen, setModalOpen] = useState(false);
    const [modalTitle, setModalTitle] = useState();
    const [modalContent, setModalContent] = useState();
    const [onClose, setOnClose] = useState(() => {});

    function openModal(title, content, callback) {
        setModalOpen(true);
        setModalTitle(title);
        setModalContent(content);
        if(callback) setOnClose(() => callback);
    }

    function closeModal() {
        if(onClose) onClose();
        setModalOpen(false);
    }

    return (
        <modalContext.Provider value={{openModal, closeModal}}>
            {children}
            {isModalOpen && ReactDOM.createPortal(
                <div className={
                    isModalOpen ? 'modal openModal' : 'modal'
                }>
                    <div className="modalWrapper">
                        <div className="modalHeader">
                            <div>
                                {modalTitle}
                            </div>
                            <div
                                onClick={closeModal}
                            >X
                            </div>
                        </div>
                        <div className="modalContent"
                        >
                            {modalContent}
                        </div>
                    </div>
                </div>
                ,
                document.body
            )}
        </modalContext.Provider>
    );
}

export function useModal() {
    return useContext(modalContext);
}