function InsertDataDetail({data, requestInsert}) {

    return (
        <div>
            <div className={'insertDataDetail'}>
                <div>{data.title}</div>
                <div>{data.address}</div>
                <div>{data.category}</div>
                <div className="btn"
                     onClick={() => requestInsert(data)}
                >선택
                </div>
            </div>
        </div>
    );
}

export default InsertDataDetail;