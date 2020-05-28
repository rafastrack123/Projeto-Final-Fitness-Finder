import React, { Component } from 'react';
import './VendorOfferSearch.css';
import { Form, Container, Card, Button, Col, Table, Modal } from 'react-bootstrap';
import axios from 'axios';
import CustomerHeader from '../Customer-Header/CustomerHeader';
import { withRouter } from 'react-router-dom';
import Cookies from 'js-cookie'

class VendorOfferSearch extends Component {

    constructor(props) {
        super(props);

        this.state = {
            customerId: Cookies.get('userId'),

            //arrays of service types
            serviceAreaArray: [],
            serviceGroupArray: [],
            serviceDetailArray: [],

            // selected id of service types
            serviceAreaIdSelected: "",
            serviceGroupIdSelected: "",
            serviceDetailIdSelected: "",

            // other filter
            vendorFirstName: "",
            vendorLastName: "",
            maxDistanceInKm: null,
            maxPrice: null,

            // checkboxs
            isHomeService: false,
            isFirstClassFree: false,
            isRemoteService: false,

            // search result
            vendorOffers: [],

            // modal control
            showLeadSendModal: false,
            showErrorModal: false,

        }
        
        this.searchVendorOffer = this.searchVendorOffer.bind(this)
        this.sendStrongLead = this.sendStrongLead.bind(this)

        this.hideLeadSendModal = this.hideLeadSendModal.bind(this)

    }


    componentDidMount() {
        this.fetchServiceAreaArray();
    }

    fetchServiceAreaArray = () => {
        axios.get('http://localhost:8080/service-area/find-all')
            .then(response => {
                this.setState({ serviceAreaArray: response.data });
            });
    }

    fetchServiceGroupArray = (serviceAreaId) => {
        axios.get('http://localhost:8080/service-group/' + serviceAreaId)
            .then(response => {
                this.setState({ serviceGroupArray: response.data });
            });
    }

    fetchServiceDetailArray = (serviceGroupId) => {
        axios.get('http://localhost:8080/service-detail/' + serviceGroupId)
            .then(response => {
                this.setState({ serviceDetailArray: response.data });
            });
    }

    selectServiceArea = (event) => {
        var serviceAreaId = event.target.value;

        this.setState({ serviceAreaIdSelected: serviceAreaId });
        this.fetchServiceGroupArray(serviceAreaId);

        this.clearServiceGroup();
        this.clearServiceDetail();
    }

    selectServiceGroup = (event) => {
        var serviceGroupId = event.target.value;

        this.setState({ serviceGroupIdSelected: event.target.value });
        this.fetchServiceDetailArray(serviceGroupId);

        this.clearServiceDetail();
    }

    clearServiceGroup() {
        this.setState({ serviceGroupIdSelected: "" });
        this.setState({ serviceGroupArray: [] });
    }

    clearServiceDetail() {
        this.setState({ serviceDetailIdSelected: "" });
        this.setState({ serviceDetailArray: [] });
    }

    selectServiceDetail = (event) => {
        this.setState({ serviceDetailIdSelected: event.target.value });
    }

    vendorFirstNameChangeHandle = (event) => {
        this.setState({ vendorFirstName: event.target.value });
    }

    vendorLastNameChangeHandle = (event) => {
        this.setState({ vendorLastName: event.target.value });
    }

    maxDistanceChangeHandle = (event) => {
        this.setState({ maxDistanceInKm: event.target.value });
    }

    maxPriceChangeHandle = (event) => {
        this.setState({ maxPrice: event.target.value });
    }

    isHomeServiceChangeHandle = () => {
        this.setState({ isHomeService: !this.state.isHomeService });
    }

    isFirstClassFreeChangeHandle = () => {
        this.setState({ isFirstClassFree: !this.state.isFirstClassFree });
    }

    isRemoteServiceChangeHandle = () => {
        this.setState({ isRemoteService: !this.state.isRemoteService });
    }



    searchVendorOffer() {
        console.log('searchVendorOffer:');

        axios.get('http://localhost:8080/vendor-offer', {
            params: {
                customerId: this.state.customerId,
                serviceAreaId: this.state.serviceAreaIdSelected,
                serviceGroupId: this.state.serviceGroupIdSelected,
                serviceDetailId: this.state.serviceDetailIdSelected,
                vendorFirstName: this.state.vendorFirstName,
                vendorLastName: this.state.vendorLastName,
                maxPrice: this.state.maxPrice,
                maxDistanceInKm: this.state.maxDistanceInKm,
                isHomeService: this.state.isHomeService,
                isFirstClassFree: this.state.isFirstClassFree,
                isRemoteService: this.state.isRemoteService
            }
        }).then(response => {
            console.log('searchVendorOffer Success');
            this.setState({ vendorOffers: response.data.offers });
        });
    }

    sendStrongLead(vendorOfferId) {
        var customerId = this.state.customerId;

        axios.post('http://localhost:8080/lead/' + vendorOfferId + '/' + customerId, {
            params: {
                isStrongLead: true
            }
        }).then(response => {
            this.setState({ showLeadSendModal: true });
        });
    }

    hideLeadSendModal() {
        this.setState({ showLeadSendModal: false });
    }


    render() {
        return (
            <div>
                <CustomerHeader />
                <div className="VendorOfferSearch">
                    <Container>

                        <h3 className="m-a text-center mt-2">Busca por Fornecedores</h3>
                        <hr />
                        <Form>
                            <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">
                                <Col md={6} xs={11}>
                                    <Form.Group controlId="firstName">
                                        <Form.Label>Nome:</Form.Label>
                                        <Form.Control type="text"
                                            value={this.state.vendorFirstName}
                                            onChange={this.vendorFirstNameChangeHandle}
                                            placeholder="Nome do Fornecedor" />

                                    </Form.Group>
                                </Col>
                                <Col md={6} xs={11}>
                                    <Form.Group controlId="lastName">
                                        <Form.Label>Sobrenome:</Form.Label>
                                        <Form.Control type="text"
                                            value={this.state.vendorLastName}
                                            onChange={this.vendorLastNameChangeHandle}
                                            placeholder="Sobrenome do Fornecedor" />
                                    </Form.Group>
                                </Col>
                            </Form.Row>

                            <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">
                                <Col md={6} xs={11}>
                                    <Form.Group controlId="distance">
                                        <Form.Label>Distância:</Form.Label>
                                        <Form.Control type="number"
                                            value={this.state.maxDistanceInKm}
                                            onChange={this.maxDistanceChangeHandle}
                                            placeholder="Distância Máxima KM" />

                                    </Form.Group>
                                </Col>
                                <Col md={6} xs={11}>
                                    <Form.Group controlId="maxprice">
                                        <Form.Label>Preço Máximo:</Form.Label>
                                        <Form.Control type="number"
                                            value={this.state.maxPrice}
                                            onChange={this.maxPriceChangeHandle}
                                            placeholder="Preço máximo" />
                                    </Form.Group>
                                </Col>
                            </Form.Row>


                            <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">
                                <Col>
                                    <Form.Group>
                                        <Form.Label>Area do Serviço: </Form.Label>
                                        <Form.Control as="select"
                                            onChange={this.selectServiceArea}
                                            value={this.state.serviceAreaIdSelected}>
                                            <option key="" value=""></option>
                                            {this.state.serviceAreaArray.map(serviceArea => (

                                                <option key={serviceArea.id} value={serviceArea.id}>
                                                    {serviceArea.name}
                                                </option>
                                            ))}
                                        </Form.Control>
                                    </Form.Group>
                                </Col>

                                <Col>
                                    <Form.Group>
                                        <Form.Label>Tipo de Serviço: </Form.Label>
                                        <Form.Control as="select"
                                            onChange={this.selectServiceGroup}
                                            value={this.state.serviceGroupIdSelected}>
                                            <option key="" value=""></option>
                                            {this.state.serviceGroupArray.map(serviceGroup => (

                                                <option key={serviceGroup.id} value={serviceGroup.id}>
                                                    {serviceGroup.name}
                                                </option>
                                            ))}
                                        </Form.Control>
                                    </Form.Group>
                                </Col>
                            </Form.Row>

                            <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">
                                <Col xs={8}>
                                    <Form.Group>
                                        <Form.Label>Especialização: </Form.Label>
                                        <Form.Control as="select"
                                            onChange={this.selectServiceDetail}
                                            value={this.state.serviceDetailIdSelected}>
                                            <option key="" value=""></option>
                                            {this.state.serviceDetailArray.map(serviceDetail => (
                                                <option key={serviceDetail.id} value={serviceDetail.id}>
                                                    {serviceDetail.name}
                                                </option>
                                            ))}
                                        </Form.Control>
                                    </Form.Group>
                                </Col>
                            </Form.Row>

                            <Form.Row>
                                <Col>
                                    <Form.Group id="isHomeServiceCheckbox">
                                        <Form.Check type="checkbox"
                                            value={this.state.isHomeService}
                                            onChange={this.isHomeServiceChangeHandle}
                                            label="Atendimento a domicílio" />
                                    </Form.Group>

                                </Col>
                                <Col>
                                    <Form.Group id="isRemoteServiceCheckbox">
                                        <Form.Check type="checkbox"
                                            value={this.state.isRemoteService}
                                            onChange={this.isRemoteServiceChangeHandle}
                                            label="Atendimento Remoto" />
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group id="isFirstClassFree">
                                        <Form.Check type="checkbox"
                                            value={this.state.isFirstClassFree}
                                            onChange={this.isFirstClassFreeChangeHandle}
                                            label="Primeiro atendimento gratuito" />
                                    </Form.Group>
                                </Col>
                            </Form.Row>

                            <Button className="mb-3"
                                variant="primary"
                                type="button"
                                onClick={this.searchVendorOffer}> Pesquisar</Button>
                        </Form>



                        {this.state.vendorOffers.length > 0 ?
                            <div className="mt-5">
                                <h3 className="m-a text-center mt-3 mb-3">Resultado</h3>

                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th>Fornecedor</th>
                                            <th>Tipo de serviço</th>
                                            <th>Especialização</th>
                                            <th>Preço</th>
                                            <th>Distância</th>
                                            <th>Ações</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {this.state.vendorOffers.map(vendorOffer => (
                                            <tr><td>{vendorOffer.vendorFirstName} {vendorOffer.vendorLastName}</td>
                                                <td>{vendorOffer.groupName}</td>
                                                <td>{vendorOffer.detailName}</td>
                                                <td>{vendorOffer.price}</td>
                                                <td>{vendorOffer.distance} Km</td>
                                                <td> <Button className="mr-2"
                                                    variant="primary">Detalhe</Button>

                                                    <Button variant="success"
                                                        onClick={() => this.sendStrongLead(vendorOffer.id)}>Contatar</Button>
                                                </td>
                                            </tr>
                                        ))}

                                    </tbody>
                                </Table>
                            </div>
                            : null}


                    </Container>

                    <Modal centered show={this.state.showLeadSendModal} onHide={this.hideLeadSendModal} animation={false}>
                        <Modal.Header closeButton>
                            <Modal.Title className="text-success">Sucesso!</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>Seu interesse foi enviado com sucesso ao fornecedor! <br />
                     Logo você será contatado para proceder com a negociação.</Modal.Body>
                        <Modal.Footer>
                            <Button variant="danger" onClick={this.hideLeadSendModal} >Fechar </Button>
                        </Modal.Footer>
                    </Modal>
                </div>
            </div>
        )
    }
}

export default withRouter(VendorOfferSearch);