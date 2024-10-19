import React from "react";
import Navbar from "../components/Navbar";
import Subnavbar from "../components/Subnavbar";
import Footer from "../components/Footer";
import RegisterForm from "../components/RegisterForm";
import styles from "../styles/Page.module.css";

const Register: React.FC = () => {
  // Hàm xử lý phản hồi khi đăng ký thành công
  const handleRegisterSuccess = (data: any) => {
    console.log("User registered successfully:", data);
    // Xử lý thêm, như chuyển hướng đến trang khác hoặc hiển thị thông báo
  };

  return (
    <div className={styles.page}>
      <Navbar />
      <Subnavbar />
      <RegisterForm onRegisterSuccess={handleRegisterSuccess} />
      <Footer />
    </div>
  );
};

export default Register;
