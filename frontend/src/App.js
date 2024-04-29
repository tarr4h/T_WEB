import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import Main from "./page/main/Main";
import './css/Comn.css';

function App() {



  return(
      <Router>
        <Routes>
          <Route path="/" element={<Main/>}/>
        </Routes>
      </Router>
  );
}

export default App;
