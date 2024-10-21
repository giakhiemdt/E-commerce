import React, { useState } from "react";
import { useLogin } from "../hooks/useAuth";
import Navbar from "../components/Navbar";
import Subnavbar from "../components/Subnavbar";
import Footer from "../components/Footer";
import styles from "../styles/Login.module.css";

const Login: React.FC = () => {
  const { handleLogin, error: apiError, loading } = useLogin();
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState<string>("");

  const submitHandler = async () => {
    if (username === "") {
      setError("Please fill username.");
      return;
    }

    if (password === "") {
      setError("Please fill password.");
      return;
    }

    const response = await handleLogin(username, password);
    return response;
  };

  return (
    <div className={styles.page}>
      <Navbar />
      <Subnavbar />
      <div className={`container ${styles.container}`}>
        <h3 className={styles["login-title"]}>Login</h3>
        <br />
        <div className={`${styles["login-form"]}`}>
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
          {error && <p className={styles["error-text"]}>{error}</p>}
          {apiError && <p className={styles["error-text"]}>{apiError}</p>}
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Login;
