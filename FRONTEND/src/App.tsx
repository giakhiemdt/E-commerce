import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";

const App: React.FC = () => {
  const isLoggedIn = () => {
    const token = sessionStorage.getItem("token");
    const username = sessionStorage.getItem("username");
    return token && username && username.trim() !== ""; // Kiểm tra username không rỗng
  };

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route
          path="/login"
          element={isLoggedIn() ? <Navigate to="/home" /> : <Login />}
        />
        {/* <Route
          path="/login"
          element={ <Login />}
        /> */}
        <Route
          path="/register"
          element={isLoggedIn() ? <Navigate to="/home" /> : <Register />}
        />
      </Routes>
    </Router>
  );
};

export default App;
