import React from 'react';
import './App.css';
import { BrowserRouter as Router } from "react-router-dom";
import InitialPage from './Initial-Page/InitialPage';
import VendorOfferSearch from './Vendor-Offer-Search/VendorOfferSearch'


function App() {
  return (
    <Router>
      <div className="App">
        <InitialPage/>
        {/* <VendorOfferSearch/> */}
      </div>
    </Router>
  );
}

export default App;
