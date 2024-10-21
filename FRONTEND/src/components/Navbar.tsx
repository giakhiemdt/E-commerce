import React from "react";
import styles from "../styles/Navbar.module.css";
import { IoSearch } from "react-icons/io5";
import { MdOutlineLogin } from "react-icons/md";
import { VscAdd } from "react-icons/vsc";
import { useNavigate } from "react-router-dom";
import { MdOutlineAccountBox } from "react-icons/md";
import { useDecodedToken } from "../hooks/useAuth";
import { useLogout } from "../hooks/useAuth";

const Navbar: React.FC = () => {
  const navigate = useNavigate();
  const { decodedToken } = useDecodedToken();

  const handleLogout = async () => {
    const response = await useLogout();
    if (response) {
      navigate("/login");
    }
  };

  return (
    <nav className={`navbar ${styles.navbar} navbar-expand-sm`}>
      <a
        className={`navbar-brand ${styles["navbar-brand"]}`}
        onClick={() => navigate("/home")}
      >
        MuaV
      </a>
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
        {decodedToken ? (
          <>
            <li className={`nav-item dropdown ${styles.dropdown}`}>
              <a
                className={`nav-link ${styles["dropdown-toggle"]}`}
                href="#"
                role="button"
                data-bs-toggle="dropdown"
              >
                <MdOutlineAccountBox className={styles["icon-large"]} />
              </a>
              <ul
                className={`dropdown-menu ${styles["dropdown-menu"]} ${styles["dropdown-menu-start"]}`}
              >
                <li>
                  <a className="dropdown-item" href="#">
                    Account
                  </a>
                </li>
                {decodedToken.role === "CUSTOMER" ? (
                  <>
                    <li>
                      <a className="dropdown-item" href="#">
                        Cart
                      </a>
                    </li>
                    <li>
                      <a className="dropdown-item" href="#">
                        Sale
                      </a>
                    </li>
                    <li>
                      <a className="dropdown-item" href="#">
                        Order History
                      </a>
                    </li>
                  </>
                ) : decodedToken.role === "SELLER" ? (
                  <>
                    <li>
                      <a className="dropdown-item" href="#">
                        My Product
                      </a>
                    </li>
                    <li>
                      <a className="dropdown-item" href="#">
                        Current Order
                      </a>
                    </li>
                    <li>
                      <a className="dropdown-item" href="#">
                        Order History
                      </a>
                    </li>
                  </>
                ) : decodedToken.role === "ADMIN" ? (
                  <>
                    <li>
                      <a className="dropdown-item" href="#">
                        Dash Board
                      </a>
                    </li>
                    <li>
                      <a
                        className="dropdown-item"
                        onClick={() => navigate("/admin/account")}
                      >
                        Account Manage
                      </a>
                    </li>
                    <li>
                      <a className="dropdown-item" href="#">
                        Product Manage
                      </a>
                    </li>
                    <li>
                      <a className="dropdown-item" href="#">
                        Support
                      </a>
                    </li>
                  </>
                ) : null}

                <li>
                  <hr className={styles["horizontal-line"]} />
                  <a className={`dropdown-item`} onClick={handleLogout}>
                    Logout
                  </a>
                </li>
              </ul>
            </li>
          </>
        ) : (
          <>
            <button
              className={`btn btn-primary ${styles["auth-btn"]}`}
              type="button"
              onClick={() => navigate("/login")}
            >
              <div className={` ${styles["btn-edit"]}`}>
                Login
                <MdOutlineLogin className={styles["icon-weight"]} />
              </div>
            </button>
            <button
              className={`btn btn-primary ${styles["auth-btn"]}`}
              type="button"
              onClick={() => navigate("/register")}
            >
              <div className={styles["btn-edit"]}>
                Register
                <VscAdd className={styles["icon-weight"]} />
              </div>
            </button>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
