import logo from '../assets/logo-uzbekistan.png'
import {Link, useNavigate} from "react-router-dom";
import {Loginjon, Verificate} from "../service/service.js";
import {useState} from "react";

export const Login = () => {
    const [password, setPassword] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')
    const navigate = useNavigate();
    const [yol, setYol] = useState('')
    const login = async () => {
        const data = {
            phoneNumber, password
        }
        await Loginjon(data)
        setPassword("")
        setPhoneNumber("")
        setTimeout(() => {
            navigate(localStorage.getItem("yol"))
            localStorage.removeItem("yol")
        }, 2000)
    }
    const verificate = async () => {
        try {
            await Verificate()
        } catch (err) {
        }
    }

    return (
        <div>
            <div className="card card-authentication1 mx-auto my-5">
                <div className="card-body">
                    <div className="card-content p-2">
                        <div className="text-center">
                            <img src={logo} alt="logo icon" width={"20%"}/>
                        </div>
                        <div className="card-title text-uppercase text-center py-3 text-dark">Sign In</div>
                        <form>
                            <div className="form-group">
                                <label htmlFor="phoneNumber" className="sr-only ">Telefon raqam</label>
                                <div className="input-group flex-nowrap mb-4">
                                    <span className="input-group-text" id="addon-wrapping">+998</span>
                                    <input type="number" className="form-control bg-secondary"
                                           placeholder="Telefon raqam"
                                           aria-label="phoneNumber" aria-describedby="addon-wrapping" id={"phoneNumber"}
                                           name={"phoneNumber"} value={phoneNumber}
                                           onChange={e => setPhoneNumber(e.target.value)}/>
                                </div>
                            </div>
                            <div className="form-group">
                                <label htmlFor="exampleInputPassword" className="sr-only">Password</label>
                                <div className="position-relative has-icon-right">
                                    <input type="password" id="password"
                                           name={"password"} value={password}
                                           onChange={e => setPassword(e.target.value)}
                                           className="form-control bg-secondary" placeholder="parol"/>
                                    <div className="form-control-position">
                                        <i className="icon-lock"></i>
                                    </div>
                                </div>
                            </div>
                            <div className="form-row">
                                <div className="form-group col-6">
                                    <div className="icheck-material-white">
                                        <input type="checkbox" id="user-checkbox" checked=""/>
                                        <label htmlFor="user-checkbox">Remember me</label>
                                    </div>
                                </div>
                                <div className="form-group col-6 text-right">
                                    <Link to="/auth/verification" onClick={() => verificate()}>parolni
                                        unutdingizmi?</Link>
                                </div>

                            </div>
                            <button type="button" className="btn btn-light btn-block" onClick={() => login()}>Sign In
                            </button>

                        </form>
                    </div>
                </div>

            </div>
        </div>
    )
}