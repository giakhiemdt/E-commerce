import axios from 'axios';
import { UpdateAccRequest } from '../models/requests/UpdateAccRequest';

export const fetchAccoutList = async () => {
    const token = sessionStorage.getItem("token");

    try {
        const response = await axios.get("http://localhost:8080/api/accounts", {
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

export const fetchUpdateIsActive = async (accountId: number) => {
    const token = sessionStorage.getItem("token");

    try {
        const response = await axios.post(
            "http://localhost:8080/api/change-account-status",
            { accountId }, 
            {
                headers: {
                    AuthenticationToken: `Bearer ${token}`,
                },
            }
        );
        return response.data; 
    } catch (error) {
        console.error("Error updating account status:", error);
        return null; 
    }
};

export const fetchUpdateAccount = async(updateAccRequest: UpdateAccRequest) => {
    const token = sessionStorage.getItem("token")

    try {
        const response = await axios.post("http://localhost:8080/api/edit-account", 
        {
            accountId: updateAccRequest.accountId,
            username: updateAccRequest.username,
            email: updateAccRequest.email,
            role: updateAccRequest.role,
        }, 
        {
            headers: {
                AuthenticationToken: `Bearer ${token}`,
            }
        }
    );
    return response.data;
    }catch (error) {
        console.error("Error updating account.");
        return null;
    }
};