import { useState } from "react";
import { fetchLogin } from "../api/auth";
import { LoginRequest } from "../models/requests/LoginRequest"; 
import { LoginResponse } from "../models/responses/LoginResponse"; 
import { useNavigate } from "react-router-dom";

import { fetchRegister } from "../api/auth"; 
import { RegisterRequest } from "../models/requests/RegisterRequest"; 

import { useEffect } from "react";
import { decodeToken } from "../utils/jwt";  
import { DecodeToken } from "../models/DecodedToken";

import { fetchLogout } from "../api/auth";

export const useLogin = () => {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleLogin = async (username: string, password: string) => {
        const loginData: LoginRequest = {
            username,
            password
        };

        setLoading(true);
        setError(null);

        try {
            const response: LoginResponse = await fetchLogin(loginData); 

            if (response.token) {
                sessionStorage.setItem("token", response.token);
                sessionStorage.setItem("username", response.username);
                window.location.href = "/home";

            }
        } catch (error) {
            setError("Login failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return { handleLogin, error, loading}; 
};


export const useRegister = () => {
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const navigate = useNavigate();


  const handleRegister = async (username: string, email: string, password: string, rePassword: string) => {

    const registerData: RegisterRequest = {
      username,
      email,
      password,
      rePassword,
    };

    setLoading(true);
    setError(null);

    try {
      const response = await fetchRegister(registerData);
      
        if (response.status === 200) {
            navigate("/login");
        }

    } catch (error) {
      setError("Registration failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return { handleRegister, error, loading };
};

export const useLogout = async () => {
  const response = await fetchLogout();
  if (response === "Successful") {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("username");
    window.location.href = "/login";
  }
  return null;
}


export const useDecodedToken = () => {
  const [decodedToken, setDecodedToken] = useState<DecodeToken | null>(null);
  const [loading, setLoading] = useState(true); // Trạng thái loading

  useEffect(() => {
    const token = sessionStorage.getItem("token");

    if (token) {
      const decoded = decodeToken(token);
      setDecodedToken(decoded);
    } else {
      setDecodedToken(null);
    }

    setLoading(false); // Kết thúc trạng thái loading
  }, []);

  return { decodedToken, loading }; // Trả về cả decodedToken và loading
};



