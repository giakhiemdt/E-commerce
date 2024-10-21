import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "../../components/Navbar";
import Subnavbar from "../../components/Subnavbar";
import Footer from "../../components/Footer";
import AccList from "../../components/AccList";
import AccEdit from "../../components/AccEdit";
import styles from "../../styles/AccountManage.module.css";

const AccM: React.FC = () => {
  return (
    <div className={styles.page}>
      <Navbar />
      <Subnavbar />
      <Routes>
        <Route path="/" element={<AccList />} />{" "}
        {/* Trường hợp đường dẫn chính */}
        <Route path="/edit/:id" element={<AccEdit />} /> {/* Trường hợp edit */}
      </Routes>
      <Footer />
    </div>
  );
};

export default AccM;
