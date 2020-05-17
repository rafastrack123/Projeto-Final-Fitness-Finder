import React from 'react';
import './SignUp.css';
import { Form } from 'react-bootstrap';


const signUp = () => {
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
                    <input type="tel" className="form-control" placeholder="Celular"/>
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
                    <Form.Control as="select">
                        <option>Perder Peso</option>
                        <option>Hipertrofia</option>
                    </Form.Control>
                </Form.Group>

                <button type="submit" className="btn btn-primary btn-block">Inscrever-se</button>
                <p className="forgot-password text-right">
                    Já cadastrado? <a href="/login">Login</a>
                </p>
            </form>
        </div>
    )
}


export default signUp;