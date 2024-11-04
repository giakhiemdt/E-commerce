import { useState } from "react";
import { fetchAccount, fetchAccounts } from "../api/fetchAccount";
import { AccountResponse } from "../models/responses/AccountResponse";
import { AccountsResponse } from "../models/responses/AccountListWithRoleResponse";

export const useAccount = () => {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleAccount = async () => {
        setLoading(true);
        setError(null);

        try {
            const response: AccountResponse = await fetchAccount();

            if (response) {
                return response;
            }
        }catch (error) {
            setError("Get Account information failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };
    return { handleAccount, loading, error };
}

export const useAccounts = () => {
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const handleAccounts = async () => {
        setLoading(true);
        setError(null);

        try {
            const response: AccountsResponse = await fetchAccounts();

            if (response) {
                return response;
            }
        }catch (error) {
            setError("Get Account information failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };
    return { handleAccounts, loading, error };
}