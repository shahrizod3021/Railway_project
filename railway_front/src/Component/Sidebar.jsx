import React, {Component, useEffect, useState} from 'react';
import '../assets/css/sidebar-menu.css';
import  logo from '../assets/images/logo-icon.png'
import {Link, useNavigate} from "react-router-dom";
import myPhoto from '../assets/images/shahrizod.jpg'
import trains from '../assets/logo-uzbekistan.png'
import {GetEslatma} from "../service/service.js";
import {set} from "mdb-ui-kit/src/js/mdb/perfect-scrollbar/lib/css.js";
import {toast} from "react-toastify";
const Sidebar = () =>  {
    const [eslatma, setEslatma] = useState([])
    const navigate = useNavigate();
    const getEslatma = async () => {
        try {
            await GetEslatma(setEslatma)
        }catch (err){
            console.log(err.message)
        }
    }
    useEffect(() => {
        getEslatma()
    }, [])

    const logout = async () => {
        localStorage.clear()
        toast.success("accauntdan chiqdingiz")
        setTimeout(() => {
             navigate('/auth/login')
        }, 2000)
    }

        return (
            <div>
                <header >
                    <nav id="sidebarMenu" className="collapse d-lg-block sidebar collapse bg-white">
                        <div className="position-sticky">
                            <div className="list-group list-group-flush mx-3 mt-4">
                                <Link
                                    to="/"
                                    className="list-group-item list-group-item-action py-2 ripple"
                                    aria-current="true"
                                >
                                    <i className="fas fa-tachometer-alt fa-fw me-3"></i><span>Asosiy bo'lim</span>
                                </Link>
                                <Link to="/worker" className="list-group-item list-group-item-action py-2 ripple"
                                ><i className="fas fa-users fa-fw me-3"></i><span>Ishchilar</span></Link
                                >

                                <Link to="/shikoyatlar" className="list-group-item list-group-item-action py-2 ripple"
                                ><i className="far fa-thumbs-down fa-fw me-3"></i><span>Shikoyatlar</span></Link
                                >
                                <Link to="/eslatmalar" className="list-group-item list-group-item-action py-2 ripple"
                                ><i className="far fa-address-book fa-fw me-3"></i><span>Eslatmalar</span></Link
                                >
                            </div>
                        </div>
                    </nav>

                    <nav id="main-navbar" className="navbar navbar-expand-lg navbar-light bg-white fixed-top">
                        <div className="container-fluid">
                            <button
                                className="navbar-toggler"
                                type="button"
                                data-mdb-toggle="collapse"
                                data-mdb-target="#sidebarMenu"
                                aria-controls="sidebarMenu"
                                aria-expanded="false"
                                aria-label="Toggle navigation"
                            >
                                <i className="fas fa-bars"></i>
                            </button>

                            <Link className="navbar-brand" to="/">
                                <img
                                    src={trains}
                                    height="25"
                                    alt="MDB Logo"
                                    loading="lazy"
                                />
                                Uzbekistan Railways
                            </Link>


                            <ul className="navbar-nav ms-auto d-flex flex-row">
                                <li className="nav-item dropdown">
                                    <Link
                                        className="nav-link me-3 me-lg-0 "
                                        to="/eslatmalar"
                                        id="navbarDropdownMenuLink"
                                        role="button"
                                    >
                                        <i className="fas fa-bell"></i>
                                    </Link>

                                </li>



                                <li className="nav-item dropdown">
                                    <a
                                        className="nav-link dropdown-toggle hidden-arrow d-flex align-items-center"
                                        href="#"
                                        id="navbarDropdownMenuLink"
                                        role="button"
                                        data-mdb-toggle="dropdown"
                                        aria-expanded="false"
                                    >
                                        <img
                                            src={trains}
                                            className="rounded-circle"
                                            height="22"
                                            alt="Avatar"
                                            loading="lazy"
                                        />
                                    </a>
                                    <ul
                                        className="dropdown-menu dropdown-menu-end"
                                        aria-labelledby="navbarDropdownMenuLink"
                                    >
                                        <li>
                                            <a className="dropdown-item" href="#">My profile</a>
                                        </li>
                                        <li>
                                            <a className="dropdown-item" href="#">Settings</a>
                                        </li>
                                        <li>
                                            <button type={"button"} className="dropdown-item" onClick={() => logout()}>Logout</button>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </header>
            </div>
        );
}
export default Sidebar;