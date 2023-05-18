import {useEffect, useState} from "react";
import {GetEslatma} from "../service/service.js";
import  '../assets/css/Media.css'
export const Eslatma = () => {
    const [eslatma, setEslatma] = useState([])
    const getEslatma = async () => {
        try {
            await GetEslatma(setEslatma)
        }catch (err){
        }
    }
    useEffect(() => {
        getEslatma()
    }, [])

    return (
        <div>
            {eslatma.length === 0 ? (
                <>
                    <div className="alert alert-info d-flex align-items-center">
                        <div className="alert-icon">
                            <i className="fas fa-info"></i>
                        </div>
                        <span><strong>ESLATMA!</strong> Sizda hali eslatmalar mavjud emas.</span>
                    </div>
                </>
            ) : (
                <>
                    {eslatma.map((item) => (
                        <div className={"eslatmajon col-4"}>
                            <div className="card profile-card-2" style={{borderTopLeftRadius:"1px", borderBottomLeftRadius:'40px'}}>
                                <div className="card-body">
                                    <h4 className={"card-title text-dark"}><span className={"text-primary"}>{item.name}</span> / {item.year}/{item.oy}/{item.kun}</h4>
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