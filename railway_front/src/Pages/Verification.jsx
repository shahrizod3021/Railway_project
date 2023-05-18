import {useState} from "react";
import {toast} from "react-toastify";
import axios from "axios";
import {BASE_URL} from "../service/BaseUrl.js";
import {Apis} from "../service/Apis.js";
import  '../assets/css/Media.css'
import {useNavigate} from "react-router-dom";
export const Verification = () => {
    const [code, setCode] = useState('')
    const navigate = useNavigate()
    const verificate = async () => {
        if (code.trim().length === 0){
            return toast.warning("ma'lumot bo'sh")
        }
        const data = {
            code
        }
        try {
            const res = await axios.post(BASE_URL + Apis.auth + "/code", data)
            if (res.data.success === true){
                localStorage.setItem("code",res.data.message)
                toast.success("Tasdiqlash paroli to'g'ri")
                navigate('/auth/resetpassword')
            }
        }catch (err){
            if (err.response.status === 400 || err.response.status === 409){
                return toast.error(err.response.data.message)
            }
        }
    }
    return (
        <div>
            <div className={"verificate"} >
                <form>
                    <label htmlFor="verification" className={"text-dark"}>Tasdiqlash paroli</label>
                    <input type="text" className={"form-control bg-secondary mb-2"} placeholder={"Tasdiqlash paroli"} id={"verification"} name={"verification"} value={code} onChange={e => setCode(e.target.value)}/>
                    <button type={"button"} className={"btn btn-sm btn-success"} onClick={() => verificate()}>Tasdiqlash</button>
                </form>
            </div>
        </div>
    )
}