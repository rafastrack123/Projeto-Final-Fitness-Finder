import React from 'react';
import './App.css';
import { BrowserRouter as Router } from "react-router-dom";
import InitialPage from './Initial-Page/InitialPage';
import CustomerPage from './Customer/CustomerPage';


function App() {
  return (
    <Router>
      <div className="App">
        <InitialPage/>
        {/* <CustomerPage/> */}
      </div>
    </Router>
  );
}

export default App;
