import React, { useState } from "react";
import axiosInstance from "../api/axiosInstance"; // 설정한 Axios 인스턴스 불러오기
import { useNavigate } from "react-router-dom"; // useNavigate import
import "../styles/Login.css"; // 스타일 파일

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [userData, setUserData] = useState(""); // 로그인 결과 메시지 저장
  const navigate = useNavigate(); // useNavigate 생성

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Email:", email);
    console.log("Password:", password);

    try {
        const response = await axiosInstance.post(`/api/auth/login`, {
          email,
          password,
        });

        // LoginResponse 객체에서 jwtToken 추출
        const jwtToken = response.data.jwtToken;

        // JWT 토큰을 localStorage에 저장
        localStorage.setItem("jwtToken", jwtToken);


        // 상태로 다른 페이지로 이동
        navigate("/main", { state: response.data }); // 데이터 전달
  
        // 성공 메시지 설정
        setUserData(response.data);
      } catch (error) {
        // 에러 처리
        if (error.response && error.response.status === 401) {
          setUserData("Invalid credentials. Please try again.");
        } else {
          setUserData("An error occurred. Please try again later.");
        }
      }
    };

  return (
    <div className="login-container">
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
        </form>
    </div>
  );
}

export default Login;
