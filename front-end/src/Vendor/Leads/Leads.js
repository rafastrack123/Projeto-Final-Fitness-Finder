import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import VendorHeader from '../Vendor-Header/VendorHeader';
import { Container, Card, Row, Button, Col, Modal, Form, Alert } from 'react-bootstrap';
import axios from 'axios';
import Cookies from 'js-cookie';

class Leads extends Component {

    constructor(props) {
        super(props);

        this.state = {
            leads: [],
            selectedLead: {},

            // Modals
            showCustomerContactModal: false,
            showSendOfferModal: false,

            // vendorProposition
            vendorPropositionMessage: null,
            showVendorPropositionSentAlert: false
        }
        this.postVendorProposition = this.postVendorProposition.bind(this);

        this.hideCustomerContactModal = this.hideCustomerContactModal.bind(this);
        this.hideSendOfferModal = this.hideSendOfferModal.bind(this);
        this.hideVendorPropositionSentAlert = this.hideVendorPropositionSentAlert.bind(this);

    }

    componentDidMount() {
        this.fetchLeads();
    }

    fetchLeads() {
        var vendorId = Cookies.get('userId');

        axios.get('http://localhost:8080/lead/' + vendorId)
            .then(response => {
                console.log('fetchLeads');
                console.log(response.data);
                this.setState({ leads: response.data })
            });
    }

    showCustomerContactModal(leadId) {
        this.changeSelectedLead(leadId);

        this.setState({ showCustomerContactModal: true });
    }

    hideCustomerContactModal() {
        this.setState({ showCustomerContactModal: false });
    }

    showSendOfferModal(leadId) {
        this.changeSelectedLead(leadId);

        this.setState({ showSendOfferModal: true });
    }

    hideSendOfferModal() {
        this.setState({ showSendOfferModal: false });
    }

    hideVendorPropositionSentAlert() {
        this.setState({ showVendorPropositionSentAlert: false });
    }

    changeSelectedLead(leadId) {
        var lead = this.state.leads
            .find(lead => lead.id == leadId);

        this.setState({ selectedLead: lead });
    }

    vendorPropositionMessageChangeHandle = (event) => {
        this.setState({ vendorPropositionMessage: event.target.value });
    }

    postVendorProposition() {
        var vendorProposition = {
            vendorOfferId: this.state.selectedLead.vendorOfferId,
            customerId: this.state.selectedLead.customerId,
            message: this.state.vendorPropositionMessage
        }

        axios.post('http://localhost:8080/vendor-proposition', vendorProposition)
            .then(response => {
                this.hideSendOfferModal();
                this.setState({ showVendorPropositionSentAlert: true });
            })

    }

    render() {
        return (
            <div className="Leads">
                <VendorHeader />

                <div className="Leads-body mt-5">

                    <Container>

                        <h3 className="m-a text-center mt-2">Leads</h3>
                        <hr />

                        <Alert variant="success text-center"
                            show={this.state.showVendorPropositionSentAlert}
                            onClose={this.hideVendorPropositionSentAlert}
                            dismissible> Oferta enviada com sucesso!
                        </Alert>

                        <Row>
                            {this.state.leads.map(lead => (
                                <Col className="mb-3 m-a" xs={6} md={4}>
                                    <Card className="text-center">
                                        <Card.Header>
                                            {lead.isStrongLead ?
                                                'Interesse no serviço' :
                                                'Visualização do serviço'}
                                        </Card.Header>
                                        <Card.Body>
                                            <Card.Title >{lead.customerFirstName} {lead.customerLastName}</Card.Title>
                                            <Card.Text>
                                                <strong>Interesse:</strong> {lead.vendorOfferDescription} <br />
                                                <strong>Objetivo:</strong> {lead.customerObjective}
                                            </Card.Text>
                                            {lead.isStrongLead ?
                                                <Button
                                                    variant="success"
                                                    onClick={() => this.showCustomerContactModal(lead.id)}>Ver Contato</Button> :
                                                <Button variant="primary"
                                                    onClick={() => this.showSendOfferModal(lead.id)}>Enviar Oferta</Button>
                                            }

                                        </Card.Body>
                                        <Card.Footer className="text-muted">{lead.updatedDate}</Card.Footer>
                                    </Card>
                                </Col>
                            ))}
                        </Row>

                    </Container>
                </div>

                <Modal centered
                    show={this.state.showCustomerContactModal}
                    onHide={this.hideCustomerContactModal}
                    animation={false}>

                    <Modal.Header closeButton>
                        <Modal.Title>Contato</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <strong>  Nome: </strong> {this.state.selectedLead.customerFirstName}  <br />
                        <strong> Sobrenome: </strong>  {this.state.selectedLead.customerLastName}  <br />
                        <strong> Objetivo: </strong>  {this.state.selectedLead.customerObjective}  <br />
                        <strong> E-mail:</strong>  {this.state.selectedLead.customerEmail}  <br />
                        {/* TODO: Lógica de contato */}
                        {/* <strong>  Telefone:</strong>  this.state.selectedLead.customerLastName    <br /> */}

                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="danger" onClick={this.hideCustomerContactModal} >Fechar </Button>
                    </Modal.Footer>
                </Modal>


                <Modal centered
                    show={this.state.showSendOfferModal}
                    onHide={this.hideSendOfferModal}
                    animation={false}>

                    <Modal.Header closeButton>
                        <Modal.Title>Envie oferta para {this.state.selectedLead.customerFirstName}
                            {this.state.selectedLead.customerLastName}
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group controlId="offerText">
                                <Form.Label>Oferta:</Form.Label>
                                <Form.Control as="textarea"
                                    value={this.state.vendorPropositionMessage}
                                    onChange={this.vendorPropositionMessageChangeHandle}
                                    rows="3" />
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="danger" onClick={this.hideSendOfferModal} >Fechar </Button>
                        <Button variant="success" onClick={this.postVendorProposition} >Enviar Oferta </Button>
                    </Modal.Footer>
                </Modal>




            </div>)
    }
}


export default withRouter(Leads);