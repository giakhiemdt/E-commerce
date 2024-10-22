import { useState } from "react";
import { fetchAccoutList, fetchUpdateAccount, fetchUpdateIsActive } from "../api/admin";
import { AccountListResponse, Admin, Seller, User } from "../models/responses/AccountListResponse";
import { UpdateAccRequest } from "../models/requests/UpdateAccRequest";

export const useGetAccList = () => {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleGetAccountList = async () => {
        setLoading(true);
        setError(null);

        try {
            const response: AccountListResponse = await fetchAccoutList();

            if (response) {
                return response;
            }
        }catch (error) {
            setError("Fail to load account list.");
        }finally {
            setLoading(false);
        }
    };
    return {handleGetAccountList, error, loading};
};

export const useChangeAccStatus = () => {
    const [error, setError] = useState<String | null>(null);
    const [loading, setLoading] = useState<boolean| null>(null);

    const handleChangeAccountStatus = async(accountId: number) => {
        setLoading(true);
        setError(null);
        if (accountId) {
            try {
                const response = await fetchUpdateIsActive(accountId);
    
                if (response) {
                    return response;
                }
            }catch(error) {
                setError("Fail to update.");
            }finally {
                setLoading(false);
            }
        }
        
    }
    return {handleChangeAccountStatus, error, loading}
};

export const useUpdateAccount = () => {
    const [error, setError] = useState<String | null>(null);
    const [loading, setLoading] = useState<boolean| null>(null);

    const handleUpdateAccount = async(updateRequest: UpdateAccRequest, account: Admin | Seller | User) => {
        setError(null);
        setLoading(true);
        if (updateRequest.username === account?.username) {
            updateRequest.username = null;
          }
          if (updateRequest.email === account?.email) {
            updateRequest.email = null;
          }
          if (updateRequest.role === account?.role) {
            updateRequest.role = null;
          }
        try {
            const response = await fetchUpdateAccount(updateRequest);

            if (response) {
                return response;
            }
        }catch(error) {
            setError("Fail to update.");
        }finally {
            setLoading(false);
        }
    }
    return {handleUpdateAccount, error, loading}
};
