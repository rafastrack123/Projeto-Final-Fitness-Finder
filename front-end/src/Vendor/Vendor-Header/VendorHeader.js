import React from 'react';
import { Navbar, Nav } from "react-bootstrap";


const vendorHeader = () => {
    return (
        <Navbar bg="primary" variant="dark">
            <Navbar.Brand href="#" >Fitness-Finder</Navbar.Brand>
            <Nav className="mr-auto">
                <Nav.Link href="#">Leads</Nav.Link>
                <Nav.Link href="#">Ofertar Serviços</Nav.Link>
                <Nav.Link href="#">Curriculo</Nav.Link>
                <Nav.Link href="#">Cadastro</Nav.Link>
            </Nav>
        </Navbar>
    )
}

export default vendorHeader;