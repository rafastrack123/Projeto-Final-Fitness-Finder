import React from 'react';
import './App.css';
import { BrowserRouter as Router } from "react-router-dom";
import InitialPage from './Initial-Page/InitialPage';



function App() {
  return (
    <Router>
      <div className="App">
        <InitialPage/>
      </div>
    </Router>
  );
}

export default App;
