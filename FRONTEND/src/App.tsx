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
import AccountManagement from "./pages/authenticaiton/admin/AccountManagement";
import { useDecodedToken } from "./hooks/useAuth";
import WebSocketComponent from "./utils/WebSocket";
import AccountProfile from "./pages/authenticaiton/AccountProfile";
import ProductManagement from "./pages/authenticaiton/seller/ProductManagement";

const App: React.FC = () => {
  const { decodedToken, loading } = useDecodedToken();
  const isSellerInfoStatus = sessionStorage.getItem("SellerInfoStatus");

  if (loading) {
    // Hiển thị loader trong khi đợi token được giải mã
    return <div>Loading...</div>;
  }

  // Nếu isSellerInfoStatus tồn tại, chuyển hướng đến trang AccountProfile
  if (isSellerInfoStatus) {
    return (
      <Router>
        <WebSocketComponent />
        <Routes>
          <Route path="/account-profile" element={<AccountProfile />} />
          <Route
            path="*"
            element={<Navigate to="/account-profile" replace />}
          />
        </Routes>
      </Router>
    );
  }

  // Nếu không có SellerInfoStatus, render các routes bình thường
  return (
    <Router>
      <WebSocketComponent />
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
          path="/account-profile"
          element={decodedToken ? <AccountProfile /> : <Navigate to="/home" />}
        />
        <Route
          path="/seller/product/*"
          element={
            decodedToken && decodedToken.role === "SELLER" ? (
              <ProductManagement />
            ) : (
              <Navigate to="/home" />
            )
          }
        />
        <Route
          path="/admin/account/*"
          element={
            decodedToken && decodedToken.role === "ADMIN" ? (
              <AccountManagement />
            ) : (
              <Navigate to="/home" />
            )
          }
        />
        {/* Các route khác */}
      </Routes>
    </Router>
  );
};

export default App;
