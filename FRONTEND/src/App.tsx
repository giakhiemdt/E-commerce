import React from "react";
import Navbar from "./components/Navbar";
import Subnavbar from "./components/Subnavbar";
import Footer from "./components/Footer";

const App: React.FC = () => {
  return (
    <div>
      <Navbar />
      <Subnavbar />
      <Footer />
    </div>
  );
};

export default App;
