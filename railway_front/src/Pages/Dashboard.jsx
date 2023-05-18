import React, {Component, useEffect, useState} from 'react';
import {GetEslatma, GetShikoyat, GetWorker} from "../service/service.js";

const Dashboard  = () =>{
    const [worker, setWorker] = useState([]);
    const [eslatma, setEslatma] = useState([])
    const [shikoyat, setShikoyat] = useState([])

    const getAll = async () => {
        await GetWorker(setWorker)
    }
    const getEslatma = async () => {
        try {
            await GetEslatma(setEslatma)
        }catch (err){
        }
    }
    const getShikoyat = async  () => {
        await GetShikoyat(setShikoyat)
    }

    useEffect(() => {
        getEslatma()
        getAll()
        getShikoyat()
    }, [])
        return (
            <div>
                <div className="card mt-3">
                    <div className="card-content">
                        <div className="row row-group m-0">
                            <div className="col-12 col-lg-6 col-xl-3 border-light">
                                <div className="card-body">
                                    <h5 className="text-dark mb-0">{worker.length} <span className="float-right"><i
                                        className="fas fa-users fa-fw"></i></span></h5>
                                    <div className="progress my-3" style={{height: "3px"}}>
                                        <div className="progress-bar" style={{width:`${worker.length }%`}} ></div>
                                    </div>
                                    <p className="mb-0 text-dark small-font">Umumiy ischilar
                                    </p>
                                </div>
                            </div>
                            <div className="col-12 col-lg-6 col-xl-3 border-light">
                                <div className="card-body">
                                    <h5 className="text-dark mb-0">{eslatma.length} <span className="float-right"><i
                                        className="far fa-address-book"></i></span></h5>
                                    <div className="progress my-3" style={{height:"3px"}}>
                                        <div className="progress-bar" style={{width:`${eslatma.length}%`}}></div>
                                    </div>
                                    <p className="mb-0 text-dark small-font">Umumiy eslatmalar
                                    </p>
                                </div>
                            </div>
                            <div className="col-12 col-lg-6 col-xl-3 border-light">
                                <div className="card-body">
                                    <h5 className="text-dark mb-0">{shikoyat.length} <span className="float-right"><i
                                        className="far fa-thumbs-down fa-fw"></i></span></h5>
                                    <div className="progress my-3" style={{height:"3px"}}>
                                        <div className="progress-bar" role="progressbar" style={{width: `${shikoyat.length}%`}}
                                             aria-valuenow={shikoyat.length} aria-valuemin="0" aria-valuemax={1000}></div>
                                    </div>
                                    <p className="mb-0 text-dark small-font">Umumiy shikoyatlar
                                    </p>
                                </div>
                            </div>
                            <div className="col-12 col-lg-6 col-xl-3 border-light">
                                <div className="card-body">
                                    <h5 className="text-dark mb-0">5630 <span className="float-right"><i
                                        className="fas fa-user-tie"></i></span></h5>
                                    <div className="progress my-3" style={{height: "3px"}}>
                                        <div className="progress-bar" style={{width:"55%"}}></div>
                                    </div>
                                    <p className="mb-0 text-dark small-font">Umumiy kiyimlar
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
}

export default Dashboard;