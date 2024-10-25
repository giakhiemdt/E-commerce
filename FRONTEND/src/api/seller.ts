import axios from "axios";
import { UpdateProfileRequest } from "../models/requests/NormalRequest";

export const fetchUpdateProfile = async (updataProfileRequest :UpdateProfileRequest) => {
    const token = sessionStorage.getItem("token");

    try {
        const response = await axios.post("http://localhost:8080/api/update-profile", 
        {
            fullname: updataProfileRequest.fullname,
            phone: updataProfileRequest.phone,
            address: updataProfileRequest.address,
        },
            {
            headers: {
                AuthenticationToken: `Bearer ${token}`, 
            }
        });
        console.log(response.data);
        return response.data;
    }catch (error){
        console.log('Error fetching data:', error);
        return null;
    }
};

export const fetchProductList = async() => {
    const token = sessionStorage.getItem("token");

    try {
        const response = await axios.get("http://localhost:8080/api/products", 
            {
                headers: {
                    AuthenticationToken: `Bearer ${token}`,                
                }
            }
        ); 
        console.log(response.data);
        return response.data;
    }catch (error){
        console.log('Error fetching data:', error);
        return null;
    }

};

export const fetchProductType = async() => {
    const token = sessionStorage.getItem("token");

    try {

        const response = await axios.get("http://localhost:8080/api/product-types", 
            {
                headers: {
                    AuthenticationToken: `Bearer ${token}`,                
                }
            }
        );
        console.log(response.data);
        return response.data;
    }catch (error){
        console.log('Error fetching data:', error);
        return null;
    }
};