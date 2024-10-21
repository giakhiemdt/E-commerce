import { useState } from "react";
import { fetchAccoutList } from "../api/admin";
import { AccountListResponse } from "../models/responses/AccountListResponse";

export const useGetAccountList = () => {
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