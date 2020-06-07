import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import VendorHeader from '../Vendor-Header/VendorHeader';
import axios from 'axios';
import Cookies from 'js-cookie';
import { Container, Form, Button, Alert, Col, Row } from 'react-bootstrap';

class VendorOfferList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            vendorId: Cookies.get('userId')
        }
    }

    componentDidMount() {

    }

    render() {
        return (
            <div className="VendorOfferList">

            </div>
        )
    }
}



export default withRouter(VendorOfferList);