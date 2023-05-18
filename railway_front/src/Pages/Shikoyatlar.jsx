import React, {useEffect, useState} from "react";
import {GetShikoyat} from "../service/service.js";
export const Shikoyatlar = () => {
    const[shikoyat, setShikoyat] = useState([])
    const getShikoyat = async () => {
        await GetShikoyat(setShikoyat)
    }

    useEffect(() => {
        getShikoyat()
    }, [])
    return (
        <div>
            {shikoyat.length === 0 ? (
                <>
                    <div className="alert alert-info d-flex align-items-center">
                        <div className="alert-icon">
                            <i className="fas fa-info"></i>
                        </div>
                        <span><strong>ESLATMA!</strong> Sizda hali shikoyatlar mavjud emas.</span>
                    </div>
                </>
            ) : (
                <>
                    {shikoyat.map((item) => (
                        <div className={"eslatmajon col-4"}>
                            <div className="card profile-card-2" style={{borderTopLeftRadius:"1px", borderBottomLeftRadius:'40px'}}>
                                <div className="card-body">
                                    <h4 className={"card-title text-dark"}><span className={"text-primary"}>{item.name}</span> tomonidan</h4>
                                    <hr/>
                                    <p className={"card-text"}>{item.message}</p>
                                </div>
                            </div>
                        </div>
                    ))}
                </>
            )}
        </div>
    )
}