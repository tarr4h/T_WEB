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
import ErrorPage from "./page/main/ErrorPage";

function App() {

  return(
      <ModalProvider>
          <AxiosInterceptorSetup/>
          <Modal/>
          <Router>
            <Routes>
              <Route path={process.env.PUBLIC_URL + "/"} element={<Main/>}/>
              <Route path="*" element={<ErrorPage/>}/>
            </Routes>
          </Router>
      </ModalProvider>
  );
}

export default App;
