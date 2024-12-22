import {useNavigate} from "react-router-dom";


function ErrorPage(){

    const navigate = useNavigate();

    const goToMain = () => {
        navigate('/');
    }

    return (
        <div>
            <h1>잘못된 요청입니다.</h1>
            <div onClick={goToMain}
                 style={{cursor : 'pointer'}}
            >GO TO MAIN</div>
        </div>
    )
}


export default ErrorPage;