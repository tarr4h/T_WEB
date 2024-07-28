import instance from "../../comn/AxiosInterceptor";


function InsertDataDetail({data, setIsOpen}) {

    const requestInsert = async () => {
        const result = (await instance.post('/comn/requestNewData', data)).data;
        if(result === 0){
            alert('이미 존재하는 업체입니다.');
        } else {
            alert('요청되었습니다.\n검토 후 추가예정입니다.');
            setIsOpen(false);
        }
    }

    return (
        <div>
            <div className={'insertDataDetail'}>
                <div>{data.title}</div>
                <div>{data.address}</div>
                <div>{data.category}</div>
                <div className="btn"
                     onClick={requestInsert}
                >선택
                </div>
            </div>
        </div>
    );
}

export default InsertDataDetail;