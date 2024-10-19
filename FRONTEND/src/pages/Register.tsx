import React from "react";
import Navbar from "../components/Navbar";
import Subnavbar from "../components/Subnavbar";
import Footer from "../components/Footer";
import styles from "../styles/Register.module.css";

const Register: React.FC = () => {
  return (
    <div className={styles.page}>
      <Navbar />
      <Subnavbar />
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
            />
            <h6 className={styles.magrintop20}></h6>
            <a>Email:</a>
            <input
              className={`form-control ${styles["register-bar"]}`}
              type="email"
              placeholder="Email"
            />
            <h6 className={styles.magrintop20}></h6>
            <a>Password:</a>
            <input
              className={`form-control ${styles["register-bar"]}`}
              type="password"
              placeholder="Password"
            />
            <h6 className={styles.magrintop20}></h6>
            <a>Re-Password:</a>
            <input
              className={`form-control ${styles["register-bar"]}`}
              type="password"
              placeholder="Re-Password"
            />
            <button
              className={`btn btn-primary ${styles["register-button"]}`}
              type="button"
            >
              Register
            </button>
          </form>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Register;
