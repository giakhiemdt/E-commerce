import React, { useEffect, useState } from "react";
import { useAccount } from "../../hooks/useAccount"
import { AccountResponse } from "../../models/responses/AccountResponse"

const Account: React.FC = () => {
    const [account, setAccount] = useState<AccountResponse | undefined>(undefined);
    const { handleAccount } = useAccount();

    useEffect(() => {

        const handleFetchAccount = async () => {
            const newAccount: AccountResponse | undefined = await handleAccount();
            setAccount(newAccount);
        }

        handleFetchAccount();
    }, []);

    return (
        <div style={{display: 'flex'}}>
            <div className={'col-5'}>
                <img style={{width: '-moz-available'}} src={'https://i.pinimg.com/originals/fa/ce/f3/facef30a9ab5f7f17197e15ab426b4d5.jpg'}/>
            </div>
            <div className={'col-7'}>
                {account && account.role === "ADMIN" ? (
                    <>
                        <h5>AccountId: {account.accountId}</h5>
                        <h5>UserName: {account.username}</h5>
                        <h5>Email: {account.email}</h5>
                        <h5>CreatedDate: {account.createdDate ?
                            new Date(account.createdDate).toLocaleDateString() : "N/A"}</h5>
                        <h5>Role: {account.role}</h5>
                        <h5>Active: {account.isactive ? (<>False</>): (<>True</>)}</h5>
                    </>
                ) : account ? (
                    <>
                        <h5>UserName: {account.username}</h5>
                        <h5>Email: {account.email}</h5>
                        <h5>CreatedDate: {account.createdDate ?
                            new Date(account.createdDate).toLocaleDateString() : "N/A"}</h5>
                    </>
                ):(null)}
                <h5></h5>
            </div>
        </div>
    )
}

export default Account;

