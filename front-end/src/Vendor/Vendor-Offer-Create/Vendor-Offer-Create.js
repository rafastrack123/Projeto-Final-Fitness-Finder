import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import VendorHeader from '../Vendor-Header/VendorHeader';
import axios from 'axios';
import Cookies from 'js-cookie';
import { Container, Form, Button, Modal, Col, Row, Alert, Card } from 'react-bootstrap';
import CurrencyFormat from 'react-currency-format';
import TimeInput from 'react-time-input';
import history from '../../History';
import { faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Loader from '../../Utils/Loader';
const API_URL = process.env.REACT_APP_API_HOST;

class VendorOfferCreate extends Component {

    constructor(props) {
        super(props);

        this.state = {
            vendorId: Cookies.get('userId'),

            //arrays of service types
            serviceAreaArray: [],
            serviceGroupArray: [],
            serviceDetailArray: [],

            // selected id of service types
            serviceAreaIdSelected: "",
            serviceGroupIdSelected: "",
            serviceDetailIdSelected: "",

            // checkboxs
            isHomeService: false,
            isFirstClassFree: false,
            isRemoteService: false,

            serviceDescription: "",
            price: "",
            imageUrl: "",

            availableOffers: [],

            daysOfTheWeekArray: this.buildDaysOfTheWeekArray(),

            // new Available Schedule
            newDayOfWeekSchedule: null,
            newStartTimeSchedule: null,
            newEndTimeSchedule: null,

            // Modal
            showAvailableScheduleModal: false,

            // invalid form 
            showInvalidAvailableScheduleAlert: false,
            showInvalidVendorOfferAlert: false,

            showAvailableScheduledCreated: false,

            invalidAvailableScheduleMessage: "",
            invalidVendorOfferMessage: "",

            // Loader
            showLoader: false,

            // Alert
            showErrorAlert: false
        }


        this.postVendorOffer = this.postVendorOffer.bind(this);

        this.showAvailableScheduleModal = this.showAvailableScheduleModal.bind(this);

        this.hideAvailableScheduleModal = this.hideAvailableScheduleModal.bind(this);
        this.hideVendorOfferAlert = this.hideVendorOfferAlert.bind(this);
        this.hideAvailableScheduleAlert = this.hideVendorOfferAlert.bind(this);

        this.validateNewAvailableSchecule = this.validateNewAvailableSchecule.bind(this);
        this.validateVendorOffer = this.validateVendorOffer.bind(this);

        this.addNewAvailableSchedule = this.addNewAvailableSchedule.bind(this);

        this.buildDaysOfTheWeekArray = this.buildDaysOfTheWeekArray.bind(this);
    }

    componentDidMount() {
        this.setState({ newDayOfWeekSchedule: this.state.daysOfTheWeekArray[0] });
        this.fetchServiceAreaArray();
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

    fetchServiceAreaArray = () => {
        axios.get(API_URL + '/service-area/find-all')
            .then(response => {
                this.setState({ serviceAreaArray: response.data });
            }).catch(response => {
                this.handleError();
            });;;
    }

    fetchServiceGroupArray = (serviceAreaId) => {
        axios.get(API_URL + '/service-group/' + serviceAreaId)
            .then(response => {
                this.setState({ serviceGroupArray: response.data });
            }).catch(response => {
                this.handleError();
            });;;
    }

    fetchServiceDetailArray = (serviceGroupId) => {
        axios.get(API_URL + '/service-detail/' + serviceGroupId)
            .then(response => {
                this.setState({ serviceDetailArray: response.data });
            }).catch(response => {
                this.handleError();
            });;;
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

    isHomeServiceChangeHandle = () => {
        this.setState({ isHomeService: !this.state.isHomeService });
    }

    isFirstClassFreeChangeHandle = () => {
        this.setState({ isFirstClassFree: !this.state.isFirstClassFree });
    }

    isRemoteServiceChangeHandle = () => {
        this.setState({ isRemoteService: !this.state.isRemoteService });
    }

    serviceDescriptionChangeHandle = (event) => {
        this.setState({ serviceDescription: event.target.value });
    }

    imageUrlChangeHandle = (event) => {
        this.setState({ imageUrl: event.target.value });
    }

    priceChangeHandle = (values) => {
        var { formattedValue, value } = values;

        this.setState({ price: value });
    }

    dayOfWeekChangeHandle = (event) => {
        this.setState({ newDayOfWeekSchedule: event.target.value });
    }

    startTimeChangeHandle = (val) => {
        this.setState({ newStartTimeSchedule: val });
    }

    endTimeChangeHandle = (val) => {
        this.setState({ newEndTimeSchedule: val });
    }

    showAvailableScheduleModal() {
        this.setState({ showAvailableScheduleModal: true });
    }

    hideAvailableScheduleModal() {
        this.setState({ showAvailableScheduleModal: false });
    }

    hideAvailableScheduleAlert() {
        this.setState({ showInvalidAvailableScheduleAlert: false });
    }

    hideVendorOfferAlert() {
        this.setState({ showInvalidVendorOfferAlert: false });
    }

    postVendorOffer() {
        this.setState({ showLoader: true });
        if (this.validateVendorOffer()) {
            var vendorOffer = {
                vendorId: this.state.vendorId,

                serviceAreaId: this.state.serviceAreaIdSelected,
                serviceGroupId: this.state.serviceGroupIdSelected,
                serviceDetailId: this.state.serviceDetailIdSelected,

                serviceDescription: this.state.serviceDescription,
                price: this.state.price,
                imageUrl: this.state.imageUrl,

                isHomeService: this.state.isHomeService,
                isRemoteService: this.state.isFirstClassFree,
                firstClassFree: this.state.isRemoteService,


                availableSchedule: this.state.availableOffers
            }
            console.log('vendorOffer');
            console.log(vendorOffer);

            axios.post(API_URL + '/vendor-offer', vendorOffer)
                .then(response => {
                    history.push('/vendor-offer/list');
                    this.setState({ showLoader: false });
                }).catch(response => {
                    this.setState({ invalidVendorOfferMessage: "Erro ao salvar oferta! Tente mais tarde" })
                    this.setState({ showInvalidVendorOfferAlert: true });
                    this.setState({ showLoader: false });
                });
        } else {
            this.setState({ showInvalidVendorOfferAlert: true });
            this.setState({ showLoader: false });
        }

    }

    addNewAvailableSchedule() {

        if (this.validateNewAvailableSchecule()) {

            var newAvailableSchedule = {
                dayOfWeek: this.state.newDayOfWeekSchedule,
                startTime: this.state.newStartTimeSchedule,
                endTime: this.state.newEndTimeSchedule
            };

            var availableScheduleList = this.state.availableOffers;
            availableScheduleList.push(newAvailableSchedule);

            this.setState({ availableOffers: availableScheduleList });

            this.setState({ showAvailableScheduledCreated: true });
            this.setState({ showInvalidAvailableScheduleAlert: false });
        } else {
            this.setState({ showAvailableScheduledCreated: false });
            this.setState({ showInvalidAvailableScheduleAlert: true });
        }

    }

    validateVendorOffer() {
        if (!this.state.price) {
            this.setState({ invalidVendorOfferMessage: "Preço é obrigatório" })
            return false;

        } else if (!this.state.serviceDescription) {
            this.setState({ invalidVendorOfferMessage: "Descrição do serviço é obrigatório" })
            return false;

        } else if (!this.state.serviceAreaIdSelected) {
            this.setState({ invalidVendorOfferMessage: "Area do Serviço é obrigatório" })
            return false;

        } else if (!this.state.serviceGroupIdSelected) {
            this.setState({ invalidVendorOfferMessage: "Tipo do Serviço é obrigatório" })
            return false;

        } else if (!this.state.serviceDetailIdSelected) {
            this.setState({ invalidVendorOfferMessage: "Especialização é obrigatório" })
            return false;
        }

        return true;
    }

    validateNewAvailableSchecule() {
        var dayOfTheWeek = this.state.newDayOfWeekSchedule;
        var startTime = this.state.newStartTimeSchedule;
        var endTime = this.state.newEndTimeSchedule;

        if (!dayOfTheWeek) {
            this.setState({ invalidAvailableScheduleMessage: "Dia da semana é obrigatório" })
            return false;
        } else if (!startTime || startTime.length < 5) {
            this.setState({ invalidAvailableScheduleMessage: "Início do atentimento é obrigatório" })
            return false;
        } else if (!endTime || endTime.length < 5) {
            this.setState({ invalidAvailableScheduleMessage: "Fim do atentimento é obrigatório" })
            return false;
        } else if (startTime >= endTime) {
            this.setState({ invalidAvailableScheduleMessage: "Horário inválido" })
            return false;
        }

        return true;
    }

    removeAvailableSchedule(index) {
        var availableScheduleList = this.state.availableOffers;
        availableScheduleList.splice(index, 1);

        this.setState({ availableOffers: availableScheduleList });
    }

    handleError() {
        this.setState({ showLoader: false });
        this.setState({ showErrorAlert: true });
    }

    render() {
        return (
            <div className="VendorOfferCreate">

                {this.state.showLoader ? <Loader /> : null}

                <VendorHeader />

                <Container>

                    <h3 className="m-a text-center mt-4 mb-4">Cadatro de oferta de serviço</h3>
                    <hr />

                    <Alert
                        show={this.state.showInvalidVendorOfferAlert}
                        className="text-center"
                        variant="danger"
                        onClose={() => { this.setState({ showInvalidVendorOfferAlert: false }) }}
                        dismissible>{this.state.invalidVendorOfferMessage}
                    </Alert>

                    <Alert
                        show={this.state.showErrorAlert}
                        className="text-center"
                        variant="danger"
                        onClose={() => { this.setState({ showErrorAlert: false }) }}
                        dismissible>Ocorreu um erro! Tente novamente mais tarde.</Alert>

                    <Form>
                        <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">

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
                        </Form.Row>

                        <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">
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

                            <Col md={6} xs={11}>
                                <Form.Group>
                                    <Form.Label>Preço por atendimento:</Form.Label>
                                    <CurrencyFormat className="form-control"
                                        decimalSeparator={","}
                                        prefix={'R$'}
                                        placeholder="Insira o preço cobrado pelo atendimento"
                                        onValueChange={(values) => { this.priceChangeHandle(values) }}
                                        value={this.state.price}
                                    />
                                </Form.Group>
                            </Col>
                        </Form.Row>

                        <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">
                            <Col md={12}>
                                <Form.Group>
                                    <Form.Label>Descrição do serviço:</Form.Label>
                                    <Form.Control type="text"
                                        value={this.state.serviceDescription}
                                        onChange={this.serviceDescriptionChangeHandle}
                                        placeholder="Insira uma descrição para o serviço" />

                                </Form.Group>
                            </Col>
                        </Form.Row>

                        <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">
                            <Col md={12}>
                                <Form.Group>
                                    <Form.Label>Foto da oferta do serviço:</Form.Label>
                                    <Form.Control type="text"
                                        value={this.state.imageUrl}
                                        onChange={this.imageUrlChangeHandle}
                                        placeholder="Insira Url da foto" />
                                </Form.Group>
                            </Col>
                        </Form.Row>


                        <Form.Row className="justify-content-md-center text-left pl-2 pr-2">
                            <Col md={4} xs={8}>
                                <Form.Group id="isHomeServiceCheckbox">
                                    <Form.Check type="checkbox"
                                        value={this.state.isHomeService}
                                        onChange={this.isHomeServiceChangeHandle}
                                        label="Atendimento a domicílio" />
                                </Form.Group>

                            </Col>

                            <Col md={4} xs={8}>
                                <Form.Group id="isRemoteServiceCheckbox">
                                    <Form.Check type="checkbox"
                                        value={this.state.isRemoteService}
                                        onChange={this.isRemoteServiceChangeHandle}
                                        label="Atendimento Remoto" />
                                </Form.Group>
                            </Col>

                            <Col md={4} xs={8}>
                                <Form.Group id="isFirstClassFree">
                                    <Form.Check type="checkbox"
                                        value={this.state.isFirstClassFree}
                                        onChange={this.isFirstClassFreeChangeHandle}
                                        label="Primeiro atendimento gratuito" />
                                </Form.Group>
                            </Col>
                        </Form.Row>

                        <Row>
                            <Col className="text-center mt-2 mb-5">
                                <Button variant="success" size="md" onClick={this.showAvailableScheduleModal}>
                                    <strong>+ </strong> Adicionar horário disponível
                                 </Button>
                            </Col>

                        </Row>

                        {this.state.availableOffers.length > 0 ?

                            <Row className="justify-content-center">
                                <Col xs={12}>
                                    <h5 className="m-a text-center mt-2 mb-4"> Horarios Cadastrados</h5>
                                </Col>

                                {this.state.availableOffers.map((offer, index) => (
                                    <Col md={4} xs={8}>
                                        <Card className="mt-4 mb-4">
                                            <Card.Body className="text-left">
                                                {offer.dayOfWeek}: {offer.startTime} - {offer.endTime}

                                                <Button className="btn btn-default float-right"
                                                    variant="danger"
                                                    type="button"
                                                    onClick={() => this.removeAvailableSchedule(index)}>
                                                    <FontAwesomeIcon icon={faTrashAlt} />
                                                </Button>
                                            </Card.Body>
                                        </Card>
                                    </Col>
                                ))
                                }
                            </Row>
                            : null}

                        <Row>
                            <Col className="text-center">
                                <Button className="btn btn-default mb-5"
                                    variant="primary"
                                    type="button"
                                    onClick={this.postVendorOffer}>
                                    Cadastrar
                                </Button>
                            </Col>
                        </Row>
                    </Form>
                </Container >

                <Modal centered show={this.state.showAvailableScheduleModal}
                    onHide={this.hideAvailableScheduleModal}
                    animation={true}>
                    <Modal.Header closeButton>
                        <Modal.Title>Insira horário disponível!</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>

                        <Alert
                            show={this.state.showAvailableScheduledCreated}
                            className="text-center"
                            variant="success"
                            onClose={() => { this.setState({ showAvailableScheduledCreated: false }) }}
                            dismissible>Horário adicionado com sucesso</Alert>


                        <Alert
                            show={this.state.showInvalidAvailableScheduleAlert}
                            className="text-center"
                            variant="danger"
                            onClose={() => { this.setState({ showInvalidAvailableScheduleAlert: false }) }}
                            dismissible>{this.state.invalidAvailableScheduleMessage}</Alert>


                        <Form>
                            <Form.Row className="justify-content-md-center mt-3 text-left pl-2 pr-2">
                                <Col md={8} xs={11}>
                                    <Form.Group>
                                        <Form.Label>Dia da Semana: </Form.Label>
                                        <Form.Control as="select"
                                            onChange={this.dayOfWeekChangeHandle}
                                            value={this.state.newDayOfWeekSchedule}>
                                            {this.state.daysOfTheWeekArray.map(dayOfweek => (
                                                <option key={dayOfweek} value={dayOfweek}>
                                                    {dayOfweek}
                                                </option>
                                            ))}
                                        </Form.Control>
                                    </Form.Group>
                                </Col>

                                <Col md={8} xs={11}>
                                    <Form.Group>
                                        <Form.Label>Início do atendimento:</Form.Label>
                                        <TimeInput
                                            className='form-control'
                                            mountFocus='true'
                                            onTimeChange={this.startTimeChangeHandle}
                                            value={this.state.newStartTimeSchedule} />
                                    </Form.Group>
                                </Col>

                                <Col md={8} xs={11}>
                                    <Form.Group>
                                        <Form.Label>Fim do atendimento:</Form.Label>
                                        <TimeInput
                                            className='form-control'
                                            onTimeChange={this.endTimeChangeHandle}
                                            value={this.state.newEndTimeSchedule} />
                                    </Form.Group>
                                </Col>
                            </Form.Row>
                        </Form>
                    </Modal.Body>

                    <Modal.Footer>
                        <Button variant="danger"
                            onClick={this.hideAvailableScheduleModal}>Fechar</Button>

                        <Button variant="success"
                            onClick={this.addNewAvailableSchedule} >Salvar </Button>
                    </Modal.Footer>
                </Modal>

            </div>
        )
    }
}



export default withRouter(VendorOfferCreate);