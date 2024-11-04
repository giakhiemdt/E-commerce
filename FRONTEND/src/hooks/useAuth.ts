import { useState } from "react";
import { fetchLogin } from "../api/fetchAuth";
import { LoginRequest } from "../models/requests/AuthRequest";
import { RegisterRequest } from "../models/requests/AuthRequest";
import { LoginResponse } from "../models/responses/AuthResponse";
import { StatusResponse } from "../models/responses/StatusResponse";

import { fetchRegister } from "../api/fetchAuth";

import { useEffect } from "react";
import { decodeToken } from "../utils/jwt";  
import { DecodeToken } from "../models/DecodedToken";

import { fetchLogout } from "../api/fetchAuth";

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

            if (response.success) {
                localStorage.setItem("token", response.token);
            }else {
                setError(response.message);
            }
            return response.success

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
      const response: StatusResponse = await fetchRegister(registerData);

      if (!response.isSuccess) {
          setError(response.message)
      }
      return response.isSuccess;

    } catch (error) {
      setError("Registration failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return { handleRegister, error, loading };
};

export const useLogout = () => {
    const logout = async () => {
        await fetchLogout(); // Dù backend có phản hồi như thế nào thì frontend vẫn xóa không cần quan tâm.
        localStorage.removeItem("token");
        localStorage.removeItem("SellerInfoStatus");
    }

  return logout;
}


export const useDecodedToken = () => {
    const [decodedToken, setDecodedToken] = useState<DecodeToken | null>(null);
    const [loading, setLoading] = useState(true); // Trạng thái loading

    useEffect(() => {
        const updateToken = () => {
            const token = localStorage.getItem("token");

            if (token) {
                const decoded = decodeToken(token);
                setDecodedToken(decoded);
                console.log(decoded);
            } else {
                setDecodedToken(null);
            }

            setLoading(false);
        };

        window.addEventListener("storage", updateToken);

        updateToken();

        return () => {
            window.removeEventListener("storage", updateToken);
        };
    }, []);

    return { decodedToken, loading };
};



