import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import VendorHeader from '../Vendor-Header/VendorHeader';
import axios from 'axios';
import Cookies from 'js-cookie';
import { Container, Form, Button, Alert, Col, Row } from 'react-bootstrap';
import Loader from '../../Utils/Loader';

class Resume extends Component {

    constructor(props) {
        super(props);

        this.state = {
            vendorId: Cookies.get('userId'),
            resume: '',

            // Modal 
            showSuccessModal: false,

            // Loader
            showLoader: false,

            // Error Alert
            showErrorAlert: false
        }

        this.postVendorResume = this.postVendorResume.bind(this);

        this.hideSuccessModal = this.hideSuccessModal(this);
    }

    componentDidMount() {
        this.getVendorResume();
    }

    getVendorResume() {
        this.setState({ showLoader: true });
        axios.get('http://localhost:8080/vendor/resume/' + this.state.vendorId)
            .then(response => {
                this.setState({ resume: response.data.resume });
                this.setState({ showLoader: false });
            }).catch(response => {
                this.handleError();
            });
    }

    postVendorResume() {
        this.setState({ showLoader: true });
        var vendorResumeJson = {
            id: this.state.vendorId,
            resume: this.state.resume
        };

        axios.post('http://localhost:8080/vendor/resume/', vendorResumeJson)
            .then(response => {
                this.setState({ showSuccessModal: true });
                this.setState({ showLoader: false });
            }).catch(response => {
                this.handleError();
            });
    }

    hideSuccessModal() {
        this.setState({ showSuccessModal: false });
    }

    resumeChangeHandle = (event) => {
        this.setState({ resume: event.target.value });
    }

    handleError() {
        this.setState({ showLoader: false });
        this.setState({ showErrorAlert: true });
    }


    render() {
        return (
            <div className="Resume">
                {this.state.showLoader ? <Loader /> : null}
                <VendorHeader />

                <Container>

                    <h3 className="m-a text-center mt-4 mb-4">Atualize seu currículo</h3>

                    <Alert variant="success text-center"
                        show={this.state.showSuccessModal}
                        onClose={() => { this.setState({ showSuccessModal: false }) }}
                        dismissible> Currículo atualizado com sucesso!
                    </Alert>

                    <Alert
                        show={this.state.showErrorAlert}
                        className="text-center"
                        variant="danger"
                        onClose={() => { this.setState({ showErrorAlert: false }) }}
                        dismissible> Ocorreu um erro! Tente mais tarde.
                    </Alert>

                    <Form>
                        <Col xs={12}>
                            <Form.Group>
                                <Form.Control as="textarea"
                                    value={this.state.resume}
                                    onChange={this.resumeChangeHandle}
                                    rows="15" />
                            </Form.Group>
                        </Col>
                    </Form>

                    <Row>
                        <Col className="text-center">
                            <Button className="btn btn-default"
                                variant="primary"
                                type="button"
                                onClick={this.postVendorResume}>
                                Enviar</Button>
                        </Col>
                    </Row>

                </Container>
            </div>
        )
    }
}

export default withRouter(Resume);