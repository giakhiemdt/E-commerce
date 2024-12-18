import axios from "axios";
import {RegisterRequest} from "../models/requests/AuthRequest";
import { LoginRequest } from "../models/requests/AuthRequest";

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

export const fetchLogout = async () => {
    const token = localStorage.getItem("token");

    console.log("token: " + token);

    try {
        const response = await axios.delete("http://localhost:8080/api/logout", { // Đặt body là null nếu không cần
            headers: {
                AuthenticationToken: `Bearer ${token}`, // Sửa từ "AuthenticationToken" thành "Authorization"
            }
        });
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.log('Error fetching data:', error);
        return null;
    }
}