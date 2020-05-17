import React from 'react';
import './Login.css';

const login = () => {
    return (
        <form>
        <h3>Login</h3>

        <div className="form-group">
            <label>Email</label>
            <input type="email" className="form-control" placeholder="Insira email" />
        </div>

        <div className="form-group">
            <label>Senha</label>
            <input type="password" className="form-control" placeholder="Insira senha" />
        </div>

        {/* <div className="form-group">
            <div className="custom-control custom-checkbox">
                <input type="checkbox" className="custom-control-input" id="customCheck1" />
                <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
            </div>
        </div> */}

        <button type="submit" className="btn btn-primary btn-block">Entrar</button>
        {/* <p className="forgot-password text-right">
            Forgot <a href="#">password?</a>
        </p> */}
        <p className="forgot-password text-right">
                Ainda não tem cadastro? <a href="/sign-up">Inscrever-se</a>
        </p>
    </form>
    )
}


export default login;