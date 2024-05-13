import axios from "axios";


function Setting(){

    const dataImport = async (e) => {
        const files = e.target.files;
        let formData = new FormData();
        Array.from(files).forEach(file => {
            formData.append('file', file);
        });

        const res = await(await insertFile(formData)).json();
        console.log('data import result : ', res);
    }

    const insertFile = (formData) => {
        return new Promise((resolve) => {
            const res = fetch('/comn/importFiles', {
                method : 'POST',
                headers : {
                    enctype : 'multipart/form-data'
                },
                body : formData
            });

            resolve(res);
        })
    }

    const test = async () => {
        const test = await(await instance.get('/awsMas/healthCheck'), {params : {}});
        console.log('tes : ', test);
    }

    return (
        <div>
            <div onClick={() => {test()}}>test</div>
            <label htmlFor="importFile">DATA_IMPORT</label>
            <input type="file" id="importFile"
                   onChange={dataImport}
                   multiple={true} hidden={true}/>
        </div>
    )
}

export default Setting;