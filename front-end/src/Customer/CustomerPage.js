import React from 'react';
import { Switch, Route } from "react-router-dom";
import './CustomerPage.css';
import CustomerHeader from './Customer-Header/CustomerHeader';
import VendorOfferSearch from './Vendor-Offer-Search/VendorOfferSearch';

const customerPage = () => {
    return (
        <div className="CustomerPage">
            <CustomerHeader />
            <Switch>
                <Route exact path='/vendor-offer-search' component={VendorOfferSearch} />
            </Switch>
        </div>
    )
}

export default customerPage;