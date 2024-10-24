import React from "react";
import Navbar from "../../components/Navbar";
import Subnavbar from "../../components/Subnavbar";
import Footer from "../../components/Footer";
import ProfileEdit from "../../components/authentication/EditProfile";

const AccountProfile: React.FC = () => {
  return (
    <div>
      <Navbar />
      <Subnavbar />
      <ProfileEdit />
      <Footer />
    </div>
  );
};

export default AccountProfile;
