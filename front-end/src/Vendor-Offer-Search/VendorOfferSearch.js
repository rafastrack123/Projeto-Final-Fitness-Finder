import React, { Component } from 'react';
import './VendorOfferSearch.css';
import { Form, InputGroup, FormControl, Container, Row } from 'react-bootstrap';

class VendorOfferSearch extends Component {
    state = {
        customerId: null, // TODO: Get from login

        //arrays of service types
        serviceAreaArray: [],
        serviceGroupArray: [],
        serviceDetailArray: [],

        // selected id of service types
        serviceAreaIdSelected: null,
        serviceGroupIdSelected: null,
        serviceDetailIdSelected: null,

        // other filter
        vendorFirstName: "",
        vendorLastName: "",
        maxPrice: null,
        isHomeService: false,
        isFirstClassFree: false,
        maxDistanceInKm: null
    }

    searchVendorOffer() {
        // axios.get('/api', {
        //     params: {
        //       foo: 'bar'
        //     }
        //   });
    }

    render() {
        return (<div className="VendorOfferSearch">
            <Container>
                <Row>
                    <h1>Dani is always drunk</h1>
                </Row>
                <Row>
                    <Form>
                        <Form.Row>
                            <InputGroup>
                                <InputGroup.Prepend>
                                    <InputGroup.Text>First and last name</InputGroup.Text>
                                </InputGroup.Prepend>
                                <FormControl />
                                <FormControl />
                            </InputGroup>
                        </Form.Row>
                    </Form>
                </Row>
            </Container>
        </div>)
    }
}

export default VendorOfferSearch;