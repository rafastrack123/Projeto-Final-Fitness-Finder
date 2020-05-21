import React from 'react';
import {Switch, Route } from "react-router-dom";
import Login from '../Login/Login';
import SignUp from '../Sign-up/SignUp';
import './InitialPage.css';
import InitialPageHeader from '../Initial-Page-Header/InitialPageHeader';


const intialPage = () => {
    return (
        <div className="IntialPage">
            <InitialPageHeader/>
            <div className="auth-wrapper">
                <div className="auth-inner">
                    <Switch>
                        <Route exact path='/' component={Login} />
                        <Route path="/login" component={Login} />
                        <Route path="/sign-up" component={SignUp} />
                    </Switch>
                </div>
            </div>
        </div>
    )
}

export default intialPage;