import React, { useState } from "react";
import axios from "../api/axiosInstance"; // 가정된 axios 인스턴스 경로
import { useNavigate } from "react-router-dom";
import "../styles/SignUp.css";

function Signup() {
  const [name, setName] = useState("");
  const [company, setCompany] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [profileImage, setProfileImage] = useState(null);
  const [userData, setUserData] = useState("");

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    // JSON 객체를 문자열로 변환한 뒤 Blob 객체로 변환하여 FormData에 추가
    const jsonBlob = new Blob([JSON.stringify({
      name: name,
      company: company,
      email: email,
      password: password
    })], { type: 'application/json' });

    formData.append("member", jsonBlob);

    if (profileImage) {
      formData.append("file", profileImage);
    }


    try {
      const response = await axios.post("/api/member/signup/multipart", formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      console.log(response.data);
      setUserData("Signup successful!");
      navigate("/login");
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setUserData("Invalid credentials. Please try again.");
      } else {
        setUserData("An error occurred. Please try again later.");
      }
    }
  };

  const handleFileChange = (e) => {
    setProfileImage(e.target.files[0]);
  };

  return (
    <div className="signup-container">
      <img src="/images/bigLogo.png" alt="Logo" className="signup-logo" />
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="company">Company</label>
          <input
            type="text"
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
        <div className="form-group">
          <label htmlFor="file">Profile Image</label>
          <input
            type="file"
            id="file"
            onChange={handleFileChange}
          />
        </div>
        <button type="submit">SignUp</button>
        {userData && <p>{userData}</p>}
      </form>
    </div>
  );
}

export default Signup;
