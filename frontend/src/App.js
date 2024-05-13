import {
    BrowserRouter as Router,
    Routes,
    Route, useNavigate
} from "react-router-dom";
import Main from "./page/main/Main";
import './css/Comn.css';

function App() {

  return(
      <Router>
        <Routes>
          {/*<Route path="/" element={<Main/>}/>*/}
          <Route path={process.env.PUBLIC_URL + "/"} element={<Main/>}/>
        </Routes>
      </Router>
  );
}

export default App;
