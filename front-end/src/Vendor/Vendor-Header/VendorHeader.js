import React, { Component } from 'react';
import { Navbar, Nav } from "react-bootstrap";
import history from '../../History';
import Cookies from 'js-cookie';


class VendorHeader extends Component {
    logout = function () {
        console.log('Log out:');

        history.push("/login");
        Cookies.remove('userId');
        Cookies.remove('userType');

    }

    render() {
        return (
            <Navbar bg="primary" variant="dark">
                <Navbar.Brand href="/leads" >Fitness-Finder</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/leads">Leads</Nav.Link>
                    <Nav.Link href="#">Ofertar Serviços</Nav.Link>
                    <Nav.Link href="/resume">Curriculo</Nav.Link>
                    <Nav.Link href="#">Cadastro</Nav.Link>
                </Nav>
                <Nav>
                    <Nav.Link onClick={this.logout} >Sair</Nav.Link>
                </Nav>
            </Navbar>
        )
    }
}

export default VendorHeader;