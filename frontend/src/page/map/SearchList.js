import '../../css/Search.css';
import Summary from "./Summary";
import InsertDataModal from "./InsertDataModal";
import {useModal} from "../modal/ModalContext";

function SearchList({dataList}) {

    const {openModal} = useModal();

    const openAddDataModal = () => {
        openModal(<InsertDataModal/>, '추가요청');
    }

    return (
        <div className={'searchList'}>
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