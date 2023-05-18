import React, {useEffect, useState} from "react";
import {Link, useParams} from "react-router-dom";
import {DeleteClothe, EditClothe, GEToneWorker} from "../service/service.js";
import logo from '../assets/logo-uzbekistan.png'

export const OneWorkerPage = () => {
    const param = useParams().id;
    const [worker, setWorker] = useState('')
    const [clothe, setClothe] = useState([])
    const [name, setName] = useState('')
    const [olishVaqti, setOlishVaqti] = useState('')
    const [eslatishVaqti, setEslatishVaqti] = useState('')

    const [id, setId] = useState('')
    const getOneWorker = async () => {
        await GEToneWorker(param, setWorker, setClothe)
    }
    useEffect(() => {
        getOneWorker()
    }, [])

    const editClothe = async () => {
        const data = {
            name, olishVaqti, eslatishVaqti
        }
        await EditClothe(data, param, id)
        setName("")
        setOlishVaqti("")
        setEslatishVaqti("")
        setTimeout(() => {
            window.location.reload()
        }, 1000)
    }
    const deleteClothe = async () => {
        await DeleteClothe(param, id)
        alert(id)
        setTimeout(() => {
            window.location.reload()
        }, 2000)
    }

    return (
        <div>
            <div className="col-lg-9">
                <div className="card profile-card-2">
                    <div className="card-img-block">
                    </div>
                    <div className="card-body pt-5">
                        <img src={logo} alt="profile-image" className="profile"/>
                        <h5 className="card-title text-dark">{worker.name} <span>{worker.lastName}</span></h5>
                        <p className="card-text">Telefon raqam: {worker.phoneNumber}</p>
                        <p className="card-text">Email pochta:
                            {worker.email === null ? (
                                <> ushbu ishida email mavjud emas</>
                            ) : (
                                <>
                                    {worker.email}
                                </>
                            )}
                        </p>
                        {worker.chatId === null ? (
                            <>
                                <p className={"text-danger"}>ushbu ishchi telegram bot orqali ro'yhatdan o'tmagan</p>
                            </>
                        ) : (
                            <>
                                <p className={"card-text"}>Telegram bot: Ro'yxatdan o'tgan</p>
                            </>
                        )}
                    </div>
                    <h6 className={"card-text text-center text-dark mb-4"}>uchbu ishchini oladigan kiyimi
                        ma'lumotlari</h6>
                    {clothe.length === 0 ? (
                        <>
                            <p className={"text-danger text-center"} style={{marginLeft: "25px"}}>ushbu ishchining
                                kiyimi mavjud emas</p>
                        </>
                    ) : (
                        <>
                            <div className={"table-responsive col-12 mt-3 bg-light"}>
                                <table className={"table align-items-center table-flush table-borderless"}>
                                    <thead>
                                    <tr>
                                        <th>T/r</th>
                                        <th>Kiyim nomi</th>
                                        <th>eslatish vaqti</th>
                                        <th>olish vaqti</th>
                                        <th colSpan={3}>action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {clothe.map((item, i) => (
                                        <tr>
                                            <td>{i + 1}</td>
                                            <th>{item.name}</th>
                                            <td className={"date"}>{item.awaredDay}/{item.awaredMonth}/{item.awaredYear}</td>
                                            <td>{item.giveDay}/{item.giveMonth}/{item.giveYear}</td>
                                            <td>
                                                <button type={"button"} onClick={() => setId(item.id)}
                                                        className={"btn btn-sm-xl btn-warning"} data-mdb-toggle="modal"
                                                        data-mdb-target="#editClothe"><i className="fas fa-pencil"></i>
                                                </button>
                                            </td>
                                            <td>
                                                <button className={"btn btn-sm-xl btn-danger"} type={"button"} onClick={() => setId(item.id)} data-mdb-toggle="modal"
                                                        data-mdb-target="#deleteClothe"><i
                                                    className="fas fa-trash"></i></button>
                                            </td>
                                        </tr>
                                    ))}

                                    </tbody>

                                </table>

                            </div>
                        </>
                    )}

                    <div className="modal fade" id="editClothe" tabIndex="-1" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">
                        <div className="modal-dialog">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h5 className="modal-title text-dark" id="exampleModalLabel">Kiyim taxrirlash</h5>
                                    <button type="button" className="btn-close" data-mdb-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div className="modal-body">
                                    <form>
                                        <label htmlFor="name" className={"text-dark"}>Kiyim nomi</label>
                                        <input type="text" className={"form-control bg-primary"}
                                               placeholder={"kiyim nomini taxrirlang"} id={"name"} name={"name"}
                                               value={name} onChange={e => setName(e.target.value)}/>
                                        <label htmlFor="olishVaqti" className={"text-dark"}>Kiyim olish vaqti</label>
                                        <input type="date" className={"form-control bg-primary"} id={"olishVaqti"}
                                               name={"olishVaqti"} value={olishVaqti}
                                               onChange={e => setOlishVaqti(e.target.value)}/>
                                        <label htmlFor="eslatishVaqti" className={"text-dark"}>Eslatish vaqti</label>
                                        <input type="date" className={"form-control bg-primary"}
                                               placeholder={"Eslatish vaqtini kiriting"} id={"eslatishVaqti"}
                                               name={"eslatishVaqti"} value={eslatishVaqti}
                                               onChange={e => setEslatishVaqti(e.target.value)}/>
                                    </form>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary"
                                            data-mdb-dismiss="modal">Yopish
                                    </button>
                                    <button type="button" className="btn btn-primary"
                                            onClick={() => editClothe()}>Saqlash
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="modal fade" id="deleteClothe" tabIndex="-1" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">
                        <div className="modal-dialog">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <h5 className="modal-title text-danger" id="exampleModalLabel">Kiyimni olib tashlash</h5>
                                    <button type="button" className="btn-close" data-mdb-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div className="modal-body">
                                    <h5 className={"text-danger"}>siz ushbu kiyimni o'chirishga rozimisiz</h5>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary"
                                            data-mdb-dismiss="modal">yo'q
                                    </button>
                                    <button type="button" className="btn btn-danger"
                                            onClick={() => deleteClothe()}>ha
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </div>
    )
}