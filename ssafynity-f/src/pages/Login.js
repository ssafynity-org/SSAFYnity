import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { login } from "../redux/userSlice";
import axiosInstance from "../api/axiosInstance";
import { useNavigate } from "react-router-dom";
import "../styles/Login.css";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axiosInstance.post(`/api/auth/login`, { email, password });
      const jwtToken = response.data.jwtToken;
      localStorage.setItem("jwtToken", jwtToken);

      const userDataResponse = await axiosInstance.get(`/api/member/login`);
      dispatch(login(userDataResponse.data)); // Redux Store에 저장

      navigate("/main");
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setErrorMessage("Invalid credentials. Please try again.");
      } else {
        setErrorMessage("An error occurred. Please try again later.");
      }
    }
  };

  const handleNavigateToSignUpPage = () => {
    navigate("/signup");
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
            <button type="button" onClick={handleNavigateToSignUpPage}>회원가입</button>
          </div>
          {errorMessage && <p>{errorMessage}</p>}
        </form>
      </div>
    </div>
  );
}

export default Login;
