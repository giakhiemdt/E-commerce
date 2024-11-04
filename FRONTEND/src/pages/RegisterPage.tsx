import React from "react";
import {Navbar} from "../components/Navbar"
import {Register} from "../components/Register"
import Footer from "../components/Footer";

const RegisterPage: React.FC = () => {

  return (
    <div>
      <Navbar />
        <Register/>
      <Footer />
    </div>
  );
};

export default RegisterPage;
