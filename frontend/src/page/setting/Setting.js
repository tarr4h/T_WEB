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

    const searchTest = async () => {
        const result = await(await axios.get('/comn/getNvSearch', {params : {searchTxt : '평릉한우설렁탕'}})).data;
        console.log('searchTest result : ', result);
    }

    return (
        <div>
            <label htmlFor="importFile">DATA_IMPORT</label>
            <input type="file" id="importFile"
                   onChange={dataImport}
                   multiple={true} hidden={true}/>

            <div onClick={() => {void searchTest()}}>searchTest</div>
        </div>
    )
}

export default Setting;