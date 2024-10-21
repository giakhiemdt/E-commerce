import React from "react";
import { useEffect, useState } from "react";
import styles from "../styles/AccountManage.module.css";
import { useGetAccList, useChangeAccStatus } from "../hooks/useAdmin";
import { Seller, Admin, User } from "../models/responses/AccountListResponse";
import { useNavigate } from "react-router-dom";

const AccList: React.FC = () => {
  const [sellers, setSellers] = useState<Seller[]>([]);
  const [admins, setAdmins] = useState<Admin[]>([]);
  const [users, setUsers] = useState<User[]>([]);

  const { handleGetAccountList, error, loading } = useGetAccList();

  const fetchData = async () => {
    try {
      const AccountListResponse = await handleGetAccountList();
      if (AccountListResponse) {
        setSellers(AccountListResponse.SELLER || []);
        setAdmins(AccountListResponse.ADMIN || []);
        setUsers(AccountListResponse.USER || []);
      }
    } catch (err) {
      console.error("Error fetching account list:", err);
    }
  };
  useEffect(() => {
    fetchData();
  }, []);

  const { handleChangeAccountStatus } = useChangeAccStatus();

  const handleChangeStatus = async (id: number) => {
    try {
      const response = await handleChangeAccountStatus(id);
      if (response) {
        console.log(`Account ${id} status changed successfully`);
        fetchData();
      }
    } catch (error) {
      console.error("Error changing status:", error);
    }
  };

  const navigate = useNavigate();

  const handleEdit = (account: Admin | Seller | User) => {
    navigate(`/admin/account/edit/${account.id}`, { state: { account } });
  };

  return (
    <div>
      <div className={`container ${styles.container}`}>
        <div className="container mt-3">
          <h2 className={styles["admin-title"]}>Account List</h2>

          {loading ? (
            <p>Loading...</p>
          ) : error ? (
            <p>Error: {error}</p>
          ) : (
            <div>
              <h2>Admin</h2>
              <table className="table">
                <thead className="table-dark">
                  <tr>
                    <th>ID</th>
                    <th>UserName</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Created Date</th>
                    <th className={styles["action-custom"]}>Action</th>
                  </tr>
                </thead>
                <tbody>
                  {admins.map((admin) => (
                    <tr key={admin.id}>
                      <td>{admin.id}</td>
                      <td>{admin.username}</td>
                      <td>{admin.email}</td>
                      <td>{admin.role}</td>
                      <td>{admin.createdDate}</td>
                      <td>
                        <button
                          className={`btn btn-primary ${styles["btn-custom"]} ${styles["btn-detail"]}`}
                        >
                          Detail
                        </button>
                        <button
                          className={`btn btn-primary ${styles["btn-custom"]} ${styles["btn-edit"]}`}
                          onClick={() => handleEdit(admin)}
                        >
                          Edit
                        </button>
                        <button
                          className={`btn btn-primary ${styles["btn-custom"]} ${
                            admin.active
                              ? styles["btn-inactive"]
                              : styles["btn-active"]
                          }`}
                          onClick={() => handleChangeStatus(admin.id)}
                        >
                          {admin.active ? "InActive" : "Active"}
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}

          {/* Hiển thị danh sách Seller */}
          <h2>Seller</h2>
          <table className="table">
            <thead className="table-dark">
              <tr>
                <th>ID</th>
                <th>UserName</th>
                <th>Email</th>
                <th>Role</th>
                <th>Created Date</th>
                <th className={styles["action-custom"]}>Action</th>
              </tr>
            </thead>
            <tbody>
              {sellers.map((seller) => (
                <tr key={seller.id}>
                  <td>{seller.id}</td>
                  <td>{seller.username}</td>
                  <td>{seller.email}</td>
                  <td>{seller.role}</td>
                  <td>{seller.createdDate}</td>
                  <td>
                    <button
                      className={`btn btn-primary ${styles["btn-custom"]} ${styles["btn-detail"]}`}
                    >
                      Detail
                    </button>
                    <button
                      className={`btn btn-primary ${styles["btn-custom"]} ${styles["btn-edit"]}`}
                      onClick={() => handleEdit(seller)}
                    >
                      Edit
                    </button>
                    <button
                      className={`btn btn-primary ${styles["btn-custom"]} ${
                        seller.active
                          ? styles["btn-inactive"]
                          : styles["btn-active"]
                      }`}
                      onClick={() => handleChangeStatus(seller.id)}
                    >
                      {seller.active ? "InActive" : "Active"}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {/* Hiển thị danh sách User */}
          <h2>User</h2>
          <table className="table">
            <thead className="table-dark">
              <tr>
                <th>ID</th>
                <th>UserName</th>
                <th>Email</th>
                <th>Role</th>
                <th>Created Date</th>
                <th className={styles["action-custom"]}>Action</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.username}</td>
                  <td>{user.email}</td>
                  <td>{user.role}</td>
                  <td>{user.createdDate}</td>
                  <td>
                    <button
                      className={`btn btn-primary ${styles["btn-custom"]} ${styles["btn-detail"]}`}
                    >
                      Detail
                    </button>
                    <button
                      className={`btn btn-primary ${styles["btn-custom"]} ${styles["btn-edit"]}`}
                      onClick={() => handleEdit(user)}
                    >
                      Edit
                    </button>
                    <button
                      className={`btn btn-primary ${styles["btn-custom"]} ${
                        user.active
                          ? styles["btn-inactive"]
                          : styles["btn-active"]
                      }`}
                      onClick={() => handleChangeStatus(user.id)}
                    >
                      {user.active ? "InActive" : "Active"}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default AccList;
