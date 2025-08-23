import { Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import axios from "axios";
import axiosInstance from "../api/axiosInstance";

function isTokenExpired(token) {
  try {
    const { exp } = jwtDecode(token);
    return Date.now() >= exp * 1000;
  } catch {
    return true;
  }
}

async function refreshAccessToken() {
  try {
    const res = await axiosInstance.post("/auth/refresh", {}, { withCredentials: true }); 
    // Refresh Token은 HttpOnly Cookie라 자동 전송됨
    const newAccessToken = res.data;
    console.log("newAccessToken : " , newAccessToken);
    localStorage.setItem("jwtToken", newAccessToken);
    return newAccessToken;
  } catch {
    return null;
  }
}

export default function ProtectedRoute({ children }) {
  let token = localStorage.getItem("jwtToken");

  if (!token || isTokenExpired(token)) {
    console.log("토큰 지워짐");
    return (async () => {
      const newToken = await refreshAccessToken();
      if (newToken) {
        token = newToken;
        return children;
      } else {
        localStorage.removeItem("jwtToken");
        return <Navigate to="/login" replace />;
      }
    })();
  }

  return children;
};
