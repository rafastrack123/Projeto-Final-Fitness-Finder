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
            <Navbar collapseOnSelect expand="lg" bg="primary" variant="dark">
                <Navbar.Brand href="/vendor-offer-search" >Fitness-Finder</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link href="/vendor-offer-search">Pesquisar</Nav.Link>
                        <Nav.Link href="/vendor-proposition">Ofertas</Nav.Link>
                        <Nav.Link href="/evaluation-request">Avaliações</Nav.Link>
                    </Nav>
                    <Nav>
                        <Nav.Link onClick={this.logout} >Sair</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        )
    }
}

export default CustomerHeader;