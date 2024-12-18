import {
    BrowserRouter as Router,
    Routes,
    Route
} from "react-router-dom";
import Main from "./page/main/Main";
import './css/Comn.css';
import {ModalProvider} from "./page/modal/ModalContext";
import {AxiosInterceptorSetup} from "./comn/AxiosInterceptor";
import Modal from "./page/modal/Modal";

function App() {

  return(
      <ModalProvider>
          <AxiosInterceptorSetup/>
          <Modal/>
          <Router>
            <Routes>
              {/*<Route path="/" element={<Main/>}/>*/}
              <Route path={process.env.PUBLIC_URL + "/"} element={<Main/>}/>
            </Routes>
          </Router>
      </ModalProvider>
  );
}

export default App;
