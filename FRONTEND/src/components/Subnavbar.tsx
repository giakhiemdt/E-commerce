import React from "react";
import styles from "../styles/Subnavbar.module.css";
import { useDecodedToken } from "../hooks/useAuth";

const Subnavbar: React.FC = () => {
  const { decodedToken } = useDecodedToken();

  return (
    <nav
      className={`navbar ${styles.navbar} navbar-expand-sm ${styles["navbar-expand-sm"]} sticky-top`}
    >
      <div className="container-fluid ">
        <ul className="navbar-nav">
          <li className="nav-item">
            <a className={`nav-link ${styles["nav-link"]}`} href="#">
              Top
            </a>
          </li>
          <li className="nav-item">
            <a className={`nav-link ${styles["nav-link"]}`} href="#">
              Today Sale
            </a>
          </li>
          <li className="nav-item">
            <a className={`nav-link ${styles["nav-link"]}`} href="#">
              Category
            </a>
          </li>
          <li className="nav-item">
            <a className={`nav-link ${styles["nav-link"]}`} href="#">
              Customer Service
            </a>
          </li>
        </ul>
        {decodedToken ? (
          <h5 className={styles["hello-message"]}>Hello, {decodedToken.sub}</h5>
        ) : null}
      </div>
    </nav>
  );
};

export default Subnavbar;
