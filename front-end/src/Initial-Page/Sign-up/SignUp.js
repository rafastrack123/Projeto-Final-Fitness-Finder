import React, { Component } from 'react';
import './SignUp.css';
import { Button, FormGroup, FormControl, Form, Alert } from "react-bootstrap";
import axios from 'axios';
import InitialPageHeader from '../Initial-Page-Header/InitialPageHeader';
//import GooglePlacesAutocomplete from 'react-google-places-autocomplete';
// If you want to use the provided css
import 'react-google-places-autocomplete/dist/index.min.css';
import CryptoJS from 'crypto-js/';
import history from '../../History';
import Loader from '../../Utils/Loader'
const API_URL = process.env.REACT_APP_API_HOST;

class SignUp extends Component {

    state = {
        firstName: "",
        lastName: "",
        email: "",
        cellphone: "",
        password: "",
        address: "",
        objectives: [],
        selectedObjective: "",
        preferredContact: "WhatsApp",
        facebook: "",
        instagram: "",
        userType: "Customer",

        // Alert
        showErrorAlert: false,
        errorMessage: '',

        // Loader
        showLoader: false
    }

    componentDidMount() {
        this.fetchObjectives();
    }

    fetchObjectives = () => {
        this.setState({ showLoader: true });
        axios.get(API_URL + '/objective/findAll')
            .then(response => {
                this.setState({ selectedObjective: response.data[0].id });
                this.setState({ objectives: response.data });
                this.setState({ showLoader: false });
            }).catch(response => {
                this.handleError();
            });;
    }

    firstNameChangeHandle = (event) => {
        this.setState({ firstName: event.target.value });
    }

    lastNameChangeHandle = (event) => {
        this.setState({ lastName: event.target.value });
    }

    emailChangeHandle = (event) => {
        this.setState({ email: event.target.value });
    }

    cellphoneChangeHandle = (event) => {
        this.setState({ cellphone: event.target.value });
    }

    passwordChangeHandle = (event) => {
        this.setState({ password: event.target.value });
    }

    addressChangeHandle = (event) => {
        this.setState({ address: event.target.value });
    }

    faceboookChangeHandle = (event) => {
        this.setState({ facebook: event.target.value });
    }

    instagramChangeHandle = (event) => {
        this.setState({ instagram: event.target.value });
    }

    preferredContactHandle = (event) => {
        this.setState({ preferredContact: event.target.value });
    }

    selectObjective = (event) => {
        this.setState({ selectedObjective: event.target.value });
    }

    selectUserType = (event) => {
        this.setState({ userType: event.target.value });
    }

    signUp = () => {
        this.setState({ showLoader: true });
        console.log("signUp:");

        if (this.formIsValid()) {


            var user = {
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                email: this.state.email,
                password: CryptoJS.MD5(this.state.password).toString(),
                address: {
                    fullAddress: this.state.address
                },
                contactInfo: {
                    cellphone: this.state.cellphone,
                    facebook: this.state.facebook,
                    instagram: this.state.instagram
                },
                preferredContact: this.state.preferredContact
            };

            if (this.state.userType === "Customer") {
                this.postCustomer(user);
            } else {
                this.postVendor(user);
            }
        }
        this.setState({ showLoader: false });
    }

    formIsValid() {
        if (!this.state.firstName) {
            this.setState({ showErrorAlert: true });
            this.setState({ errorMessage: "Nome é obrigatório" });
            window.scrollTo(0, 0);
            return false;

        } else if (!this.state.lastName) {
            this.setState({ showErrorAlert: true });
            this.setState({ errorMessage: "Sobrenome é obrigatório" });
            window.scrollTo(0, 0);
            return false;

        } else if (!this.state.email) {
            this.setState({ showErrorAlert: true });
            this.setState({ errorMessage: "E-mail é obrigatório" });
            window.scrollTo(0, 0);
            return false;

        } else if (!this.state.password) {
            this.setState({ showErrorAlert: true });
            this.setState({ errorMessage: "Senha é obrigatório" });
            window.scrollTo(0, 0);
            return false;

        } else if (!this.state.cellphone) {
            this.setState({ showErrorAlert: true });
            this.setState({ errorMessage: "Celular é obrigatório" });
            window.scrollTo(0, 0);
            return false;
        } else if (!this.state.address) {
            this.setState({ showErrorAlert: true });
            this.setState({ errorMessage: "Endereço é obrigatório" });
            window.scrollTo(0, 0);
            return false;

        }

        return true;
    }

    postCustomer = (customer) => {
        console.log('Post Customer');
        console.log('objective:');
        console.log(this.state.selectedObjective);

        customer.objective = this.state.objectives
            .find(objective => objective.id == this.state.selectedObjective)

        axios.post(API_URL + '/customer', customer)
            .then(response => {
                console.log('Post Customer Success');
                console.log(response.data);
                history.push('/login');
            }).catch(response => {
                this.handleError();
            });
    }


    postVendor = (vendor) => {
        console.log('Post Vendor');

        axios.post(API_URL + '/vendor', vendor).then(response => {
            console.log('Post Vendor Success');
            console.log(response.data);
            history.push('/login');
        }).catch(response => {
            this.handleError();
        });
    }


    handleError() {
        this.setState({ showLoader: false });
        this.setState({ showErrorAlert: true });
        this.setState({ errorMessage: "Ocorreu um erro! Tente mais tarde" });
        window.scrollTo(0, 0);
    }

    render() {

        return (
            <div>
                {this.state.showLoader ? <Loader /> : null}

                <InitialPageHeader />

                <div className="SignUp">
                    <h3 id="sign-up-header">Cadastro</h3>


                    <form id="sign-up-from">

                        <Alert
                            show={this.state.showErrorAlert}
                            className="text-center"
                            variant="danger"
                            onClose={() => { this.setState({ showErrorAlert: false }) }}
                            dismissible> {this.state.errorMessage}
                        </Alert>
                        {/* Remove to component */}

                        <Form.Group>
                            <Form.Label>Modalidade</Form.Label>
                            <Form.Control as="select"
                                onChange={this.selectUserType}
                                value={this.state.userType} >
                                <option value="Customer">Cliente</option>
                                <option value="Vendor">Fornecedor</option>

                            </Form.Control>
                        </Form.Group>

                        <FormGroup controlId="firstName" bsSize="large">
                            <Form.Label>Nome</Form.Label>
                            <FormControl
                                type="text"
                                value={this.state.firstName}
                                onChange={this.firstNameChangeHandle}
                                placeholder="Insira nome" />
                        </FormGroup>

                        <FormGroup controlId="LastName" bsSize="large">
                            <Form.Label>Sobrenome</Form.Label>
                            <FormControl
                                type="text"
                                value={this.state.lastName}
                                onChange={this.lastNameChangeHandle}
                                placeholder="Insira sobrenome" />
                        </FormGroup>

                        <FormGroup controlId="email" bsSize="large">
                            <Form.Label>Email</Form.Label>
                            <FormControl
                                type="email"
                                value={this.state.email}
                                onChange={this.emailChangeHandle}
                                placeholder="Insira E-mail" />
                        </FormGroup>

                        <FormGroup controlId="password" bsSize="large">
                            <Form.Label>Senha</Form.Label>
                            <FormControl
                                type="password"
                                value={this.state.password}
                                onChange={this.passwordChangeHandle}
                                placeholder="Insira senha" />
                        </FormGroup>

                        <FormGroup controlId="cellphone" bsSize="large">
                            <Form.Label>Celular</Form.Label>
                            <FormControl
                                type="tel"
                                value={this.state.cellphone}
                                onChange={this.cellphoneChangeHandle}
                                placeholder="Insira celular" />
                        </FormGroup>

                        {this.state.userType === "Customer" ?
                            <FormGroup controlId="facebook" bsSize="large">
                                <Form.Label>Facebook</Form.Label>
                                <FormControl
                                    type="tel"
                                    value={this.state.facebook}
                                    onChange={this.faceboookChangeHandle}
                                    placeholder="Insira do facebook" />
                            </FormGroup>
                            : null
                        }

                        {this.state.userType === "Customer" ?
                            <FormGroup controlId="instagram" bsSize="large">
                                <Form.Label>Instagram</Form.Label>
                                <FormControl
                                    type="tel"
                                    value={this.state.instagram}
                                    onChange={this.instagramChangeHandle}
                                    placeholder="Insira usuário do instagram" />
                            </FormGroup>
                            : null
                        }


                        {this.state.userType === "Customer" ?
                            <Form.Group>
                                <Form.Label>Forma preferida de contato</Form.Label>
                                <Form.Control as="select"
                                    onChange={this.preferredContactHandle}
                                    value={this.state.preferredContact}>
                                    <option key="WhatsApp" value="WhatsApp">WhatsApp</option>
                                    <option key="Facebook" value="Facebook">Facebook</option>
                                    <option key="Instagram" value="Instagram">Instagram</option>
                                    <option key="SMS" value="SMS">SMS</option>
                                </Form.Control>
                            </Form.Group>
                            : null
                        }


                        <FormGroup controlId="text" bsSize="large">
                            <Form.Label>Endereço</Form.Label>
                            <FormControl
                                type="text"
                                value={this.state.address}
                                onChange={this.addressChangeHandle}
                                placeholder="Insira senha" />
                            {/* <GooglePlacesAutocomplete
                                onSelect={console.log}
                            /> */}
                        </FormGroup>

                        {this.state.userType === "Customer" ?
                            // Remove to component
                            <Form.Group>
                                <Form.Label>Objetivo</Form.Label>
                                <Form.Control as="select"
                                    onChange={this.selectObjective}
                                    value={this.state.selectedObjective}>
                                    {this.state.objectives.map(objective => (
                                        <option key={objective.id} value={objective.id}>
                                            {objective.name}
                                        </option>
                                    ))}
                                </Form.Control>
                            </Form.Group>
                            : null
                        }
                        <Button block variant="btn btn-primary btn-block mb-2" bsSize="large"
                            onClick={() => this.signUp()} type="button">Inscrever-se</Button>

                        <p className="forgot-password text-right">
                            Já cadastrado? <a href="/login">Login</a>
                        </p>
                    </form>
                </div>
            </div>
        )
    }
}

export default SignUp;