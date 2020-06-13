import React, { Component } from 'react';
import './VendorOfferSearch.css';
import { Form, Container, Button, Col, Table, Modal, Tabs, Tab, Row, Card, Image } from 'react-bootstrap';
import axios from 'axios';
import CustomerHeader from '../Customer-Header/CustomerHeader';
import { withRouter } from 'react-router-dom';
import Cookies from 'js-cookie';
import TimeInput from 'react-time-input';
import Loader from '../../Utils/Loader'


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

            // Schedule filters
            dayOfWeek: null,
            startTime: null,
            endTime: null,

            // search result
            vendorOffers: [],

            // modal control
            showLeadSendModal: false,
            showErrorModal: false,
            showDetailModal: false,

            //Tab control
            tabsCurrentKey: "offerDetail",

            selectedVendorOffer: null,

            // Loader
            showLoader: false,

            daysOfTheWeekArray: this.buildDaysOfTheWeekArray()
        }

        this.searchVendorOffer = this.searchVendorOffer.bind(this)
        this.sendStrongLead = this.sendStrongLead.bind(this)

        this.hideLeadSendModal = this.hideLeadSendModal.bind(this)
        this.hideDetailModal = this.hideDetailModal.bind(this)

    }

    buildDaysOfTheWeekArray() {
        return [
            'Segunda-Feira',
            'Terça-Feira',
            'Quarta-Feira',
            'Quinta-Feira',
            'Sexta-Feira',
            'Sábado',
            'Domingo'
        ]
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

    dayOfWeekChangeHandle = (event) => {
        this.setState({ dayOfWeek: event.target.value });
    }

    startTimeChangeHandle = (val) => {
        this.setState({ startTime: val });
    }

    endTimeChangeHandle = (val) => {
        this.setState({ endTime: val });
    }


    searchVendorOffer() {
        console.log('searchVendorOffer:');
        this.setState({ showLoader: true });

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
                isRemoteService: this.state.isRemoteService,
                dayOfWeek: this.state.dayOfWeek,
                startTime: this.state.startTime,
                endTime: this.state.endTime
            }
        }).then(response => {
            console.log('searchVendorOffer Success');
            this.setState({ vendorOffers: response.data.offers });
            this.setState({ showLoader: false });
        }).catch(err => {
            console.log(err);
            this.setState({ showLoader: false });
        });
    }

    sendStrongLead(vendorOfferId) {
        var customerId = this.state.customerId;

        axios.post('http://localhost:8080/lead/' + vendorOfferId + '/' + customerId, null, {
            params: {
                isStrongLead: 'true'
            }
        }).then(response => {
            this.setState({ showLeadSendModal: true });
        });
    }

    hideLeadSendModal() {
        this.setState({ showLeadSendModal: false });
    }

    tabsCurrentKeyHandle(key) {
        this.setState({ tabsCurrentKey: key });
    }

    selectVendorOfferDetail(vendorOffer) {
        axios.post('http://localhost:8080/lead/' + vendorOffer.id + '/' + this.state.customerId, null, {
            params: {
                isStrongLead: 'false'
            }
        });

        this.getAvailableScheduleFromVendorOffer(vendorOffer);

        this.setState({ showDetailModal: true });
    }

    getAvailableScheduleFromVendorOffer(vendorOffer) {
        axios.get('http://localhost:8080/available-schedule/vendor-offers/' + vendorOffer.id)
            .then(response => {
                console.log('getAvailableScheduleFromVendorOffer');
                console.log(response.data)
                vendorOffer.availableSchedule = response.data;
                this.setState({ selectedVendorOffer: vendorOffer });
            })
            .catch(response => console.log(response));
    }

    hideDetailModal() {
        this.setState({ tabsCurrentKey: "offerDetail" });
        this.setState({ showDetailModal: false });
    }

    render() {
        return (
            <div>
                {this.state.showLoader ? <Loader /> : null}
                <CustomerHeader />
                <div className="VendorOfferSearch">
                    <Container>
                        <h3 className="m-a text-center mt-2">Busca por Profissionais Fitness</h3>
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
                                <Col md={3} xs={11}>
                                    <Form.Group controlId="distance">
                                        <Form.Label>Distância:</Form.Label>
                                        <Form.Control type="number"
                                            value={this.state.maxDistanceInKm}
                                            onChange={this.maxDistanceChangeHandle}
                                            placeholder="Distância Máxima KM" />

                                    </Form.Group>
                                </Col>
                                <Col md={3} xs={11}>
                                    <Form.Group controlId="maxprice">
                                        <Form.Label>Preço Máximo:</Form.Label>
                                        <Form.Control type="number"
                                            value={this.state.maxPrice}
                                            onChange={this.maxPriceChangeHandle}
                                            placeholder="Preço máximo" />
                                    </Form.Group>
                                </Col>

                                <Col md={6} xs={11}>
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
                            </Form.Row>


                            <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">

                                <Col md={6} xs={11}>
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

                                <Col md={6} xs={11}>
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

                            <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">
                                <Col md={6} xs={11}>
                                    <Form.Group>
                                        <Form.Label className="float-left">Dia da Semana: </Form.Label>
                                        <Form.Control as="select"
                                            onChange={this.dayOfWeekChangeHandle}
                                            value={this.state.dayOfWeek}>
                                            <option key="" value=""></option>
                                            {this.state.daysOfTheWeekArray.map(dayOfweek => (
                                                <option key={dayOfweek} value={dayOfweek}>
                                                    {dayOfweek}
                                                </option>
                                            ))}
                                        </Form.Control>
                                    </Form.Group>
                                </Col>

                                <Col md={3} xs={6}>
                                    <Form.Group>
                                        <Form.Label>Início do atendimento:</Form.Label>
                                        <TimeInput
                                            className='form-control'
                                            onTimeChange={this.startTimeChangeHandle}
                                            value={this.state.startTime} />
                                    </Form.Group>
                                </Col>

                                <Col md={3} xs={5}>
                                    <Form.Group>
                                        <Form.Label>Fim do atendimento:</Form.Label>
                                        <TimeInput
                                            className='form-control'
                                            onTimeChange={this.endTimeChangeHandle}
                                            value={this.state.endTime} />
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
                                <Row className="text-left">
                                    {this.state.vendorOffers.map(vendorOffer => (
                                        <Col md={6} xs={12}>
                                            <Card className="mb-3">
                                                <Card.Body>
                                                    <Row>

                                                        <Col xs={12} className="mb-2 text-center m-a">
                                                            <Image style={{ height: '300px' }}
                                                                src="https://sportsregras.com/wp-content/uploads/2016/04/boxe-combate-1024x662.jpg"
                                                                thumbnail />
                                                        </Col>

                                                        <Col xs={12}>

                                                            <Col>
                                                                <Card.Title>{vendorOffer.groupName} {vendorOffer.detailName}</Card.Title>
                                                            </Col>

                                                            <Col>
                                                                <Card.Subtitle className="mb-2 text-muted">{vendorOffer.vendorFirstName} {vendorOffer.vendorLastName}</Card.Subtitle>
                                                            </Col>

                                                            <Col>
                                                                <Card.Text>
                                                                    {vendorOffer.serviceDescription}
                                                                </Card.Text>
                                                            </Col>

                                                        </Col>
                                                        <Col xs={12}>
                                                            <Button className="mt-2 mr-2 mb-4"
                                                                onClick={() => this.selectVendorOfferDetail(vendorOffer)}
                                                                variant="primary btn-block">Detalhe</Button>

                                                            <Button onClick={() => this.sendStrongLead(vendorOffer.id)}
                                                                variant="success btn-block">Contatar</Button>
                                                        </Col>
                                                    </Row>
                                                </Card.Body>
                                            </Card>
                                        </Col>
                                    ))}
                                </Row>
                            </div>
                            : null}

                    </Container>

                    <Modal centered show={this.state.showLeadSendModal} onHide={this.hideLeadSendModal} animation={false}>
                        <Modal.Header closeButton>
                            <Modal.Title className="text-success">Sucesso!</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>Seu interesse foi enviado com sucesso ao fornecedor! <br />
                                    Logo você será contatado para proceder com a negociação.
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="danger" onClick={this.hideLeadSendModal} >Fechar </Button>
                        </Modal.Footer>
                    </Modal>


                    <Modal id="vendor-offer-modal-dialog" centered show={true} show={this.state.showDetailModal} animation={true}>
                        <Container>
                            <Modal.Header>
                                <Modal.Title>Detalhes da Oferta</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <Tabs className="mt-2 ml-2"
                                    activeKey={this.state.tabsCurrentKey}
                                    onSelect={(k) => this.tabsCurrentKeyHandle(k)}>

                                    <Tab eventKey="offerDetail" title="Detalhe" >

                                        {this.state.selectedVendorOffer ?
                                            <Row className="mt-2 ml-1">
                                                <Col>
                                                    <p >
                                                        <strong>Área:</strong> {this.state.selectedVendorOffer.areaName}
                                                        <br />
                                                        <strong>Serviço:</strong> {this.state.selectedVendorOffer.groupName + ' ' + this.state.selectedVendorOffer.detailName}
                                                        <br />
                                                        <strong>Descrição:</strong> {this.state.selectedVendorOffer.serviceDescription}
                                                        <br />
                                                        <strong>Preço:</strong> {this.state.selectedVendorOffer.price.toLocaleString("pt-BR", { minimumFractionDigits: 2, style: 'currency', currency: 'BRL' })}
                                                        <br />
                                                        <strong>Distância:</strong> {this.state.selectedVendorOffer.distance} Km
                                                        <br />
                                                        <strong>Profissional:</strong> {this.state.selectedVendorOffer.vendorFirstName + ' ' + this.state.selectedVendorOffer.vendorLastName}
                                                        <br />
                                                        <strong>Atendimento a Domicílio:</strong> {this.state.selectedVendorOffer.isHomeService ? <span className="text-success">Sim</span> : <span className="text-danger">Não</span>}
                                                        <br />
                                                        <strong>Atendimento Remoto:</strong> {this.state.selectedVendorOffer.isRemoteService ? <span className="text-success">Sim</span> : <span className="text-danger">Não</span>}
                                                        <br />
                                                        <strong>Primeiro Atendimento Grátis:</strong> {this.state.selectedVendorOffer.firstClassFree ? <span className="text-success">Sim</span> : <span className="text-danger">Não</span>}
                                                        <br />
                                                    </p>
                                                </Col>
                                            </Row>
                                            : null}

                                    </Tab>

                                    <Tab eventKey="schedule" title="Horários" >
                                        {this.state.selectedVendorOffer ?
                                            <Row className="mt-2 ml-1 mb-2" id="vendor-offer-modal-body">
                                                {/* {this.state.vendorOffers.map(vendorOffer => ( */}
                                                {this.state.selectedVendorOffer.availableSchedule.map(
                                                    availableSchedule => (
                                                        <Col xs={12} className="mb-1">
                                                            <Card>
                                                                <Card.Body className="text-left">
                                                                    {availableSchedule.dayOfWeek}: {availableSchedule.startTime} - {availableSchedule.endTime}
                                                                </Card.Body>
                                                            </Card>
                                                        </Col>
                                                    ))}
                                            </Row>
                                            : null}
                                    </Tab>
                                    {this.state.selectedVendorOffer && this.state.selectedVendorOffer.resume ?
                                        <Tab eventKey="resume" title="Currículo">

                                            <Row>
                                                <Col className="mr-2 ml-2">
                                                    <p className="mt-2">{this.state.selectedVendorOffer.resume}</p>
                                                </Col>
                                            </Row>

                                        </Tab>
                                        : null}
                                </Tabs>
                            </Modal.Body>

                            <Modal.Footer>
                                <Button className="btn btn-default"
                                    variant="danger"
                                    type="button"
                                    onClick={this.hideDetailModal}>Fechar</Button>
                            </Modal.Footer>

                        </Container>
                    </Modal>
                </div>
            </div >
        )
    }
}

export default withRouter(VendorOfferSearch);