import React, { useState } from "react";
import axiosInstance from "../api/axiosInstance"; // 설정한 Axios 인스턴스 불러오기
import { useNavigate } from "react-router-dom"; // useNavigate import
import "../styles/Login.css"; // 스타일 파일

function SignUp() {
  const [name, setName] = useState("");
  const [company, setCompany] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [userData, setUserData] = useState(""); // 회원가입 결과 메시지 저장
  
  const navigate = useNavigate(); // useNavigate 생성

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Name:", name);
    console.log("Company:", company);
    console.log("Email:", email);
    console.log("Password:", password);

    try {
        const response = await axiosInstance.post(`/api/member`, {
          name,
          company,
          email,
          password,
        });

        // 상태로 다른 페이지로 이동
        navigate("/main"); // 데이터 전달
  
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
        <div className="signup-container">
          <h1>SignUp</h1>
          <form onSubmit={handleSubmit}>
          <div className="form-group">
              <label htmlFor="name">Name</label>
              <input
                type="name"
                id="name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="company">Company</label>
              <input
                type="company"
                id="company"
                value={company}
                onChange={(e) => setCompany(e.target.value)}
                required
              />
            </div>
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
            <button type="submit">SignUp</button>
            </form>
        </div>
      );
}

export default SignUp;
