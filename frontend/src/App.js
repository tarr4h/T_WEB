import {
    BrowserRouter as Router,
    Routes,
    Route
} from "react-router-dom";
import Main from "./page/main/Main";
import './css/Comn.css';
import {ModalProvider} from "./page/modal/ModalContext";

function App() {

  return(
      <ModalProvider>
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
