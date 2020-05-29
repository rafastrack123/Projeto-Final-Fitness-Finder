import React, { Component } from 'react';
import { Navbar, Nav } from "react-bootstrap";
import history from '../../History';
import Cookies from 'js-cookie';



class CustomerHeader extends Component {
    logout = function () {
        console.log('Log out:');

        history.push("/login");
        Cookies.remove('userId');
        Cookies.remove('userType');

    }
    
    render() {
        return (
            <Navbar bg="primary" variant="dark">
                <Navbar.Brand href="/vendor-offer-search" >Fitness-Finder</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/vendor-offer-search">Fornecedores</Nav.Link>
                    <Nav.Link href="/vendor-proposition">Ofertas</Nav.Link>
                    <Nav.Link href="#">Editar Cadastro</Nav.Link>
                </Nav>
                <Nav>
                    <Nav.Link onClick={this.logout} >Sair</Nav.Link>
                </Nav>
            </Navbar>
        )
    }
}

export default CustomerHeader;