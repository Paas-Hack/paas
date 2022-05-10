import {
  BrowserRouter as Router,
  Route,
  Link,
  Routes
} from "react-router-dom";
import './App.scss';
import Header from './components/Header';
import Footer from './components/Footer';
import HomePage from './components/HomePage';
import RecordComponent from "./components/RecordComponent";
import ResultComponent from "./components/ResultComponent";


function App() {
  return (
    <div className="App">
      <Header></Header>
      <div className="bodyContainer">
        <div className="scrollContainer">
            <Routes>
              <Route path="/home" element={ <HomePage/> } />
              <Route path="/record" element={ <RecordComponent/> } />
              <Route path="/result" element={ <ResultComponent/> } /> 
              <Route path="/" element={ <HomePage/> } />
            </Routes>
          <Footer></Footer>
        </div>
      </div>
    </div>
  );
}

export default App;
