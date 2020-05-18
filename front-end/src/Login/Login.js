import React, { Component } from 'react';
import './Login.css';
import axios from 'axios';

class Login extends Component {

    state = {
        login: "",
        password: ""
    }

    loginChangeHandle = (event) => {
        this.setState({ login: event.target.value });
    }

    passwordChangeHandle = (event) => {
        this.setState({ password: event.target.value });
    }

    authenticate = () => {

        var userLogin = {
            username: this.state.login,
            password: this.state.password
        }

        axios.post('http://localhost:8080/login', userLogin)
            .then(response => console.log(response.data))
            .catch(response => console.log("ERRO!"));
    }

    render() {
        return (
            <form>
                <h3>Login</h3>

                <div className="form-group">
                    <label>Email</label>

                    <input type="text"
                        className="form-control"
                        value={this.state.login}
                        onChange={this.loginChangeHandle}
                        placeholder="Insira email" />
                </div>

                <div className="form-group">
                    <label>Senha</label>
                    <input type="password"
                        className="form-control"
                        value={this.state.password}
                        onChange={this.passwordChangeHandle}
                        placeholder="Insira senha" />
                </div>

                {/* <div className="form-group">
            <div className="custom-control custom-checkbox">
                <input type="checkbox" className="custom-control-input" id="customCheck1" />
                <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
            </div>
        </div> */}

                <button type="button" className="btn btn-primary btn-block" onClick={this.authenticate}>Entrar</button>
                {/* <p className="forgot-password text-right">
            Forgot <a href="#">password?</a>
        </p> */}
                <p className="forgot-password text-right">
                    Ainda não tem cadastro? <a href="/sign-up">Inscrever-se</a>
                </p>
            </form>
        )
    }
}


export default Login;