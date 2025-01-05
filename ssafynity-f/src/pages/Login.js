import React, { useState } from "react";
import axiosInstance from "../api/axiosInstance"; // 설정한 Axios 인스턴스 불러오기
import { useAuth } from '../components/AuthContext';
import { useNavigate } from "react-router-dom"; // useNavigate import
import "../styles/Login.css"; // 스타일 파일

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState(""); // 에러 메시지를 위한 상태
  const { login } = useAuth(); // useAuth 훅에서 login 메서드 가져오기
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

        // 인증 상태를 업데이트 (AuthContext의 login 메서드 호출)
        login();


        // 메인 페이지로 이동하면서 서버 응답 데이터 전달
        navigate("/main", { state: response.data }); // 데이터 전달
  
      } catch (error) {
        // 에러 처리
        if (error.response && error.response.status === 401) {
          setErrorMessage("Invalid credentials. Please try again.");
        } else {
          setErrorMessage("An error occurred. Please try again later.");
        }
      }
    };

  return (
    <div className="login-page">
      <div className="login-container">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">아이디</label>
            <input
              type="email"
              id="email"
              placeholder="ID"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">비밀번호</label>
            <input
              type="password"
              id="password"
              placeholder="PASSWORD"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <button type="submit">로그인</button>
          </div>
          {/* 에러메세지 출력 */}
          {errorMessage && <p>{errorMessage}</p>} 
          </form>
      </div>
    </div>
  );
}

export default Login;
