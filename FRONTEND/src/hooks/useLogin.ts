import { useState } from "react";
import { fetchLogin } from "../api/auth";
import { LoginRequest } from "../models/requests/LoginRequest"; 
import { LoginResponse } from "../models/responses/LoginResponse"; 
import { useNavigate } from "react-router-dom";

export const useLogin = () => {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [user, setUser] = useState<LoginResponse | null>(null); 
    const navigate = useNavigate();

    const handleLogin = async (username: string, password: string) => {
        const loginData: LoginRequest = {
            username,
            password
        };

        setLoading(true);
        setError(null);

        try {
            const response: LoginResponse = await fetchLogin(loginData); // Sử dụng await

            if (response.token) {
                sessionStorage.setItem("token", response.token);
                sessionStorage.setItem("username", response.username);
                
                navigate("/home");

                // setUser(response); 
                // return response; 
            }
        } catch (error) {
            setError("Login failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return { handleLogin, error, loading, user }; 
};
