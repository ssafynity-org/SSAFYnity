import React, { useState } from "react";
import axios from "axios"; // Axios import
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
        const response = await axios.post(`${process.env.REACT_APP_API_URL}/api/member/login`, {
          email,
          password,
        });

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
        {/* 결과 메시지 출력 */}
        {userData && userData.memberId && <p>MemberId: {userData.memberId}</p>}
        {userData && userData.email && <p>Email: {userData.email}</p>}
        {userData && userData.password && <p>Password: {userData.password}</p>}
        {userData && userData.name && <p>Name: {userData.name}</p>}
        {userData && userData.company && <p>Company: {userData.company}</p>}
      </form>
    </div>
  );
}

export default Login;
