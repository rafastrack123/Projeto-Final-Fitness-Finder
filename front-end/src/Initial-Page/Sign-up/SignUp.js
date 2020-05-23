import React, { Component } from 'react';
import './SignUp.css';
import { Button, FormGroup, FormControl, Form } from "react-bootstrap";
import axios from 'axios';

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
        userType: "Customer"
    }

    componentDidMount() {
        this.fetchObjectives();
    }

    fetchObjectives = () => {
        axios.get('http://localhost:8080/objective/findAll')
            .then(response => {
                this.setState({ selectedObjective: response.data[0].id });
                this.setState({ objectives: response.data });
            });
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

    selectObjective = (event) => {
        this.setState({ selectedObjective: event.target.value });
    }

    selectUserType = (event) => {
        this.setState({ userType: event.target.value });
    }

    signUp = () => {
        console.log("signUp:");

        var user = {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            email: this.state.email,
            password: this.state.password,
            address: {
                fullAddress: this.state.address
            }
        };

        if (this.state.userType === "Customer") {
            this.postCustomer(user);
        } else {
            this.postVendor(user);
        }
    }

    postCustomer = (customer) => {
        console.log('Post Customer');
        console.log('objective:');
        console.log(this.state.selectedObjective);

        customer.objective = this.state.objectives
            .find(objective => objective.id == this.state.selectedObjective)

        axios.post('http://localhost:8080/customer', customer)
            .then(response => {
                console.log('Post Customer Success');
                console.log(response.data);
            });
    }

    postVendor = (vendor) => {
        console.log('Post Vendor');

        axios.post('http://localhost:8080/vendor', vendor).then(response => {
            console.log('Post Vendor Success');
            console.log(response.data);
        });
    }

    render() {

        return (
            <div className="SignUp">
                <h3 id="sign-up-header">Cadastro</h3>

                <form id="sign-up-from">

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

                    <FormGroup controlId="cellphone" bsSize="large">
                        <Form.Label>Celular</Form.Label>
                        <FormControl
                            type="tel"
                            value={this.state.cellphone}
                            onChange={this.cellphoneChangeHandle}
                            placeholder="Insira celular" />
                    </FormGroup>

                    <FormGroup controlId="password" bsSize="large">
                        <Form.Label>Senha</Form.Label>
                        <FormControl
                            type="password"
                            value={this.state.password}
                            onChange={this.passwordChangeHandle}
                            placeholder="Insira senha" />
                    </FormGroup>

                    <FormGroup controlId="text" bsSize="large">
                        <Form.Label>Endereço</Form.Label>
                        <FormControl
                            type="text"
                            value={this.state.address}
                            onChange={this.addressChangeHandle}
                            placeholder="Insira senha" />
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
                    <Button block variant="btn btn-primary btn-block" bsSize="large"
                        onClick={() => this.signUp()} type="button">Inscrever-se</Button>

                    <p className="forgot-password text-right">
                        Já cadastrado? <a href="/login">Login</a>
                    </p>
                </form>
            </div>
        )
    }
}

export default SignUp;