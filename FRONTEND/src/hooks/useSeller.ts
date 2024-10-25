import { useState } from "react";
import { UpdateProfileRequest } from "../models/requests/NormalRequest";
import { fetchProductList, fetchUpdateProfile } from "../api/seller";

export const useUpdateProfile = () => {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleUpdateProfile = async (updateRequest: UpdateProfileRequest) => {
        setLoading(true);
        setError(null);
        
        try {
            const response = await fetchUpdateProfile(updateRequest);

            if (response) {
                sessionStorage.removeItem("SellerInfoStatus");
                
                if (response) {
                    sessionStorage.removeItem("SellerInfoStatus");
                    
                    window.location.href = "/home";
                }
                return response;
            }
        }catch (error) {
            setError("Fail to load account list.");
        }finally {
            setLoading(false);
        }

    };

    return {handleUpdateProfile, error, loading};

};

export const useGetProductList = () => {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleGetProductList = async () => {
        setLoading(true);
        setError(null);
        
        try {
            const response = await fetchProductList();

            if (response) {
                return response;
            }
        }catch (error) {
            setError("Fail to load account list.");
        }finally {
            setLoading(false);
        }

    };

    return {handleGetProductList, error, loading};
};