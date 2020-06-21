import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import VendorHeader from '../Vendor-Header/VendorHeader';
import axios from 'axios';
import Cookies from 'js-cookie';
import { Container, Form, Button, Alert, Col, Row, Card } from 'react-bootstrap';
import { faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Loader from '../../Utils/Loader';
const API_URL = process.env.REACT_APP_API_HOST;

class VendorOfferList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            vendorId: Cookies.get('userId'),
            vendorOffers: [],

            // Loader
            showLoader: false,

            // Alert
            showSuccessAlert: false,
            showErrorAlert: false
        }
    }

    componentDidMount() {
        this.fetchVendorOffers();
    }


    fetchVendorOffers = () => {
        this.setState({ showLoader: true });
        axios.get(API_URL + '/vendor-offer/' + this.state.vendorId)
            .then(response => {
                this.setState({ vendorOffers: response.data.vendorOffers });
                this.setState({ showLoader: false });

            }).catch(response => {
                this.handleError();
            });;;
    }

    deleteVendorOffer(vendorOfferId) {
        this.setState({ showLoader: true });
        axios.delete(API_URL + '/vendor-offer/' + vendorOfferId)
            .then(response => {

                this.fetchVendorOffers();
                this.setState({ showSuccessAlert: true });
                this.setState({ showLoader: true });
            }).catch(response => {
                this.handleError();
            });
    }

    handleError() {
        this.setState({ showLoader: false });
        this.setState({ showErrorAlert: true });
    }

    render() {
        return (
            <div className="VendorOfferList">

                {this.state.showLoader ? <Loader /> : null}

                <VendorHeader />

                <Container>
                    <h3 className="m-a text-center mt-4 mb-4">Ofertas Cadastradas</h3>
                    <hr />

                    <Alert
                        show={this.state.showSuccessAlert}
                        className="text-center"
                        variant="success"
                        onClose={() => { this.setState({ showSuccessAlert: false }) }}
                        dismissible>Oferta deletada com sucesso!</Alert>


                    <Alert
                        show={this.state.showErrorAlert}
                        className="text-center"
                        variant="danger"
                        onClose={() => { this.setState({ showErrorAlert: false }) }}
                        dismissible>Ocorreu um erro! Tente novamente mais tarde.</Alert>


                    <Row className="justify-content-center">
                        {this.state.vendorOffers.map(offer => (
                            <Col md={4} xs={8} className="text-center">
                                <Card className="mb-4">
                                    <Card.Body >
                                        <Card.Title>{offer.groupName} {offer.detailName}</Card.Title>
                                        <Card.Subtitle className="mb-2 text-muted">{offer.areaName}</Card.Subtitle>
                                        <Card.Text>
                                            {offer.serviceDescription}
                                        </Card.Text>

                                        <Row>
                                            <Col className="text-center">
                                                <Button className="btn btn-default"
                                                    variant="danger"
                                                    type="button"
                                                    onClick={() => this.deleteVendorOffer(offer.id)}>
                                                    Deletar Oferta
                                                </Button>
                                            </Col>
                                        </Row>

                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}

                    </Row>
                </Container>
            </div >
        )
    }
}



export default withRouter(VendorOfferList);