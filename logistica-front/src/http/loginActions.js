import { Navigate, useNavigate } from "react-router-dom";
import { useLocalStorage } from "../hooks/useLocalStorage";
import {instance} from "./axiosConfig";


export const loginRequest = async (loginData) =>{
    const data = await instance.post("/auth/login", loginData)
    const navigate = useNavigate();
    
    if(data.status == 200){
        let token = "Bearer " + data.data;        
        instance.defaults.headers["Authorization"] = token;
        localStorage.setItem("user", token);
        navigate("/login");
    } 
}