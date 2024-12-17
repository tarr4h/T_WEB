import '../../css/Comn.css';
import React from "react";

function Modal({title, content, isOpen, centerText, size, closeModal}){

    return (
        <div className={
            isOpen ? "modal openModal" : "modal"
        }>
            <div className={`modalWrapper ${size}`}>
                <div className="modalHeader">
                    <div>
                        {title}
                    </div>
                    <div
                        onClick={closeModal}
                    >X
                    </div>
                </div>
                <div className={
                    centerText ? 'modalContent text_center' : 'modalContent'
                }
                >
                    {content}
                </div>
            </div>
        </div>
    )
}

export default Modal;