import React, { useEffect, useState } from "react";
import { AccountsResponse } from "../../models/responses/AccountListWithRoleResponse";
import { useAccounts } from "../../hooks/useAccount";

const Accounts: React.FC = () => {
    const [accounts, setAccounts] = useState<AccountsResponse | undefined>(undefined); // Khởi tạo là undefined
    const { handleAccounts } = useAccounts();

    useEffect(() => {
        const getAccounts = async () => {
            const response: AccountsResponse | undefined = await handleAccounts();
            setAccounts(response);
        };
        getAccounts();
    }, []);

    return (
        <div>
            <br/>
            <h4>Admin:</h4>
            {accounts && accounts.ADMIN && accounts.ADMIN.length > 0 ? (
                <table>
                    <thead>
                    <tr>
                        <th>AccountId</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>CreatedDate</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {accounts.ADMIN.map((admin) => (
                        <tr key={admin.accountId}>
                            <td>{admin.accountId}</td>
                            <td>{admin.username}</td>
                            <td>{admin.email}</td>
                            <td>{admin.role}</td>
                            <td>
                                {admin.createdDate
                                    ? new Date(admin.createdDate).toLocaleDateString()
                                    : "N/A"}
                            </td>
                            <td>{admin.isactive ? "False" : "True"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <p>No admin data available.</p>
            )}

            <br/>
            <h4>Seller:</h4>
            {accounts && accounts.SELLER && accounts.SELLER.length > 0 ? (
                <table>
                    <thead>
                    <tr>
                        <th>AccountId</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>CreatedDate</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {accounts.SELLER.map((seller) => (
                        <tr key={seller.accountId}>
                            <td>{seller.accountId}</td>
                            <td>{seller.username}</td>
                            <td>{seller.email}</td>
                            <td>{seller.role}</td>
                            <td>
                                {seller.createdDate
                                    ? new Date(seller.createdDate).toLocaleDateString()
                                    : "N/A"}
                            </td>
                            <td>{seller.isactive ? "False" : "True"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <p>No seller data available.</p>
            )}

            <br/>
            <h4>User:</h4>
            {accounts && accounts.USER && accounts.USER.length > 0 ? (
                <table>
                    <thead>
                    <tr>
                        <th>AccountId</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>CreatedDate</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {accounts.USER.map((user) => (
                        <tr key={user.accountId}>
                            <td>{user.accountId}</td>
                            <td>{user.username}</td>
                            <td>{user.email}</td>
                            <td>{user.role}</td>
                            <td>
                                {user.createdDate
                                    ? new Date(user.createdDate).toLocaleDateString()
                                    : "N/A"}
                            </td>
                            <td>{user.isactive ? "False" : "True"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <p>No user data available.</p>
            )}
        </div>
    );
};

export default Accounts;
