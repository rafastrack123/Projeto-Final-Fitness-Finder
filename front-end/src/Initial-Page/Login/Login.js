import React, { Component } from 'react';
import './Login.css';
import axios from 'axios';
import { Button, FormGroup, FormControl, Form, Spinner, Alert } from "react-bootstrap";


class Login extends Component {

    state = {
        login: "",
        password: "",
        loading: false,

        showUnauthorizedAlert: false
    }

    loginChangeHandle = (event) => {
        this.setState({ login: event.target.value });
    }

    passwordChangeHandle = (event) => {
        this.setState({ password: event.target.value });
    }

    authenticate = () => {

        this.setState({ loading: true })

        var userLogin = {
            email: this.state.login,
            password: this.state.password
        }

        axios.post('http://localhost:8080/login', userLogin)
            .then(response => {
                console.log(response.data);
                this.setState({ loading: false })
            })
            .catch(response => {
                console.log("ERRO!");
                this.setState({ loading: false })
                this.setState({ showUnauthorizedAlert: true });
            });
    }

    render() {
        return (
            <div className="Login">

                <h3 id="login-header">Login</h3>
                <form id="login-from">

                    <Alert
                        show={this.state.showUnauthorizedAlert}
                        className="text-center"
                        variant="danger">Usuário não encontrado</Alert>

                    <FormGroup controlId="email" bsSize="large">
                        <Form.Label>Email</Form.Label>
                        <FormControl
                            autoFocus
                            type="email"
                            value={this.state.login}
                            onChange={this.loginChangeHandle}
                            placeholder="Insira email" />
                    </FormGroup>

                    <FormGroup controlId="password" bsSize="large">
                        <Form.Label>Senha</Form.Label>
                        <FormControl
                            type="password"
                            value={this.state.password}
                            onChange={this.passwordChangeHandle}
                            placeholder="Insira senha" />
                    </FormGroup>

                    {this.state.loading ?
                        <Button variant="btn btn-primary btn-block" disabled>
                            <Spinner
                                as="span"
                                animation="grow"
                                size="sm"
                                role="status"
                                aria-hidden="true"
                            />
                            Loading...
                         </Button>
                        :
                        <Button block variant="btn btn-primary btn-block mb-2" bsSize="large"
                            onClick={this.authenticate} type="button">Entrar</Button>
                    }

                    <p className="forgot-password text-right">
                        Ainda não tem cadastro? <a href="/sign-up">Inscrever-se</a>
                    </p>
                </form>
            </div>
        )
    }
}


export default Login;