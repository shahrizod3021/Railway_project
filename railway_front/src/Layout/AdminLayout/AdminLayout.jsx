import React, {Component, useEffect, useState} from 'react';
import Sidebar from "../../Component/Sidebar.jsx";
import {Outlet} from "react-router-dom";
import {Tekshirish} from "../../service/service.js";
import {NotFoundaPage} from "../../Component/NotFoundaPage.jsx";

const AdminLayout = () => {
    const [user, setUser] = useState([])

    const check = async  () =>{
        await Tekshirish(setUser)
    }
    useEffect(() => {
        check()
    }, [])
    return (
            <div>
                {user.length === 0 ? (
                    <>
                        <NotFoundaPage/>
                    </>
                ) : (
                    <>
                        <Layoutjon/>
                    </>
                )}
            </div>
        );
}

const Layoutjon  = () => {
    return (
        <div className={"bg-theme1"}>
            <Sidebar/>
            <main style={{marginTop:"50px"}}>
                <div className={"container pt-4"}>
                    <Outlet/>
                </div>
            </main>

        </div>
    )
}
export default AdminLayout;
