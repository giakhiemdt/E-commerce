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
import AccM from "./pages/admin/AccM";
import { useDecodedToken } from "./hooks/useAuth";

const App: React.FC = () => {
  const { decodedToken, loading } = useDecodedToken();

  if (loading) {
    // Có thể hiển thị một loader hoặc một trang trống trong khi loading
    return <div>Loading...</div>; // Hoặc có thể dùng spinner
  }

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route
          path="/login"
          element={decodedToken ? <Navigate to="/home" /> : <Login />}
        />
        <Route
          path="/register"
          element={decodedToken ? <Navigate to="/home" /> : <Register />}
        />
        <Route
          path="/admin/account/*"
          element={
            decodedToken && decodedToken.role === "ADMIN" ? (
              <AccM />
            ) : (
              <Navigate to="/home" />
            )
          }
        />
      </Routes>
    </Router>
  );
};

export default App;
