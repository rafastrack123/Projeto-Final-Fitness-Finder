import React from 'react';
import { Navbar, Nav } from "react-bootstrap";


const customerHeader = () => {
    return (
        <Navbar bg="primary" variant="dark">
            <Navbar.Brand href="/vendor-offer-search" >Fitness-Finder</Navbar.Brand>
            <Nav className="mr-auto">
                <Nav.Link href="/vendor-offer-search">Fornecedores</Nav.Link>
                <Nav.Link href="#">Mensagens</Nav.Link>
                <Nav.Link href="#">Editar Cadastro</Nav.Link>
            </Nav>
            <Nav>
                <Nav.Link href="/login">Sair</Nav.Link>
            </Nav>
        </Navbar>
    )
}

export default customerHeader;