import React from 'react';
import './App.css';
import { Router } from "react-router-dom";
import { Switch, Route } from "react-router-dom";
import Login from './Initial-Page/Login/Login';
import SignUp from './Initial-Page/Sign-up/SignUp';
import VendorOfferSearch from './Customer/Vendor-Offer-Search/VendorOfferSearch';
import history from './History.js'
import Leads from './Vendor/Leads/Leads';
import VendorProposition from './Customer/Vendor-Proposition/VendorProposition';
import Resume from './Vendor/Resume/Resume';
import VendorOfferCreate from './Vendor/Vendor-Offer-Create/Vendor-Offer-Create';
import VendorOfferList from './Vendor/Vendor-Offer-List/Vendor-Offer-List';


function App() {
  return (
    <Router history={history}>
      <div className="App">
        <Switch>
          <Route exact path='/' component={Login} />
          <Route path="/login" component={Login} />
          <Route path="/sign-up" component={SignUp} />
          <Route exact path='/vendor-offer-search' component={VendorOfferSearch} />
          <Route exact path='/leads' component={Leads} />
          <Route exact path='/vendor-proposition' component={VendorProposition} />
          <Route exact path='/resume' component={Resume} />
          <Route exact path='/vendor-offer/create' component={VendorOfferCreate} />
          <Route exact path='/vendor-offer/list' component={VendorOfferList} />
        </Switch>
      </div>
    </Router>
  );
}

export default App;
