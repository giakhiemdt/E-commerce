import { useState } from "react";
import { fetchRegister } from "../api/auth"; // Import hàm gọi API
import { RegisterRequest } from "../models/requests/RegisterRequest"; // Import model cho request

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
      const response = await fetchRegister(registerData);
      return response; 
    } catch (error) {
      setError("Registration failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return { handleRegister, error, loading };
};
