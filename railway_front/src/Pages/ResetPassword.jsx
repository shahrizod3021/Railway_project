import {useEffect, useState} from "react";
import {editPassword, Validate} from "../service/service.js";
import {NotFoundaPage} from "../Component/NotFoundaPage.jsx";
import {useNavigate} from "react-router-dom";

export const ResetPassword = () => {
    const [code, setCode] = useState('');
    const validate = async () => {
        await Validate(setCode)
    }
    useEffect(() => {
        validate()
    }, [])



    return(
        <div>
            {code.length === 0 ? (
                <NotFoundaPage/>
            ) : (
                <Passwordjon/>
            )}
        </div>
    )
}

const Passwordjon = () => {
    const [password, setPassword] = useState('')
    const [prePassword, setPrePassword] = useState('')
    const navigate = useNavigate();

    const reset = async () => {
        const data = {
            password,prePassword
        }
        await editPassword(data)
        setPassword("")
        setPrePassword("")
        setTimeout(() => {
            navigate("/auth/login")
        }, 2000)
    }
    return(
        <div>
                <div className={"verificate"} >
                    <form>
                        <label htmlFor="password" className={"text-dark"}>Parol</label>
                        <input type="password" className={"form-control bg-secondary mb-2"} placeholder={"Yangi parol"} id={"password"} name={"password"} value={password} onChange={e => setPassword(e.target.value)}/>
                        <label htmlFor="prePassword" className={"text-dark"}>Parolni tasdiqlash</label>
                        <input type="password" className={"form-control bg-secondary mb-2"} placeholder={"Yangi parol"} id={"prePassword"} name={"prePassword"} value={prePassword} onChange={e => setPrePassword(e.target.value)}/>
                        <button type={"button"} className={"btn btn-sm btn-success"} onClick={() => reset()}>kiritish</button>
                    </form>
                </div>
        </div>
    )
}