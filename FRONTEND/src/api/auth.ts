import axios from "axios";
import {RegisterRequest} from "../models/requests/RegisterRequest";
import { LoginRequest } from "../models/requests/LoginRequest";

export const fetchRegister = async (register : RegisterRequest) => {
    try {
        const response = await axios.post("http://localhost:8080/api/register", register);
        console.log(response.data);
        return response.data;
    }catch (error) {
        console.log('Error fetching data:', error);
        return null;
    }
};

export const fetchLogin = async (login : LoginRequest) => {
    try {
        const response = await axios.post("http://localhost:8080/api/login", login);
        console.log(response.data);
        return response.data;
    }catch (error) {
        console.log('Error fetching data:', error);
        return null;
    }
};
