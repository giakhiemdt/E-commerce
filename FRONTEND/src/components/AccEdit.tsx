import React, { useState } from "react";
import styles from "../styles/AccountManage.module.css";
import { useLocation } from "react-router-dom";
import { UpdateAccRequest } from "../models/requests/UpdateAccRequest";
import { useUpdateAccount } from "../hooks/useAdmin";

const AccEdit: React.FC = () => {
  const location = useLocation();
  const { account } = location.state || {};

  const [accountId] = useState(account?.id || null);
  const [username, setUserName] = useState(account?.username || null);
  const [email, setEmail] = useState(account?.email || null);
  const [role, setRole] = useState(account?.role || null);

  const { handleUpdateAccount, error, loading } = useUpdateAccount();

  const handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setRole(event.target.value);
    console.log("Selected Role:", event.target.value);
  };

  const handleSubmit = async () => {
    if (
      username !== account?.username ||
      email !== account?.email ||
      role !== account?.role
    ) {
      if (username === account?.username) {
        setUserName(null);
      }
      if (email === account?.email) {
        setEmail(null);
      }
      if (role !== account?.role) {
        setRole(null);
      }
      const updateRequest: UpdateAccRequest = {
        accountId,
        username,
        email,
        role,
      };

      const response = await handleUpdateAccount(updateRequest);
      if (response) {
        // window.location.href = "/admin/account";
      }
    }
  };

  return (
    <div>
      <div className={`container ${styles.container}`}>
        <h2 className={styles["admin-title"]}>Account Edit</h2> <br />
        <div className="row">
          <div className="col-5">
            <h2 className={styles["small-title"]}>Information</h2>
            <div className={`${styles.paddingleft25} ${styles.paddingright25}`}>
              <p>
                <span className={`label ${styles.info}`}>Id:</span> {accountId}
              </p>
              <p>
                <span className={`label ${styles.info}`}>Username:</span>{" "}
                {account?.username}
              </p>
              <p>
                <span className={`label ${styles.info}`}>Email:</span>{" "}
                {account?.email}
              </p>
              <p>
                <span className={`label ${styles.info}`}>Role:</span>{" "}
                {account?.role}
              </p>
              <p>
                <span className={`label ${styles.info}`}>Created Date:</span>{" "}
                {account?.createdDate}
              </p>
              <p>
                <span className={`label ${styles.info}`}>Active:</span>{" "}
                {account?.active ? "True" : "False"}
              </p>
            </div>
          </div>
          <div className="col-7">
            <h2 className={styles["small-title"]}>Edit</h2>
            <div className={`${styles.paddingleft25} ${styles.paddingright25}`}>
              <form
                onSubmit={(e) => {
                  e.preventDefault();
                  handleSubmit();
                }}
              >
                <label>Username:</label>
                <input
                  className={`form-control ${styles["input-bar"]}`}
                  value={username}
                  type="text"
                  placeholder="Username"
                  onChange={(e) => setUserName(e.target.value)}
                />
                <label>Email:</label>
                <input
                  className={`form-control ${styles["input-bar"]}`}
                  value={email}
                  type="text"
                  placeholder="Email"
                  onChange={(e) => setEmail(e.target.value)}
                />
                <label htmlFor="dropdown">Role:</label>
                <select
                  id="dropdown"
                  value={role}
                  onChange={handleChange}
                  className="form-control"
                >
                  <option value="ADMIN">ADMIN</option>
                  <option value="SELLER">SELLER</option>
                  <option value="USER">USER</option>
                </select>
                <div className="d-grid">
                  <button
                    className={`btn btn-primary btn-block ${styles["btn-submit"]}`}
                    type="submit"
                  >
                    {loading ? "Updating..." : "Update"}
                  </button>
                </div>
              </form>
              {error && <p className={styles["error-text"]}>{error}</p>}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AccEdit;
