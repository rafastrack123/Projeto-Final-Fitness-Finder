import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import Cookies from 'js-cookie';
import CustomerHeader from '../Customer-Header/CustomerHeader';
import { Container, Row, Toast, Col, Button, Modal, Alert } from 'react-bootstrap';
import Loader from '../../Utils/Loader'

class VendorProposition extends Component {

    constructor(props) {
        super(props);

        this.state = {
            customerId: Cookies.get('userId'),
            vendorPropositions: [],

            selectedVendorPropositionId: null,

            // Modals
            showLeadSendModal: false,
            showRefusePropositionModal: false,

            // Loader 
            showLoader: false,

            // Alert
            showErrorAlert:false
        }

        this.showRefusePropositionModal = this.showRefusePropositionModal.bind(this);
        this.hideLeadSendModal = this.hideLeadSendModal.bind(this);
        this.hideRefusePropositionModal = this.hideRefusePropositionModal.bind(this);
        this.sendStrongLead = this.sendStrongLead.bind(this);
    }

    componentDidMount() {
        this.getVendorPropositions();
    }

    getVendorPropositions() {
        this.setState({ showLoader: true });

        axios.get('http://localhost:8080/vendor-proposition/' + this.state.customerId)
            .then(response => {
                console.log('VendorPropositions:')
                console.log(response.data);

                this.setState({ vendorPropositions: response.data });
                this.setState({ showLoader: false });
            }).catch(response => {
                this.handleError();
            });
    }

    sendStrongLead(vendorOfferId, vendorPropositionId) {
        this.setState({ showLoader: true });
        var customerId = this.state.customerId;

        axios.post('http://localhost:8080/lead/' + vendorOfferId + '/' + customerId, null, {
            params: {
                isStrongLead: true
            }
        }).then(response => {
            this.setState({ showLeadSendModal: true });
            this.markAsViewed(vendorPropositionId);
        }).catch(response => {
            this.handleError();
        });;
    }

    denyProposition(selectedVendorPropositionId) {
        this.markAsViewed(selectedVendorPropositionId);
        this.setState({ showRefusePropositionModal: false });
    }

    markAsViewed(vendorPropositionId) {
        this.setState({ showLoader: true });
        console.log('markAsViewed');
        axios.put('http://localhost:8080/vendor-proposition/' + vendorPropositionId)
            .then(response => {
                this.getVendorPropositions();
            }).catch(response => {
                this.handleError();
            });;
    }

    showRefusePropositionModal(vendorPropositionId) {
        this.setState({ selectedVendorPropositionId: vendorPropositionId });
        this.setState({ showRefusePropositionModal: true });
    }

    hideRefusePropositionModal() {
        this.setState({ showRefusePropositionModal: false });
    }

    hideLeadSendModal() {
        this.setState({ showLeadSendModal: false });
    }

    handleError(){
        this.setState({ showLoader: false });
        this.setState({ showErrorAlert: true });
    }

    render() {
        return (
            <div className="VendorProposition">
                {this.state.showLoader ? <Loader /> : null}
                <CustomerHeader />

                <div className="Vendor-Proposition-body mt-5">
                    <Container>
                        <h3 className="m-a text-center mt-2">Ofertas</h3>
                        <hr />

                        <Alert
                            show={this.state.showErrorAlert}
                            className="text-center"
                            variant="danger"
                            onClose={() => { this.setState({ showErrorAlert: false }) }}
                            dismissible> Ocorreu um erro! Tente mais tarde.
                        </Alert>


                        <Row>

                            {this.state.vendorPropositions.map(proposition => (
                                <Col xs={12} md={4} className="mb-2 m-a">
                                    <Toast className="h-100 text-center d-flex flex-column">
                                        <Toast.Header className="text-left">
                                            <img src="https://i.pinimg.com/originals/51/f6/fb/51f6fb256629fc755b8870c801092942.png"
                                                height="40px" className="rounded mr-2" />
                                            <strong
                                                className="mr-auto">{proposition.vendorFirstName} {proposition.vendorLastName}</strong>
                                            <span className="float-left"> {proposition.serviceDescription}</span>

                                        </Toast.Header>
                                        <Toast.Body>
                                            <p>
                                                {proposition.message}
                                            </p>
                                        </Toast.Body>
                                        <Row className="justify-content-center mt-auto">
                                            <Col xs={11}>
                                                <Button variant="danger mt-2 mb-2"
                                                    onClick={() => this.showRefusePropositionModal(proposition.id)}
                                                    block >Recusar </Button>
                                            </Col>
                                        </Row>
                                        <Row className="justify-content-center">
                                            <Col xs={11}>
                                                <Button variant="success mb-2"
                                                    onClick={() => this.sendStrongLead(proposition.vendorOfferId,
                                                        proposition.id)}
                                                    block >Aceitar </Button>
                                            </Col>
                                        </Row>
                                    </Toast>
                                </Col>)
                            )}
                        </Row>
                    </Container>


                    <Modal centered show={this.state.showLeadSendModal}
                        onHide={this.hideLeadSendModal}
                        animation={false}>
                        <Modal.Header closeButton>
                            <Modal.Title className="text-success">Sucesso!</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>Seu interesse foi enviado com sucesso ao fornecedor! <br />
                     Logo você será contatado para proceder com a negociação.</Modal.Body>
                        <Modal.Footer>
                            <Button variant="danger" onClick={this.hideLeadSendModal} >Fechar </Button>
                        </Modal.Footer>
                    </Modal>

                    <Modal centered show={this.state.showRefusePropositionModal}
                        onHide={this.hideRefusePropositionModal}
                        animation={false}>
                        <Modal.Header closeButton>
                            <Modal.Title className="text-danger">Deseja recusar esta proposta?</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>Está proposta será apagada de suas ofertas! </Modal.Body>

                        <Modal.Footer>

                            <Button variant="danger"
                                onClick={() => this.denyProposition(this.state.selectedVendorPropositionId)} >Recusar </Button>

                            <Button variant="primary"
                                onClick={this.hideRefusePropositionModal} >Fechar </Button>
                        </Modal.Footer>
                    </Modal>
                </div>
            </div>



        )
    }
}


export default withRouter(VendorProposition);