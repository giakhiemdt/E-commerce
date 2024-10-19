import React from "react";
import styles from "../styles/Footer.module.css";

const Footer: React.FC = () => {
  return (
    <footer className={styles.footer}>
      <div className={`container`}>
        <div className="">
          <div>
            <a className={styles["footer-title"]} href="#">
              MuaV
            </a>
            <a className={styles["footer-title-link"]} href="#">
              New Arrivals
            </a>
            <a className={styles["footer-title-link"]} href="#">
              Special Offers
            </a>
            <a className={styles["footer-title-link"]} href="#">
              Discounts
            </a>
          </div>
          <div className="row">
            <div className={`col-4 ${styles["footer-s-title"]}`}>
              Let you know about us
              <br></br>
              <a className={styles["footer-link"]} href="#">
                About Us
              </a>
              <br></br>
              <a className={styles["footer-link"]} href="#">
                Contact Us
              </a>
              <br></br>
              <a className={styles["footer-link"]} href="#">
                Privacy Policy
              </a>
              <br></br>
              <a className={styles["footer-link"]} href="#">
                Terms and Conditions
              </a>
            </div>

            <div className={`col-4 ${styles["footer-s-title"]}`}>
              Contact Information
              <br></br>
              <a className={styles["footer-link"]} href="#">
                Address: S01.5 VinhomeGrandpark
              </a>
              <br></br>
              <a className={styles["footer-link"]} href="#">
                Phone: 999000111
              </a>
              <br></br>
              <a className={styles["footer-link"]} href="#">
                Email: khiem1371@gmail.com
              </a>
              <br></br>
              <a className={styles["footer-link"]} href="#">
                Facebook: khiemjar
              </a>
            </div>
            <div className={`col-4 ${styles["footer-s-title"]}`}>
              Customer Support
              <br></br>
              <a className={styles["footer-link"]} href="#">
                Online support
              </a>
              <br></br>
              <a className={styles["footer-link"]} href="#">
                Guides
              </a>
              <br></br>
              <a className={styles["footer-link"]} href="#">
                instructional video
              </a>
            </div>
          </div>
        </div>
        <br></br>

        <div className={`col-12 ${styles["footer-text"]}`}>
          Copyright notice: "© 2024 MuaV. All rights reserved."
        </div>
      </div>
    </footer>
  );
};

export default Footer;
