import React from "react";
import Footer from "../components/Footer";
import {Navbar} from "../components/Navbar";
import {Login} from "../components/Login";

const LoginPage: React.FC = () => {

  return (
    <div>
      <Navbar />
        <Login/>
      <Footer />
    </div>
  );
};

export default LoginPage;
