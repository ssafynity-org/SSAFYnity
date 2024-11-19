import React, { useState } from "react";
import axios from "axios"; // Axios import
import "../styles/Login.css"; // 스타일 파일

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState(""); // 로그인 결과 메시지 저장

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Email:", email);
    console.log("Password:", password);

    try {
        const response = await axios.post("http://localhost:8080/member/login", {
          email,
          password,
        });
  
        // 성공 메시지 설정
        setMessage(response.data);
      } catch (error) {
        // 에러 처리
        if (error.response && error.response.status === 401) {
          setMessage("Invalid credentials. Please try again.");
        } else {
          setMessage("An error occurred. Please try again later.");
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
        {message && message.memberId && <p>MemberId: {message.memberId}</p>}
        {message && message.email && <p>Email: {message.email}</p>}
        {message && message.password && <p>Password: {message.password}</p>}
        {message && message.name && <p>Name: {message.name}</p>}
        {message && message.company && <p>Company: {message.company}</p>}
      </form>
    </div>
  );
}

export default Login;
