import React, { Component } from 'react';
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
import LoginPage from "./components/LoginComponent";
import CircularProgress from '@mui/material/CircularProgress';


class App extends Component<any> { 

    state = {
      loader:false,
      intervalId: 0
    }

    componentDidMount(){ 	
     	const invId = setInterval(()=>{
            const showLoader =  sessionStorage.getItem('showLoader') || '';
            this.setState({loader: (showLoader == 'true' ? true : false)});
        },100);
        this.setState({intervalId: invId });
    }
    
    componentWillUnmount(){
      sessionStorage.clear();
      clearInterval(this.state.intervalId); 
    }

		
    render() {
      return (
      <div className="App">
        <Header></Header>
        <div className="bodyContainer">
          <div className="scrollContainer">
            { this.state.loader && <CircularProgress /> }
              <Routes>
                <Route path="/home" element={ <HomePage/> } />
                <Route path="/record" element={ <RecordComponent/> } />
                <Route path="/result" element={ <ResultComponent/> } />
                <Route path="/login" element={ <LoginPage/> } /> 
                <Route path="/" element={ <LoginPage/> } />
              </Routes>
          </div>
          <Footer></Footer>
        </div>
      </div>
    );
  }
}

export default App;
