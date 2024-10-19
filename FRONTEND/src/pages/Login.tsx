import React from "react";
import Navbar from "../components/Navbar";
import Subnavbar from "../components/Subnavbar";
import Footer from "../components/Footer";
import styles from "../styles/Login.module.css";

const Login: React.FC = () => {
  return (
    <div className={styles.page}>
      <Navbar />
      <Subnavbar />
      <div className={`container ${styles.container}`}>
        <h3 className={styles["login-title"]}>Login</h3>
        <br></br>
        <div className={styles["login-form"]}>
          <form>
            <a>User Name:</a>
            <input
              className={`form-control ${styles["login-bar"]}`}
              type="text"
              placeholder="Username"
            />
            <h6 className={styles.magrintop20}></h6>
            <a>Password:</a>
            <input
              className={`form-control ${styles["login-bar"]}`}
              type="password"
              placeholder="Password"
            />
            <button
              className={`btn btn-primary ${styles["login-button"]}`}
              type="button"
            >
              Login
            </button>
          </form>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Login;
