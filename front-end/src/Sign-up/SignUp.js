import React, { Component } from 'react';
import './SignUp.css';
import { Form } from 'react-bootstrap';
import axios from 'axios';
import { throwStatement, tsObjectKeyword } from '@babel/types';

class SignUp extends Component {

    state = {
        objectives: [],
        selectedObjective: ""
    }

    componentDidMount() {
        this.fetchObjectives();
    }

    fetchObjectives = () => {
        axios.get('http://localhost:8080/objective/findAll')
            .then(response => {
                console.log("objective:");
                console.log(response.data[2]);
                this.setState({ selectedObjective: response.data[2]});
                this.setState({ objectives: response.data });
            });
    }

    selectObjective = (event) => {
        //var objective = this.objectives.find(obj => obj.id === objectiveId);
        console.log("O baita");
        console.log( event.target.value);
       this.setState({ selectedObjective: event.target.value });
    }

    signUp = () => {
        console.log("signUp:");
        console.log(this.state.selectedObjective);
    }

    render() {

        return (
            <div className="Signup">
                <form>
                    <h3>Cadastro</h3>

                    <Form.Group>
                        <Form.Label>Modalidade</Form.Label>
                        <Form.Control as="select">
                            <option>Cliente</option>
                            <option>Fornecedor</option>

                        </Form.Control>
                    </Form.Group>

                    <div className="form-group">
                        <label>Name</label>
                        <input type="text" className="form-control" placeholder="Nome" />
                    </div>

                    <div className="form-group">
                        <label>Sobrenome</label>
                        <input type="text" className="form-control" placeholder="Sobrenome" />
                    </div>

                    <div className="form-group">
                        <label>Email</label>
                        <input type="email" className="form-control" placeholder="Email" />
                    </div>

                    <div className="form-group">
                        <label>Celular</label>
                        <input type="tel" className="form-control" placeholder="Celular" />
                    </div>

                    <div className="form-group">
                        <label>Senha</label>
                        <input type="password" className="form-control" placeholder="Senha" />
                    </div>

                    <div className="form-group">
                        <label>Endereço</label>
                        <input type="text" className="form-control" placeholder="Endereço" />
                    </div>

                    <Form.Group>
                        <Form.Label>Objetivo</Form.Label>
                        <Form.Control as="select" onChange={this.selectObjective} value={this.state.selectedObjective}>
                            {this.state.objectives.map(objective => (
                                <option key={objective.id} value={objective.id}>
                                    {objective.name}
                                </option>
                            ))}
                        </Form.Control>
                    </Form.Group>


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