import axios from "axios";

export const fetchAccount = async () => {
    const token = localStorage.getItem("token");
    try {
        const response = await axios.get("http://localhost:8080/api/account", {
            headers: {
                AuthenticationToken: `Bearer ${token}`, // Sửa từ "AuthenticationToken" thành "Authorization"
            }
        });
        console.log(response.data);
        return response.data;
    }catch(error) {
        console.log('Error fetching data:', error);
        return null;
    }
}

export const fetchAccounts = async () => {
    const token = localStorage.getItem("token");
    try {
        const response = await axios.get("http://localhost:8080/api/accounts", {
            headers: {
                AuthenticationToken: `Bearer ${token}`, // Sửa từ "AuthenticationToken" thành "Authorization"
            }
        });
        console.log(response.data.accounts);
        return response.data.accounts;
    }catch(error) {
        console.log('Error fetching data:', error);
        return null;
    }
}