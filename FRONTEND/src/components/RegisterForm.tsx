import React, { useState } from "react";
import { useRegister } from "../hooks/useRegister"; // Import logic xử lý
import styles from "../styles/Register.module.css";

interface RegisterFormProps {
  onRegisterSuccess: (data: any) => void; // Hàm callback để xử lý phản hồi khi đăng ký thành công
}

const RegisterForm: React.FC<RegisterFormProps> = ({ onRegisterSuccess }) => {
  const { handleRegister, error, loading } = useRegister(); // Sử dụng logic từ useRegister
  const [username, setUsername] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [repassword, setRepassword] = useState<string>("");

  const submitHandler = async () => {
    const response = await handleRegister(
      username,
      email,
      password,
      repassword
    );
    if (response) {
      onRegisterSuccess(response); // Gọi hàm callback với dữ liệu phản hồi
    }
  };

  return (
    <div className={styles.page}>
      <div className={`container ${styles.container}`}>
        <h3 className={styles["register-title"]}>Register</h3>
        {error && <p style={{ color: "red" }}>{error}</p>} <br></br>
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
        </div>
      </div>
    </div>
  );
};

export default RegisterForm;
