import React, { useState } from "react";
import { useRegister } from "../hooks/useRegister"; // Import logic xử lý
import styles from "../styles/Register.module.css";

const RegisterForm: React.FC = () => {
  const { handleRegister, error: apiError, loading } = useRegister(); // Sử dụng logic từ useRegister
  const [username, setUsername] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [repassword, setRepassword] = useState<string>("");
  const [error, setError] = useState<string>("");

  const submitHandler = async () => {
    setError("");
    if (username.length < 6) {
      setError("Username at least 6 charater.");
      return;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError("Invalid email.");
      return;
    }

    if (password !== repassword) {
      setError("Passwords do not match");
      return;
    }

    const response = await handleRegister(
      username,
      email,
      password,
      repassword
    );

    if (response.status === 200 && response.data.success) {
      return response.data;
    } else {
      // Đăng ký không thành công
      setError("Registration failed. Please try again.");
      return null; // Trả về null nếu đăng ký thất bại
    }
  };

  return (
    <div className={styles.page}>
      <div className={`container ${styles.container}`}>
        <h3 className={styles["register-title"]}>Register</h3>
        <br></br>
        <div className={styles["register-form"]}>
          <form>
            <a>User Name:</a>
            <input
              className={`form-control ${styles["register-bar"]}`}
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <h6 className={styles.magrintop20}></h6>
            <a>Email:</a>
            <input
              className={`form-control ${styles["register-bar"]}`}
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <h6 className={styles.magrintop20}></h6>
            <a>Password:</a>
            <input
              className={`form-control ${styles["register-bar"]}`}
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <h6 className={styles.magrintop20}></h6>
            <a>Re-Password:</a>
            <input
              className={`form-control ${styles["register-bar"]}`}
              type="password"
              placeholder="Re-Password"
              value={repassword}
              onChange={(e) => setRepassword(e.target.value)}
            />
            <button
              className={`btn btn-primary ${styles["register-button"]}`}
              type="button"
              onClick={submitHandler}
              disabled={loading}
            >
              {loading ? "Registering..." : "Register"}
            </button>
          </form>
          {error && <p className={styles["error-text"]}>{error}</p>}
          {apiError && <p className={styles["error-text"]}>{apiError}</p>}
        </div>
      </div>
    </div>
  );
};

export default RegisterForm;
