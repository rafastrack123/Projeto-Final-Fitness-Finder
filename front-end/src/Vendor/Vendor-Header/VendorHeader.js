import React, { Component } from 'react';
import { Navbar, Nav, NavDropdown } from "react-bootstrap";
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
            <Navbar collapseOnSelect expand="lg" bg="primary" variant="dark">
                <Navbar.Brand href="/leads" >Fitness-Finder</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link href="/leads">Oportunidades</Nav.Link>
                        <NavDropdown title="Serviços">
                            <NavDropdown.Item href="/vendor-offer/create">Ofertar</NavDropdown.Item>
                            <NavDropdown.Item href="/vendor-offer/list">Visualizar</NavDropdown.Item>
                        </NavDropdown>
                        <Nav.Link href="/resume">Curriculo</Nav.Link>
                    </Nav>
                    <Nav>
                        <Nav.Link onClick={this.logout} >Sair</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        )
    }
}

export default VendorHeader;