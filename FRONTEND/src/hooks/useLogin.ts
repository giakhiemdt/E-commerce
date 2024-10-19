import { useState } from "react";
import { fetchLogin } from "../api/auth";
import { LoginRequest } from "../models/requests/LoginRequest";

export const useLogin = () => {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleLogin = async (username: string, password: string) => {

        const loginData: LoginRequest = {
            username,
            password
        }

        setLoading(true);
        setError(null);

        try {
            const response = fetchLogin(loginData);
            return response;
        }catch(error) {
            setError("Login failed. Please try again.");
        }finally {
            setLoading(false);
        }
    }

    return {handleLogin, error, loading}
};