import React from 'react';
import { Link } from "react-router-dom";
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
        // <nav className="navbar navbar-expand-lg navbar-light fixed-top">
        //     <div className="container">
        //         <Link className="navbar-brand" to={"/login"}>Fitness-Finder</Link>
        //         <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
        //             <ul className="navbar-nav ml-auto">
        //                 <li className="nav-item">
        //                     <Link className="nav-link" to={"/login"}>Login</Link>
        //                 </li>
        //                 <li className="nav-item">
        //                     <Link className="nav-link" to={"/sign-up"}>Sign up</Link>
        //                 </li>
        //             </ul>
        //         </div>
        //     </div>
        // </nav>

    )
}

export default intialPageHeader;