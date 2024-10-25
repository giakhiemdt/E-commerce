import React from "react";
import { useEffect, useState } from "react";
import styles from "../../../styles/AccountManage.module.css";
import { useChangeAccountStatus } from "../../../hooks/useAdmin";
import { Seller, Admin, User } from "../../../models/responses/AdminResponse";
import { useNavigate } from "react-router-dom";
import { useGetProductList } from "../../../hooks/useSeller";

const ProductList: React.FC = () => {
  const [sellers, setSellers] = useState<Seller[]>([]);
  const [admins, setAdmins] = useState<Admin[]>([]);
  const [users, setUsers] = useState<User[]>([]);

  const { handleGetProductList, error, loading } = useGetProductList();

  const fetchData = async () => {
    try {
      const ProductListResponse = await handleGetProductList();
    } catch (err) {
      console.error("Error fetching account list:", err);
    }
  };
  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      <div className={`container ${styles.container}`}>
        <div className="container mt-3">
          <h2 className={styles["admin-title"]}>Product List</h2>

          {loading ? (
            <p>Loading...</p>
          ) : error ? (
            <p>Error: {error}</p>
          ) : (
            <div>
              <h2>Your Product</h2>
              <table className="table">
                <thead className="table-dark">
                  <tr>
                    <th>Product ID</th>
                    <th>Name</th>
                    <th>Quantity</th>
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
                          //   onClick={() => handleEdit(admin)}
                        >
                          Edit
                        </button>
                        <button
                          className={`btn btn-primary ${styles["btn-custom"]} ${
                            admin.active
                              ? styles["btn-inactive"]
                              : styles["btn-active"]
                          }`}
                          //   onClick={() => handleChangeStatus(admin.id)}
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
        </div>
      </div>
    </div>
  );
};

export default ProductList;
