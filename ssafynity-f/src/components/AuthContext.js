import axiosInstance from "../api/axiosInstance";
import React, { createContext, useState, useContext, useEffect } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(null); // 초기값을 null로 설정
    const [loading, setLoading] = useState(true); // 로딩 상태 추가
    const [user, setUser] = useState(null); // 사용자 데이터를 위한 상태

    const login = (userData) => {
        setIsAuthenticated(true)
        setUser(userData); // 사용자 데이터 설정
        console.log(userData);
    };
    
    const logout = () => {
        setIsAuthenticated(false);
        setUser(null); // 사용자 데이터 클리어
        localStorage.removeItem("jwtToken");
    };

    const verifyToken = async () => {
        const token = localStorage.getItem("jwtToken");
        if (!token) {
            setIsAuthenticated(false);
            setLoading(false);
            return;
        }

        try {
            await axiosInstance.get("/api/auth/verify", {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setIsAuthenticated(true); // 토큰 유효 시 로그인 상태 설정
        } catch (error) {
            console.error("Token verification failed", error);
            setIsAuthenticated(false); // 토큰이 유효하지 않으면 로그아웃 상태
            localStorage.removeItem("jwtToken"); // 유효하지 않은 토큰 삭제
        } finally {
            setLoading(false); // 로딩 상태 완료
        }
    };

    useEffect(() => {
        verifyToken();
    }, []);

    return (
        <AuthContext.Provider value={{ isAuthenticated, user, login, logout, loading }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
};
