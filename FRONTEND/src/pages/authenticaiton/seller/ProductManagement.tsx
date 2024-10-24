import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "../../../components/Navbar";
import Subnavbar from "../../../components/Subnavbar";
import Footer from "../../../components/Footer";
import ProductList from "../../../components/authentication/seller/ProductList";
import AccountEdit from "../../../components/authentication/admin/AccountEdit";
import styles from "../../../styles/AccountManage.module.css";

const ProductManagement: React.FC = () => {
  return (
    <div className={styles.page}>
      <Navbar />
      <Subnavbar />
      <Routes>
        <Route path="/" element={<ProductList />} />{" "}
        {/* Trường hợp đường dẫn chính */}
        <Route path="/edit/:id" element={<AccountEdit />} />{" "}
        {/* Trường hợp edit */}
      </Routes>
      <Footer />
    </div>
  );
};

export default ProductManagement;
