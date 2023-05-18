import {useEffect, useState} from "react";
import {DeleteWorker, EditWorker, GetWorker, SaveClothe, SaveWorker} from "../service/service.js";
import {toast} from "react-toastify";
import {Pagenation} from "../Component/Pagenation.jsx";
import {Link} from "react-router-dom";

export const Worker = () => {
    const[name, setName] = useState('');
    const[surname, setSurname] = useState('');
    const[phoneNumber, setPhoneNumber] = useState('');
    const[email, setEmail] = useState('');
    const [worker, setWorker] = useState([])
    const [currentPage, setCurrentPage] = useState(1);
    const [prePage] = useState(10)
    const getAll = async () => {
        await GetWorker(setWorker)
    }

    useEffect(() => {
        getAll()
    }, [])
    const saveWorker = async  () => {
        const data = {
             name, surname, phoneNumber:"+998"+phoneNumber, email
        }
        await SaveWorker(data)
        setTimeout(() => {
            setName("")
            setSurname("")
            setPhoneNumber("")
            setEmail("")
            getAll()
        }, 1000)

    }



    const indexOfLastData = currentPage * prePage;
    const indexOfFirstData = indexOfLastData - prePage;
    const currentData = worker.slice(indexOfFirstData, indexOfLastData);
    const paginate = (pageNumber) => setCurrentPage(pageNumber);
    return (
        <div>
            <button type="button" className="btn btn-primary" data-mdb-toggle="modal" data-mdb-target="#exampleModal">
                Ishchi qo'shish <i className="fas fa-user-plus"></i>
            </button>
            <WorkerJon data={currentData}/>
            <Pagenation totalData={worker.length} perPage={prePage} paginate={paginate}/>
            <div className="modal fade" id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title text-dark" id="exampleModalLabel">Ishchi qo'shish</h5>
                            <button type="button" className="btn-close" data-mdb-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <form onSubmit={saveWorker}>
                                <label htmlFor="name" className={"text-dark"}>Ishchi ismi</label>
                                <input type="text" className={"form-control bg-primary"}  placeholder={"ishchi ismini kiriting"} id={"name"} name={"name"} value={name} onChange={e => setName(e.target.value)}/>
                                <label htmlFor="surname" className={"text-dark"}>Ishchi familyasi</label>
                                <input type="text" className={"form-control bg-primary"}  placeholder={"ishchi familyasi kiriting"} id={"surname"} name={"surname"} value={surname} onChange={e => setSurname(e.target.value)}/>
                                <label htmlFor="phoneNumber " className={"text-dark mt-2"}>Ishchi telefon raqami</label>
                                <div className="input-group flex-nowrap ">
                                    <span className="input-group-text" id="addon-wrapping">+998 </span>
                                    <input type="number" className="form-control bg-primary" id={"phoneNumber"} name={"phoneNumber"} placeholder="telefon raqam"
                                           aria-label="Username" aria-describedby="addon-wrapping" value={phoneNumber} onChange={e => setPhoneNumber(e.target.value)}/>
                                </div>
                                <label htmlFor="email" className={"text-dark mt-3"}>Ishchi email</label>
                                <input type="text" className={"form-control bg-primary"}  placeholder={"ishchi emailini kiriting"} id={"email"} name={"email"} value={email} onChange={e => setEmail(e.target.value)}/>

                            </form>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-mdb-dismiss="modal">Yopish</button>
                            <button type="button" className="btn btn-primary" onClick={() => saveWorker()} >Saqlash</button>
                        </div>
                    </div>
                </div>
            </div>


        </div>

    )
}

const WorkerJon = ({data}) => {
    const [id, setId] = useState('')
    const[olishVaqti, setOlishVaqti] = useState('')
    const[name, setName] = useState('')
    const[eslatishVaqti, setEslatishVaqti] = useState('')
    const[email, setEmail] = useState('')
    const[surname, setSurname] = useState('')
    const[phoneNumber, setPhoneNumber] = useState('')
    const saveClothe =async () => {
        const data = {
            name,olishVaqti,eslatishVaqti
        }
        await SaveClothe(id,data)
        setOlishVaqti("")
        setEslatishVaqti("")
        setTimeout(() => {
            window.local.reload()
        }, 1000)
    }

    const editWorker = async  () => {
        const data = {
            name,surname,phoneNumber:"+998"+phoneNumber,email
        }
        await EditWorker(data, id)
        setName("")
        setSurname("")
        setPhoneNumber("")
        setEmail("")
    }

    const deleteWorker = async () => {
        await DeleteWorker(id)
    }
    return(
        <div className={"table-responsive mt-3 bg-light"}>
            <table className={"table align-items-center table-flush table-borderless"}>
                <thead>
                <tr>
                    <th>T/r</th>
                    <th>ism</th>
                    <th>familya</th>
                    <th>telefon raqam</th>
                    <th>email</th>
                    <th colSpan={3}>action</th>
                </tr>
                </thead>
                <tbody>
                {data.map((item, i) => (
                    <tr>
                        <td>{i+1}</td>
                        <td>{item.name}</td>
                        <td>{item.lastName}</td>
                        <td>{item.phoneNumber}</td>
                        {item.email === null || item.email.length === 0 ? (
                            <>
                                <td className={"text-danger"}>Mavjud emas</td>
                            </>
                        ) : (
                            <>
                                <td>{item.email}</td>

                            </>
                        )}
                        <td>
                            <button type={"button"} onClick={() => setId(item.id)}  className={"btn btn-warning"}  data-mdb-toggle="modal" data-mdb-target="#editworker"><i className="fas fa-pencil"></i></button>
                            <button className={"btn btn-danger"} data-mdb-toggle="modal" data-mdb-target="#deleteWorker" onClick={() => setId(item.id)}><i className="fas fa-trash"></i></button>
                            <Link to={"/worker/" + item.id} className={"btn btn-primary"}><i className="fas fa-eye"></i></Link>
                            <button type={"button"} className={"btn btn-info"} onClick={() => setId(item.id)} data-mdb-toggle="modal" data-mdb-target="#addClothe"><i className="fas fa-shirt"></i></button>
                        </td>
                    </tr>
                ))}
                </tbody>

            </table>

            <div className="modal fade" id="addClothe" tabIndex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title text-dark" id="exampleModalLabel">Kiyim kiritish</h5>
                            <button type="button" className="btn-close" data-mdb-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <form>
                                <label htmlFor="name" className={"text-dark"}>Kiyim nomi</label>
                                <input type="text" className={"form-control bg-primary"} placeholder={"kiyim nomini kiriting"}   id={"name"} name={"name"} value={name} onChange={e => setName(e.target.value)}/>
                                <label htmlFor="olishVaqti" className={"text-dark"}>Kiyim olish vaqti</label>
                                <input type="date" className={"form-control bg-primary"}   id={"olishVaqti"} name={"olishVaqti"} value={olishVaqti} onChange={e => setOlishVaqti(e.target.value)}/>
                                <label htmlFor="eslatishVaqti" className={"text-dark"}>Eslatish vaqti</label>
                                <input type="date" className={"form-control bg-primary"}  placeholder={"Eslatish vaqtini kiriting"} id={"eslatishVaqti"} name={"eslatishVaqti"} value={eslatishVaqti} onChange={e => setEslatishVaqti(e.target.value)}/>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-mdb-dismiss="modal">Yopish</button>
                            <button type="button" className="btn btn-primary" onClick={() => saveClothe()} >Saqlash</button>
                        </div>
                    </div>
                </div>
            </div>
            <div className="modal fade" id="deleteWorker" tabIndex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title text-dark" id="exampleModalLabel">Ishchini olib tashlash</h5>
                            <button type="button" className="btn-close" data-mdb-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                           <h5 className={"text-danger"}>siz ushbu ishchini olib tashlashga rozimisiz</h5>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-mdb-dismiss="modal">yo'q</button>
                            <button type="button" className="btn btn-primary" onClick={() => deleteWorker()} >ha</button>
                        </div>
                    </div>
                </div>
            </div>

            <div className="modal fade" id="editworker" tabIndex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title text-dark" id="exampleModalLabel">ishchi ma'lumotlarini taxrirlash</h5>
                            <button type="button" className="btn-close" data-mdb-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <form>
                                <label htmlFor="name" className={"text-dark"}>ishchi ismi</label>
                                <input type="text" className={"form-control bg-primary"} placeholder={"ishchi ismini taxrirlang"}   id={"name"} name={"name"} value={name} onChange={e => setName(e.target.value)}/>
                                <label htmlFor="surname" className={"text-dark"}>Ishchi familyasi</label>
                                <input type="text" className={"form-control bg-primary"} placeholder={"ishchi familyasini taxrirlang"} id={"surname"} name={"surname"} value={surname} onChange={e => setSurname(e.target.value)}/>
                                <label htmlFor="phoneNumber " className={"text-dark mt-2"}>Ishchi telefon raqami</label>
                                <div className="input-group flex-nowrap ">
                                    <span className="input-group-text" id="addon-wrapping">+998 </span>
                                    <input type="number" className="form-control bg-primary" id={"phoneNumber"} name={"phoneNumber"} placeholder="Telefon raqamini taxrirlang"
                                           aria-label="phoneNumber" aria-describedby="addon-wrapping" value={phoneNumber} onChange={e => setPhoneNumber(e.target.value)}/>
                                </div>
                                <label htmlFor="email" className={"text-dark mt-3"}>Ishchi email</label>
                                <input type="text" className={"form-control bg-primary"}  placeholder={"ishchi emailini taxrirlang"} id={"email"} name={"email"} value={email} onChange={e => setEmail(e.target.value)}/>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-mdb-dismiss="modal">Yopish</button>
                            <button type="button" className="btn btn-primary" onClick={() => editWorker()} >Saqlash</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}