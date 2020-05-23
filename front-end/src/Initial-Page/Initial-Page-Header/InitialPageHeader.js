import React from 'react';
import { Navbar, Nav, Form,FormControl, Button } from "react-bootstrap";


const intialPageHeader = () => {
    return (
        <Navbar bg="primary" variant="dark">
            <Navbar.Brand href="/login" >Fitness-Finder</Navbar.Brand>
            <Nav className="mr-auto">
                <Nav.Link href="/login">Login</Nav.Link>
                <Nav.Link href="/sign-up">Cadastro</Nav.Link>
            </Nav>
        </Navbar>
    )
}

export default intialPageHeader;