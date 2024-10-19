import React, { useState } from "react";
import { useLogin } from "../hooks/useLogin";
import styles from "../styles/Login.module.css";

interface LoginFormProps {
  onLoginSuccess: (data: any) => void;
}

const LoginForm: React.FC<LoginFormProps> = ({ onLoginSuccess }) => {
  const { handleLogin, error, loading } = useLogin();
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const submitHandler = async () => {
    const response = await handleLogin(username, password);
    if (response) {
      onLoginSuccess(response); // Gọi hàm callback với dữ liệu phản hồi
    }
  };

  return (
    <div className={styles.page}>
      <div className={`container ${styles.container}`}>
        <h3 className={styles["login-title"]}>Login</h3>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <br />
        <div className={styles["login-form"]}>
          <form
            onSubmit={(e) => {
              e.preventDefault();
              submitHandler();
            }}
          >
            <label>User Name:</label>
            <input
              className={`form-control ${styles["login-bar"]}`}
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <h6 className={styles.magrintop20}></h6>
            <label>Password:</label>
            <input
              className={`form-control ${styles["login-bar"]}`}
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <button
              className={`btn btn-primary ${styles["login-button"]}`}
              type="submit"
              disabled={loading}
            >
              {loading ? "Logging..." : "Login"}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
