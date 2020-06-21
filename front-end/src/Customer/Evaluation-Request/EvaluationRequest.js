import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Container, Card, Row, Button, Col, Modal, Form, Alert } from 'react-bootstrap';
import axios from 'axios';
import Cookies from 'js-cookie';
import CustomerHeader from '../Customer-Header/CustomerHeader';
import StarRatings from 'react-star-ratings';
import Loader from '../../Utils/Loader';

class EvaluationRequest extends Component {

    constructor(props) {
        super(props);

        this.state = {
            customerId: Cookies.get('userId'),

            evaluationRequestArray: [],

            selectedEvaluationRequest: null,

            rating: 0,
            feedback: '',

            // Alert
            showSuccessAlert: false,
            successAlertMessage: '',
            showErrorAlert: false,

            // Modal
            showEvaluationModal: false,
            modalAlert: false,

            // Loader
            showLoader: false
        }

        this.hideEvaluationModal = this.hideEvaluationModal.bind(this);
        this.ratingChangeHandle = this.ratingChangeHandle.bind(this);
        this.sendEvaluation = this.sendEvaluation.bind(this);
        this.feedbackChangeHandle = this.feedbackChangeHandle.bind(this);
    }

    componentDidMount() {
        this.getEvaluationRequests(true);
    }

    getEvaluationRequests(showEmptyMessage) {
        this.setState({ showLoader: true });
        axios.get('http://localhost:8080/evaluation-request/customers/' + this.state.customerId)
            .then(response => {

                this.setState({ evaluationRequestArray: response.data });
                if (response.data.length === 0 && showEmptyMessage) {
                    this.showSuccessAlert('Nenhum pedido de avaliação foi encontrado!');
                }
                this.setState({ showLoader: false });
            }).catch(response => {
                this.handleError();
            });;
    }

    showSuccessAlert(message) {
        this.setState({ showSuccessAlert: true })
        this.setState({ successAlertMessage: message })
    }

    showEvaluationModal(selectedEvaluationRequest) {
        this.setState({ selectedEvaluationRequest: selectedEvaluationRequest });
        this.setState({ showEvaluationModal: true });
    }

    hideEvaluationModal() {
        this.resetEvaluationValues();

        this.setState({ showEvaluationModal: false });
    }

    ratingChangeHandle(newRating) {
        this.setState({ rating: newRating });
    }

    feedbackChangeHandle(event) {
        this.setState({ feedback: event.target.value });
    }

    resetEvaluationValues() {
        this.setState({ rating: 0 });
        this.setState({ feedback: '' });
    }

    deleteEvaluationRequest(evaluationRequestId) {
        this.setState({ showLoader: true });
        console.log('deleteEvaluationRequest');
        axios.delete('http://localhost:8080/evaluation-request/' + evaluationRequestId)
            .then(response => {
                this.showSuccessAlert("Pedido de avaliação deleteado!");
                this.getEvaluationRequests(false);
                this.setState({ showLoader: false });
            }).catch(response => {
                this.handleError();
            });
    }

    sendEvaluation() {
        this.hideEvaluationModal();
        this.setState({ showLoader: true });
        if (this.state.rating < 1) {
            this.setState({ modalAlert: true });
            return;
        }

        var evaluation = {
            evaluationRequestId: this.state.selectedEvaluationRequest.id,
            vendorId: this.state.selectedEvaluationRequest.vendorId,
            customerId: this.state.customerId,
            rating: this.state.rating,
            feedback: this.state.feedback
        };
        console.log('sendEvaluation');
        console.log(evaluation);

        axios.post('http://localhost:8080/evaluation', evaluation)
            .then(response => {

                this.showSuccessAlert('Avaliação enviada com sucesso');
                this.getEvaluationRequests(false);
                this.setState({ showLoader: false });
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
            <div className="EvaluationRequest">

                {this.state.showLoader ? <Loader /> : null}

                <CustomerHeader />

                <Container>

                    <h3 className="m-a text-center mt-4 mb-4">Avaliações pendentes</h3>
                    <hr />

                    <Alert
                        show={this.state.showSuccessAlert}
                        className="text-center"
                        variant="success"
                        onClose={() => { this.setState({ showSuccessAlert: false }) }}
                        dismissible>{this.state.successAlertMessage}
                    </Alert>

                    <Alert
                        show={this.state.showErrorAlert}
                        className="text-center"
                        variant="danger"
                        onClose={() => { this.setState({ showErrorAlert: false }) }}
                        dismissible> Ocorreu um erro! Tente mais tarde.
                    </Alert>


                    <Row>
                        {this.state.evaluationRequestArray.map(evaluationRequest => (
                            <Col xs={6} md={3} className="text-center m-a">
                                <Card>
                                    <Card.Img variant="top"
                                        src="https://sportsregras.com/wp-content/uploads/2016/04/boxe-combate-1024x662.jpg" />
                                    <Card.Body>

                                        <Card.Title>{evaluationRequest.vendorFirstName + ' ' + evaluationRequest.vendorLastName}</Card.Title>
                                        <Card.Subtitle className="mb-2  mt-3 text-muted">{evaluationRequest.groupName + ' ' + evaluationRequest.detailName}</Card.Subtitle>
                                        <Row>
                                            <Col>

                                                <Button variant="success"
                                                    onClick={() => this.showEvaluationModal(evaluationRequest)}>Avaliar</Button>
                                            </Col>
                                            <Col>
                                                <Button variant="danger"
                                                    onClick={() => this.deleteEvaluationRequest(evaluationRequest.id)}>Recusar</Button>
                                            </Col>
                                        </Row>
                                    </Card.Body>
                                </Card>

                            </Col>
                        ))}
                    </Row>
                </Container>

                <Modal centered show={this.state.showEvaluationModal} onHide={this.hideEvaluationModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Insira a avaliação do serviço:</Modal.Title>
                    </Modal.Header>

                    <Alert
                        show={this.state.modalAlert}
                        className="text-center"
                        variant="warning"
                        onClose={() => { this.setState({ modalAlert: false }) }}
                        dismissible>Nota é obrigatória!
                    </Alert>

                    <Modal.Body>
                        <Form>
                            <Form.Row className="mb-3">
                                <Col className="m-a text-center">
                                    <StarRatings
                                        rating={this.state.rating}
                                        starRatedColor="yellow"
                                        changeRating={this.ratingChangeHandle}
                                        numberOfStars={5}
                                        name='rating'
                                        starDimension='40px'
                                    />
                                </Col>
                            </Form.Row>
                            <Form.Row>
                                <Col>
                                    <Form.Group>
                                        <Form.Label>Feedback:</Form.Label>
                                        <Form.Control as="textarea"
                                            rows="3"
                                            onChange={this.feedbackChangeHandle} />
                                    </Form.Group>
                                </Col>
                            </Form.Row>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="success" onClick={this.sendEvaluation}>Enviar </Button>
                        <Button variant="danger" onClick={this.hideEvaluationModal}>Fechar </Button>
                    </Modal.Footer>
                </Modal>

            </div>
        )
    }
}


export default withRouter(EvaluationRequest);