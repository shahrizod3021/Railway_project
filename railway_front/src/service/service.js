import {toast} from "react-toastify";
import axios from "axios";
import {BASE_URL} from "./BaseUrl.js";
import {Apis} from "./Apis.js";
import {resStatus} from "../handler/ResponseStatus.js";
import {useNavigate, useParams} from "react-router-dom";

export const GetWorker = async (setData) => {
    try {
        const res = await axios.get(BASE_URL + Apis.worker)
        setData(res.data)
    }catch (err){
    }
}

export const Validate = async (setCode) => {
    try {
        if (localStorage.getItem("code") !== null){
            setCode(localStorage.getItem("code"))
        }
    }catch (err){

    }
}
export const Tekshirish = async (setUser) => {
   if (localStorage.getItem("foydalanuvchijonxoja") !== null){
       setUser(localStorage.getItem("foydalanuvchijonxoja"))
   }
}

export const editPassword = async (data) =>{
    const check  = {
        password:data.password.trim().length === 0,
        prePassword:data.prePassword.trim().length === 0,
    }
    if (check.password || check.prePassword){
        return toast.warning("ma'lumot bo'sh")
    }
    if (data.password !== data.prePassword){
        return toast.warning("parol va tasdiqlash paroli bir hil bo'lishi lozim")
    }
    try {
        const res = await axios.post(BASE_URL + Apis.auth + "/reset", data)
        if (resStatus(res.status)){
            return toast.success(res.data.message)
        }
    }catch (err){
        if (err.response.status === 409){
            return toast.error(err.response.data.message)
        }
    }
}

export const GEToneWorker = async  (id, setData, setClothe) => {
try {
    const res = await axios.get(BASE_URL + Apis.worker + "/" + id)
    setData(res.data)
    setClothe(res.data.clothe)
}catch (err){

    }
}

export const EditClothe = async  (data, uuid, id) => {
    try {
        const res = await axios.put(BASE_URL + Apis.clothe + "/" + uuid + "/" + id, data)
        if (resStatus(res.status)){
            return toast.success(res.data.message)
        }
    }catch (err){
        if (err.response.status === 409){
            return toast.error(err.response.data.message)
        }
    }
}

export const GetShikoyat = async (setShikoyat) => {
    try {
        const res = await axios.get(BASE_URL + Apis.shikoyat)
        setShikoyat(res.data)
        console.log(res)
    }catch (err){
        console.log(err)
    }
}

export const EditWorker =  async (data, id) => {
    const check  = {
        name:data.name.trim().length === 0,
        surname:data.surname.trim().length === 0,
        email:data.email.trim().length === 0,
        phoneNumber:data.phoneNumber.length === 0,
    }
    if (check.name || check.surname || check.email || check.phoneNumber){
        return toast.warning("ma'lumot to'liq kiritilinmadi")
    }
    try {
        const  res = await axios.put(BASE_URL + Apis.worker + "/" + id, data)
        if (resStatus(res.status)){
            setTimeout(() => {
                window.location.reload()
            } , 2000)
            return toast.success(res.data.message)
        }
    }catch (err){
      if (err.response.status === 403 || err.response.status === 409){
          return toast.error(err.response.data.message)
      }
      toast.error(err.message)
    }
}

export const DeleteWorker = async (id) => {
    try {
        const res = await axios.delete(BASE_URL + Apis.worker + "/" + id)
        if (resStatus(res.status)){
            setTimeout(() => {
                window.location.reload()
            }, 2000)
            return toast.success(res.data.message)

        }
    }catch (err){
        if (err.response.status === 409){
            return toast.error(err.response.data.message)
        }
        toast.error(err.message)
    }
}

export const DeleteClothe = async (uuid, id)=>{
    try {
        const res = await axios.delete(BASE_URL + Apis.clothe + "/" + uuid + "/" + id)
        if (resStatus(res.status)){
            return toast.success(res.data.message)
        }
    }catch (err){
        if (err.response.status === 409){
            return toast.error(err.response.data.message)
        }
        if (err.response.status === 403){
            return toast.error("ma'lumotlar bazasida hatolik")
        }
    }
}
export const Verificate = async () => {
    try {
        const res = await axios.post(BASE_URL  + Apis.auth + "/verification")
        toast(res.data.message)
    }catch (err){
        if (err.response.status === 409){
            return toast.error(err.response.data.message)
        }

    }
}

export const Loginjon  = async (data) => {
    const check  = {
        password:data.password.trim().length === 0,
        phoneNumber:data.phoneNumber.length === 0,
    }
    if (check.password || check.phoneNumber){
        return toast.warning("ma'lumot bo'sh")
    }
    try {
        const res = await axios.post(BASE_URL + Apis.auth + "/login", data)
        localStorage.setItem("foydalanuvchijonxoja", res.data)
        if (resStatus(res.status)){
            localStorage.setItem("yol","/")
            return toast.success("hush kelibsiz admin biroz kuting")
        }
    }catch (err){
        if (err.response.status === 403){
            localStorage.setItem("yol","/auth/login")
           return  toast.error("ma'lumotlar hato qaytadan uruning")
        }
    }
}
export const GetEslatma = async (setData) => {
    try {
        const res = await axios.get(BASE_URL + Apis.eslatma)
        setData(res.data)
    }catch (err){
    }
}
export const SaveWorker = async (data) => {

    const check  = {
        name:data.name.trim().length === 0,
        surname:data.name.trim().length === 0,
    }
    if (check.name || check.surname){
        return toast.warning("ma'lumot bo'sh")
    }
    try {
        const res = await axios.post(BASE_URL + Apis.worker, data)
        if (resStatus(res.status)){
            toast.success(res.data.message)
        }
    }catch (err){
        if (err.response.status === 409){
            return toast.error(err.response.data.message)
        }
        toast(err.message)
    }
}


export const  SaveClothe = async  (id, data) => {
    const check  = {
        olishVaqti:data.olishVaqti.trim().length === 0,
        eslatishVaqti:data.eslatishVaqti.trim().length === 0,
    }
    if (check.olishVaqti || check.eslatishVaqti){
        return toast.warning("ma'lumot bo'sh")
    }
    try {
        const res = await axios.post(BASE_URL + Apis.clothe + "/" + id, data)
        if (resStatus(res.status)){
            toast.success(res.data.message)
        }
    }catch (err){
        if (err.response.status === 409){
            return toast.error(err.response.data.message)
        }
    }
}