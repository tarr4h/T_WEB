import SearchList from "./SearchList";
import SearchCond from "./SearchCond";
import * as comn from "../../comn/comnFunction";
import {useRef, useState} from "react";
import Modal from "../modal/Modal";
import InsertDataModal from "./InsertDataModal";

function Search({data, mcidList, searchParam, setParam, setLatlng}){

    const scrollRef = useRef(null);
    const [addDataModalOpened, setAddDataModalOpened] = useState(false);

    const scrollTop = () => {
        if(scrollRef.current){
            scrollRef.current.scrollTop = 0;
        }
    }

    const openAddDataModal = () => {
        setAddDataModalOpened(true);
    }

    return (
        <div className={'searchWrapper'}
             ref={scrollRef}
        >
            <Modal title={'추가요청'}
                   content={<InsertDataModal/>}
                   isOpen={addDataModalOpened}
                   setIsOpen={setAddDataModalOpened}
            />
            <SearchCond setParam={setParam}
                        searchParam={searchParam}
                        mcidList={mcidList}
                        setLatlng={setLatlng}
            />
            <div className={'searchListHd'}>
                <div>
                    검색결과 : {data.length}건
                </div>
                <div>
                    <div className={'btn'}
                         onClick={openAddDataModal}
                    >추가요청</div>
                </div>
            </div>
            <SearchList dataList={data}/>
            {
                comn.isMobile() ? (
                    <div className={'scrollTop'}
                         onClick={() => {scrollTop()}}
                    >
                        맨위로
                    </div>
                ) : null
            }
        </div>
    )
}


export default Search;