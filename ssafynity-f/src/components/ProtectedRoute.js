import { Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

function isTokenExpired(token) {
  try {
    const { exp } = jwtDecode(token);
    return Date.now() >= exp * 1000;
  } catch {
    return true;
  }
}

export default function ProtectedRoute({ children }) {
  const token = localStorage.getItem("jwtToken");
  console.log("토큰값 : " + token);
  if (!token || isTokenExpired(token)) {
    localStorage.removeItem("jwtToken");
    return <Navigate to="/login" replace />;
  }
  return children;
};
