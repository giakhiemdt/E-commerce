import React, { useEffect } from "react";
import Navbar from "../components/Navbar";
import Subnavbar from "../components/Subnavbar";
import Footer from "../components/Footer";
import { decodeToken } from "../utils/jwt";

const Home: React.FC = () => {
  useEffect(() => {
    const token = sessionStorage.getItem("token");

    if (token) {
      const decoded = decodeToken(token);

      if (decoded) {
        console.log("Decoded Token:", decoded);
        console.log("User Role:", decoded.role); // Lấy vai trò từ token
        console.log("User Name (sub):", decoded.sub); // Lấy tên từ token
        console.log("Issued At (iat):", new Date(decoded.iat * 1000)); // Thời gian phát hành
        console.log("Expiration (exp):", new Date(decoded.exp * 1000)); // Thời gian hết hạn
      } else {
        console.error("Token is invalid or could not be decoded");
      }
    }
  }, []); // Chỉ chạy một lần khi component được mount

  return (
    <div>
      <Navbar />
      <Subnavbar />
      <Footer />
    </div>
  );
};

export default Home;
