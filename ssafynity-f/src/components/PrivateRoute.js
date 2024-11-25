import React from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

const PrivateRoute = ({ children }) => {
    const { isAuthenticated, loading } = useAuth();

    // 로딩 중일 때 아무것도 렌더링하지 않음
    if (loading) {
        return <div>Loading...</div>;
    }

    // 인증되지 않은 경우 로그인 페이지로 리다이렉트
    return isAuthenticated ? children : <Navigate to="/login" replace />;
};

export default PrivateRoute;
