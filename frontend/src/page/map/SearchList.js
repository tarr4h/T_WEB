import '../../css/Search.css';
import Summary from "./Summary";
import {useState} from "react";
import Modal from "../modal/Modal";
import InsertDataModal from "./InsertDataModal";

function SearchList({dataList}) {

    const [addDataModalOpened, setAddDataModalOpened] = useState(false);

    const openAddDataModal = () => {
        setAddDataModalOpened(true);
    }

    return (
        <div className={'searchList'}>
            <Modal title={'추가요청'}
                   content={<InsertDataModal isOpen={addDataModalOpened}
                                             setIsOpen={setAddDataModalOpened}
                            />}
                   isOpen={addDataModalOpened}
                   setIsOpen={setAddDataModalOpened}
            />
            {
                dataList.length > 0 ?
                    dataList.map((item, index) => (
                        <Summary key={index}
                            data={item}/>
                    ))
                    :
                    <div className={'nrAskRequest'}>
                        <span>검색결과가 없습니다. 추가하시겠습니까?</span>
                        <div className={'btn'}
                             onClick={openAddDataModal}
                        >추가요청</div>
                    </div>
            }
        </div>
    )
}


export default SearchList;