import React, { Component } from 'react';
import './SignUp.css';
import { Form } from 'react-bootstrap';
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
            username: "Remover",
            password: this.state.password,
            contactInfo: {
                email: this.state.email
            },
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
            <div className="Signup">
                <form>
                    <h3>Cadastro</h3>

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
                    
                    <div className="form-group">
                        <label>Name</label>
                        <input type="text" value={this.state.firstName} onChange={this.firstNameChangeHandle} className="form-control" placeholder="Nome" />
                    </div>

                    <div className="form-group">
                        <label>Sobrenome</label>
                        <input type="text" value={this.state.lastName} onChange={this.lastNameChangeHandle} className="form-control" placeholder="Sobrenome" />
                    </div>

                    <div className="form-group">
                        <label>Email</label>
                        <input type="email" value={this.state.email} onChange={this.emailChangeHandle} className="form-control" placeholder="Email" />
                    </div>

                    <div className="form-group">
                        <label>Celular</label>
                        <input type="tel" value={this.state.cellphone} onChange={this.cellphoneChangeHandle} className="form-control" placeholder="Celular" />
                    </div>

                    <div className="form-group">
                        <label>Senha</label>
                        <input type="password" value={this.state.password} onChange={this.passwordChangeHandle} className="form-control" placeholder="Senha" />
                    </div>

                    <div className="form-group">
                        <label>Endereço</label>
                        <input type="text" value={this.state.address} onChange={this.addressChangeHandle} className="form-control" placeholder="Endereço" />
                    </div>

                    {this.state.userType === "Customer" ?
                        // Remove to component
                        <Form.Group>
                            <Form.Label>Objetivo</Form.Label>
                            <Form.Control as="select" onChange={this.selectObjective} value={this.state.selectedObjective}>
                                {this.state.objectives.map(objective => (
                                    <option key={objective.id} value={objective.id}>
                                        {objective.name}
                                    </option>
                                ))}
                            </Form.Control>
                        </Form.Group> : null
                    }

                    <button type="button" href="#" className="btn btn-primary btn-block"
                        onClick={() => this.signUp()}>Inscrever-se</button>
                    <p className="forgot-password text-right">
                        Já cadastrado? <a href="/login">Login</a>
                    </p>
                </form>
            </div>
        )
    }
}

export default SignUp;