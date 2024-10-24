import React, { useState } from "react";
import styles from "../../styles/AccountManage.module.css";
import { UpdateProfileRequest } from "../../models/requests/NormalRequest";
import { useUpdateProfile } from "../../hooks/useSeller";

const ProfileEdit: React.FC = () => {
  const { handleUpdateProfile, error, loading } = useUpdateProfile();
  const [fullname, setFullName] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [phone, setPhone] = useState<string>("");
  const isSellerInfoStatus = sessionStorage.getItem("SellerInfoStatus");

  const handleSubmit = async () => {
    const updateRequest: UpdateProfileRequest = {
      fullname,
      phone,
      address,
    };

    const response = await handleUpdateProfile(updateRequest);

    if (response) {
      window.location.href = "/home";
    }
  };

  return (
    <div className={styles.page}>
      <div className={`container ${styles.container}`}>
        <h2 className={styles["admin-title"]}>Profile</h2> <br />
        <div className="row">
          <div className="col-12">
            <h2 className={styles["small-title"]}>
              {isSellerInfoStatus ? "Add Profile" : "Update Profile"}
            </h2>
            <div className={`${styles.paddingleft25} ${styles.paddingright25}`}>
              <form
                onSubmit={(e) => {
                  e.preventDefault();
                  handleSubmit();
                }}
              >
                <label>Full Name:</label>
                <input
                  className={`form-control ${styles["input-bar"]}`}
                  // value={isSellerInfoStatus ? "" : fullname}
                  type="text"
                  placeholder="FullName"
                  onChange={(e) => setFullName(e.target.value)}
                />
                <label>Phone:</label>
                <input
                  className={`form-control ${styles["input-bar"]}`}
                  // value={isSellerInfoStatus ? "" : phone}
                  type="text"
                  placeholder="Phone"
                  onChange={(e) => setPhone(e.target.value)}
                />
                <label>Adress:</label>
                <input
                  className={`form-control ${styles["input-bar"]}`}
                  // value={isSellerInfoStatus ? "" : address}
                  type="text"
                  placeholder="Address"
                  onChange={(e) => setAddress(e.target.value)}
                />
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

export default ProfileEdit;
