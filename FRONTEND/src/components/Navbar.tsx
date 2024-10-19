import React from "react";
import styles from "../styles/Navbar.module.css";
import { IoSearch } from "react-icons/io5";
import { MdOutlineLogin } from "react-icons/md";
import { VscAdd } from "react-icons/vsc";

const Navbar: React.FC = () => {
  return (
    <nav className={`navbar ${styles.navbar} navbar-expand-sm`}>
      <a className={`navbar-brand ${styles["navbar-brand"]}`} href="#">
        MuaV
      </a>{" "}
      <div className={`container-fluid ${styles["container-fluid"]}`}>
        <form className="d-flex">
          <input
            className={`form-control ${styles["search-bar"]}`}
            type="text"
            placeholder="Search"
          />
          <button
            className={`btn btn-primary ${styles["search-button"]}`}
            type="button"
          >
            <div className={styles["btn-edit"]}>
              <IoSearch />
            </div>
          </button>
        </form>
      </div>
      <div className={styles["auth-bar"]}>
        <button
          className={`btn btn-primary ${styles["auth-btn"]}`}
          type="button"
        >
          <div className={` ${styles["btn-edit"]}`}>
            Login
            <MdOutlineLogin className={styles["icon-weight"]} />
          </div>
        </button>
        <button
          className={`btn btn-primary ${styles["auth-btn"]}`}
          type="button"
        >
          <div className={styles["btn-edit"]}>
            Register
            <VscAdd className={styles["icon-weight"]} />
          </div>
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
