import React from "react";
import Navbar from "../components/Navbar";
import Subnavbar from "../components/Subnavbar";
import Footer from "../components/Footer";
import LoginForm from "../components/LoginForm";
import styles from "../styles/Page.module.css";

const Login: React.FC = () => {
  const handleLoginSuccess = (data: any) => {
    console.log("User login successfully:", data);
  };

  return (
    <div className={styles.page}>
      <Navbar />
      <Subnavbar />
      <LoginForm onLoginSuccess={handleLoginSuccess} />
      <Footer />
    </div>
  );
};

export default Login;
